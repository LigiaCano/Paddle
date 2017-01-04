package config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import business.controllers.JobCompletionNotificationListener;
import business.controllers.TokenTasklet;
import business.controllers.StepCompletionNotificationListener;

@Configuration
@EnableBatchProcessing
public class BatchConfig  {

	@Autowired
	DataSource dataSource;
	
	@Autowired	
	PlatformTransactionManager transactionManager;

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Bean
	public Step stepToken() {
		return stepBuilderFactory.get("stepToken").tasklet(tasklet()).allowStartIfComplete(true)
				.listener(stepListener()).build();
	}

	@Bean
	public Job job(Step stepToken) throws Exception {
		return jobBuilderFactory.get("jobToken").incrementer(new RunIdIncrementer()).start(stepToken)
				.listener(jobListener()).build();
	}

	@Bean
	public Tasklet tasklet() {
		return new TokenTasklet();
	}

	@Bean
	public StepExecutionListener stepListener() {
		return new StepCompletionNotificationListener();
	}

	@Bean
	public JobExecutionListener jobListener() {
		return new JobCompletionNotificationListener();
	}

	public JobRepository jobRepository() throws Exception {
		JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
		factory.setDataSource(dataSource);
		factory.setTransactionManager(transactionManager);
		factory.setDatabaseType("mysql");
		factory.afterPropertiesSet();
		factory.setIsolationLevelForCreate("ISOLATION_DEFAULT");
		return (JobRepository) factory.getObject();
	}

	@Bean
	public TaskExecutor taskExecutor() {
		SimpleAsyncTaskExecutor ex = new SimpleAsyncTaskExecutor();
		ex.setConcurrencyLimit(1);
		return ex;
	}

	@Bean
	public JobLauncher jobLauncher(JobRepository jobRepository) throws Exception {
		SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
		jobLauncher.setJobRepository(jobRepository);
		jobLauncher.setTaskExecutor(taskExecutor());
		return jobLauncher;
	}



}

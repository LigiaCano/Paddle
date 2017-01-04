package business.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@EnableScheduling
@RestController
@RequestMapping(Uris.SERVLET_MAP + Uris.BATCH)
public class JobLauncherResource {
	private static final Logger log = LoggerFactory.getLogger(JobLauncherResource.class);
	private final String blankSpace = " ";
	private final String everyTenSecond = "*/10";
	private final String everyMinute = "*";
	private final String everyHour = "*";
	private final String everyMounth = "*";
	private final String everyDayOfTheMonth = "*";
	private final String everyDayOfTheWeek = "*";

	@Autowired
	JobLauncher jobLauncher;

	@Autowired
	Job job;

//	@Scheduled(cron = everyTenSecond + blankSpace + everyMinute + blankSpace + everyHour + blankSpace + everyMounth
//			+ blankSpace + everyDayOfTheMonth + blankSpace + everyDayOfTheWeek + blankSpace)
	@RequestMapping(value = Uris.JOB, method = RequestMethod.GET)
	public void launch() throws JobExecutionAlreadyRunningException, JobRestartException,
			JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
				.toJobParameters();

		JobExecution execution = jobLauncher.run(job, jobParameters);
		log.info("Exit Status : " + execution.getStatus());

	}
}

package business.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;

public class JobCompletionNotificationListener extends JobExecutionListenerSupport {
	private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);
	@Override
	public void beforeJob(JobExecution jobExecution) {
		log.info("!!! JOB Before! Time this execution started " + jobExecution.getStartTime());
		log.info("JOB Before " + jobExecution.getExitStatus());
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		log.info("!!! JOB FINISHED! Time that this execution ended" + jobExecution.getEndTime());
		log.info("JOB After " + jobExecution.getExitStatus());
	}

}

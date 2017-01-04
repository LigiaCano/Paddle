package business.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;

public class StepCompletionNotificationListener extends StepExecutionListenerSupport {
	private static final Logger log = LoggerFactory.getLogger(StepCompletionNotificationListener.class);

	@Override
	public void beforeStep(StepExecution stepExecution) {
		log.info("!!! STEP Before! Time this execution started " + stepExecution.getStartTime());
		log.info("STEP Before " + stepExecution.getExitStatus());
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		log.info("!!! STEP FINISHED! Time that this execution ended " + stepExecution.getEndTime());
		log.info("STEP After " + stepExecution.getExitStatus());
		return stepExecution.getExitStatus();
	}
}

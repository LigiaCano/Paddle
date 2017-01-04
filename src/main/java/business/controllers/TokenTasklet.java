package business.controllers;

import java.util.Calendar;

import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

import data.daos.TokenDao;

public class TokenTasklet implements Tasklet {
	
	private static final Logger log = LoggerFactory.getLogger(TokenTasklet.class);
	private TokenDao tokenDao;

	@Autowired
	public void setTokenDao(TokenDao tokenDao) {
		this.tokenDao = tokenDao;
	}

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		tokenDao.deleteExpiredDate(Calendar.getInstance());
		log.info("Tokens eliminados");
		return RepeatStatus.FINISHED;
	}

}

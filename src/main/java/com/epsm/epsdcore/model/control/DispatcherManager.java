package com.epsm.epsdcore.model.control;

import com.epsm.epsdcore.model.Dispatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DispatcherManager {

	@Autowired
	private SchedulesProcessingRunner runner;

	@Autowired
	private Dispatcher dispatcher;

	private static final Logger logger = LoggerFactory.getLogger(DispatcherManager.class);

	@PostConstruct
	public Dispatcher createAndRun(){
		runner.runDispatcher(dispatcher);
		logger.info("Dispatcher created and run.");
		
		return dispatcher;
	}
}

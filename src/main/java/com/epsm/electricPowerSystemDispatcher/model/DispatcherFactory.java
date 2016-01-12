package com.epsm.electricPowerSystemDispatcher.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.epsm.electricPowerSystemDispatcher.model.DispatcherImpl;
import com.epsm.electricPowerSystemDispatcher.model.control.RealTimeOperationsRunner;
import com.epsm.electricPowerSystemModel.model.dispatch.Dispatcher;
import com.epsm.electricPowerSystemModel.model.generalModel.TimeService;

@Component
public class DispatcherFactory {
	private DispatcherImpl dispatcher;
	private RealTimeOperationsRunner runner;
	private TimeService timeService;
	private Logger logger;
	
	@Autowired
	public DispatcherFactory(TimeService timeService){
		this.timeService = timeService;
		runner = new RealTimeOperationsRunner();
		logger = LoggerFactory.getLogger(PowerObjectManagerStub.class);
	}
	
	@Bean
	public Dispatcher createDispatcher(){
		createNewDispatcher();
		runDispatcher();
		
		logger.info("DispatcherImpl created and run.");
		
		return dispatcher;
	}
	
	private void createNewDispatcher(){
		dispatcher = new DispatcherImpl(timeService);
	}
	
	private void runDispatcher(){
		runner.runDispatcher(dispatcher);
	}
}

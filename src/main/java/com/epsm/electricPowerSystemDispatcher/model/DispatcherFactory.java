package com.epsm.electricPowerSystemDispatcher.model;

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
	
	@Autowired
	public DispatcherFactory(TimeService timeService){
		this.timeService = timeService;
		runner = new RealTimeOperationsRunner();
	}
	
	@Bean
	public Dispatcher createDispatcher(){
		createNewDispatcher();
		runDispatcher();
		
		return dispatcher;
	}
	
	private void createNewDispatcher(){
		dispatcher = new DispatcherImpl(timeService);
	}
	
	private void runDispatcher(){
		runner.runDispatcher(dispatcher);
	}
}

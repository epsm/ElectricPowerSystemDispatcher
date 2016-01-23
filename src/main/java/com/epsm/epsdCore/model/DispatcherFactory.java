package com.epsm.epsdCore.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epsm.epsdCore.model.control.RealTimeOperationsRunner;
import com.epsm.epsmCore.model.dispatch.Dispatcher;
import com.epsm.epsmCore.model.generalModel.TimeService;

public class DispatcherFactory {
	private DispatcherImpl dispatcher;
	private RealTimeOperationsRunner runner;
	private StateSaver saver;
	private ObjectsConnector connector;
	private PowerObjectManagerStub manager;
	private Logger logger;
	
	public DispatcherFactory(TimeService timeService, StateSaver saver, ObjectsConnector connector){
		logger = LoggerFactory.getLogger(DispatcherFactory.class);
		
		if(timeService == null){
			logger.error("Null TimeService in constructor.");
			String message = "DispatcherFactory constructor: timeService must not be null.";
			throw new IllegalArgumentException(message);
		}else if(saver == null){
			logger.error("Null StateSaver in constructor.");
			String message = "DispatcherFactory constructor: saver must not be null.";
			throw new IllegalArgumentException(message);
		}else if(connector == null){
			logger.error("Null ObjectsConnector in constructor.");
			String message = "DispatcherFactory constructor: connector must not be null.";
			throw new IllegalArgumentException(message);
		}
		
		this.saver = saver;
		this.connector = connector;
		runner = new RealTimeOperationsRunner();
		manager = new PowerObjectManagerStub(timeService);
	}
	
	public Dispatcher createDispatcher(){
		createNewDispatcher();
		runDispatcher();
		
		logger.info("DispatcherImpl created and run.");
		
		return dispatcher;
	}
	
	private void createNewDispatcher(){
		dispatcher = new DispatcherImpl(manager, saver, connector);
	}
	
	private void runDispatcher(){
		runner.runDispatcher(dispatcher);
	}
}

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
	private PowerObjectsDateTimeSource dateTimeSource;
	private PowerObjectManagerStub manager;
	private Logger logger;
	
	public DispatcherFactory(TimeService timeService, StateSaver saver, ObjectsConnector connector, 
			PowerObjectsDateTimeSource dateTimeSource){
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
		}else if(dateTimeSource == null){
			logger.error("Null PowerObjectsDateTimeSource in constructor.");
			String message = "DispatcherFactory constructor: dateTimeSource must not be null.";
			throw new IllegalArgumentException(message);
		}
		
		this.saver = saver;
		this.connector = connector;
		this.dateTimeSource = dateTimeSource;
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
		dispatcher = new DispatcherImpl(manager, saver, connector, dateTimeSource);
	}
	
	private void runDispatcher(){
		runner.runDispatcher(dispatcher);
	}
}

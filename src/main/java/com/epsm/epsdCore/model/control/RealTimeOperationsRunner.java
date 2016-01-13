package com.epsm.epsdCore.model.control;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epsm.epsmCore.model.generalModel.RealTimeOperations;

public class RealTimeOperationsRunner{
	private RealTimeOperations object;
	private final int PAUSE_BETWEEN_REAL_TIME_STEPS_IN_MS = 500;
	private Logger logger;
	
	public void runDispatcher(RealTimeOperations object){
		logger = LoggerFactory.getLogger(RealTimeOperationsRunner.class);
		
		if(object == null){
			logger.error("Attempt to run null object.");
			throw new IllegalArgumentException("RealTimeOperationsRunner: object must not"
					+ " be null.");
		}
		
		this.object = object;
		runObject();
		
		logger.info("{} run.", object);
	}
	
	private void runObject(){
		Runnable object = new RealTimeRunner();
		Thread objectThread = new Thread(object);
		
		objectThread.start();
	}
	
	private class RealTimeRunner implements Runnable{
		
		@Override
		public void run() {
			Thread.currentThread().setName("Real time");
			
			while(true){
				object.doRealTimeDependingOperations();
				
				logger.debug("Step performed.");
				
				pause();
			}
		}
		
		private void pause(){
			if(PAUSE_BETWEEN_REAL_TIME_STEPS_IN_MS != 0){
				try {
					Thread.sleep(PAUSE_BETWEEN_REAL_TIME_STEPS_IN_MS);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
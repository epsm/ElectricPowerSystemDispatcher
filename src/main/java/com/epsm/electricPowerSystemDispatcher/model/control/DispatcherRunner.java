package com.epsm.electricPowerSystemDispatcher.model.control;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epsm.electricPowerSystemDispatcher.model.DispatcherImpl;

public class DispatcherRunner{
	private DispatcherImpl dispatcher;
	private Logger logger = LoggerFactory.getLogger(DispatcherRunner.class);
	private final int PAUSE_BETWEEN_REAL_TIME_STEPS_IN_MS = 500;
	
	public void runDispatcher(DispatcherImpl dispatcher){
		if(dispatcher == null){
			logger.error("DispatcherRunner: attempt to run null dispatcher.");
			throw new IllegalArgumentException("DispatcherRunner: dispatcher must not be null.");
		}
		
		this.dispatcher = dispatcher;
		
		runSimulation();
		
		logger.info("Simulation was created and run.");
	}
	
	private void runSimulation(){
		Runnable realTimeOperations = new RealTimeRunner();
		Thread runningRealTime = new Thread(realTimeOperations);
		
		runningRealTime.start();
	}
	
	private class RealTimeRunner implements Runnable{
		
		@Override
		public void run() {
			Thread.currentThread().setName("Real time");
			
			while(true){
				dispatcher.doRealTimeDependingOperations();
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

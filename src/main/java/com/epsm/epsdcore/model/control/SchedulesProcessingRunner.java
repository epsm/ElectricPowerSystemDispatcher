package com.epsm.epsdcore.model.control;

import com.epsm.epsdcore.model.Constants;
import com.epsm.epsdcore.model.Dispatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SchedulesProcessingRunner {

	private static final Logger logger = LoggerFactory.getLogger(SchedulesProcessingRunner.class);
	
	public void runDispatcher(Dispatcher dispatcher){
		Runnable runnable = new RealTimeRunner(dispatcher);
		Thread dispatcherThread = new Thread(runnable);

		dispatcherThread.start();
		logger.info("Dispatcher run.");
	}
	
	private class RealTimeRunner implements Runnable{

		private int stepCounter;
		private Dispatcher dispatcher;

		RealTimeRunner(Dispatcher dispatcher) {
			this.dispatcher = dispatcher;
		}

		@Override
		public void run() {
			Thread.currentThread().setName("Real time");
			
			while(true){
				dispatcher.processSchedules();
				
				if(stepCounter++ > Constants.LOG_EVERY_REALTIME_STEPS){
					logger.debug("{} step performed.", Constants.LOG_EVERY_REALTIME_STEPS);
					resetCounter();
				}
				
				pause();
			}
		}
		
		private void resetCounter(){
			stepCounter = 1;
		}
		
		private void pause(){
			try {
				Thread.sleep(Constants.PAUSE_BETWEEN_REAL_TIME_STEPS_IN_MS);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}
}

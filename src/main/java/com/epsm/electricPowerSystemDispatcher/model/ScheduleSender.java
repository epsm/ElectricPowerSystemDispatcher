package com.epsm.electricPowerSystemDispatcher.model;

import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScheduleSender {
	private DispatcherStub dispatcher;
	private int powerStationNumber;
	private Timer sendingTimer;
	private SendScheduleTask task;
	private Logger logger;
	private final int DELAY_BEFORE_SENDING_REPORTS = 0;
	private final int PAUSE_BETWEEN_SENDING_IN_MILLISECONDS = 10_000;
	
	public ScheduleSender(DispatcherStub disppacther, int powerStationNumber){
		this.dispatcher = disppacther;
		this.powerStationNumber = powerStationNumber;
		task = new SendScheduleTask();
		logger = LoggerFactory.getLogger(ScheduleSender.class);
	}
	
	public void startSending(){
		createTimer();
		scheduletSending();
		logger.info("Dispatcher will be sending reports to powerstation №{}.", powerStationNumber);
	}
	
	private void createTimer(){
		sendingTimer = new Timer();
	}
	
	private void scheduletSending(){
		sendingTimer.schedule(task, DELAY_BEFORE_SENDING_REPORTS, PAUSE_BETWEEN_SENDING_IN_MILLISECONDS);
	}

	private class SendScheduleTask extends TimerTask{
		
		@Override
		public void run(){
			setThreadName();
			sendScheduleToPowerStation();
		}
		
		private void setThreadName(){
			Thread.currentThread().setName("sender");
		}
		
		private void sendScheduleToPowerStation(){
			dispatcher.sendGenerationScheduleToPowerStation(powerStationNumber);
		}
	}
}

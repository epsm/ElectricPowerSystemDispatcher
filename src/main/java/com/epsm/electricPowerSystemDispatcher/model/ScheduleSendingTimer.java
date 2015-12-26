package com.epsm.electricPowerSystemDispatcher.model;

import java.util.Timer;
import java.util.TimerTask;

public class ScheduleSendingTimer {
	private DispatcherStub dispatcher;
	private int powerStationNumber;
	private Timer sendingTimer;
	private SendScheduleTask task;
	private final int DELAY_BEFORE_SENDING_REPORTS = 0;
	private final int PAUSE_BETWEEN_SENDING_IN_MILLISECONDS = 10_000;
	
	public ScheduleSendingTimer(DispatcherStub disppacther, int powerStationNumber){
		this.dispatcher = disppacther;
		this.powerStationNumber = powerStationNumber;
		task = new SendScheduleTask();
	}
	
	public void turnOn(){
		createTimer();
		scheduletSending();
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
			Thread.currentThread().setName("schedule timer " + powerStationNumber);
		}
		
		private void sendScheduleToPowerStation(){
			dispatcher.sendGenerationScheduleToPowerStation(powerStationNumber);
		}
	}
}
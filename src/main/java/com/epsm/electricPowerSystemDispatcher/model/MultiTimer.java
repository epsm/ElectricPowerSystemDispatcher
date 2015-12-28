package com.epsm.electricPowerSystemDispatcher.model;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.epsm.electricPowerSystemModel.model.generalModel.TimeService;
import com.epsm.electricPowerSystemModel.model.generalModel.TimeServiceConsumer;

public class MultiTimer implements TimeServiceConsumer{
	private ConcurrentHashMap<Long, LocalDateTime> timers;
	private TimeService timeService;
	
	public MultiTimer(TimeService timeService) {
		timers = new ConcurrentHashMap<Long, LocalDateTime>();
		this.timeService = timeService;
	}
	
	public void startOrUpdateDelayOnTimeNumber(long timerNumber){
		LocalDateTime currentTime = timeService.getCurrentTime();
		timers.put(timerNumber, currentTime);
	}
	
	public Set<Long> getActiveTimers(){
		return timers.keySet();
	}
	
	@Override
	public void doRealTimeDependOperation() {
		manageTimers();
	}
	
	private void manageTimers(){
		for(Long timerNumber: timers.keySet()){
			manageTimer(timerNumber);
		}
	}
	
	private void manageTimer(long timerNumber){
		if(isTimeLeft(timerNumber)){
			deleteTimer(timerNumber);
		}
	}

	private boolean isTimeLeft(long timerNumber){
		LocalDateTime currentTime = timeService.getCurrentTime();
		LocalDateTime lastSetTime = timers.get(timerNumber);
		
		return lastSetTime.plusSeconds(Constants.ACCEPTABLE_PAUSE_BETWEEN_RECEIVED_MESSAGES_IN_SECONDS)
				.isBefore(currentTime);
	}
	
	private void deleteTimer(long timerNumber){
		timers.remove(timerNumber);
	}

	public boolean isTimerActive(long timerNumber) {
		return timers.containsKey(timerNumber);
	}
}

package com.epsm.electricPowerSystemDispatcher.model;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.epsm.electricPowerSystemModel.model.generalModel.TimeService;

public class MultiTimer{
	private Map<Long, LocalDateTime> timers;
	private TimeService timeService;
	private int delayInSeconds;
	
	public MultiTimer(TimeService timeService, int delayInSeconds) {
		timers = new ConcurrentHashMap<Long, LocalDateTime>();
		this.timeService = timeService;
		this.delayInSeconds = delayInSeconds;
	}
	
	public void startOrUpdateDelayOnTimeNumber(long timerNumber){
		LocalDateTime currentTime = timeService.getCurrentTime();
		timers.put(timerNumber, currentTime);
	}
	
	public Set<Long> getActiveTimers(){
		return timers.keySet();
	}
	
	public void manageTimers() {
		manageEveryTimerTimers();
	}
	
	private void manageEveryTimerTimers(){
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
		
		return lastSetTime.plusSeconds(delayInSeconds).isBefore(currentTime);
	}
	
	private void deleteTimer(long timerNumber){
		timers.remove(timerNumber);
	}

	public boolean isTimerActive(long timerNumber) {
		return timers.containsKey(timerNumber);
	}
}

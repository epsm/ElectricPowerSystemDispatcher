package com.epsm.electricPowerSystemDispatcher.model;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.epsm.electricPowerSystemModel.model.generalModel.TimeService;

public class ActiveConnectionContainer {
	private ConcurrentHashMap<Integer, LocalDateTime> activeConnections;
	private TimeService timeService;
		
	public ActiveConnectionContainer(TimeService timeService) {
		activeConnections = new ConcurrentHashMap<Integer, LocalDateTime>();
		this.timeService = timeService;
	}
	
	public void addOrUpdateConnection(int connectionNumber){
		LocalDateTime currentTime = timeService.getCurrentTime();
		activeConnections.put(connectionNumber, currentTime);
	}
	
	public Set<Integer> getActiveConnections(){
		return activeConnections.keySet();
	}
	
	public void manageConnections(){
		for(Integer connectionNumber: activeConnections.keySet()){
			manageConnection(connectionNumber);
		}
	}
	
	private void manageConnection(int connectionNumber){
		if(isConnectionOutOfTime(connectionNumber)){
			deleteConnection(connectionNumber);
		}
	}

	private boolean isConnectionOutOfTime(int connectionNumber){
		LocalDateTime currentTime = timeService.getCurrentTime();
		LocalDateTime lastConnectionTime = activeConnections.get(connectionNumber);
		
		return lastConnectionTime.plusSeconds(Constants.CONNECTION_TIMEOUT_IN_SECONDS)
				.isBefore(currentTime);
	}
	
	private void deleteConnection(int connectionNumber){
		activeConnections.remove(connectionNumber);
	}
}

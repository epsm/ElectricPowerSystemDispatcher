package com.epsm.electricPowerSystemDispatcher.model;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.epsm.electricPowerSystemModel.model.generalModel.TimeService;
import com.epsm.electricPowerSystemModel.model.generalModel.TimeServiceConsumer;

public class ConnectionKeeper implements TimeServiceConsumer{
	private ConcurrentHashMap<Long, LocalDateTime> connections;
	private TimeService timeService;
	
	public ConnectionKeeper(TimeService timeService) {
		connections = new ConcurrentHashMap<Long, LocalDateTime>();
		this.timeService = timeService;
	}
	
	public void addOrUpdateConnection(long connectionNumber){
		LocalDateTime currentTime = timeService.getCurrentTime();
		connections.put(connectionNumber, currentTime);
	}
	
	public Set<Long> getActiveConnections(){
		return connections.keySet();
	}
	
	@Override
	public void doRealTimeDependOperation() {
		manageConnections();
	}
	
	private void manageConnections(){
		for(Long connectionNumber: connections.keySet()){
			manageConnection(connectionNumber);
		}
	}
	
	private void manageConnection(long connectionNumber){
		if(isConnectionOutOfTime(connectionNumber)){
			deleteConnection(connectionNumber);
		}
	}

	private boolean isConnectionOutOfTime(long connectionNumber){
		LocalDateTime currentTime = timeService.getCurrentTime();
		LocalDateTime lastConnectionTime = connections.get(connectionNumber);
		
		return lastConnectionTime.plusSeconds(Constants.PAUSE_BETWEEN_SENDING_MESSAGE_IN_SECONDS)
				.isBefore(currentTime);
	}
	
	private void deleteConnection(long connectionNumber){
		connections.remove(connectionNumber);
	}

	public boolean isConnectionActive(long connectionNumber) {
		return connections.containsKey(connectionNumber);
	}
}

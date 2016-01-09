package com.epsm.electricPowerSystemDispatcher.model;

import java.util.HashSet;
import java.util.Set;

import com.epsm.electricPowerSystemModel.model.generalModel.TimeService;

public class ConnectionManager {
	private MultiTimer activeConnectionsTimer;
	private MultiTimer servedConnectionsTimer;
	private Set<Long> powerObjectsToSendingMessages;
	private Set<Long> activePowerObjects;
	private Set<Long> servedPowerObjects;
	
	public ConnectionManager(TimeService timeService){
		activeConnectionsTimer = new MultiTimer(timeService,
				Constants.CONNECTION_TIMEOUT_IN_SECONDS);
		servedConnectionsTimer = new MultiTimer(timeService,
				Constants.PAUSE_BETWEEN_SENDING_MESSAGES_IN_SECONDS);
	}

	public void refreshTimeout(long powerObjectId){
		activeConnectionsTimer.startOrUpdateDelayOnTimerNumber(powerObjectId);
	}
	
	public void refreshLastSentCommandTime(long powerObjectId){
		servedConnectionsTimer.startOrUpdateDelayOnTimerNumber(powerObjectId);
	}
	
	public Set<Long> getConnectionsForSendingCommand(){
		filterPowerObjectForSendingMessages();
		
		return powerObjectsToSendingMessages;
	}
	
	private void filterPowerObjectForSendingMessages(){
		getAllActiveConnections();
		getServedConnections();
		substarctServedConnectionsFromActive();
		fillPowerObjectsToSendingMessages();
	}
	
	private void getAllActiveConnections(){
		activePowerObjects = new HashSet<Long>(activeConnectionsTimer.getActiveTimers());
	}
	
	private void getServedConnections(){
		servedPowerObjects = new HashSet<Long>(servedConnectionsTimer.getActiveTimers());
	}
	
	private void substarctServedConnectionsFromActive(){
		activePowerObjects.removeAll(servedPowerObjects);
	}
	
	private void fillPowerObjectsToSendingMessages(){
		powerObjectsToSendingMessages = activePowerObjects;
	}
	
	public boolean isConnectionActive(long connectiomNumber) {
		return activeConnectionsTimer.isTimerActive(connectiomNumber);
	}
}
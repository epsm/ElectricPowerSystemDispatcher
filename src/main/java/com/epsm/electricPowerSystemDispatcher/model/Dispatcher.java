package com.epsm.electricPowerSystemDispatcher.model;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epsm.electricPowerSystemDispatcher.service.OutgoingMessageService;
import com.epsm.electricPowerSystemDispatcher.service.PowerObjectService;
import com.epsm.electricPowerSystemModel.model.dispatch.Command;
import com.epsm.electricPowerSystemModel.model.dispatch.Parameters;
import com.epsm.electricPowerSystemModel.model.dispatch.State;
import com.epsm.electricPowerSystemModel.model.generalModel.RealTimeOperations;

@Component
public class Dispatcher implements RealTimeOperations{
	private MultiTimer receivedMessagesTimer;
	private MultiTimer sentMessagesTimer;
	private PowerObjectService powerObjectService;
	private PowerObjectManagerStub manager;
	private Set<Long> powerObjectsToSendingMessages;
	private Set<Long> activePowerObjects;
	private Set<Long> servedPowerObjects;
	private Logger logger;

	@Autowired
	private OutgoingMessageService service;
	
	public Dispatcher(MultiTimer receivedMessagesTimer, MultiTimer sentMessagesTimer,
			PowerObjectService powerObjectService, PowerObjectManagerStub manager) {

		this.receivedMessagesTimer = receivedMessagesTimer;
		this.sentMessagesTimer = sentMessagesTimer;
		this.powerObjectService = powerObjectService;
		this.manager = manager;
		logger = LoggerFactory.getLogger(Dispatcher.class);
	}

	public void establishConnection(Parameters parameters){
		long powerObjectId = parameters.getPowerObjectId();
		refreshReceivedMessageTimerForPowerObject(powerObjectId);
		manager.rememberObject(parameters);
		
		logger.info("Parameters was received: {}." + parameters);
	}
	
	private void refreshReceivedMessageTimerForPowerObject(long powerObjectId){
		receivedMessagesTimer.startOrUpdateDelayOnTimeNumber(powerObjectId);
	}

	public void acceptState(State state){
		long powerObjectId = state.getPowerObjectId();
		
		if(isConnectionWithPowerObjectActive(powerObjectId)){
			savePowerObjectState(state);
			refreshReceivedMessageTimerForPowerObject(powerObjectId);
		}
	}
	
	private boolean isConnectionWithPowerObjectActive(long powerObjectId){
		return receivedMessagesTimer.isTimerActive(powerObjectId);
	}
	
	private void savePowerObjectState(State state){
		powerObjectService.savePowerObjectState(state);
	}

	@Override
	public void doRealTimeDependingOperations() {
		receivedMessagesTimer.manageTimers();
		sentMessagesTimer.manageTimers();
		sendMesagesToPowerObjects();
	}
	
	private void sendMesagesToPowerObjects(){
		filterPowerObjectForSendingMessages();
		sendMessageForAllAproropriateObjects();
	}
	
	private void filterPowerObjectForSendingMessages(){
		getAllActiveConnections();
		getServedConnections();
		substarctServedConnectionsFromActive();
		fillPowerObjectsToSendingMessages();
	}
	
	private void getAllActiveConnections(){
		activePowerObjects = new HashSet<Long>(receivedMessagesTimer.getActiveTimers());
	}
	
	private void getServedConnections(){
		servedPowerObjects = new HashSet<Long>(sentMessagesTimer.getActiveTimers());
	}
	
	private void substarctServedConnectionsFromActive(){
		activePowerObjects.removeAll(servedPowerObjects);
	}
	
	private void fillPowerObjectsToSendingMessages(){
		powerObjectsToSendingMessages = activePowerObjects;
	}
	
	private void sendMessageForAllAproropriateObjects(){
		for(Long powerObjectId: powerObjectsToSendingMessages){
			if(isItTimeToSendMessageForPowerObject(powerObjectId)){
				sendMessageForPowerObject(powerObjectId);
			}
		}
	}
	
	private boolean isItTimeToSendMessageForPowerObject(long powerObjectId){
		return !sentMessagesTimer.isTimerActive(powerObjectId);
	}
	
	private void sendMessageForPowerObject(long powerObjectId){
		manager.sendMessage(powerObjectId);
		refreshSentMessageTimerForPowerObject(powerObjectId);
	}
	
	private void refreshSentMessageTimerForPowerObject(long powerObjectId){
		sentMessagesTimer.startOrUpdateDelayOnTimeNumber(powerObjectId);
	}

	public void sendCommand(Command command) {
		service.sendCommand(command);
	}
}
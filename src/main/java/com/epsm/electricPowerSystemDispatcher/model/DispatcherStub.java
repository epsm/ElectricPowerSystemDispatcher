package com.epsm.electricPowerSystemDispatcher.model;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.epsm.electricPowerSystemDispatcher.service.PowerObjectService;
import com.epsm.electricPowerSystemModel.model.dispatch.PowerObjectParameters;
import com.epsm.electricPowerSystemModel.model.dispatch.PowerObjectState;
import com.epsm.electricPowerSystemModel.model.generalModel.TimeServiceConsumer;

//It just a stub. More complex model see com.epsm.electricPowerSystemModel.model.*;
@Component
public class DispatcherStub implements TimeServiceConsumer{
	private MultiTimer receivedMessagesTimer;
	private MultiTimer sentMessagesTimer;
	private PowerObjectService powerObjectService;
	private PowerObjectManager manager;
	private Set<Long> powerObjectsToSendingMessages;

	public DispatcherStub(MultiTimer receivedMessagesTimer, MultiTimer sentMessagesTimer,
			PowerObjectService powerObjectService, PowerObjectManager manager) {
		super();
		this.receivedMessagesTimer = receivedMessagesTimer;
		this.sentMessagesTimer = sentMessagesTimer;
		this.powerObjectService = powerObjectService;
		this.manager = manager;
	}

	//Just a stub. Power object parameters have no effect.
	public void establishConnection(PowerObjectParameters parameters){
		long powerObjectId = parameters.getPowerObjectId();
		refreshReceivedMessageTimerForPowerObject(powerObjectId);
		manager.rememberPowerObjectParameters(parameters);
	}
	
	private void refreshReceivedMessageTimerForPowerObject(long powerObjectId){
		receivedMessagesTimer.startOrUpdateDelayOnTimeNumber(powerObjectId);
	}

	public void acceptState(PowerObjectState state){
		long powerObjectId = state.getPowerObjectId();
		if(isConnectionWithPowerObjectActive(powerObjectId)){
			savePowerObjectState(state);
			refreshReceivedMessageTimerForPowerObject(powerObjectId);
		}
	}
	
	private boolean isConnectionWithPowerObjectActive(long powerObjectId){
		return receivedMessagesTimer.isTimerActive(powerObjectId);
	}
	
	private void savePowerObjectState(PowerObjectState state){
		powerObjectService.savePowerObjectState(state);
	}

	@Override
	public void doRealTimeDependOperation() {
		receivedMessagesTimer.doRealTimeDependOperation();
		sentMessagesTimer.doRealTimeDependOperation();
		sendMesagesToPowerObjects();
	}
	
	private void sendMesagesToPowerObjects(){
		filterPowerObjectForSendingMessages();
		sendMessageForAllAproropriateObjects();
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
	
	private void filterPowerObjectForSendingMessages(){
		Set<Long> activePowerObjects = new HashSet<Long>(receivedMessagesTimer.getActiveTimers());
		Set<Long> notApropriatePowerObjects = new HashSet<Long>(sentMessagesTimer.getActiveTimers());
		activePowerObjects.removeAll(notApropriatePowerObjects);
		powerObjectsToSendingMessages = activePowerObjects;
	}
	
	private void sendMessageForPowerObject(long powerObjectId){
		manager.sendMessage(powerObjectId);
		refreshSentMessageTimerForPowerObject(powerObjectId);
	}
	
	private void refreshSentMessageTimerForPowerObject(long powerObjectId){
		sentMessagesTimer.startOrUpdateDelayOnTimeNumber(powerObjectId);
	}
}






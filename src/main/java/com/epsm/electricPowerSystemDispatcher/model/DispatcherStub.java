package com.epsm.electricPowerSystemDispatcher.model;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epsm.electricPowerSystemDispatcher.service.DispatcherService;
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
	private MessageSender sender;

	public DispatcherStub(MultiTimer receivedMessagesTimer, MultiTimer sentMessagesTimer,
			PowerObjectService powerObjectService, MessageSender sender) {
		super();
		this.receivedMessagesTimer = receivedMessagesTimer;
		this.sentMessagesTimer = sentMessagesTimer;
		this.powerObjectService = powerObjectService;
		this.sender = sender;
	}

	//Just a stub. Power object paramters have no effect.
	public void establishConnection(PowerObjectParameters parameters){
		System.out.println("est=" + parameters.getPowerObjectId());
		long powerObjectId = parameters.getPowerObjectId();
		refreshReceivedMessageTimerWithPowerObject(powerObjectId);
	}
	
	private void refreshReceivedMessageTimerWithPowerObject(long powerObjectId){
		receivedMessagesTimer.startOrUpdateDelayOnTimeNumber(powerObjectId);
	}

	public void acceptState(PowerObjectState state){
		long powerObjectId = state.getPowerObjectId();
		if(isConnectionWithPowerObjectActive(powerObjectId)){
			savePowerObjectState(state);
			refreshReceivedMessageTimerWithPowerObject(powerObjectId);
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
		System.out.println("size before=" + receivedMessagesTimer.getActiveTimers().size());
		sendMesagesToPowerObjects();
		System.out.println("size after=" + receivedMessagesTimer.getActiveTimers().size());
	}
	
	private void sendMesagesToPowerObjects(){
		Set<Long> powerObjectsToSendingMessages = filterPowerObjectForSendingMessages();
		System.out.println("ac=" + receivedMessagesTimer.getActiveTimers());
		System.out.println("app= " + powerObjectsToSendingMessages.size());
		for(Long powerObjectId: powerObjectsToSendingMessages){
			System.out.println(powerObjectId);
			if(isItTimeToSendMessageForPowerObject(powerObjectId)){
				sendAppropriateMessageForPowerObject(powerObjectId);
			}
		}
	}
	
	private Set<Long> filterPowerObjectForSendingMessages(){
		Set<Long> activePowerObjects = new HashSet<Long>(receivedMessagesTimer.getActiveTimers());
		Set<Long> notApropriatePowerObjects = new HashSet<Long>(sentMessagesTimer.getActiveTimers());
		activePowerObjects.removeAll(notApropriatePowerObjects);
		return activePowerObjects;
	}

	private boolean isItTimeToSendMessageForPowerObject(long powerObjectId){
		return !sentMessagesTimer.isTimerActive(powerObjectId);
	}
	
	private void sendAppropriateMessageForPowerObject(long powerObjectId){
		sender.sendMessage(powerObjectId);
	}
}






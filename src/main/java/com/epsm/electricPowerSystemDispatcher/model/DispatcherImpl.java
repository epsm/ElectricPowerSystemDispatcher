package com.epsm.electricPowerSystemDispatcher.model;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.epsm.electricPowerSystemDispatcher.service.OutgoingMessageService;
import com.epsm.electricPowerSystemDispatcher.service.PowerObjectService;
import com.epsm.electricPowerSystemModel.model.bothConsumptionAndGeneration.Message;
import com.epsm.electricPowerSystemModel.model.dispatch.Command;
import com.epsm.electricPowerSystemModel.model.dispatch.Dispatcher;
import com.epsm.electricPowerSystemModel.model.dispatch.Parameters;
import com.epsm.electricPowerSystemModel.model.dispatch.State;
import com.epsm.electricPowerSystemModel.model.generalModel.RealTimeOperations;
import com.epsm.electricPowerSystemModel.model.generalModel.TimeService;

public class DispatcherImpl implements Dispatcher, RealTimeOperations{
	private ConnectionManager connectionManager;
	private PowerObjectManagerStub objectManager;
	private Set<Long> powerObjectsToSendingMessages;
	private Logger logger;

	@Autowired
	private PowerObjectService powerObjectService;
	
	@Autowired
	private OutgoingMessageService outgoingMessageService;

	@Autowired
	public DispatcherImpl(TimeService timeService){
		connectionManager = new ConnectionManager(timeService);
		objectManager = new PowerObjectManagerStub(timeService, this);
		
		logger = LoggerFactory.getLogger(DispatcherImpl.class);
	}

	@Override
	public void establishConnection(Parameters parameters){
		if(parameters == null){
			logger.warn("Received null parameters.");
		}else if(tryToRecognizeAndRememberObject(parameters)){
			refreshConnectionTimeout(parameters);
			
			logger.info("Received {} from power object#{}.",
					parameters.getClass().getSimpleName(), parameters.getPowerObjectId());
		}else{
			logger.warn("Recived unknown parameters: {}.", parameters);
		}
	}
	
	private boolean tryToRecognizeAndRememberObject(Parameters parameters){
		return objectManager.rememberObjectIfItTypeIsKnown(parameters);
	}
	
	private void refreshConnectionTimeout(Message parameters){
		connectionManager.refreshTimeout(parameters.getPowerObjectId());
	}

	@Override
	public void acceptState(State state){
		if(state == null){
			logger.warn("Received null state.");
		}else if(isConnectiosActive(state)){
			refreshConnectionTimeout(state);
			savePowerObjectState(state);
			
			logger.info("Received {} from power object#{}.",
					state.getClass().getSimpleName(), state.getPowerObjectId());
		}else{
			logger.info("Recived state from innactive object#{}.", state.getPowerObjectId());
		}
	}
	
	private boolean isConnectiosActive(State state){
		long objectId = state.getPowerObjectId();
		return connectionManager.isConnectionActive(objectId);
	}
	
	private void savePowerObjectState(State state){
		powerObjectService.savePowerObjectState(state);
	}

	@Override
	public void doRealTimeDependingOperations() {
		sendCommandsToObjects();
	}
	
	private void sendCommandsToObjects(){
		getNecessaryObjectsId();
		sendMessageForAllAproropriateObjects();
	}
	
	private void getNecessaryObjectsId(){
		powerObjectsToSendingMessages = connectionManager.getConnectionsForSendingCommand();
	}
	
	private void sendMessageForAllAproropriateObjects(){
		for(Long powerObjectId: powerObjectsToSendingMessages){
			sendMessageForPowerObject(powerObjectId);
		}
	}

	private void sendMessageForPowerObject(long powerObjectId){
		objectManager.sendMessage(powerObjectId);
	}
	
	private void refreshSentMessageTimerForPowerObject(long powerObjectId){
		connectionManager.refreshLastSentCommandTime(powerObjectId);
	}

	public void sendCommand(Command command) {
		outgoingMessageService.sendCommand(command);
		refreshSentMessageTimerForPowerObject(command.getPowerObjectId());
		
		logger.info("Sent {} to power object#{}.", command.getClass().getSimpleName(),
				command.getPowerObjectId());
	}

	void setConnectionManager(ConnectionManager connectionManager) {
		this.connectionManager = connectionManager;
	}

	void setObjectManager(PowerObjectManagerStub objectManager) {
		this.objectManager = objectManager;
	}

	void setPowerObjectService(PowerObjectService powerObjectService) {
		this.powerObjectService = powerObjectService;
	}

	void setOutgoingMessageService(OutgoingMessageService outgoingMessageService) {
		this.outgoingMessageService = outgoingMessageService;
	}
}
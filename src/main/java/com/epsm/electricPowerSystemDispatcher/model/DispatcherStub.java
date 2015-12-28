package com.epsm.electricPowerSystemDispatcher.model;

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
	@Autowired
	private MultiTimer keeper;
	
	@Autowired
	private DispatcherService dispatcherService;
	
	@Autowired
	private PowerObjectService powerObjectService;
	
	//Just a stub. Power object paramters have no effect.
	public void establishConnection(PowerObjectParameters parameters){
		long powerObjectId = parameters.getPowerObjectId();
		keeper.startOrUpdateDelayOnTimeNumber(powerObjectId);
	}

	public void acceptState(PowerObjectState state){
		long powerObjectId = state.getPowerObjectId();
		if(isConnectionWithPowerObjectActive(powerObjectId)){
			savePowerObjectState(state);
			refreshLastReceivedMessageTimeWithPowerObject(powerObjectId);
		}
	}
	
	private boolean isConnectionWithPowerObjectActive(long powerObjectId){
		return keeper.isTimerActive(powerObjectId);
	}
	
	private void savePowerObjectState(PowerObjectState state){
		powerObjectService.savePowerObjectState(state);
	}
	
	private void refreshLastReceivedMessageTimeWithPowerObject(long powerObjectId){
		keeper.startOrUpdateDelayOnTimeNumber(powerObjectId);
	}

	@Override
	public void doRealTimeDependOperation() {
		sendMesagesToPowerObjects();
	}
	
	private void sendMesagesToPowerObjects(){
		for(Long powerObjectId: keeper.getActiveConnections()){
			
		}
	}

	private boolean isItTimeToSendMessageForPowerObject(){
		
	}
}






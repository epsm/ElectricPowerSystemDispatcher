package com.epsm.electricPowerSystemDispatcher.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epsm.electricPowerSystemDispatcher.service.DispatcherService;
import com.epsm.electricPowerSystemModel.model.dispatch.PowerStationState;

//It just a stub. More complex model see com.epsm.electricPowerSystemModel.model.*;
@Component
public class DispatcherStub{
	@Autowired
	private ConnectionKeeper keeper;
	
	@Autowired
	private DispatcherService service;
	
	public void establishConnectionWithPowerStation(int powerStationNumber){
		keeper.addOrUpdatePowerStationConnection(powerStationNumber);
	}
	
	public void establishConnectionWithConsumer(int consumerNumber){
		keeper.addOrUpdateConsumerConnection(consumerNumber);
	}

	public void acceptPowerStationState(PowerStationState state){
		int powerStationNumber = state.getPowerStationNumber();
		
		if(isConnectionWithPowerStationActive(powerStationNumber)){
			savePowerStationState(state);
			refreshLastReceivedMessageTimeWithPowerstation(powerStationNumber);
		}
	}
	
	private boolean isConnectionWithPowerStationActive(int powerStationNumber){
		return keeper.isConnectionWithPowerStationActive(powerStationNumber);
	}
	
	private void savePowerStationState(PowerStationState state){
		service.savePowerStationState(state);
	}
	
	private void refreshLastReceivedMessageTimeWithPowerstation(int powerStationNumber){
		keeper.addOrUpdatePowerStationConnection(powerStationNumber);
	}
	
}






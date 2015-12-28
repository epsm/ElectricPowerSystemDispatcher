package com.epsm.electricPowerSystemDispatcher.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.epsm.electricPowerSystemDispatcher.model.DispatcherStub;
import com.epsm.electricPowerSystemDispatcher.model.domain.SavedConsumerState;
import com.epsm.electricPowerSystemModel.model.dispatch.PowerStationParameters;
import com.epsm.electricPowerSystemModel.model.dispatch.PowerStationState;

public class PowerObjectServiceImpl {
	
	@Autowired
	private DispatcherStub dispatcher;
	
	void acceptConsumerConnection(int consumerNumber){
		dispatcher.acceptConsumerConnection(consumerNumber);
	}
	
	void acceptConsumerState(SavedConsumerState state){
		dispatcher.acceptConsumerState(state);
	}
	
	void acceptPowerStationConnection(PowerStationParameters parameters){
		dispatcher.acceptPowerStationConnection(parameters);
	}
	
	void acceptPowerStationState(PowerStationState state){
		dispatcher.acceptPowerStationState(state);
	}
	
}

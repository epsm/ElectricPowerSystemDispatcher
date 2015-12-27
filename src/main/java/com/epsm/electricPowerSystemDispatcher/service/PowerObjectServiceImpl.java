package com.epsm.electricPowerSystemDispatcher.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.epsm.electricPowerSystemDispatcher.model.DispatcherStub;
import com.epsm.electricPowerSystemDispatcher.model.domain.SavedConsumerState;
import com.epsm.electricPowerSystemModel.model.dispatch.PowerStationParameters;
import com.epsm.electricPowerSystemModel.model.dispatch.PowerStationState;

public class PowerObjectServiceImpl {
	
	@Autowired
	private DispatcherStub dispatcher;
	
	void registerConsumer(int consumerNumber){
		dispatcher.registerConsumer(consumerNumber);
	}
	
	void acceptConsumerState(SavedConsumerState state){
		dispatcher.acceptConsumerState(state);
	}
	
	void registerPowerStation(PowerStationParameters parameters){
		dispatcher.registerPowerStation(parameters);
	}
	
	void acceptPowerStationState(PowerStationState state){
		dispatcher.acceptPowerStationState(state);
	}
}

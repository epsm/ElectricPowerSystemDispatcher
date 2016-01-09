package com.epsm.electricPowerSystemDispatcher.service;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.stereotype.Component;

import com.epsm.electricPowerSystemDispatcher.model.domain.SavedConsumerState;
import com.epsm.electricPowerSystemModel.model.consumption.ConsumerState;

@Component
public class ConsumerStateConverter {
	private ConsumerState consumerState;
	private long consumerId;
	private float load;
	private LocalTime simulationTimeStamp;
	private LocalDateTime realTimeStamp;
	private SavedConsumerState convertedState;
	
	public synchronized SavedConsumerState convert(ConsumerState consumerState) {
		saveState(consumerState);
		getData();
		cerateSavedConsumerState();
		fillOutConvertedState();
		
		return convertedState;
	}
	
	private void saveState(ConsumerState consumerState){
		this.consumerState = consumerState;
	}
	
	private void getData(){
		consumerId = consumerState.getPowerObjectId();
		load = consumerState.getLoad();
		simulationTimeStamp = consumerState.getSimulationTimeStamp();
		realTimeStamp = consumerState.getRealTimeStamp();
	}
	
	private void cerateSavedConsumerState(){
		convertedState = new SavedConsumerState();
	}
	
	private void fillOutConvertedState(){
		convertedState.setConsumerId(consumerId);
		convertedState.setLoad(load);
		convertedState.setSimulationTimeStamp(simulationTimeStamp);
		convertedState.setRealTimeStamp(realTimeStamp);
	}
}

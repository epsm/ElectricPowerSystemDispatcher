package com.epsm.electricPowerSystemDispatcher.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epsm.electricPowerSystemDispatcher.client.ConsumerClient;
import com.epsm.electricPowerSystemDispatcher.client.PowerStationClient;
import com.epsm.electricPowerSystemModel.model.consumption.ConsumptionPermissionStub;
import com.epsm.electricPowerSystemModel.model.dispatch.Command;
import com.epsm.electricPowerSystemModel.model.generation.PowerStationGenerationSchedule;

@Service
public class OutgoingMessageServiceImpl implements OutgoingMessageService{

	@Autowired
	private ConsumerClient consumerClient;
	
	@Autowired
	private PowerStationClient powerStationClient;
	
	@Override
	public void sendCommand(Command command) {
		if(command == null){
			String message = "Command must not be null.";
			throw new IllegalArgumentException(message);
		}else if(command instanceof ConsumptionPermissionStub){
			consumerClient.sendConsumerPermissionToConsumer((ConsumptionPermissionStub) command);
		}else if(command instanceof PowerStationGenerationSchedule){
			powerStationClient.sendGenerationScheduleToPowerStation(
					(PowerStationGenerationSchedule) command);
		}else{
			String message = String.format("Unsuported type of Command: %s.",
					command.getClass().getSimpleName());
			throw new IllegalArgumentException(message);
		}
	}
}

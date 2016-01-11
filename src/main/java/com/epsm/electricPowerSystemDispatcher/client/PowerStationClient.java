package com.epsm.electricPowerSystemDispatcher.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.epsm.electricPowerSystemModel.client.AbstractClient;
import com.epsm.electricPowerSystemModel.model.generation.PowerStationGenerationSchedule;

@Component
public class PowerStationClient extends AbstractClient<PowerStationGenerationSchedule>{

	@Value("${api.powerstation.command}")
	private String api;
	
	public void sendGenerationScheduleToPowerStation(PowerStationGenerationSchedule schedule){
		sendMessage(schedule, api);
	}
}

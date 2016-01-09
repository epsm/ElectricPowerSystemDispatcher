package com.epsm.electricPowerSystemDispatcher.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.epsm.electricPowerSystemModel.model.generation.PowerStationGenerationSchedule;
import com.epsm.electricPowerSystemModel.util.UrlRequestSender;

@Component
public class PowerStationClient{
	
	@Autowired
	private UrlRequestSender<PowerStationGenerationSchedule> sender;
	
	@Value("${api.powerStation.command}")
	private String powerStationApi;
	
	public void sendGenerationScheduleToPowerStation(PowerStationGenerationSchedule schedule){
		String url = prepareUrl(schedule);
		sendSchedule(url, schedule);
	}
	
	private String prepareUrl(PowerStationGenerationSchedule schedule){
		Long powerStationId = schedule.getPowerObjectId();
		
		return powerStationApi + powerStationId;
	}
	
	private void sendSchedule(String url, PowerStationGenerationSchedule schedule){
		sender.sendObjectInJsonToUrlWithPOST(url, schedule);
	}
}

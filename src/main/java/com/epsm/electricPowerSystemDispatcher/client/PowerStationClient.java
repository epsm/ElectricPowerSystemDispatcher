package com.epsm.electricPowerSystemDispatcher.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epsm.electricPowerSystemDispatcher.util.UrlRequestSender;
import com.epsm.electricPowerSystemModel.model.generation.PowerStationGenerationSchedule;

@Component
public class PowerStationClient{
	
	@Autowired
	private UrlRequestSender sender;
	
	public void sendGenerationScheduleToPowerStation(PowerStationGenerationSchedule schedule)
			throws Exception{
		Long powerStationId = schedule.getPowerObjectId();
		String url = prepareUrlForSendingSchedule(powerStationId);
		
		sender.sendObjectInJsonToUrlWithPOST(url, schedule);
	}
	
	private String prepareUrlForSendingSchedule(long powerStationId){
		String host = "http://localhost:8080/";
		String apiUrl ="epsm/api/powerstation/acceptschedule";
		
		return host + apiUrl + powerStationId;
	}
}

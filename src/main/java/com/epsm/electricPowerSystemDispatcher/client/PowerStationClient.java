package com.epsm.electricPowerSystemDispatcher.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epsm.electricPowerSystemDispatcher.util.UrlRequestSender;
import com.epsm.electricPowerSystemModel.model.generation.PowerStationGenerationSchedule;

@Component
public class PowerStationClient{
	
	@Autowired
	private UrlRequestSender sender;
	
	public void sendSubscribeRequestToPowerStation(int powerStationNumber) throws Exception{
		String url = prepareUrlForSubscribing(powerStationNumber);
		
		sender.sendEmptyRequestToUrlWithPOST(url);
	}
	
	private String prepareUrlForSubscribing(int powerStationNumber){
		String host = "http://localhost:8080/";
		String apiUrl ="epsm/api/powerstation/subscribe/";
		
		return host + apiUrl + powerStationNumber;
	}
	
	public void sendGenerationScheduleToPowerStation(int powerStationNumber,
			PowerStationGenerationSchedule schedule) throws Exception{
		
		String url = prepareUrlForSendingSchedule(powerStationNumber);
		
		sender.sendObjectInJsonToUrlWithPOST(url, schedule);
	}
	
	private String prepareUrlForSendingSchedule(int powerStationNumber){
		String host = "http://localhost:8080/";
		String apiUrl ="epsm/api/powerstation/acceptschedule";
		
		return host + apiUrl + powerStationNumber;
	}
}

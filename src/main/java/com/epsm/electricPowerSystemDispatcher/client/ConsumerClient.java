package com.epsm.electricPowerSystemDispatcher.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epsm.electricPowerSystemDispatcher.util.UrlRequestSender;
import com.epsm.electricPowerSystemModel.model.consumption.ConsumptionPermissionStub;

@Component
public class ConsumerClient{
	
	@Autowired
	private UrlRequestSender sender;
	
	public void sendConsumerPermissionToConsumer(ConsumptionPermissionStub permission) throws Exception{
		Long consumerId = permission.getPowerObjectId();
		String url = prepareUrl(consumerId);
		
		sender.sendObjectInJsonToUrlWithPOST(url, permission);
	}
	
	private String prepareUrl(long consumerId){
		String host = "http://localhost:8080/";
		String apiUrl ="epsm/api/consumer/subscribe/";
		
		return host + apiUrl + consumerId;
	}
}

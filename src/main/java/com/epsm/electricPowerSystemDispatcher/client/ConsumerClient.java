package com.epsm.electricPowerSystemDispatcher.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

import com.epsm.electricPowerSystemModel.model.consumption.ConsumptionPermissionStub;
import com.epsm.electricPowerSystemModel.util.UrlRequestSender;

@Import(UrlRequestSender.class)
@Component
public class ConsumerClient{
	
	@Autowired
	private UrlRequestSender<ConsumptionPermissionStub> sender;
	
	@Value("${api.consumer.command}")
	private String consumerApi;
	
	public void sendConsumerPermissionToConsumer(ConsumptionPermissionStub permission){
		String url = prepareUrl(permission);
		sendPermission(url, permission);
	}
	
	private String prepareUrl(ConsumptionPermissionStub permission){
		Long consumerId = permission.getPowerObjectId();
		
		return consumerApi + consumerId;
	}
	
	private void sendPermission(String url, ConsumptionPermissionStub permission){
		sender.sendObjectInJsonToUrlWithPOST(url, permission);
	}
}

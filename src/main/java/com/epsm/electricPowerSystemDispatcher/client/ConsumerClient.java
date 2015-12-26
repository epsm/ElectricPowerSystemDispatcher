package com.epsm.electricPowerSystemDispatcher.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epsm.electricPowerSystemDispatcher.util.UrlRequestSender;

@Component
public class ConsumerClient{
	
	@Autowired
	private UrlRequestSender sender;
	
	public void sendSubscribeRequestToConsumer(int consumerNumber) throws Exception{
		String url = prepareUrl(consumerNumber);
		
		sender.sendEmptyRequestToUrlWithPOST(url);
	}
	
	private String prepareUrl(int consumerNumber){
		String host = "http://localhost:8080/";
		String apiUrl ="epsm/api/consumer/subscribe/";
		
		return host + apiUrl + consumerNumber;
	}
}

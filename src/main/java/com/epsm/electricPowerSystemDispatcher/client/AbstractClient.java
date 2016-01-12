package com.epsm.electricPowerSystemDispatcher.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import com.epsm.electricPowerSystemDispatcher.copiesFromEPSMmodel.Message;
import com.epsm.electricPowerSystemDispatcher.copiesFromEPSMmodel.UrlRequestSender;

@Import(UrlRequestSender.class)
public class AbstractClient<T extends Message> {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UrlRequestSender<T> sender;
	
	protected void sendMessage(T message, String url){
		logger.debug("Sent {}.", message);
		sender.sendObjectInJsonToUrlWithPOST(url, message);
	}
}

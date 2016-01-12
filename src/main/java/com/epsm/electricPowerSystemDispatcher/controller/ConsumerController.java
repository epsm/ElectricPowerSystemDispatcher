package com.epsm.electricPowerSystemDispatcher.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.epsm.electricPowerSystemDispatcher.copiesFromEPSMmodel.ConsumerParametersStub;
import com.epsm.electricPowerSystemDispatcher.copiesFromEPSMmodel.ConsumerState;
import com.epsm.electricPowerSystemDispatcher.service.IncomingMessageService;

@RestController
@RequestMapping("/api/consumer")
public class ConsumerController {
	private Logger logger = LoggerFactory.getLogger(ConsumerController.class);
	
	@Autowired
	private IncomingMessageService service;
	
	@RequestMapping(value="/esatblishconnection", method = RequestMethod.POST)
	public @ResponseBody void establishConnection(@RequestBody ConsumerParametersStub parameters){
		logger.debug("Received {}.", parameters);
		service.establishConnectionWithConsumer(parameters);
	}
	
	/*@RequestMapping(value="/esatblishconnection", method = RequestMethod.POST)
	public @ResponseBody void establishConnection(@RequestBody String parameters){
		System.out.println("epsd cons est got: " + parameters);
	}*/
	
	
	@RequestMapping(value="/acceptstate", method=RequestMethod.POST)
	public @ResponseBody void acceptConsumerState(@RequestBody ConsumerState state){
		logger.debug("Received {}.", state);
		service.acceptConsumerState(state);
	}
}

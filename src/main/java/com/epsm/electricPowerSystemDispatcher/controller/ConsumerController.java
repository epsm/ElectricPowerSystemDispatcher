package com.epsm.electricPowerSystemDispatcher.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.epsm.electricPowerSystemDispatcher.model.domain.SavedConsumerState;
import com.epsm.electricPowerSystemDispatcher.service.IncomingMessageService;
import com.epsm.electricPowerSystemModel.model.consumption.ConsumerParametersStub;

@RequestMapping("/api/consumer")
public class ConsumerController {
	
	@Autowired
	private IncomingMessageService service;
	
	@RequestMapping(value="/esatblishconnection", method = RequestMethod.POST)
	public @ResponseBody void registerConsumer(@RequestBody ConsumerParametersStub parameters){
		service.registerConsumer(parameters);
	}
	
	@RequestMapping(value="/acceptstate", method=RequestMethod.POST)
	public @ResponseBody void acceptConsumerState(@RequestBody SavedConsumerState state){
		service.acceptConsumerState(state);
	}
}

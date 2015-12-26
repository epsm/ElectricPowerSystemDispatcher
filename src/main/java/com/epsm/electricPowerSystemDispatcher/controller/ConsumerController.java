package com.epsm.electricPowerSystemDispatcher.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.epsm.electricPowerSystemDispatcher.model.domain.ConsumerState;
import com.epsm.electricPowerSystemDispatcher.service.PowerObjectService;

@RequestMapping("/api/consumer")
public class ConsumerController {
	
	@Autowired
	private PowerObjectService service;
	
	@RequestMapping(value="/register/{consumerNumber}", method = RequestMethod.POST)
	public @ResponseBody void registerConsumer(@PathVariable("consumerNumber") int consumerNumber){
		service.registerConsumer(consumerNumber);
	}
	
	@RequestMapping(value="/acceptstate", method=RequestMethod.POST, consumes="application/json")
	public @ResponseBody void acceptConsumerState(@RequestBody ConsumerState state){
		service.acceptConsumerState(state);
	}
}

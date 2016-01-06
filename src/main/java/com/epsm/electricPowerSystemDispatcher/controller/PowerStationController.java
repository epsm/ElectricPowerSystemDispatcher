package com.epsm.electricPowerSystemDispatcher.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.epsm.electricPowerSystemDispatcher.service.IncomingMessageService;
import com.epsm.electricPowerSystemModel.model.generation.PowerStationParameters;
import com.epsm.electricPowerSystemModel.model.generation.PowerStationState;

@RequestMapping("/api/powerstation")
public class PowerStationController {
	
	@Autowired
	private IncomingMessageService service;
	
	@RequestMapping(value="/esatblishconnection", method = RequestMethod.POST)
	public @ResponseBody void registerPowerStation(@RequestBody PowerStationParameters parameters){
		service.registerPowerStation(parameters);
	}
	
	@RequestMapping(value="/acceptstate", method=RequestMethod.POST)
	public @ResponseBody void acceptPowerStationState(@RequestBody PowerStationState state){
		service.acceptPowerStationState(state);
	}
}

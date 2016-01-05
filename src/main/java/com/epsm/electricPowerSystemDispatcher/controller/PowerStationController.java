package com.epsm.electricPowerSystemDispatcher.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.epsm.electricPowerSystemDispatcher.service.PowerObjectService;
import com.epsm.electricPowerSystemModel.model.dispatch.Parameters;
import com.epsm.electricPowerSystemModel.model.dispatch.State;

@RequestMapping("/api/powerstation")
public class PowerStationController {
	
	@Autowired
	private PowerObjectService service;
	
	@RequestMapping(value="/register", method = RequestMethod.POST)
	public @ResponseBody void registerPowerStation(@RequestBody Parameters parameters){
		service.registerPowerStation(parameters);
	}
	
	@RequestMapping(value="/acceptstate", method=RequestMethod.POST)
	public @ResponseBody void acceptPowerStationState(@RequestBody State state){
		service.acceptPowerStationState(state);
	}
}

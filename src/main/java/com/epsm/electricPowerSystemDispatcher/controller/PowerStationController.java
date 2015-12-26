package com.epsm.electricPowerSystemDispatcher.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.epsm.electricPowerSystemDispatcher.model.domain.ConsumerState;
import com.epsm.electricPowerSystemDispatcher.service.PowerObjectService;
import com.epsm.electricPowerSystemModel.model.dispatch.PowerStationParameters;
import com.epsm.electricPowerSystemModel.model.dispatch.PowerStationState;

@RequestMapping("/api/powerstation")
public class PowerStationController {
	
	@Autowired
	private PowerObjectService service;
	
	@RequestMapping(value="/register", method = RequestMethod.POST)
	public @ResponseBody void registerPowerStation(@RequestBody PowerStationParameters parameters){
		service.registerPowerStation(parameters);
	}
	
	@RequestMapping(value="/acceptstate", method=RequestMethod.POST)
	public @ResponseBody void acceptPowerStationState(@RequestBody PowerStationState state){
		service.acceptPowerStationState(state);
	}
}

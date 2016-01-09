package com.epsm.electricPowerSystemDispatcher.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.epsm.electricPowerSystemModel.model.consumption.ConsumptionPermissionStub;
import com.epsm.electricPowerSystemModel.model.dispatch.Command;
import com.epsm.electricPowerSystemModel.model.generation.PowerStationGenerationSchedule;
import com.epsm.electricPowerSystemModel.util.UrlRequestSender;

@Service
@PropertySource("classpath:application.properties")
public class OutgoingMessageServiceImpl implements OutgoingMessageService{

	@Autowired
	private UrlRequestSender<ConsumptionPermissionStub> permissionSender;
	
	@Autowired
	private UrlRequestSender<PowerStationGenerationSchedule> scheduleSender;
	
	@Value("${api.powerStation}")
	private String powerStationApi;
	
	@Value("${api.consumer}")
	private String consumerApi;
	
	@Override
	public void sendCommand(Command command) {
		if(command == null){
			String message = "Command must not be null.";
			throw new IllegalArgumentException(message);
		}else if(command instanceof ConsumptionPermissionStub){
			permissionSender.sendObjectInJsonToUrlWithPOST(
					consumerApi, (ConsumptionPermissionStub) command);
		}else if(command instanceof PowerStationGenerationSchedule){
			scheduleSender.sendObjectInJsonToUrlWithPOST(
					powerStationApi, (PowerStationGenerationSchedule) command);
		}else{
			String message = String.format("Unsuported type of Command: %s.",
					command.getClass().getSimpleName());
			throw new IllegalArgumentException(message);
		}
	}
}

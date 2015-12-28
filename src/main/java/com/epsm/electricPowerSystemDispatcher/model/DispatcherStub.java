package com.epsm.electricPowerSystemDispatcher.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epsm.electricPowerSystemDispatcher.model.PowerSystemObject.Type;
import com.epsm.electricPowerSystemDispatcher.model.domain.SavedConsumerState;
import com.epsm.electricPowerSystemDispatcher.service.DispatcherService;
import com.epsm.electricPowerSystemModel.model.dispatch.PowerStationGenerationSchedule;
import com.epsm.electricPowerSystemModel.model.dispatch.PowerStationParameters;
import com.epsm.electricPowerSystemModel.model.dispatch.PowerStationState;
import com.epsm.electricPowerSystemModel.model.generalModel.GlobalConstants;
import com.epsm.electricPowerSystemModel.model.generalModel.TimeService;

//It just a stub. It doesn't do any calculation as real dispatcher. More complex model see in EPSM model.
@Component
public class DispatcherStub{
	private PowerStationGenerationScheduleCalculatorStub calculator; 
	private Map<Long, PowerSystemObject> objectsWithActiveConnection;
	private PowerStationGenerationSchedule schedule;
	private TimeService timeService;
	private LocalDateTime currentTime;
	private Logger logger;
	private final int ACCEPTABLE_PAUSE_BETWEEN_MESSAGES_FROM_OBJECTS_IN_SECCONDS = 10;
	
	@Autowired
	private DispatcherService service;
	
	public DispatcherStub() {
		calculator 	= new PowerStationGenerationScheduleCalculatorStub();
		objectsWithActiveConnection = new ConcurrentHashMap<Long, PowerSystemObject>();
		timeService = new TimeService();
		logger = LoggerFactory.getLogger(DispatcherStub.class);
	}
	
	public void acceptPowerObjectConnection(PowerSystemObject object){
		long objectId = object.getId();
		
		if(isConnectionWithThisPowerStationActive(objectId)){
			refreshTimeWhenLastMessageRecievedForPowerStation(objectId);
		}else{
			setConnectionWithThisPowerStationActive(powerStationNumber);
		}
	}
	
	private boolean isConnectionWithThisPowerStationActive(long objectId){
		return objectsWithActiveConnection.containsKey(objectId);
	}
	
	private void refreshTimeWhenLastMessageRecievedForPowerStation(long objectId){
		PowerSystemObject powerStation = objectsWithActiveConnection.get(objectId);
		if(powerStation != null){
			powerStation.setTimeWhenRecievedLastState(LocalDateTime.now());
		}
	}

	private void setConnectionWithThisPowerStationActive(int powerStationNumber){
		PowerSystemObject powerStation = new PowerSystemObject(Type.POWERSTATION, powerStationNumber);
		powerStation.setTimeWhenRecievedLastState(LocalDateTime.now());
		dispatcheredObjects.put(powerStationNumber, powerStation);
	}
	
	
	

	public void interactWithPowerSystemObjects(){
		private void getCurrentTime(){
			currentTime = timeService.getCurrentTime();
		}
		
		
	}
	
	private void processPowerObject(){
		if(isConnectionWithObjectActive()){
			sendMessageToObject();
		}else{
			
		}
	}
	
	private boolean isConnectionWithObjectActive()owerSystemObject object){
		LocalDateTime timeWhenRecievedLastMessage = object.getTimeWhenRecievedLastState();
		return timeWhenRecievedLastMessage.plusSeconds(
				ACCEPTABLE_PAUSE_BETWEEN_MESSAGES_FROM_OBJECTS_IN_SECCONDS).isAfter(currentTime);
	}
	
	private sendMessageToObject()
}






package com.epsm.electricPowerSystemDispatcher.model;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epsm.electricPowerSystemModel.model.dispatch.PowerStationGenerationSchedule;

//It just a stub. It doesn't do any calculation as real dispatcher. More complex model see in EPSM model.
public class DispatcherStub{
	private ScheduleCalculatorStub calculator = new ScheduleCalculatorStub();  
	private PowerStationGenerationSchedule schedule;
	private Set<Integer> powerStations = new HashSet<Integer>();
	private Set<Integer> consumers = new HashSet<Integer>();
	
	private Logger logger = LoggerFactory.getLogger(DispatcherStub.class);
	
	private void registerPowerStation(int number){
		
	}
	
	private void registerConsumer(int number){
		
	}
	
	private void sendConfirmationToPowerStation(int number){
		
	}
	
	private void sendConfirmationToConsumer(int number){
		
	}
	
	private void sendGenerationScheduleToPowerStation(int number){
		
	}
	
	private void acceptPowerStationState(int number){
		//TODO save to db
	}
	
	private void acceptConsumerState(int number){
		//save to db
	}
}

package com.epsm.electricPowerSystemDispatcher.service;

import java.time.LocalTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epsm.electricPowerSystemDispatcher.client.ConsumerClient;
import com.epsm.electricPowerSystemDispatcher.client.PowerStationClient;
import com.epsm.electricPowerSystemDispatcher.model.domain.SavedConsumerState;
import com.epsm.electricPowerSystemDispatcher.model.domain.SavedGeneratorState;
import com.epsm.electricPowerSystemDispatcher.repository.ConsumerStateDao;
import com.epsm.electricPowerSystemDispatcher.repository.GeneratorStateDao;
import com.epsm.electricPowerSystemModel.model.dispatch.GeneratorState;
import com.epsm.electricPowerSystemModel.model.dispatch.PowerStationGenerationSchedule;
import com.epsm.electricPowerSystemModel.model.dispatch.PowerStationState;

@Service
public class DispatcherServiceImpl implements DispatcherService{
	
	@Autowired
	private PowerStationClient powerStationClient;
	
	@Autowired
	private ConsumerClient consumerClient;
	
	@Autowired
	private GeneratorStateDao generatorDao;
	
	@Autowired
	private ConsumerStateDao consumerDao;
	
	private Logger logger = LoggerFactory.getLogger(DispatcherServiceImpl.class);
	
	public void sendConfirmationToPowerStation(int powerStationNumber){
		try {
			powerStationClient.sendSubscribeRequestToPowerStation(powerStationNumber);
		} catch (Exception e) {
			logger.info("Error sending subscribe report to power station.", e);
		}
	}
	
	public void sendConfirmationToConsumer(int consumerNumber){
		try {
			consumerClient.sendSubscribeRequestToConsumer(consumerNumber);
		} catch (Exception e) {
			logger.info("Error sending subscribe report to consumer.", e);
		}
	}
	
	public void sendGenerationScheduleToPowerStation(
			int powerStationNumber, PowerStationGenerationSchedule schedule){
			
		try {
			powerStationClient.sendGenerationScheduleToPowerStation(powerStationNumber, schedule);
		} catch (Exception e) {
			logger.info("Error sending generation schedule.", e);
		}
	}
	
	public void savePowerStationState(PowerStationState powerStationState){
		for(GeneratorState generatorState: powerStationState.getGeneratorsStates()){
			int powerStationNumber = powerStationState.getPowerStationNumber();
			float frequency = powerStationState.getFrequency();
			LocalTime timeStamp = powerStationState.getTimeStamp();
			
			SavedGeneratorState toSave 
					= convertGeneratorState(generatorState, powerStationNumber, frequency, timeStamp);
			
			generatorDao.saveState(toSave);		
		}
	}
	
	private SavedGeneratorState convertGeneratorState(GeneratorState originalState, int powerStationNumber,
			float frequency, LocalTime timeStamp){
		
		SavedGeneratorState convertedState = new SavedGeneratorState();
		
		convertedState.setStationNumber(powerStationNumber);
		convertedState.setGeneratorNumber(originalState.getGeneratorNumber());
		convertedState.setGenerationInWM(originalState.getGenerationInWM());
		convertedState.setFrequency(frequency);
		convertedState.setTimeStamp(timeStamp);
		
		return convertedState;
	}
	
	public void saveConsumerState(SavedConsumerState state){
		consumerDao.saveState(state);
	}
}

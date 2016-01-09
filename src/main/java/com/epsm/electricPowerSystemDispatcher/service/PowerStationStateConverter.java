package com.epsm.electricPowerSystemDispatcher.service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.epsm.electricPowerSystemDispatcher.model.domain.SavedGeneratorState;
import com.epsm.electricPowerSystemModel.model.generation.GeneratorState;
import com.epsm.electricPowerSystemModel.model.generation.PowerStationState;

@Component
public class PowerStationStateConverter {
	private PowerStationState powerStationState;
	private List<SavedGeneratorState> convertedStates;
	private long powerStationId;
	private float frequency;
	private LocalTime simulationTimeStamp;
	private LocalDateTime realTimeStamp;
	private int generatorNumber;
	private float generationInMW;
	private SavedGeneratorState savedGeneratorState;
	
	public PowerStationStateConverter() {
		convertedStates = new ArrayList<SavedGeneratorState>();
	}
	
	public synchronized List<SavedGeneratorState> convert(PowerStationState powerStationState) {
		saveState(powerStationState);
		erseConvertedStates();
		getGeneralFields();
		createSavedGeneratorStates();
		
		return convertedStates;
	}
	
	private void saveState(PowerStationState powerStationState){
		this.powerStationState = powerStationState;
	}
	
	private void erseConvertedStates(){
		convertedStates.clear();
	}
	
	private void getGeneralFields(){
		powerStationId = powerStationState.getPowerObjectId();
		frequency = powerStationState.getFrequency();
		simulationTimeStamp = powerStationState.getSimulationTimeStamp();
		realTimeStamp = powerStationState.getRealTimeStamp();
	}
	
	private void createSavedGeneratorStates(){
		Set<Integer> generatorNumbers = powerStationState.getGeneratorsNumbers();
		
		for(int generator: generatorNumbers){
			GeneratorState generatorState = powerStationState.getGeneratorState(generator);
			getDataForConcreteGenerator(generatorState);
			createSavedGeneratorState();
			fillOutConcreteSavedGeneratorState();
			addSavedGeneratorStateToList();
		}
	}
	
	private void getDataForConcreteGenerator(GeneratorState generatorState){
		generatorNumber = generatorState.getGeneratorNumber();
		generationInMW = generatorState.getGenerationInWM();
	}
	
	private void createSavedGeneratorState(){
		savedGeneratorState = new SavedGeneratorState();
	}
	
	private void fillOutConcreteSavedGeneratorState(){
		savedGeneratorState = new SavedGeneratorState();
		savedGeneratorState.setPowerStationId(powerStationId);
		savedGeneratorState.setGeneratorNumber(generatorNumber);
		savedGeneratorState.setGenerationInMW(generationInMW);
		savedGeneratorState.setFrequency(frequency);
		savedGeneratorState.setSimulationTimeStamp(simulationTimeStamp);
		savedGeneratorState.setRealTimeStamp(realTimeStamp);
	}
	
	private void addSavedGeneratorStateToList(){
		convertedStates.add(savedGeneratorState);
	}
}

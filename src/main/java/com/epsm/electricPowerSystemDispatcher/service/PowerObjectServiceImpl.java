package com.epsm.electricPowerSystemDispatcher.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epsm.electricPowerSystemDispatcher.model.domain.SavedConsumerState;
import com.epsm.electricPowerSystemDispatcher.model.domain.SavedGeneratorState;
import com.epsm.electricPowerSystemDispatcher.repository.SavedConsumerStateDao;
import com.epsm.electricPowerSystemDispatcher.repository.SavedGeneratorStateDao;
import com.epsm.electricPowerSystemModel.model.consumption.ConsumerState;
import com.epsm.electricPowerSystemModel.model.dispatch.State;
import com.epsm.electricPowerSystemModel.model.generation.PowerStationState;

@Service
public class PowerObjectServiceImpl implements PowerObjectService{

	@Autowired
	private SavedGeneratorStateDao generatorDao;
	
	@Autowired
	private SavedConsumerStateDao consumerDao;
	
	@Autowired
	private ConsumerStateConverter consumerConverter;
	
	@Autowired
	private PowerStationStateConverter powerStationConverter;
	
	@Override
	public void savePowerObjectState(State state) {
		if(state == null){
			String message = "State must not be null.";
			throw new IllegalArgumentException(message);
		}else if(state instanceof PowerStationState){
			saveGeneratorState((PowerStationState) state);
		}else if(state instanceof ConsumerState){
			saveConsumerState((ConsumerState) state);
		}else{
			String message = String.format("Unsuported type of State: %s.",
					state.getClass().getSimpleName());
			throw new IllegalArgumentException(message);
		}
	}
	
	private void saveGeneratorState(PowerStationState powerStationState){
		List<SavedGeneratorState> 
		states = powerStationConverter.convert(powerStationState);
		
		for(SavedGeneratorState generatorState: states){
			generatorDao.saveState(generatorState);
		}
	}
	
	private void saveConsumerState(ConsumerState state){
		SavedConsumerState convertedState = consumerConverter.convert(state);
		consumerDao.saveState(convertedState);
	}
}

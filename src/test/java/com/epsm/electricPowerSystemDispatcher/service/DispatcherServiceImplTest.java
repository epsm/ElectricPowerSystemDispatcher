package com.epsm.electricPowerSystemDispatcher.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.epsm.electricPowerSystemDispatcher.model.domain.SavedGeneratorState;
import com.epsm.electricPowerSystemDispatcher.repository.GeneratorStateDao;
import com.epsm.electricPowerSystemModel.model.dispatch.GeneratorState;
import com.epsm.electricPowerSystemModel.model.dispatch.PowerStationState;

@RunWith(MockitoJUnitRunner.class)
public class DispatcherServiceImplTest{
	
	private PowerStationState powerstationState;
	private ArgumentCaptor<SavedGeneratorState> captor =  ArgumentCaptor.forClass(SavedGeneratorState.class);
	private List<SavedGeneratorState> capturedStates;
	private final int POWERSTATION_NUMBER = 1;
	private final LocalTime TIME = LocalTime.MIDNIGHT;
	private final float FREQUENCY = 49.99f;
	private final float GENERATION_1 = 75f;
	private final float GENERATION_2 = 67f;
	
	@InjectMocks
	private DispatcherServiceImpl service;
	
	@Mock
	private GeneratorStateDao generatorDao;
	
	@Test
	public void dispatcherConvertsPowerStationStateCorrect(){
		preparePowerStationState();
		savePowerStationState();
		getConvertedGeneratorStates();
		compareDate();
	}
	
	private void preparePowerStationState(){
		HashSet<GeneratorState> states = new HashSet<GeneratorState>();
		GeneratorState state_1 = new GeneratorState(1, GENERATION_1);
		GeneratorState state_2 = new GeneratorState(2, GENERATION_2);
		
		states.add(state_1);
		states.add(state_2);
		powerstationState = new PowerStationState(POWERSTATION_NUMBER, TIME, FREQUENCY, states);
	}
	
	private void savePowerStationState(){
		service.savePowerStationState(powerstationState);
	}
	
	private void getConvertedGeneratorStates(){
		verify(generatorDao, times(2)).saveState(captor.capture());
		capturedStates = captor.getAllValues();
	}
	
	private void compareDate(){
		boolean state_1_verified = false;
		boolean state_2_verified = false;
		
		for(SavedGeneratorState state: capturedStates){
			if(state.getGeneratorNumber() == 1){
				Assert.assertEquals(FREQUENCY, state.getFrequency(), 0);
				Assert.assertEquals(GENERATION_1, state.getGenerationInWM(), 0);
				Assert.assertEquals(POWERSTATION_NUMBER, state.getStationNumber());
				Assert.assertEquals(TIME, state.getTimeStamp());
				state_1_verified = true;
			}else if(state.getGeneratorNumber() == 2){
				Assert.assertEquals(FREQUENCY, state.getFrequency(), 0);
				Assert.assertEquals(GENERATION_2, state.getGenerationInWM(), 0);
				Assert.assertEquals(POWERSTATION_NUMBER, state.getStationNumber());
				Assert.assertEquals(TIME, state.getTimeStamp());
				state_2_verified = true;
			}
		}
		
		Assert.assertTrue(state_1_verified);
		Assert.assertTrue(state_2_verified);
	}
}

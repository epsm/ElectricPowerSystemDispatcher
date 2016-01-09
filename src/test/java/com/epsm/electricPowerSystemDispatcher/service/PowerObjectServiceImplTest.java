package com.epsm.electricPowerSystemDispatcher.service;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.epsm.electricPowerSystemDispatcher.model.domain.SavedGeneratorState;
import com.epsm.electricPowerSystemDispatcher.repository.SavedConsumerStateDao;
import com.epsm.electricPowerSystemDispatcher.repository.SavedGeneratorStateDao;
import com.epsm.electricPowerSystemModel.model.consumption.ConsumerState;
import com.epsm.electricPowerSystemModel.model.dispatch.State;
import com.epsm.electricPowerSystemModel.model.generation.PowerStationState;

@RunWith(MockitoJUnitRunner.class)
public class PowerObjectServiceImplTest {
	private PowerStationState powerStationState;
	private ConsumerState consumerState;
	
	@InjectMocks
	private PowerObjectServiceImpl service;
	
	@Mock
	private SavedGeneratorStateDao generatorDao;
	
	@Mock
	private SavedConsumerStateDao consumerDao;
	
	@Mock
	private ConsumerStateConverter consumerConverter;
	
	@Mock
	private PowerStationStateConverter powerStationConverter;
	
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
	@Before
	public void setUp(){
		List<SavedGeneratorState> convertedStates = new ArrayList<SavedGeneratorState>();
		SavedGeneratorState state = new SavedGeneratorState();
		convertedStates.add(state);
		when(powerStationConverter.convert(any())).thenReturn(convertedStates);
	}
	
	@Test
	public void exceptionIfStateisNull(){
		expectedEx.expect(IllegalArgumentException.class);
	    expectedEx.expectMessage("State must not be null.");
		
		service.savePowerObjectState(null);
	}
	
	@Test
	public void savesPowerStationState(){
		powerStationState = new PowerStationState(1, LocalDateTime.MIN, LocalTime.MIN, 1, 1);
		
		service.savePowerObjectState(powerStationState);
		
		verify(generatorDao).saveState(any());
	}
	
	@Test
	public void usesPowerStationStateConverterToConvertPowerStationState(){
		powerStationState = new PowerStationState(1, LocalDateTime.MIN, LocalTime.MIN, 1, 1);
		
		service.savePowerObjectState(powerStationState);
		
		verify(powerStationConverter).convert(powerStationState);
	}
	
	@Test
	public void savesConsumerState(){
		consumerState = new ConsumerState(1, LocalDateTime.MIN, LocalTime.MIN, 1);
		
		service.savePowerObjectState(consumerState);
		
		verify(consumerDao).saveState(any());
	}
	
	@Test
	public void usesConsumerStateConverterToConvertConsumerState(){
		consumerState = new ConsumerState(1, LocalDateTime.MIN, LocalTime.MIN, 1);
		
		service.savePowerObjectState(consumerState);
		
		verify(consumerConverter).convert(consumerState);
	}
	
	@Test
	public void exceptionIfStateUnsuported(){
		expectedEx.expect(IllegalArgumentException.class);
	    expectedEx.expectMessage("Unsuported type of State: UnsupportedState.");
		
		service.savePowerObjectState(new UnsupportedState(1, LocalDateTime.MIN, LocalTime.MIN));
	}
	
	private class UnsupportedState extends State{
		public UnsupportedState(long powerObjectId, LocalDateTime realTimeStamp, LocalTime simulationTimeStamp) {
			super(powerObjectId, realTimeStamp, simulationTimeStamp);
		}

		@Override
		public String toString() {
			return null;
		}
	}
}

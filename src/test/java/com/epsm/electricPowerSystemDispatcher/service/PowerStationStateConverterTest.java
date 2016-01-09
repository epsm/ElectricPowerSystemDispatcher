package com.epsm.electricPowerSystemDispatcher.service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.epsm.electricPowerSystemDispatcher.model.domain.SavedGeneratorState;
import com.epsm.electricPowerSystemModel.model.generation.GeneratorState;
import com.epsm.electricPowerSystemModel.model.generation.PowerStationState;

public class PowerStationStateConverterTest {
	private PowerStationStateConverter converter;
	private PowerStationState powerStationState;
	private List<SavedGeneratorState> convertedStates;
	private SavedGeneratorState firstConvertedGeneratoeState;
	private SavedGeneratorState secondConvertedGeneratoeState;
	private final LocalDateTime REAL_TIME_TIMESTAMP = LocalDateTime.of(1, 2, 3, 4, 5, 6, 7);
	private final LocalTime SIMULATION_TIMESTAMP = LocalTime.of(1, 2, 3, 4);
	private final int POWER_STATION_ID = 684646;
	private final int QUANTITY_OF_GENERATORS = 2;
	private final float FREQUENCY = 49.6567f;
	private final int FIRST_GENERATOR_NUMBER = 464;
	private final int SECOND_GENERATOR_NUMBER = 778;
	private final float FIRST_GENERATOR_GENERATION = 998.448f;
	private final float SECOND_GENERATOR_GENERATION = 746.885f;
	
	@Before
	public void setUp(){
		converter = new PowerStationStateConverter();
		powerStationState = new PowerStationState(POWER_STATION_ID, REAL_TIME_TIMESTAMP,
				SIMULATION_TIMESTAMP, QUANTITY_OF_GENERATORS, FREQUENCY);
		GeneratorState state_1 = new GeneratorState(FIRST_GENERATOR_NUMBER,
				FIRST_GENERATOR_GENERATION);
		GeneratorState state_2 = new GeneratorState(SECOND_GENERATOR_NUMBER,
				SECOND_GENERATOR_GENERATION);
			
		powerStationState.addGeneratorState(state_1);
		powerStationState.addGeneratorState(state_2);
		convertedStates = converter.convert(powerStationState);
		firstConvertedGeneratoeState = convertedStates.get(0);
		secondConvertedGeneratoeState = convertedStates.get(1);
	}
	
	@Test
	public void convertedListKeepsTheSameQuantityOfGeneratorsAsPowerStationState(){
		Assert.assertEquals(QUANTITY_OF_GENERATORS, convertedStates.size());
	}
	
	@Test
	public void firstGeneratorHasTheSamePowerStationNumberAsPowerStationState(){
		Assert.assertEquals(POWER_STATION_ID,
				firstConvertedGeneratoeState.getPowerStationId());
	}
	
	@Test
	public void firstGeneratorHasTheSameGeneratorNumberAsInPowerStationState(){
		Assert.assertEquals(FIRST_GENERATOR_NUMBER,
				firstConvertedGeneratoeState.getGeneratorNumber());
	}
	
	@Test
	public void firstGeneratorHasTheSameGenerationAsInPowerStationState(){
		Assert.assertEquals(FIRST_GENERATOR_GENERATION,
				firstConvertedGeneratoeState.getGenerationInWM(), 0);
	}
	
	@Test
	public void firstGeneratorHasTheSameFrequencyAsPowerStationState(){
		Assert.assertEquals(FREQUENCY,
				firstConvertedGeneratoeState.getFrequency(), 0);
	}
	
	@Test
	public void firstGeneratorHasTheSameSimulationTimeStampAsPowerStationState(){
		Assert.assertEquals(SIMULATION_TIMESTAMP,
				firstConvertedGeneratoeState.getSimulationTimeStamp());
	}
	
	@Test
	public void firstGeneratorHasTheSameRealTimeTimeStampAsPowerStationState(){
		Assert.assertEquals(REAL_TIME_TIMESTAMP,
				firstConvertedGeneratoeState.getRealTimeStamp());
	}
	
	@Test
	public void secondGeneratorHasTheSamePowerStationNumberAsPowerStationState(){
		Assert.assertEquals(POWER_STATION_ID,
				secondConvertedGeneratoeState.getPowerStationId());
	}
	
	@Test
	public void secondGeneratorHasTheSameGeneratorNumberAsInPowerStationState(){
		Assert.assertEquals(SECOND_GENERATOR_NUMBER,
				secondConvertedGeneratoeState.getGeneratorNumber());
	}
	
	@Test
	public void secondGeneratorHasTheSameGenerationAsInPowerStationState(){
		Assert.assertEquals(SECOND_GENERATOR_GENERATION,
				secondConvertedGeneratoeState.getGenerationInWM(), 0);
	}
	
	@Test
	public void secondGeneratorHasTheSameFrequencyAsPowerStationState(){
		Assert.assertEquals(FREQUENCY,
				secondConvertedGeneratoeState.getFrequency(), 0);
	}
	
	@Test
	public void secondGeneratorHasTheSameSimulationTimeStampAsPowerStationState(){
		Assert.assertEquals(SIMULATION_TIMESTAMP,
				secondConvertedGeneratoeState.getSimulationTimeStamp());
	}
	
	@Test
	public void secondGeneratorHasTheSameRealTimeTimeStampAsPowerStationState(){
		Assert.assertEquals(REAL_TIME_TIMESTAMP,
				secondConvertedGeneratoeState.getRealTimeStamp());
	}
}

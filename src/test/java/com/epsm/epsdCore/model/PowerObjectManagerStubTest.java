package com.epsm.epsdCore.model;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.epsm.epsmCore.model.consumption.ConsumerParametersStub;
import com.epsm.epsmCore.model.dispatch.Parameters;
import com.epsm.epsmCore.model.generalModel.TimeService;
import com.epsm.epsmCore.model.generation.PowerStationGenerationSchedule;
import com.epsm.epsmCore.model.generation.PowerStationParameters;

public class PowerObjectManagerStubTest {
	private TimeService timeService;
	private PowerObjectManagerStub manager;
	private PowerStationParameters powerStationParameters;
	private ConsumerParametersStub consumerParameters;
	private final int POWER_OBJECT_ID = 468;
	private final LocalDateTime REAL_TIMESTAMP = LocalDateTime.MIN;
	private final LocalDateTime SIMULATION_TIMESTAMP = LocalDateTime.MIN;
	private final int QUANTITY_OF_GENERATORS = 2;
	private final long STATION_1_ID = 10;
	private final long STATION_2_ID = 30;
	private final long CONSUMER_1_ID = 20;
	private final long CONSUMER_2_ID = 40;
	
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
	@Before
	public void setUp(){
		timeService = new TimeService();
		manager = spy(new PowerObjectManagerStub(timeService));
		
		powerStationParameters = new PowerStationParameters(
				POWER_OBJECT_ID, REAL_TIMESTAMP, SIMULATION_TIMESTAMP, QUANTITY_OF_GENERATORS);
		consumerParameters = new ConsumerParametersStub(
				POWER_OBJECT_ID, REAL_TIMESTAMP, SIMULATION_TIMESTAMP);
	}
	
	@Test
	public void exceptionInConstructorIfTimeServiceIsNull(){
		expectedEx.expect(IllegalArgumentException.class);
	    expectedEx.expectMessage("PowerObjectManagerStub constructor: timeService can't be null.");
	
	    new PowerObjectManagerStub(null);
	}
	
	@Test
	public void registersPowerStation(){
		boolean powerStationRegistered 
				= manager.registerObjectIfItTypeIsKnown(powerStationParameters);
		
		Assert.assertTrue(powerStationRegistered);
	}
	
	@Test
	public void registersConsumer(){
		boolean consumerRegistered 
				= manager.registerObjectIfItTypeIsKnown(consumerParameters);
		
		Assert.assertTrue(consumerRegistered);
	}
	
	@Test
	public void doesNotRegisterUnknownPowerObject(){
		Parameters parameters = mock(Parameters.class);
		
		boolean consumerRegistered = manager.registerObjectIfItTypeIsKnown(parameters);

		Assert.assertFalse(consumerRegistered);
	}
	
	@Test
	public void doesNotRegisterPowerObjectWithNonUniqueId(){
		manager.registerObjectIfItTypeIsKnown(powerStationParameters);
		boolean registered = manager.registerObjectIfItTypeIsKnown(consumerParameters);
		
		Assert.assertFalse(registered);
	}
	
	@Test
	public void generatesSchedulesOnlyForPowerStations(){
		final int REGISTERD_GENERATORS = 2;
		registerTwoPowerStationAndTwoConsumers();
		List<PowerStationGenerationSchedule> schedules = manager.getPowerStationGenerationSchedules();
		
		Assert.assertEquals(REGISTERD_GENERATORS, schedules.size());
		Assert.assertEquals(STATION_1_ID, schedules.get(0).getPowerObjectId());
		Assert.assertEquals(STATION_2_ID, schedules.get(1).getPowerObjectId());
	}
	
	private void registerTwoPowerStationAndTwoConsumers(){
		Parameters stationParameters_1 = new PowerStationParameters(
				STATION_1_ID, REAL_TIMESTAMP, SIMULATION_TIMESTAMP, QUANTITY_OF_GENERATORS);
		Parameters stationParameters_2 = new PowerStationParameters(
				STATION_2_ID, REAL_TIMESTAMP, SIMULATION_TIMESTAMP, QUANTITY_OF_GENERATORS);
		Parameters consumerParameters_1 = new ConsumerParametersStub(
				CONSUMER_1_ID, REAL_TIMESTAMP, SIMULATION_TIMESTAMP);
		Parameters consumerParameters_2 = new ConsumerParametersStub(
				CONSUMER_2_ID, REAL_TIMESTAMP, SIMULATION_TIMESTAMP);
		
		manager.registerObjectIfItTypeIsKnown(stationParameters_1);
		manager.registerObjectIfItTypeIsKnown(consumerParameters_1);
		manager.registerObjectIfItTypeIsKnown(stationParameters_2);
		manager.registerObjectIfItTypeIsKnown(consumerParameters_2);
	}
	
	@Test
	public void isObjectRegisteredMethodReturnsTrueIfObjectRegistered(){
		manager.registerObjectIfItTypeIsKnown(consumerParameters);
		
		Assert.assertTrue(manager.isObjectRegistered(POWER_OBJECT_ID));
	}
	
	@Test
	public void isObjectRegisteredMethodReturnsFalseIfObjectNotRegistered(){
		Assert.assertFalse(manager.isObjectRegistered(POWER_OBJECT_ID));
	}
}

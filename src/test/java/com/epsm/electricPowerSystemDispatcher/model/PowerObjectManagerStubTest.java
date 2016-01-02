package com.epsm.electricPowerSystemDispatcher.model;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.util.Collections;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.epsm.electricPowerSystemDispatcher.service.OutgoingMessageService;
import com.epsm.electricPowerSystemModel.model.dispatch.ConsumerParametersStub;
import com.epsm.electricPowerSystemModel.model.dispatch.ConsumptionPermissionStub;
import com.epsm.electricPowerSystemModel.model.dispatch.PowerStationGenerationSchedule;
import com.epsm.electricPowerSystemModel.model.dispatch.PowerStationParameters;

@RunWith(MockitoJUnitRunner.class)
public class PowerObjectManagerStubTest {
	private PowerStationGenerationScheduleCalculatorStub calculator;
	private PowerStationGenerationSchedule schedule;
	private ConsumptionPermissionStub permission;
	private PowerStationParameters powerStationParameters;
	private ConsumerParametersStub consumerParameters;
	private final int POWER_OBJECT_ID = 468;
	
	@InjectMocks
	private PowerObjectManagerStub manager;
	
	@Mock
	private OutgoingMessageService service;
	
	@Before
	public void initialize(){
		calculator = new PowerStationGenerationScheduleCalculatorStub();
		schedule = calculator.getSchedule(POWER_OBJECT_ID);
		permission = new ConsumptionPermissionStub(POWER_OBJECT_ID);
		powerStationParameters = new PowerStationParameters(POWER_OBJECT_ID, Collections.EMPTY_MAP);
		consumerParameters = new ConsumerParametersStub(POWER_OBJECT_ID);
	}
	
	@Test
	public void sendsNotNulScheduleToPowerStationWithKnownParameters(){
		manager.rememberPowerObjectParameters(powerStationParameters);
		manager.sendMessage(POWER_OBJECT_ID);
		
		verify(service).sendMessageToPowerStation(isA(PowerStationGenerationSchedule.class));
	}
	
	@Test
	public void sendsNotNulMessagesToConsumersWithKnownPaarameters(){
		
	}
	
	@Ignore
	@Test
	public void sendsNothingToUnknownPowerObject(){
		
	}
}

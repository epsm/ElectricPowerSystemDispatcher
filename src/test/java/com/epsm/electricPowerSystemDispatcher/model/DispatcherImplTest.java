package com.epsm.electricPowerSystemDispatcher.model;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.epsm.electricPowerSystemDispatcher.service.PowerObjectService;
import com.epsm.electricPowerSystemModel.model.dispatch.PowerObjectParameters;
import com.epsm.electricPowerSystemModel.model.dispatch.PowerObjectState;
import com.epsm.electricPowerSystemModel.model.generalModel.TimeService;

public class DispatcherImplTest {
	private PowerObjectParameters parametersForTestObject;
	private PowerObjectState state;
	private TimeService timeService;
	private MultiTimer receivedMessagesTimer;
	private MultiTimer sentMessagesTimer;
	private PowerObjectManagerStub manager;
	private PowerObjectService powerObjectService;
	private Dispatcher dispatcher;
	private final long TEST_POWER_OBJECT_ID = 7676;
	private final int TIME_DEALY = 10;
	private LocalDateTime testTime;
	
	@Before
	public void initialize(){
		parametersForTestObject = mock(PowerObjectParameters.class);
		state = mock(PowerObjectState.class);
		timeService = mock(TimeService.class);
		receivedMessagesTimer = spy(new MultiTimer(timeService, TIME_DEALY));
		sentMessagesTimer = spy(new MultiTimer(timeService, TIME_DEALY));
		manager = mock(PowerObjectManagerStub.class);
		powerObjectService = mock(PowerObjectService.class);
		dispatcher = new Dispatcher(receivedMessagesTimer, sentMessagesTimer,
				powerObjectService, manager);
		
		when(parametersForTestObject.getPowerObjectId()).thenReturn(TEST_POWER_OBJECT_ID);
		when(state.getPowerObjectId()).thenReturn(TEST_POWER_OBJECT_ID);
		when(timeService.getCurrentTime()).thenReturn(LocalDateTime.of(2000, 01, 01, 00, 00));
		testTime = timeService.getCurrentTime();
	}
	
	@Test
	public void establishesConnectionWithPowerStations(){
		dispatcher.establishConnection(parametersForTestObject);
		
		verify(receivedMessagesTimer).startOrUpdateDelayOnTimeNumber(TEST_POWER_OBJECT_ID);
	}
	
	@Test
	public void establishedConnectionActiveWithinTimeDelay(){
		dispatcher.establishConnection(parametersForTestObject);
		addToTimeValueLessThanTimerDelayAndExecuteDispatcherTimeDependentOperation();
		
		Assert.assertTrue(receivedMessagesTimer.isTimerActive(TEST_POWER_OBJECT_ID));
	}
	
	private void addToTimeValueLessThanTimerDelayAndExecuteDispatcherTimeDependentOperation(){
		when(timeService.getCurrentTime()).thenReturn(testTime.plusSeconds(TIME_DEALY - 1));
		dispatcher.doRealTimeDependingOperations();
	}
	
	@Test
	public void acceptsAndSaveToDBMesagesFromPowerObjectIfConnectionEstablished(){
		dispatcher.establishConnection(parametersForTestObject);
		dispatcher.acceptState(state);
		
		verify(powerObjectService).savePowerObjectState(state);
	}
	
	@Test
	public void doNothingIfAcceptedPowerObjectMessageWasFromDisconnectedObject(){
		dispatcher.acceptState(state);
		
		verify(powerObjectService, never()).savePowerObjectState(state);
	}
	
	@Test
	public void sendsMessagesForActivePowerObjectsIfItIsTimeToSendMessage(){
		dispatcher.establishConnection(parametersForTestObject);
		addToTimeValueLessThanTimerDelayAndExecuteDispatcherTimeDependentOperation();
		
		verify(manager).sendMessage(eq(TEST_POWER_OBJECT_ID));
	}
	
	@Test
	public void notSendsMessagesForActivePowerObjectsIfItIsNotTimeToSendMessageToThem(){
		dispatcher.establishConnection(parametersForTestObject);
		addToTimeValueThanSeveralTimeLessThenDelayAndExecuteDispatcherTimeDependentOperation();
		addToTimeValueThanSeveralTimeLessThenDelayAndExecuteDispatcherTimeDependentOperation();
		
		verifiedConnectionStillActive();
		messageWasSentOnce();
	}
	
	private void addToTimeValueThanSeveralTimeLessThenDelayAndExecuteDispatcherTimeDependentOperation(){
		when(timeService.getCurrentTime()).thenReturn(testTime.plusSeconds(TIME_DEALY / 3));
		testTime = testTime.plusSeconds(TIME_DEALY / 3);
		dispatcher.doRealTimeDependingOperations();
	}
	
	private void verifiedConnectionStillActive(){
		Assert.assertTrue(receivedMessagesTimer.isTimerActive(TEST_POWER_OBJECT_ID));
	}
	
	private void messageWasSentOnce(){
		verify(manager).sendMessage(eq(TEST_POWER_OBJECT_ID));
	}
	
	@Test
	public void passPowerObjectParametersToPowerObjectManager(){
		dispatcher.establishConnection(parametersForTestObject);
		
		verify(manager).rememberPowerObjectParameters(parametersForTestObject);
	}
}

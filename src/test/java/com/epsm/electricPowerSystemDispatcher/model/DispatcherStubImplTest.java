package com.epsm.electricPowerSystemDispatcher.model;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import com.epsm.electricPowerSystemDispatcher.model.domain.SavedConsumerState;
import com.epsm.electricPowerSystemDispatcher.service.DispatcherService;
import com.epsm.electricPowerSystemDispatcher.service.PowerObjectService;
import com.epsm.electricPowerSystemModel.model.dispatch.ConsumerParameters;
import com.epsm.electricPowerSystemModel.model.dispatch.PowerObjectParameters;
import com.epsm.electricPowerSystemModel.model.dispatch.PowerObjectState;
import com.epsm.electricPowerSystemModel.model.dispatch.PowerStationGenerationSchedule;
import com.epsm.electricPowerSystemModel.model.dispatch.PowerStationParameters;
import com.epsm.electricPowerSystemModel.model.dispatch.PowerStationState;
import com.epsm.electricPowerSystemModel.model.generalModel.TimeService;

public class DispatcherStubImplTest {
	private PowerObjectParameters parameters;
	private PowerObjectState state;
	private TimeService timeService;
	private MultiTimer receivedMessagesTimer;
	private MultiTimer sentMessagesTimer;
	private MessageSender sender;
	private PowerObjectService powerObjectService;
	private DispatcherStub dispatcher;
	private final long POWER_OBJECT_ID = 7676;
	private final int TIME_DEALY = 10;
	private LocalDateTime startTestTime;
	
	@Before
	public void initialize(){
		parameters = mock(PowerObjectParameters.class);
		state = mock(PowerObjectState.class);
		timeService = mock(TimeService.class);
		receivedMessagesTimer = spy(new MultiTimer(timeService, TIME_DEALY));
		sentMessagesTimer = spy(new MultiTimer(timeService, TIME_DEALY));
		sender = mock(MessageSender.class);
		powerObjectService = mock(PowerObjectService.class);
		dispatcher = new DispatcherStub(receivedMessagesTimer, sentMessagesTimer,
				powerObjectService, sender);
		
		when(parameters.getPowerObjectId()).thenReturn(POWER_OBJECT_ID);
		when(state.getPowerObjectId()).thenReturn(POWER_OBJECT_ID);
		when(timeService.getCurrentTime()).thenReturn(LocalDateTime.of(2000, 01, 01, 00, 00));
		startTestTime = timeService.getCurrentTime();
	}
	
	@Test
	public void establishesConnectionWithPowerStations(){
		dispatcher.establishConnection(parameters);
		
		verify(receivedMessagesTimer).startOrUpdateDelayOnTimeNumber(POWER_OBJECT_ID);
	}
	
	@Test
	public void acceptsAndSaveToDBMesagesFromPowerObjectIfConnectionEstablished(){
		when(receivedMessagesTimer.isTimerActive(eq(POWER_OBJECT_ID))).thenReturn(true);
		dispatcher.acceptState(state);
		
		verify(powerObjectService).savePowerObjectState(state);
	}
	
	@Test
	public void doNothingIfAcceptedPowerStationMessageWasFromDisconnectedPowerStation(){
		when(receivedMessagesTimer.isTimerActive(eq(POWER_OBJECT_ID))).thenReturn(false);
		dispatcher.acceptState(state);
		
		verify(powerObjectService, never()).savePowerObjectState(state);
	}
	
	@Test
	public void callsMessageSenderForActivePowerObjectsIfItTimeToSendMessage(){
		dispatcher.establishConnection(parameters);
		dispatcher.doRealTimeDependOperation();
		verify(sender).sendMessage(eq(POWER_OBJECT_ID));
	}
	
	@Test
	public void notCallsMessageSenderForActivePowerObjectsIfItNotTimeToSendMessage(){
		dispatcher.establishConnection(parameters);
		dispatcher.doRealTimeDependOperation();
		when(timeService.getCurrentTime()).thenReturn(startTestTime.plusSeconds(TIME_DEALY - 1));
		dispatcher.doRealTimeDependOperation();
		
		verify(sender).sendMessage(eq(POWER_OBJECT_ID));
	}
	
	/*private void doObjectWithParametersNotApropriateToSendingMesage(){
		Set<Long> notApropriatePowerObjectsToSendMessage = new HashSet<Long>();
		notApropriatePowerObjectsToSendMessage.add(POWER_OBJECT_ID);
		when(sentMessagesTimer.isTimerActive(eq(POWER_OBJECT_ID))).thenReturn(false);
		when(sentMessagesTimer.getActiveTimers()).thenReturn(notApropriatePowerObjectsToSendMessage);
	}*/
}

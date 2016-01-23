package com.epsm.epsdCore.model;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.epsm.epsmCore.model.consumption.ConsumerState;
import com.epsm.epsmCore.model.dispatch.Parameters;
import com.epsm.epsmCore.model.dispatch.State;
import com.epsm.epsmCore.model.generation.PowerStationGenerationSchedule;
import com.epsm.epsmCore.model.generation.PowerStationParameters;

public class DispatcherImplTest {
	private PowerObjectManagerStub objectManager;
	private StateSaver saver;
	private ObjectsConnector connector;
	private DispatcherImpl dispatcher;
	private Parameters parameters;
	private State state;
	private PowerStationGenerationSchedule generationSchedule;
	private final long OBJECT_ID = 6464;
	
	@Before
	public void setUp(){
		objectManager = mock(PowerObjectManagerStub.class);
		saver = mock(StateSaver.class);
		connector = mock(ObjectsConnector.class);
		dispatcher = new DispatcherImpl(objectManager, saver, connector);
		parameters = mock(PowerStationParameters.class);
		state = new ConsumerState(OBJECT_ID, LocalDateTime.MIN, LocalDateTime.MIN, 100);
		generationSchedule = mock(PowerStationGenerationSchedule.class);
		ArrayList<PowerStationGenerationSchedule> schedules = new ArrayList<PowerStationGenerationSchedule>();
		
		schedules.add(generationSchedule);
		when(parameters.getPowerObjectId()).thenReturn(OBJECT_ID);
		when(objectManager.getPowerStationGenerationSchedules()).thenReturn(schedules);
	}
	
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
	@Test
	public void exceptionInConstructorIfTimeServiceIsNull(){
		expectedEx.expect(IllegalArgumentException.class);
	    expectedEx.expectMessage("DispatcherImpl constructor: objectManager can't be null.");
		
		new DispatcherImpl(null, saver, connector);
	}
	
	@Test
	public void exceptionInConstructorIfStateSaverIsNull(){
		expectedEx.expect(IllegalArgumentException.class);
	    expectedEx.expectMessage("DispatcherImpl constructor: saver can't be null.");
		
		new DispatcherImpl(objectManager, null, connector);
	}
	
	@Test
	public void exceptionInConstructorIfObjectsConnectorIsNull(){
		expectedEx.expect(IllegalArgumentException.class);
	    expectedEx.expectMessage("DispatcherImpl constructor: connector can't be null.");
		
		new DispatcherImpl(objectManager, saver, null);
	}
	
	@Test
	public void doNothingIfRegisterObjectMethodParametersIsNUll(){
		dispatcher.registerObject(null);
		
		verify(connector, never()).sendCommand(any());
		verify(saver, never()).savePowerObjectState(any());
		verify(objectManager, never()).registerObjectIfItTypeIsKnown(any());
	}
	
	@Test
	public void passesNotNullObjectParametersToPowerObjectManager(){
		dispatcher.registerObject(parameters);
		
		verify(objectManager).registerObjectIfItTypeIsKnown(parameters);
	}
	
	@Test
	public void returnsTrueIfObjectManagerRegisteredObject(){
		when(objectManager.registerObjectIfItTypeIsKnown(parameters)).thenReturn(true);
		
		Assert.assertTrue(dispatcher.registerObject(parameters));
	}
	
	@Test
	public void returnsFalseIfObjectManagerDidnotRegisterObject(){
		when(objectManager.registerObjectIfItTypeIsKnown(parameters)).thenReturn(false);
		
		Assert.assertFalse(dispatcher.registerObject(parameters));
	}
	
	@Test
	public void doNothingIfAcceptedNUllState(){
		dispatcher.acceptState(null);
		
		verify(connector, never()).sendCommand(any());
		verify(saver, never()).savePowerObjectState(any());
		verify(objectManager, never()).registerObjectIfItTypeIsKnown(any());
	}
	
	@Test
	public void doNothingIfAcceptedUnknownState(){
		dispatcher.acceptState(mock(State.class));
		
		verify(connector, never()).sendCommand(any());
		verify(saver, never()).savePowerObjectState(any());
		verify(objectManager, never()).registerObjectIfItTypeIsKnown(any());
	}
	
	@Test
	public void savesStateIfObjectRegistered(){
		when(objectManager.isObjectRegistered(OBJECT_ID)).thenReturn(true);
		
		dispatcher.acceptState(state);
		
		verify(saver).savePowerObjectState(state);
	}
	
	@Test
	public void doNothingIfAcceptedStateFromUnregisterObject(){
		when(objectManager.isObjectRegistered(OBJECT_ID)).thenReturn(false);
		
		dispatcher.acceptState(state);
		
		verify(saver, never()).savePowerObjectState(state);
	}
	
	@Test
	public void retrievesSimulationTimeFromObjectsConnectorIfDoRealTimeOperationsInvoked(){
		when(connector.getDateTimeInSimulation()).thenReturn(LocalDateTime.MIN);
		
		dispatcher.doRealTimeDependingOperations();
		
		verify(connector).getDateTimeInSimulation();
	}
	
	@Test
	public void retrievesGenerationSchedulesFromObjectManagerIfItIsTime(){
		makeTimeToSendSchedule();
		
		dispatcher.doRealTimeDependingOperations();
		
		verify(objectManager).getPowerStationGenerationSchedules();
	}
	
	private void makeTimeToSendSchedule(){
		when(connector.getDateTimeInSimulation()).thenReturn(LocalDateTime.MAX);
	}
	
	@Test
	public void doNotRetrieveGenerationSchedulesFromObjectManagerIfItIsNotTimeTime(){
		makeTimeNotToSendSchedule();
		
		dispatcher.doRealTimeDependingOperations();
		
		verify(objectManager, never()).getPowerStationGenerationSchedules();
	}
	
	private void makeTimeNotToSendSchedule(){
		when(connector.getDateTimeInSimulation()).thenReturn(LocalDateTime.MIN);
	}
	
	@Test
	public void sendsGenerationSchedulesIfItIsTime(){
		makeTimeToSendSchedule();
		
		dispatcher.doRealTimeDependingOperations();
		
		verify(connector).sendCommand(generationSchedule);
	}
	
	@Test
	public void doNotSendSchedulesIfItIsNotTimeTime(){
		makeTimeNotToSendSchedule();
		
		dispatcher.doRealTimeDependingOperations();
		
		verify(connector, never()).sendCommand(generationSchedule);
	}
	
	@Test
	public void doNotSendSchedulesIfDateAppropriateButTimeNot(){
		makeAppropriateDateButUnappropriateTime();
		
		dispatcher.doRealTimeDependingOperations();
		
		verify(connector, never()).sendCommand(generationSchedule);
	}
	
	private void makeAppropriateDateButUnappropriateTime(){
		LocalDate date = LocalDate.MAX;
		LocalTime time = Constants.HOUR_TO_SEND_MESSAGE.minusNanos(1);
		
		when(connector.getDateTimeInSimulation()).thenReturn(LocalDateTime.of(date, time));
	}
}

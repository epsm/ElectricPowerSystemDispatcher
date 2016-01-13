package com.epsm.epsdCore.model;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.epsm.epsmCore.model.consumption.ConsumerParametersStub;
import com.epsm.epsmCore.model.consumption.ConsumerState;
import com.epsm.epsmCore.model.consumption.ConsumptionPermissionStub;
import com.epsm.epsmCore.model.dispatch.Command;
import com.epsm.epsmCore.model.dispatch.Parameters;
import com.epsm.epsmCore.model.dispatch.State;
import com.epsm.epsmCore.model.generalModel.TimeService;

public class DispatcherImplTest {
	private DispatcherImpl dispatcher;
	private ConnectionManager connectionManager;
	private PowerObjectManagerStub objectManager;
	private Parameters parameters;
	private State state;
	private Command command;
	private StateSaver saver;
	private ObjectsConnector connector;
	private TimeService timeService;
	private final int OBJECT_ID = 6464;
	
	@Before
	public void setUp(){
		timeService = mock(TimeService.class);
		saver = mock(StateSaver.class);
		connector = mock(ObjectsConnector.class);
		dispatcher = new DispatcherImpl(timeService, saver, connector);
		connectionManager = spy(new ConnectionManager(timeService));
		objectManager = spy(new PowerObjectManagerStub(timeService, dispatcher));
		parameters = new ConsumerParametersStub(OBJECT_ID, LocalDateTime.MIN, LocalTime.MIN);
		state = new ConsumerState(OBJECT_ID, LocalDateTime.MIN, LocalTime.MIN, 100);
		command = new ConsumptionPermissionStub(OBJECT_ID, LocalDateTime.MIN, LocalTime.MIN);
		
		dispatcher.setConnectionManager(connectionManager);
		dispatcher.setObjectManager(objectManager);
		when(timeService.getCurrentTime()).thenReturn(LocalDateTime.MIN);
	}
	
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
	@Test
	public void exceptionInConstructorIfTimeServiceIsNull(){
		expectedEx.expect(IllegalArgumentException.class);
	    expectedEx.expectMessage("DispatcherImpl constructor: timeService must not be null.");
		
		new DispatcherImpl(null, saver, connector);
	}
	
	@Test
	public void exceptionInConstructorIfStateSaverIsNull(){
		expectedEx.expect(IllegalArgumentException.class);
	    expectedEx.expectMessage("DispatcherImpl constructor: saver must not be null.");
		
		new DispatcherImpl(timeService, null, connector);
	}
	
	@Test
	public void exceptionInConstructorIfObjectsConnectorIsNull(){
		expectedEx.expect(IllegalArgumentException.class);
	    expectedEx.expectMessage("DispatcherImpl constructor: connector must not be null.");
		
		new DispatcherImpl(timeService, saver, null);
	}
	
	@Test
	public void doNothingIfInEstablishConnectionMethodParametersIsNUll(){
		dispatcher.establishConnection(null);
		
		verify(connector, never()).sendCommand(any());
		verify(saver, never()).savePowerObjectState(any());
		verify(objectManager, never()).rememberObjectIfItTypeIsKnown(any());
	}
	
	@Test
	public void passesNotNullParametersToPowerObjectManager(){
		dispatcher.establishConnection(parameters);
		
		verify(objectManager).rememberObjectIfItTypeIsKnown(parameters);
	}
	
	@Test
	public void refreshesConnectionTimeoutIfParameteesRecognized(){
		dispatcher.establishConnection(parameters);
		
		verify(connectionManager).refreshTimeout(OBJECT_ID);
	}
	
	@Test
	public void doesNotrefreshConnectionTimeoutIfParameteesNotRecognized(){
		parameters = new UnknownParameters(OBJECT_ID, LocalDateTime.MIN, LocalTime.MIN);
		dispatcher.establishConnection(parameters);
		
		verify(connectionManager, never()).refreshTimeout(OBJECT_ID);
	}
	
	private class UnknownParameters extends Parameters{
		public UnknownParameters(long powerObjectId, LocalDateTime realTimeStamp,
				LocalTime simulationTimeStamp) {
			
			super(powerObjectId, realTimeStamp, simulationTimeStamp);
		}

		@Override
		public String toString() {
			return null;
		}
	}
	
	@Test
	public void doNothingIfAcceptedNUllState(){
		dispatcher.acceptState(null);
		
		verify(connector, never()).sendCommand(any());
		verify(saver, never()).savePowerObjectState(any());
		verify(objectManager, never()).rememberObjectIfItTypeIsKnown(any());
	}
	
	@Test
	public void refreshesConnectionTimeoutAfterRecievingStateIfConnectionISActive(){
		dispatcher.establishConnection(parameters);
		dispatcher.acceptState(state);
		
		verify(connectionManager, times(2)).refreshTimeout(OBJECT_ID);
	}
	
	@Test
	public void doesNotRefreshesConnectionTimeoutAfterRecievingStateIfConnectionISNotActive(){
		dispatcher.acceptState(state);
		
		verify(connectionManager, never()).refreshTimeout(OBJECT_ID);
	}
	
	@Test
	public void savesStateAfterRecievingStateIfConnectionISActive(){
		dispatcher.establishConnection(parameters);
		dispatcher.acceptState(state);
		
		verify(saver).savePowerObjectState(state);
	}
	
	@Test
	public void sendsCommandsForActiveConnections(){
		dispatcher.establishConnection(parameters);
		dispatcher.doRealTimeDependingOperations();
		
		verify(connector).sendCommand(isA(Command.class));
	}
	
	@Test
	public void refreshesServedTimeAfterSendingCommand(){
		dispatcher.sendCommand(command);
		
		verify(connectionManager).refreshLastSentCommandTime(OBJECT_ID);
	}
}

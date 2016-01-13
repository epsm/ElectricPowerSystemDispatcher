package com.epsm.epsdCore.model;

import static org.mockito.Mockito.mock;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.epsm.epsmCore.model.generalModel.TimeService;

public class DispatcherFactoryTest {
	private StateSaver saver;
	private ObjectsConnector connector;
	private TimeService timeService;
	private DispatcherFactory factory;
	
	@Before
	public void setUp(){
		timeService = mock(TimeService.class);
		saver = mock(StateSaver.class);
		connector = mock(ObjectsConnector.class);
		factory = new DispatcherFactory(timeService, saver, connector);
	}
	
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
	@Test
	public void exceptionInConstructorIfTimeServiceIsNull(){
		expectedEx.expect(IllegalArgumentException.class);
	    expectedEx.expectMessage("DispatcherFactory constructor: timeService must not be null.");
	    
		new DispatcherFactory(null, saver, connector);
	}
	
	@Test
	public void exceptionInConstructorIfStateSaverIsNull(){
		expectedEx.expect(IllegalArgumentException.class);
	    expectedEx.expectMessage("DispatcherFactory constructor: saver must not be null.");
		
		new DispatcherFactory(timeService, null, connector);
	}
	
	@Test
	public void exceptionInConstructorIfObjectsConnectorIsNull(){
		expectedEx.expect(IllegalArgumentException.class);
	    expectedEx.expectMessage("DispatcherFactory constructor: connector must not be null.");
		
		new DispatcherFactory(timeService, saver, null);
	}
}

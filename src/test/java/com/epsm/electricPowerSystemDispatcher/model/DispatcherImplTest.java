package test.java.com.epsm.electricPowerSystemDispatcher.model;

import org.junit.Before;
import org.junit.Test;

import main.java.com.epsm.electricPowerSystemDispatcher.model.DispatcherImpl;
import main.java.com.epsm.electricPowerSystemModel.model.control.DefaultConfigurator;
import main.java.com.epsm.electricPowerSystemModel.model.control.SimulationRunner;
import main.java.com.epsm.electricPowerSystemModel.model.dispatch.Dispatcher;
import main.java.com.epsm.electricPowerSystemModel.model.generalModel.ElectricPowerSystemSimulation;
import main.java.com.epsm.electricPowerSystemModel.model.generalModel.ElectricPowerSystemSimulationImpl;

public class DispatcherImplTest {
	private Dispatcher dispatcher;
	private SimulationRunner runner;
	
	
	@Before
	public void initialize(){
		ElectricPowerSystemSimulation system = new ElectricPowerSystemSimulationImpl();
		DefaultConfigurator configurator = new DefaultConfigurator();
		dispatcher = new DispatcherImpl();
		runner = new SimulationRunner();
		
		configurator.initialize(system, dispatcher);		
	}
	
	@Test
	public void dispatcherSendsPreparedScheduleToPowerStation(){
		
	}
	
	@Test
	public void dispatcherGetsDataFromDispatcheredObjects(){
		
	}
	
	@Test
	public void dispatcherSavesReceivedDataToDao(){
		
	}
}

package main.java.com.epsm.electricPowerSystemDispatcher.model;

import main.java.com.epsm.electricPowerSystemModel.model.dispatch.Dispatcher;
import main.java.com.epsm.electricPowerSystemModel.model.dispatch.MainControlPanel;
import main.java.com.epsm.electricPowerSystemModel.model.dispatch.ObjectToBeDispatching;
import main.java.com.epsm.electricPowerSystemModel.model.dispatch.PowerObjectState;
import test.java.com.epsm.electricPowerSystemModel.model.dispatch.MainControlPanelTest;

public class DispatcherImpl implements Dispatcher{
	private ObjectToBeDispatching object;
	
	
	@Override
	public void registerPowerObject(ObjectToBeDispatching powerObject) {
		
		// TODO Auto-generated method stub
		
	}

	private boolean isObjectPowerStation(){
		return object instanceof MainControlPanel;
	}
	
	@Override
	public void acceptReport(PowerObjectState report) {
		// TODO Auto-generated method stub
		
	}

	
}

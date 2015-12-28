package com.epsm.electricPowerSystemDispatcher.model;

import org.springframework.beans.factory.annotation.Autowired;

public class ConnectionKeeper {
	
	@Autowired
	private ActiveConnectionContainer powerStations;
	
	@Autowired
	private ActiveConnectionContainer consumers;
	
	public void addOrUpdatePowerStationConnection(int powerStationNumber){
		powerStations.addOrUpdateConnection(powerStationNumber);
	}
	
	public void addOrUpdateConsumerConnection(int consumerNumber){
		consumers.addOrUpdateConnection(consumerNumber);
	}
	
	public void manageConnections(){
		powerStations.manageConnections();
		consumers.manageConnections();
	}

	public boolean isConnectionWithPowerStationActive(int powerStationNumber) {
		return powerStations.isConnectionActive(powerStationNumber);
	}
	
	public boolean isConnectionWithConsumerActive(int consumerNumber) {
		return consumers.isConnectionActive(consumerNumber);
	}
}

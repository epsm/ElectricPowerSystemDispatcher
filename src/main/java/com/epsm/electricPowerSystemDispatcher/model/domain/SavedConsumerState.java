package com.epsm.electricPowerSystemDispatcher.model.domain;

import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="consumer_state")
public class SavedConsumerState{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private long id;
	
	@Column(name="consumerId")
	private long consumerId;
	
	@Column(name="load")
	private float load;
	
	@Column(name="simulationTimeStamp")
	private LocalTime simulationTimeStamp;
	
	@Column(name="realTimeStamp")
	protected LocalDateTime realTimeStamp;

	public long getConsumerId() {
		return consumerId;
	}

	public void setConsumerId(long consumerId) {
		this.consumerId = consumerId;
	}

	public float getLoad() {
		return load;
	}

	public void setLoad(float load) {
		this.load = load;
	}

	public LocalTime getSimulationTimeStamp() {
		return simulationTimeStamp;
	}

	public void setSimulationTimeStamp(LocalTime simulationTimeStamp) {
		this.simulationTimeStamp = simulationTimeStamp;
	}

	public LocalDateTime getRealTimeStamp() {
		return realTimeStamp;
	}

	public void setRealTimeStamp(LocalDateTime realTimeStamp) {
		this.realTimeStamp = realTimeStamp;
	}

	public long getId() {
		return id;
	}
}

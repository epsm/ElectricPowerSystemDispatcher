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
@Table(name="generator_state")
public class SavedGeneratorState{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private long id;
	
	@Column(name="powerStationId")
	private  long powerStationId;
	
	@Column(name="generator_number")
	private  int generatorNumber;
	
	@Column(name="generation")
	private float generationInMW;
	
	@Column(name="frequency")
	private float frequency;

	@Column(name="simulationTimeStamp")
	private LocalTime simulationTimeStamp;
	
	@Column(name="realTimeStamp")
	protected LocalDateTime realTimeStamp;

	public long getPowerStationId() {
		return powerStationId;
	}

	public void setPowerStationId(long powerStationId) {
		this.powerStationId = powerStationId;
	}

	public int getGeneratorNumber() {
		return generatorNumber;
	}

	public void setGeneratorNumber(int generatorNumber) {
		this.generatorNumber = generatorNumber;
	}

	public float getGenerationInWM() {
		return generationInMW;
	}

	public void setGenerationInMW(float generationInWM) {
		this.generationInMW = generationInWM;
	}

	public float getFrequency() {
		return frequency;
	}

	public void setFrequency(float frequency) {
		this.frequency = frequency;
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

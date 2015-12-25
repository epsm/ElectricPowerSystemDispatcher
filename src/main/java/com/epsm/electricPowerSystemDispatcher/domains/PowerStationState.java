package com.epsm.electricPowerSystemDispatcher.domains;

import java.time.LocalTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "StationsState")
public class PowerStationState{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="station_id")
	private long id;
	
	@Column(name="number", unique=true)
	private int powerStationNumber;
	
	@Column(name="frequency")
	private float frequency;
	
	@Column(name="timestamp")
	private LocalTime timeStamp;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL) 
	@JoinColumn(name="station_number", referencedColumnName="number")
	private Set<GeneratorState> generatorsStates;

	public long getId() {
		return id;
	}

	public int getPowerStationNumber() {
		return powerStationNumber;
	}

	public void setPowerStationNumber(int powerStationNumber) {
		this.powerStationNumber = powerStationNumber;
	}

	public float getFrequency() {
		return frequency;
	}

	public void setFrequency(float frequency) {
		this.frequency = frequency;
	}

	public LocalTime getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(LocalTime timeStamp) {
		this.timeStamp = timeStamp;
	}

	public Set<GeneratorState> getGeneratorsStates() {
		return generatorsStates;
	}

	public void setGeneratorsStates(Set<GeneratorState> generatorsStates) {
		this.generatorsStates = generatorsStates;
	}
}

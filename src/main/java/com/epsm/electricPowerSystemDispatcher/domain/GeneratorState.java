package com.epsm.electricPowerSystemDispatcher.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="generator_state")
public class GeneratorState{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="state_id")
	private long id;
	
	@Column(name="station")
	private  int station;
	
	@Column(name="number")
	private  int generatorNumber;
	
	@Column(name="generation")
	private float generationInWM;

	public long getId() {
		return id;
	}

	public int getStationNumber() {
		return station;
	}

	public void setStationNumber(int stationNumber) {
		this.station = stationNumber;
	}

	public int getGeneratorNumber() {
		return generatorNumber;
	}

	public void setGeneratorNumber(int generatorNumber) {
		this.generatorNumber = generatorNumber;
	}

	public float getGenerationInWM() {
		return generationInWM;
	}

	public void setGenerationInWM(float generationInWM) {
		this.generationInWM = generationInWM;
	}
}

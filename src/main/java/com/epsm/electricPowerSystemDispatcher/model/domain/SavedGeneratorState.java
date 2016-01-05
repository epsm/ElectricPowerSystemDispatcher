package com.epsm.electricPowerSystemDispatcher.model.domain;

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
	
	@Column(name="station")
	private  int station;
	
	@Column(name="generator_number")
	private  int generatorNumber;
	
	@Column(name="generation")
	private float generationInWM;
	
	@Column(name="frequency")
	private float frequency;

	@Column(name="timestamp")
	private LocalTime timeStamp;

	public long getId() {
		return id;
	}

	public int getStationNumber() {
		return station;
	}

	public void setStationNumber(int station) {
		this.station = station;
	}

	public int getGeneratorNumber() {
		return generatorNumber;
	}

	public void setGeneratorNumber(int number) {
		this.generatorNumber = number;
	}

	public float getGenerationInWM() {
		return generationInWM;
	}

	public void setGenerationInWM(float generationInWM) {
		this.generationInWM = generationInWM;
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
}

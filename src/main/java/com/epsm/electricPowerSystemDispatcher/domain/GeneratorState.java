package com.epsm.electricPowerSystemDispatcher.domain;

import java.time.LocalTime;

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
	@Column(name="id")
	private long id;
	
	@Column(name="station")
	private  int station;
	
	@Column(name="number")
	private  int number;
	
	@Column(name="generation")
	private float generationInWM;
	
	@Column(name="frequency")
	private float frequency;

	@Column(name="timestamp")
	private LocalTime timeStamp;

	public long getId() {
		return id;
	}

	public int getStation() {
		return station;
	}

	public void setStation(int station) {
		this.station = station;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
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


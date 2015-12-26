package com.epsm.electricPowerSystemDispatcher.model.domain;

import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="consumer_state")
public class ConsumerState{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private long id;
	
	@Column(name="consumer_number")
	private int consumerNumber;
	
	@Column(name="load")
	private float load;
	
	@Column(name="timestamp")
	private LocalTime timeStamp;

	public int getConsumerNumber() {
		return consumerNumber;
	}

	public void setNumber(int number) {
		this.consumerNumber = number;
	}

	public float getLoad() {
		return load;
	}

	public void setLoad(float load) {
		this.load = load;
	}

	public LocalTime getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(LocalTime timeStamp) {
		this.timeStamp = timeStamp;
	}

	public long getId() {
		return id;
	}
}

package com.epsm.electricPowerSystemDispatcher.domains;

import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class ConsumerState{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="station_id")
	private long id;
	
	@Column(name="number", unique=true)
	private int consumerNumber;
	
	@Column(name="load")
	private float load;
	
	@Column(name="timestamp")
	private LocalTime timeStamp;
}

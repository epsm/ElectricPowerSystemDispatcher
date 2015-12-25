package com.epsm.electricPowerSystemDispatcher.domains;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "GeneratorStates")
public class GeneratorState{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="generator_id")
	private long id;
	
	@Column(name="number", unique=true)
	private  int generatorNumber;
	
	@Column(name="generation")
	private float generationInWM;

	public long getId() {
		return id;
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

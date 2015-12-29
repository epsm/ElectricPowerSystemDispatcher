package com.epsm.electricPowerSystemDispatcher.model;

import com.epsm.electricPowerSystemModel.model.bothConsumptionAndGeneration.LoadCurve;
import com.epsm.electricPowerSystemModel.model.dispatch.GeneratorGenerationSchedule;
import com.epsm.electricPowerSystemModel.model.dispatch.PowerStationGenerationSchedule;

//It is just stub. More complex model see com.epsm.electricPowerSystemModel.model.*;
public class PowerStationGenerationScheduleCalculatorStub {
	private final static float[] GENERATION_BY_HOURS = new float[]{
			55.15f,  50.61f,  47.36f,  44.11f, 	41.20f,  41.52f,
			40.87f,  48.66f,  64.89f,  77.86f,  85.00f,  84.34f,
			77.86f,  77.86f,  77.53f,  77.20f,  77.20f,  77.20f,
			77.20f,  77.20f,  77.20f,  77.20f,  77.20f,  77.20f 
	};
	
	//TODO calculate schedule according to power station parameters and situations with consumers. 
	public PowerStationGenerationSchedule getSchedule(long powerStationId){
		return createSchedule(powerStationId);
	}
	
	private PowerStationGenerationSchedule createSchedule(long powerStationId){
		LoadCurve generationCurve;
		PowerStationGenerationSchedule generationSchedule;
		GeneratorGenerationSchedule genrationSchedule_1;
		GeneratorGenerationSchedule genrationSchedule_2;
		
		generationSchedule = new PowerStationGenerationSchedule(powerStationId);
		generationCurve = new LoadCurve(GENERATION_BY_HOURS);
		genrationSchedule_1 = new GeneratorGenerationSchedule(1, true, true, null);
		genrationSchedule_2 = new GeneratorGenerationSchedule(2, true, false, generationCurve);
		generationSchedule.addGeneratorGenerationSchedule(genrationSchedule_1);
		generationSchedule.addGeneratorGenerationSchedule(genrationSchedule_2);
		
		return generationSchedule;
	}
}

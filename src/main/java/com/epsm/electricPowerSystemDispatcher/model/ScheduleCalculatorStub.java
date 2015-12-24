package main.java.com.epsm.electricPowerSystemDispatcher.model;

import main.java.com.epsm.electricPowerSystemModel.model.bothConsumptionAndGeneration.LoadCurve;
import main.java.com.epsm.electricPowerSystemModel.model.dispatch.GeneratorGenerationSchedule;
import main.java.com.epsm.electricPowerSystemModel.model.dispatch.PowerStationGenerationSchedule;
import test.java.com.epsm.electricPowerSystemModel.model.constantsForTests.TestsConstants;

public class ScheduleCalculatorStub {
	public PowerStationGenerationSchedule createGenerationSchedule(){
		LoadCurve generationCurve;
		PowerStationGenerationSchedule generationSchedule;
		GeneratorGenerationSchedule genrationSchedule_1;
		GeneratorGenerationSchedule genrationSchedule_2;
		float[] generation = prepareArrayWithFifteenPercentLessValues(TestsConstants.LOAD_BY_HOURS);
		
		generationSchedule = new PowerStationGenerationSchedule();
		generationCurve = new LoadCurve(generation);
		genrationSchedule_1 = new GeneratorGenerationSchedule(1, true, true, null);
		genrationSchedule_2 = new GeneratorGenerationSchedule(2, true, false, generationCurve);
		generationSchedule.addGeneratorGenerationSchedule(genrationSchedule_1);
		generationSchedule.addGeneratorGenerationSchedule(genrationSchedule_2);
		
		return generationSchedule;
	}
	
	private float[] prepareArrayWithFifteenPercentLessValues(float[] array){
		float[] newArray = new float[array.length];
		
		for(int i= 0; i < array.length; i++){
			newArray[i] = array[i] * 0.85f; 
		}
		
		return newArray;
	}
}

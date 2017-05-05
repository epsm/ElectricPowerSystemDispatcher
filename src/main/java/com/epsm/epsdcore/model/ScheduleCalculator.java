package com.epsm.epsdcore.model;

import com.epsm.epsmcore.model.common.PowerCurve;
import com.epsm.epsmcore.model.consumption.RandomLoadConsumerParameters;
import com.epsm.epsmcore.model.consumption.ScheduledLoadConsumerParameters;
import com.epsm.epsmcore.model.generation.GeneratorGenerationSchedule;
import com.epsm.epsmcore.model.generation.PowerStationGenerationSchedule;
import com.epsm.epsmcore.model.generation.PowerStationParameters;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

import static java.util.Arrays.asList;

@Service
public class ScheduleCalculator {

	private final static List<Float> PRECALCULATED_GENERATION_BY_HOURS = asList(
			55.15f,  50.61f,  47.36f,  44.11f, 	41.20f,  41.52f,
			40.87f,  48.66f,  64.89f,  77.86f,  85.00f,  84.34f,
			77.86f,  77.86f,  77.53f,  77.20f,  77.20f,  77.20f,
			77.20f,  77.20f,  77.20f,  77.20f,  77.20f,  77.20f);

	private final int FIRST_POWER_STATION_ID = 1;
	private final int FIRST_GENERATOR_NUMBER = 1;
	private final int SECOND_GENERATOR_NUMBER = 2;
	private final boolean GENERATOR_ON = true;
	private final boolean ASTATIC_REGULATION_ON = true;
	private final boolean ASTATIC_REGULATION_OFF = false;
	private final PowerCurve NULL_CURVE = null;

	public List<PowerStationGenerationSchedule> getSchedules(
			Collection<ScheduledLoadConsumerParameters> scheduledLoadConsumerParameters,
			Collection<RandomLoadConsumerParameters> randomLoadConsumerParameters,
			Collection<PowerStationParameters> powerStationParameters) {

		PowerCurve generationCurve = new PowerCurve(PRECALCULATED_GENERATION_BY_HOURS);
		PowerStationGenerationSchedule generationSchedule = new PowerStationGenerationSchedule(FIRST_POWER_STATION_ID);
		GeneratorGenerationSchedule genrationSchedule_1
				= new GeneratorGenerationSchedule(FIRST_GENERATOR_NUMBER, GENERATOR_ON, ASTATIC_REGULATION_ON, NULL_CURVE);
		GeneratorGenerationSchedule genrationSchedule_2
				= new GeneratorGenerationSchedule(SECOND_GENERATOR_NUMBER, GENERATOR_ON, ASTATIC_REGULATION_OFF, generationCurve);

		generationSchedule.getGeneratorSchedules().put(1, genrationSchedule_1);
		generationSchedule.getGeneratorSchedules().put(2, genrationSchedule_2);

		return asList(generationSchedule);
	}
}

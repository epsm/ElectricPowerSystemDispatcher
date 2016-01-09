package ConsumerStateConverter;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.epsm.electricPowerSystemDispatcher.model.domain.SavedConsumerState;
import com.epsm.electricPowerSystemDispatcher.service.ConsumerStateConverter;
import com.epsm.electricPowerSystemModel.model.consumption.ConsumerState;

public class ConsumerStateConverterTest {
	private ConsumerStateConverter converter;
	private ConsumerState consumerState;
	private SavedConsumerState convertedState;
	private final int CONSUMER_ID = 684646;
	private final float LOAD = 49.6567f;
	private final LocalDateTime REAL_TIME_TIMESTAMP = LocalDateTime.of(1, 2, 3, 4, 5, 6, 7);
	private final LocalTime SIMULATION_TIMESTAMP = LocalTime.of(1, 2, 3, 4);
	
	@Before
	public void setUp(){
		converter = new ConsumerStateConverter();
		consumerState = new ConsumerState(CONSUMER_ID, REAL_TIME_TIMESTAMP,
				SIMULATION_TIMESTAMP, LOAD);
		convertedState = converter.convert(consumerState);
	}
	
	@Test
	public void powerObjectIdsAreEquals(){
		Assert.assertEquals(CONSUMER_ID, convertedState.getConsumerId());
	}
	
	@Test
	public void loadsAreEquals(){
		Assert.assertEquals(LOAD, convertedState.getLoad(), 0);
	}
	
	@Test
	public void realTimeTimestampsAreEquals(){
		Assert.assertEquals(REAL_TIME_TIMESTAMP, convertedState.getRealTimeStamp());
	}
	
	@Test
	public void simulationTimeTimestampsAreEquals(){
		Assert.assertEquals(SIMULATION_TIMESTAMP, convertedState.getSimulationTimeStamp());
	}
}

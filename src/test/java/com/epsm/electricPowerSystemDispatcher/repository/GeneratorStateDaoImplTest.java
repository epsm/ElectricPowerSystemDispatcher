package com.epsm.electricPowerSystemDispatcher.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.epsm.electricPowerSystemDispatcher.configuration.DbTestConfiguration;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={DbTestConfiguration.class})
@TestExecutionListeners({DbUnitTestExecutionListener.class, TransactionalTestExecutionListener.class,
		DependencyInjectionTestExecutionListener.class,	DirtiesContextTestExecutionListener.class,})
@Transactional
@DatabaseSetup(value="generator_states.xml", type= DatabaseOperation.REFRESH)
public class GeneratorStateDaoImplTest{
	
	@Autowired
	SavedGeneratorStateDao dao;
	
	@Test
	public void testGetStatesByPowerStationNumber(){
		int statesForFistStation = dao.getStatesByPowerStationNumber(1).size();
		int statesForSecondStation = dao.getStatesByPowerStationNumber(2).size();
		int thirdForSecondStation = dao.getStatesByPowerStationNumber(3).size();
		
		Assert.assertEquals(1, statesForFistStation);
		Assert.assertEquals(2, statesForSecondStation);
		Assert.assertEquals(0, thirdForSecondStation);
	}
}

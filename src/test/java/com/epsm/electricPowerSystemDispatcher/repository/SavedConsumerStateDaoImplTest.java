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

@ContextConfiguration(classes={DbTestConfiguration.class})
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({DbUnitTestExecutionListener.class, TransactionalTestExecutionListener.class,
		DependencyInjectionTestExecutionListener.class,	DirtiesContextTestExecutionListener.class,})
@Transactional
@DatabaseSetup(value="consumer_states.xml", type= DatabaseOperation.REFRESH)
public class SavedConsumerStateDaoImplTest{
	
	@Autowired
	SavedConsumerStateDao dao;
	
	@Test
	public void testGetStatesByPowerStationNumber(){
		int statesForFistConsumer = dao.getStatesByNumber(1).size();
		int statesForSecondConsumer = dao.getStatesByNumber(2).size();
		int thirdForSecondConsumer = dao.getStatesByNumber(3).size();
		
		Assert.assertEquals(1, statesForFistConsumer);
		Assert.assertEquals(2, statesForSecondConsumer);
		Assert.assertEquals(0, thirdForSecondConsumer);
	}
}

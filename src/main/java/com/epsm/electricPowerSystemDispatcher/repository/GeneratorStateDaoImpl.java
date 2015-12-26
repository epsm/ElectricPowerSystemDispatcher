package com.epsm.electricPowerSystemDispatcher.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.epsm.electricPowerSystemDispatcher.model.domain.GeneratorState;

@Repository
public class GeneratorStateDaoImpl implements GeneratorStateDao{

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<GeneratorState> getStatesByPowerStationNumber(int powerStationNumber) {
		Query query = em.createQuery("SELECT c FROM GeneratorState c WHERE c.station "
				+ "= :powerStationNumber");
		query.setParameter("powerStationNumber", powerStationNumber);
		
		return (List<GeneratorState>)query.getResultList();
	}

	@Override
	public void saveState(GeneratorState state) {
		em.merge(state);
	}
}

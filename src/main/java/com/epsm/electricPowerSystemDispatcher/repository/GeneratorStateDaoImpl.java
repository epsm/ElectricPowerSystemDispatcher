package com.epsm.electricPowerSystemDispatcher.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.epsm.electricPowerSystemDispatcher.domains.GeneratorState;

@Repository
public class GeneratorStateDaoImpl implements GeneratorStateDao{

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<GeneratorState> getStates(int powerStationNumber) {
		Query query = em.createQuery("SELECT c FROM GeneratorStates c WHERE c.station_number = "
				+ ":powerStationNumber");
		query.setParameter("powerStationNumber", powerStationNumber);
		
		return (List<GeneratorState>)query.getResultList();
	}

	@Override
	public void saveState(GeneratorState state) {
		em.merge(state);
	}
}

package com.epsm.electricPowerSystemDispatcher.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.epsm.electricPowerSystemDispatcher.model.domain.SavedGeneratorState;

@Repository
public class SavedGeneratorStateDaoImpl implements SavedGeneratorStateDao{

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<SavedGeneratorState> getStatesByPowerStationNumber(long powerStationId) {
		Query query = em.createQuery("SELECT c FROM SavedGeneratorState c WHERE c.powerStationId "
				+ "= :powerStationId");
		query.setParameter("powerStationId", powerStationId);
		
		return (List<SavedGeneratorState>)query.getResultList();
	}

	@Override
	public void saveState(SavedGeneratorState state) {
		em.merge(state);
	}
}

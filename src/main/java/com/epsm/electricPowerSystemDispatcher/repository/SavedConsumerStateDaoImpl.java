package com.epsm.electricPowerSystemDispatcher.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.epsm.electricPowerSystemDispatcher.model.domain.SavedConsumerState;

@Repository
public class SavedConsumerStateDaoImpl implements SavedConsumerStateDao{

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<SavedConsumerState> getStatesByNumber(long consumerId) {
		Query query = em.createQuery("SELECT c FROM SavedConsumerState c WHERE c.consumerId "
				+ "= :consumerId");
		query.setParameter("consumerId", consumerId);
		
		return (List<SavedConsumerState>)query.getResultList();
	}
	
	@Override
	public void saveState(SavedConsumerState state) {
		em.merge(state);
	}
}

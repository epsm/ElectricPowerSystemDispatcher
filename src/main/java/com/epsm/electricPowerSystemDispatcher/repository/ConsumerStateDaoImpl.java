package com.epsm.electricPowerSystemDispatcher.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.epsm.electricPowerSystemDispatcher.model.domain.SavedConsumerState;

@Repository
public class ConsumerStateDaoImpl implements ConsumerStateDao{

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<SavedConsumerState> getStatesByNumber(int consumerNumber) {
		Query query = em.createQuery("SELECT c FROM SavedConsumerState c WHERE c.consumerNumber "
				+ "= :consumerNumber");
		query.setParameter("consumerNumber", consumerNumber);
		
		return (List<SavedConsumerState>)query.getResultList();
	}
	
	@Override
	public void saveState(SavedConsumerState state) {
		em.merge(state);
	}
}

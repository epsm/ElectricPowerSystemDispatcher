package com.epsm.electricPowerSystemDispatcher.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.epsm.electricPowerSystemDispatcher.model.domain.ConsumerState;

@Repository
public class ConsumerStateDaoImpl implements ConsumerStateDao{

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<ConsumerState> getStatesByNumber(int consumerNumber) {
		Query query = em.createQuery("SELECT c FROM ConsumerState c WHERE c.consumerNumber "
				+ "= :consumerNumber");
		query.setParameter("consumerNumber", consumerNumber);
		
		return (List<ConsumerState>)query.getResultList();
	}
	
	@Override
	public void saveState(ConsumerState state) {
		em.merge(state);
	}
}

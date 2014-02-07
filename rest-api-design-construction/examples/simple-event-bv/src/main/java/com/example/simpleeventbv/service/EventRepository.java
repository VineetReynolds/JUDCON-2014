package com.example.simpleeventbv.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import com.example.simpleeventbv.model.Event;

@Stateless
public class EventRepository {

	@PersistenceContext(unitName = "forge-default")
	private EntityManager em;

	public Event save(Event entity) {
		em.persist(entity);
		return entity;
	}

	public Event findById(Long id) {
		TypedQuery<Event> findByIdQuery = em
				.createQuery(
						"SELECT DISTINCT e FROM Event e WHERE e.id = :entityId ORDER BY e.id",
						Event.class);
		findByIdQuery.setParameter("entityId", id);
		Event entity;
		try {
			entity = findByIdQuery.getSingleResult();
		} catch (NoResultException nre) {
			entity = null;
		}
		return entity;
	}

	public void delete(Event entity) {
		em.remove(entity);
	}

	public List<Event> findAll() {
		final List<Event> results = em.createQuery(
				"SELECT DISTINCT e FROM Event e ORDER BY e.id", Event.class)
				.getResultList();
		return results;
	}

	public Event update(Event entity) {
		entity = em.merge(entity);
		return entity;
	}

}

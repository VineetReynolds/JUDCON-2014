package com.example.bidieagerevent.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import com.example.bidieagerevent.model.Event;

/**
 * 
 */
@Stateless
@Path("/events")
public class EventEndpoint {
	@PersistenceContext(unitName = "forge-default")
	private EntityManager em;

	@POST
	@Consumes("application/json")
	public Response create(Event entity) {
		em.persist(entity);
		return Response.created(
				UriBuilder.fromResource(EventEndpoint.class)
						.path(String.valueOf(entity.getId())).build()).build();
	}

	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public Response deleteById(@PathParam("id") Long id) {
		Event entity = em.find(Event.class, id);
		if (entity == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		em.remove(entity);
		return Response.noContent().build();
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces("application/json")
	public Response findById(@PathParam("id") Long id) {
		TypedQuery<Event> findByIdQuery = em
				.createQuery(
						"SELECT DISTINCT e FROM Event e LEFT JOIN FETCH e.sessions WHERE e.id = :entityId ORDER BY e.id",
						Event.class);
		findByIdQuery.setParameter("entityId", id);
		Event entity;
		try {
			entity = findByIdQuery.getSingleResult();
		} catch (NoResultException nre) {
			entity = null;
		}
		if (entity == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(entity).build();
	}

	@GET
	@Produces("application/json")
	public List<Event> listAll() {
		final List<Event> results = em
				.createQuery(
						"SELECT DISTINCT e FROM Event e LEFT JOIN FETCH e.sessions ORDER BY e.id",
						Event.class).getResultList();
		return results;
	}

	@PUT
	@Path("/{id:[0-9][0-9]*}")
	@Consumes("application/json")
	public Response update(Event entity) {
		entity = em.merge(entity);
		return Response.noContent().build();
	}
}
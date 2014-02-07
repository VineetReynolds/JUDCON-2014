package com.example.simpleeventbv.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import org.jboss.resteasy.spi.validation.ValidateRequest;

import com.example.simpleeventbv.model.Event;
import com.example.simpleeventbv.service.EventRepository;

/**
 * 
 */
@Path("/events")
public class EventEndpoint {

	@Inject
	private EventRepository repository;

	@POST
	@Consumes("application/json")
	@ValidateRequest
	public Response create(@Valid Event entity) {
		entity = repository.save(entity);
		return Response.created(
				UriBuilder.fromResource(EventEndpoint.class)
						.path(String.valueOf(entity.getId())).build()).build();
	}

	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public Response deleteById(@PathParam("id") Long id) {
		Event entity = repository.findById(id);
		if (entity == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		repository.delete(entity);
		return Response.noContent().build();
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces("application/json")
	public Response findById(@PathParam("id") Long id) {
		Event entity = repository.findById(id);
		if (entity == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(entity).build();
	}

	@GET
	@Produces("application/json")
	public List<Event> listAll() {
		return repository.findAll();
	}

	@PUT
	@Path("/{id:[0-9][0-9]*}")
	@Consumes("application/json")
	@ValidateRequest
	public Response update(@Valid Event entity) {
		repository.update(entity);
		return Response.noContent().build();
	}
}
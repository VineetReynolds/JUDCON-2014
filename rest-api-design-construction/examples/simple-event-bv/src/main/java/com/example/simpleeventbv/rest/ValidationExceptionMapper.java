package com.example.simpleeventbv.rest;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.hibernate.validator.method.MethodConstraintViolation;
import org.hibernate.validator.method.MethodConstraintViolationException;

@Provider
public class ValidationExceptionMapper implements
		ExceptionMapper<MethodConstraintViolationException> {

	public Response toResponse(MethodConstraintViolationException methodCVEx) {
		Map<String, String> errors = new HashMap<String, String>();
		for (MethodConstraintViolation<?> violation : methodCVEx
				.getConstraintViolations()) {
			errors.put(violation.getParameterName(), violation.getMessage());
		}
		return Response.status(Status.PRECONDITION_FAILED).entity(errors)
				.build();
	}
}

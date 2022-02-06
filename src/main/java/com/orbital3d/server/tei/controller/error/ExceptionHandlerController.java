package com.orbital3d.server.tei.controller.error;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.orbital3d.server.tei.error.AuthenticationFailedExcetion;

@Controller
public class ExceptionHandlerController
{
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ExceptionHandler(NotFoundException.class)
	public void notFound()
	{
		// Nothing to do
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(NullPointerException.class)
	public void internalServerError()
	{
		// Nothing to do
	}

	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ExceptionHandler(AuthenticationFailedExcetion.class)
	public void unauthorized()
	{
		// Nothing to do
	}
}

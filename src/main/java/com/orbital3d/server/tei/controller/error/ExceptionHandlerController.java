package com.orbital3d.server.tei.controller.error;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionHandlerController
{
	@ResponseStatus(code = HttpStatus.FORBIDDEN)
	@ExceptionHandler(AuthenticationException.class)
	public ModelAndView unauthenticated()
	{
		ModelAndView mav = new ModelAndView("login");
		mav.addObject("unauth", "not authorized");
		return mav;
	}

	@ExceptionHandler(AuthorizationException.class)
	public String unauthorized()
	{
		return "error/403";
	}
}

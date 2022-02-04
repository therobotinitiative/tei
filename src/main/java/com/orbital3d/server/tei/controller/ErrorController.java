package com.orbital3d.server.tei.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for displaying error pages.
 * 
 * @author msiren
 *
 */
@Controller
public class ErrorController
{
	/**
	 * 403 Forbidden error page.
	 */
	@GetMapping(path =
	{ "/403" })
	public String noPermission()
	{
		return "error/403";
	}

	/**
	 * 404 Not found error page.
	 */
	@GetMapping("/404")
	public String noyFound()
	{
		return "error/404";
	}

	/**
	 * 500 Internal server error page.
	 */
	@GetMapping("/500")
	public String internalServerError()
	{
		return "error/500";
	}
}

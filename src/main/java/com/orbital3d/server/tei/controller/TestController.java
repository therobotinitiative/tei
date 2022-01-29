package com.orbital3d.server.tei.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController("/")
public class TestController
{
	private static final Logger LOG = LoggerFactory.getLogger(TestController.class);

	@GetMapping("test/")
	public ModelAndView test()
	{
		LOG.trace("Invoking end point test/");

		ModelAndView mv = new ModelAndView("index");
		mv.addObject("attr", (Object) "test value");
		return mv;
	}
}

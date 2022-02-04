package com.orbital3d.server.tei.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.orbital3d.server.tei.database.entity.User;
import com.orbital3d.server.tei.database.repository.UserRepository;

/**
 * Test controller, will be removed or controlled by profile so it wont be
 * active in production environment by accident.
 * 
 * @author msiren
 *
 */
@RestController()
public class TestController
{
	private static final Logger LOG = LoggerFactory.getLogger(TestController.class);

	@Autowired
	private UserRepository userRepository;

	@RequestMapping(path = "test/", method = RequestMethod.GET)
	public ModelAndView test()
	{
		LOG.trace("Invoking end point test/");

		ModelAndView mv = new ModelAndView("index");
		mv.addObject("attr", (Object) "test value");
		return mv;
	}

	@GetMapping("/initialize")
	public String initializeGet()
	{
		return "init";
	}

	@PostMapping("/initialize")
	public String initializePost(@RequestParam(name = "password") String password)
	{
		User admin = userRepository.findByUserName("admin");
		admin.setPassword(password.toCharArray());
		admin.setSalt(null); // Generate salt
		userRepository.save(admin);
		// TODO: Verify that admin password is not yet initialised
		return "init";
	}
}

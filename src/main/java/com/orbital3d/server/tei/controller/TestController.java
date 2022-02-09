package com.orbital3d.server.tei.controller;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.orbital3d.server.tei.database.document.Permissions;
import com.orbital3d.server.tei.database.document.User;
import com.orbital3d.server.tei.database.repository.PermissionsRepository;
import com.orbital3d.server.tei.database.repository.UserRepository;
import com.orbital3d.server.tei.service.PasswordService;
import com.orbital3d.server.tei.service.UserService;

/**
 * Test controller, will be removed or controlled by profile so it wont be
 * active in production environment by accident.
 * 
 * @author msiren
 *
 */
@Controller()
public class TestController
{
	private static final Logger LOG = LoggerFactory.getLogger(TestController.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PermissionsRepository pr;

	@Autowired
	private PasswordService passwordService;

	@Autowired
	private UserService userService;

	@RequestMapping(path = "/test", method = RequestMethod.GET)
	public ModelAndView test()
	{
		LOG.trace("Invoking end point test/");

		ModelAndView mv = new ModelAndView("test");
		mv.addObject("attr", (Object) "test value");
		mv.addObject("admin", userRepository.findByUserName("admin"));
		mv.addObject("tei", userRepository.findByUserName("tei"));
		mv.addObject("adminserv", userService.findUser("admin"));
		mv.addObject("teiserv", userService.findUser("tei"));
		return mv;
	}

	@GetMapping("/initialize")
	public String initializeGet(Model model)
	{
		return "init";
	}

	@PostMapping("/initialize")
	public String initializePost(@RequestParam(name = "password") String password, Model model)
	{
		User admin = userRepository.findByUserName("admin");
		if (admin == null)
		{
			admin = new User();
			admin.setUserName("admin");
		}
		admin.setSalt(passwordService.generateSalt()); // Generate salt
		admin.setPassword(passwordService.hashPassword(password, admin.getSalt()));
		LOG.info("Created admin object {}", admin);
		userRepository.save(admin);
		LOG.info("Saved entity");
		return "pages/index";
	}

	@GetMapping("/perms")
	public String testPermission()
	{
		User user = userRepository.findByUserName("admin");
		Set<String> perms = new HashSet<>();
		perms.add("tei:index");
		perms.add("tei:dashboard");
		perms.add("tei:view");
		perms.add("tei:templates");
		perms.add("tei:send");
		Permissions p = new Permissions();
		p.setUser(user);
		p.setPermissions(perms);
		pr.save(p);
		return "pages/index";
	}
}

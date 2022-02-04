package com.orbital3d.server.tei.controller.pages;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Send page controller.
 * 
 * @author msiren
 *
 */
@Controller
public class SendController
{
	/**
	 * @return Send page template name
	 */
	@GetMapping("/send")
	public String sendPage()
	{
		return "pages/send";
	}
}

package com.orbital3d.server.tei;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerApplication
{
	public static void main(String[] args)
	{
		// Setting the property here while trying to avoid any configuration files
		System.setProperty("shiro.loginUrl", "/login");
		SpringApplication.run(ServerApplication.class, args);
	}
}

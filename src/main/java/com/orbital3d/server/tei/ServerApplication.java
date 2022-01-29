package com.orbital3d.server.tei;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(
{ "com.orbital3d.server.tei" })
public class ServerApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(ServerApplication.class, args);
	}
}

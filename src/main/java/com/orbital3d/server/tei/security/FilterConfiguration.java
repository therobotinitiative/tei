package com.orbital3d.server.tei.security;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

@Component
public class FilterConfiguration implements TemporaryFilterWhiteList
{
	private Set<String> whiteList = new HashSet<>();

	@PostConstruct
	public void postConstruct()
	{
		addWhiteListedPath("/login");
		addWhiteListedPath("/initialize");
	}

	@Override
	public void addWhiteListedPath(String path)
	{
		whiteList.add(path);
	}

	@Override
	public boolean isPathWhiteListed(String path)
	{
		return whiteList.contains(path);
	}

}

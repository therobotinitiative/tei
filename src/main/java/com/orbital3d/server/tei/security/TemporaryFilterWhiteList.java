package com.orbital3d.server.tei.security;

public interface TemporaryFilterWhiteList
{
	void addWhiteListedPath(String path);

	boolean isPathWhiteListed(String path);
}

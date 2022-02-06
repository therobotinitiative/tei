package com.orbital3d.server.tei.security;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.orbital3d.server.tei.error.AuthenticationFailedExcetion;

/**
 * Temporary filter. Checks that user is authenticated for paths that are not
 * white listed. This filter does not check authorization that mechanism is
 * currently being omitted. Apache Shiro is being planned, but there to be some
 * issues with it.
 * 
 * This is very rudimentary and should <b>NOT</b> be considered secure in any
 * way. At the moment this only works for view end points and does not secure
 * resources.
 * 
 * @author msiren
 *
 */
@Component
public class TemporaryFilter extends OncePerRequestFilter
{
	private static final Logger LOG = LoggerFactory.getLogger(TemporaryFilter.class);

	@Autowired
	private TemporaryFilterWhiteList wl;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException
	{
		LOG.info("Filtering path {}, method: {}", request.getServletPath(), request.getMethod());

		// Check white list or verify the authentication token
		if (wl.isPathWhiteListed(request.getServletPath()) || isAuthenticated(request))
		{
			filterChain.doFilter(request, response);
		}
		else
		{
			throw new AuthenticationFailedExcetion();
		}
	}

	/*
	 * Verify that the token in the session data matches token in the cookies.
	 */
	private boolean isAuthenticated(HttpServletRequest request)
	{
		String sessionToken = (String) request.getSession(false).getAttribute("auth");
		String cookieToken = Arrays.stream(request.getCookies()).filter(cookie -> (cookie.getName().equals("auth"))).findFirst().orElse(null).getValue();
		LOG.info("Tokens found match[{}], session:[{}], cookie:[{}]", sessionToken.equals(cookieToken), sessionToken, cookieToken);
		return sessionToken.equals(cookieToken);
	}

}

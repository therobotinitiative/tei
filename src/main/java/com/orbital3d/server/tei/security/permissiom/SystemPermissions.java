package com.orbital3d.server.tei.security.permissiom;

import java.util.Set;

/**
 * Interface to contain all the permissions in the implementing class.<br>
 * <br>
 * 
 * @author msiren
 *
 */
public interface SystemPermissions
{
	/**
	 * @return {@link Set} of all permissions available in the system
	 * @throws IllegalArgumentException In case reflection is used
	 * @throws IllegalAccessException   In case reflection is used
	 */
	Set<String> allPermissions() throws IllegalArgumentException, IllegalAccessException;
}

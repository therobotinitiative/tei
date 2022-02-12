package com.orbital3d.server.tei.security.permissiom;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

/**
 * TEI specific permissions.
 * 
 * @author msiren
 *
 */
@Component
public final class TEIPermissions implements SystemPermissions
{
	public static final String TEI_INDEX = "tei:index";
	public static final String TEI_VIEW_DASHBOARD = "tei:dashboard";
	public static final String TEI_VIEW_VIEW = "tei:view";
	public static final String TEI_VIEW_TEMPLATE = "tei:templates";
	public static final String TEI_VIEW_SEND = "tei:send";
	public static final String ADMINISTRATOR = "tei:administrator";
	public static final String ADMIN_CREATE_USER = "tei:administrator:createuser";
	public static final String ADMIN_UPDATE_USE_PASSWORD = "tei:administrator:updateuserpassword";
	public static final String ADMIN_USER_PERMISSIONS = "tei:administrator:permissions";

	@Override
	public Set<String> allPermissions() throws IllegalArgumentException, IllegalAccessException
	{
		Set<String> permissions = new HashSet<>();
		for (Field field : this.getClass().getFields())
		{
			// Only get the static fibal fields
			if ((field.getModifiers() & Modifier.STATIC) != 0 && (field.getModifiers() & Modifier.FINAL) != 0)
			{
				Object object = field.get(this);
				if (object.getClass().isAssignableFrom(String.class))
				{
					permissions.add((String) object);
				}
			}
		}
		return permissions;
	}
}

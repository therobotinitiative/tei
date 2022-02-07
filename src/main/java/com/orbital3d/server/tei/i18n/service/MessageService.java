package com.orbital3d.server.tei.i18n.service;

import java.util.Locale;

import com.orbital3d.server.tei.i18n.type.LocalisationKey;

/**
 * Convenience service for localised text. Uses generic to increase the type
 * safety and using strongly typed key reduces possible typos compared to when
 * using pure strings.
 * 
 * @author msiren
 *
 */
public interface MessageService
{
	/**
	 * @see MessageService#getMessage(LocalisationKey, Object[]). No arguments.
	 */
	<T extends LocalisationKey> String getMessage(T messageKey, Locale locale);

	/**
	 * Returns localised {@link String} associated with the key. If localised text
	 * is not found the key as a {@link String} is returned.
	 * 
	 * @param <T>        Key type must be type of {@link LocalisationKey}
	 * @param messageKey Key
	 * @param args       Arguments; can be null
	 * @param locale     {@link Locale} to use
	 * @return Localised text or the message key if localised text not found
	 */
	<T extends LocalisationKey> String getMessage(T messageKey, Object[] args, Locale locale);
}

package com.orbital3d.server.tei.i18n.service.impl;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.LocaleResolver;

import com.orbital3d.server.tei.i18n.service.MessageService;
import com.orbital3d.server.tei.i18n.type.LocalisationKey;

@Service
public class MessageServiceImpl implements MessageService
{
	private static final Logger LOG = LoggerFactory.getLogger(MessageServiceImpl.class);

	@Autowired
	private MessageSource messageSource;

	@Autowired
	LocaleResolver localeResolver;

	@Override
	public <T extends LocalisationKey> String getMessage(T messageKey, Locale locale)
	{
		return getMessage(messageKey, null, locale);
	}

	@Override
	public <T extends LocalisationKey> String getMessage(T messageKey, Object[] args, Locale locale)
	{
		try
		{
			LOG.trace("Getting localised message {} with arguments {}", messageKey, args);
			return messageSource.getMessage(messageKey.toString(), args, locale);
		}
		catch (NoSuchMessageException e)
		{
			LOG.warn(e.getMessage());
		}
		return messageKey.toString();
	}

}

package com.orbital3d.server.tei.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orbital3d.server.tei.database.repository.TemplateRepository;
import com.orbital3d.server.tei.service.TemplateService;

@Service
public class TemplateServiceImpl implements TemplateService
{
	@Autowired
	private TemplateRepository templateRepository;

	@Override
	public TemplateRepository getRepository()
	{
		return templateRepository;
	}

}

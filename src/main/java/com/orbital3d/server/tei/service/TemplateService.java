package com.orbital3d.server.tei.service;

import java.util.HashSet;
import java.util.Set;

import com.orbital3d.server.tei.database.document.template.Template;
import com.orbital3d.server.tei.database.repository.TemplateRepository;

public interface TemplateService extends CrudService<Template, TemplateRepository>
{
	default Set<String> findAllTemplateIds()
	{
		// This should be done in better way. Repository method or streams, but it works
		// now.
		Set<String> templateIds = new HashSet<>();
		for (Template template : getRepository().findAll())
		{
			templateIds.add(template.getTemplateId());
		}
		return templateIds;
	}

	default Set<Template> findByTags(Set<String> tags)
	{
		return getRepository().findByTags(tags);
	}

	default Template findByTemplateId(String templateId)
	{
		return getRepository().findByTemplateId(templateId);
	}
}

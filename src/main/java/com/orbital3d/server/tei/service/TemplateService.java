package com.orbital3d.server.tei.service;

import java.util.HashSet;
import java.util.Set;

import com.orbital3d.server.tei.database.document.template.Template;
import com.orbital3d.server.tei.database.repository.TemplateRepository;

/**
 * Template service to decouple the business logic from the underlying data
 * storage. It is advised to use the service instead of repository in the
 * business/domain layer.
 * 
 * @author msiren
 *
 */
public interface TemplateService extends CrudService<Template, TemplateRepository>
{
	/**
	 * TODO: Restrict by group.
	 * 
	 * @return {@link Set} of available template ids
	 */
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

	/**
	 * @param tags {@link Set} of tags to search by
	 * @return {@link Set} of templates containing the given tags
	 */
	default Set<Template> findByTags(Set<String> tags)
	{
		return getRepository().findByTags(tags);
	}

	/**
	 * @param templateId Template id to search by
	 * @return {@link Template} instance; null if no match
	 */
	default Template findByTemplateId(String templateId)
	{
		return getRepository().findByTemplateId(templateId);
	}

	/**
	 * @param templateId Template id to search by
	 * @return {@link Set} of tags associated with template
	 */
	default Set<String> findTags(String templateId)
	{
		Template template = getRepository().findByTemplateId(templateId);
		return template.getTags();
	}
}

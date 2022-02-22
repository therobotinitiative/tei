package com.orbital3d.server.tei.database.repository;

import java.util.Set;

import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;

import com.orbital3d.server.tei.database.document.template.Template;

public interface TemplateRepository extends CrudRepository<Template, ObjectId>
{
	Iterable<String> findAllByTemplateId();

	Template findByTemplateId(String templateId);

	Set<Template> findByTags(Set<String> tags);

	Set<String> findTagsByTemplateId(String templateId);
}

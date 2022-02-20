package com.orbital3d.server.tei.database.document.template;

import java.util.Date;
import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.orbital3d.server.tei.type.DomainObject;

@Document(collection = "template")
public class Template implements DomainObject
{
	@Id
	private ObjectId id;

	private String templateId;
	private Set<String> tags;
	private Set<QuestionTemplate> questionTemplates;
	private Date created;

	public Template(ObjectId id, String templateId, Set<String> tags, Set<QuestionTemplate> questionTemplates, Date created)
	{
		this.id = id;
		this.templateId = templateId;
		this.tags = tags;
		this.questionTemplates = questionTemplates;
		this.created = created;
	}

	public Template()
	{
		// Default
	}

	public ObjectId getId()
	{
		return id;
	}

	public void setId(ObjectId id)
	{
		this.id = id;
	}

	public String getTemplateId()
	{
		return templateId;
	}

	public void setTemplateId(String templateId)
	{
		this.templateId = templateId;
	}

	public Set<String> getTags()
	{
		return tags;
	}

	public void setTags(Set<String> tags)
	{
		this.tags = tags;
	}

	public Set<QuestionTemplate> getQuestionTemplates()
	{
		return questionTemplates;
	}

	public void setQuestionTemplates(Set<QuestionTemplate> questionTemplates)
	{
		this.questionTemplates = questionTemplates;
	}

	public void addQuestionTemplate(QuestionTemplate questionTemplate)
	{
		this.questionTemplates.add(questionTemplate);
	}

	public void removeQuestionTemplate(QuestionTemplate questionTemplate)
	{
		this.questionTemplates.remove(questionTemplate);
	}

	public Date getCreated()
	{
		return created;
	}

	public void setCreated(Date created)
	{
		this.created = created;
	}

	@Override
	public boolean equals(Object obj)
	{
		return EqualsBuilder.reflectionEquals(this, obj, false);
	}

	@Override
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this);
	}
}

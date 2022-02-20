package com.orbital3d.server.tei.database.document.template;

import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.tuple.Pair;

import com.orbital3d.server.tei.type.template.AnswerType;
import com.orbital3d.server.tei.type.template.QuestionType;

public final class QuestionTemplate
{
	private QuestionType quetionType;
	private String question;
	private AnswerType answerType;
	private Set<Pair<String, String>> options;

	public QuestionTemplate(QuestionType quetionType, String question, AnswerType answerType, Set<Pair<String, String>> options)
	{
		this.quetionType = quetionType;
		this.question = question;
		this.answerType = answerType;
		this.options = options;
	}

	public QuestionTemplate()
	{
		// Default
	}

	public QuestionType getQuetionType()
	{
		return quetionType;
	}

	public void setQuetionType(QuestionType quetionType)
	{
		this.quetionType = quetionType;
	}

	public String getQuestion()
	{
		return question;
	}

	public void setQuestion(String question)
	{
		this.question = question;
	}

	public AnswerType getAnswerType()
	{
		return answerType;
	}

	public void setAnswerType(AnswerType answerType)
	{
		this.answerType = answerType;
	}

	public Set<Pair<String, String>> getOptions()
	{
		return options;
	}

	public void setOptions(Set<Pair<String, String>> options)
	{
		this.options = options;
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

package com.orbital3d.server.tei.database.document.template;

import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.orbital3d.server.tei.type.template.AnswerType;
import com.orbital3d.server.tei.type.template.QuestionType;
import com.orbital3d.server.tei.type.unresolved.IndexedOption;

public final class QuestionTemplate
{
	private QuestionType quetionType;
	private String question;
	private AnswerType answerType;
	// private Set<Pair<String, String>> options;
	private Set<IndexedOption> options;
	private int index;

	public QuestionTemplate(QuestionType quetionType, String question, AnswerType answerType, Set<IndexedOption> options, int indexPosition)
	{
		this.quetionType = quetionType;
		this.question = question;
		this.answerType = answerType;
		this.options = options;
		this.index = indexPosition;
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

	public Set<IndexedOption> getOptions()
	{
		return options;
	}

	public int getIndex()
	{
		return index;
	}

	public void setIndex(int index)
	{
		this.index = index;
	}

	public void setOptions(Set<IndexedOption> options)
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

package com.orbital3d.server.tei.type.template;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Answer type for the questionnaire. Define the supported types for the answer
 * in open question. Types are used as input field type in HTML side.
 * 
 * @author msiren
 *
 */
public enum AnswerType
{
	TEXT, NUMBER, EMAIL;

	/**
	 * Finds matching enumeration ignoring the case. Static factory method annotated
	 * as {@link JsonCreator}.
	 * 
	 * @param value Value to compare ignoring the case
	 * @return Matching enumeration; null if no match is found
	 */
	@JsonCreator
	public static AnswerType valueFor(String value)
	{
		for (AnswerType answerType : AnswerType.values())
		{
			if (answerType.name().equalsIgnoreCase(value))
			{
				return answerType;
			}
		}
		return null;
	}
}

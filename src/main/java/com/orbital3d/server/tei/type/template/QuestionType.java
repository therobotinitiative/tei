package com.orbital3d.server.tei.type.template;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Question type. This enumeration defines supported question types.
 * 
 * @author msiren
 *
 */
public enum QuestionType
{
	OPEN, CLOSED;

	/**
	 * Finds matching enumeration ignoring the case. Static factory method annotated
	 * as {@link JsonCreator}.
	 * 
	 * @param value Value to compare ignoring the case
	 * @return Matching enumeration; null if no match is found
	 */
	@JsonCreator
	public static QuestionType valueFor(String value)
	{
		for (QuestionType questionType : QuestionType.values())
		{
			if (questionType.name().equalsIgnoreCase(value))
			{
				return questionType;
			}
		}
		return null;
	}
}

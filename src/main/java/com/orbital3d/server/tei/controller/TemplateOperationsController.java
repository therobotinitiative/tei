package com.orbital3d.server.tei.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.orbital3d.server.tei.type.template.AnswerType;
import com.orbital3d.server.tei.type.template.QuestionType;

@RestController
public class TemplateOperationsController
{
	/**
	 * Closed question option data transfer object.
	 * 
	 * @author msiren
	 *
	 */
	private static class OptionDTO
	{
		@JsonProperty("value")
		private String value;
		@JsonProperty("text")
		private String text;

		private OptionDTO()
		{
			// Default
		}
	}

	/**
	 * Data transfer object for moving the questionnaire template from and to UI.
	 * Annotated to reflect the JSON format the UI uses.
	 * 
	 * @author msiren
	 *
	 */
	private static class QuestionDTO
	{
		@JsonProperty("type")
		private QuestionType type;
		@JsonProperty("question")
		private String question;
		@JsonProperty("answer_type")
		private AnswerType answerType;
		@JsonProperty("options")
		private Set<OptionDTO> options;

		private QuestionDTO()
		{
			// Default
		}
	}

	/**
	 * Temporary storing of the template for developing reasons only. The order of
	 * questions does stay consistent, issue that has to be tackled at some point,
	 * but storing to database will be implemented first. One solution is to have
	 * index value in the question. Or maybe it works now that I changed the
	 * parameter from set to an array.
	 */
	private static Map<String, QuestionDTO[]> tmpl = new HashMap<>();

	@PostMapping("/template/store/{template_id}")
	public void storeTemplate(@RequestBody QuestionDTO[] templateElements, @PathVariable("template_id") String templateId)
	{
		tmpl.put(templateId, templateElements);
	}

	@GetMapping("/template/restore/{template_id}")
	public QuestionDTO[] restoreTemplate(@PathVariable(name = "template_id", required = false) String templateId)
	{
		if (templateId == null)
		{
			templateId = (String) tmpl.keySet().toArray()[tmpl.keySet().size() - 1];
		}
		return tmpl.get(templateId);
	}
}

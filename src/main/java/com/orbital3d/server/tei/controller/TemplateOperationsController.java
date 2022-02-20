package com.orbital3d.server.tei.controller;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.orbital3d.server.tei.database.document.template.QuestionTemplate;
import com.orbital3d.server.tei.database.document.template.Template;
import com.orbital3d.server.tei.service.TemplateService;
import com.orbital3d.server.tei.type.template.AnswerType;
import com.orbital3d.server.tei.type.template.QuestionType;

@RestController
public class TemplateOperationsController
{
	/**
	 * Closed question option data transfer object. Indented to be used only in the
	 * controller.
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

		private OptionDTO(String value, String text)
		{
			this.value = value;
			this.text = text;
		}

		/**
		 * Convert to {@link Pair}.
		 * 
		 * @param optionDTO Convert from
		 * @return Converted instance
		 */
		private static Pair<String, String> to(OptionDTO optionDTO)
		{
			return Pair.of(optionDTO.value, optionDTO.text);
		}

		/**
		 * Convert from {@link Pair}. This is also a static factory method.
		 * 
		 * @param optionPair Convert from
		 * @return Converted instance
		 */
		private static OptionDTO from(Pair<String, String> optionPair)
		{
			return new OptionDTO(optionPair.getLeft(), optionPair.getRight());
		}

	}

	/**
	 * Data transfer object for moving the questionnaire template from and to UI.
	 * Annotated to reflect the JSON format the UI uses. Indented to be used only in
	 * the controller.
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
			this.options = new HashSet<>();
		}

		private static QuestionDTO to(QuestionTemplate questionTemplate)
		{
			QuestionDTO questionDTO = new QuestionDTO();
			questionDTO.type = questionTemplate.getQuetionType();
			questionDTO.question = questionTemplate.getQuestion();
			questionDTO.answerType = questionTemplate.getAnswerType();
			// Options are optional
			if (questionTemplate.getOptions() != null)
			{
				for (Pair<String, String> option : questionTemplate.getOptions())
				{
					questionDTO.options.add(OptionDTO.from(option));
				}
			}
			return questionDTO;
		}

		private static QuestionTemplate from(QuestionDTO questionDTO)
		{
			QuestionTemplate questionTemplate = new QuestionTemplate();
			questionTemplate.setAnswerType(questionDTO.answerType);
			questionTemplate.setQuestion(questionDTO.question);
			questionTemplate.setQuetionType(questionDTO.type);
			// Options are optional
			if (questionDTO.options != null)
			{
				Set<Pair<String, String>> optionSet = new HashSet<>();
				for (OptionDTO optionDTO : questionDTO.options)
				{
					optionSet.add(OptionDTO.to(optionDTO));
				}
				questionTemplate.setOptions(optionSet);
			}
			return questionTemplate;
		}
	}

	@Autowired
	private TemplateService templateService;

	@PostMapping(path = "/template/store/{template_id}", produces =
	{ MediaType.APPLICATION_JSON_VALUE })
	public Map<String, String> storeTemplate(@RequestBody QuestionDTO[] templateElements, @PathVariable(name = "template_id", required = true) String templateId)
	{
		if (templateId.equalsIgnoreCase("generate"))
		{
			templateId = UUID.randomUUID().toString();
		}

		Template existingTemplate = templateService.findByTemplateId(templateId);
		Template template = new Template();
		if (existingTemplate != null)
		{
			template.setId(existingTemplate.getId());
			template.setCreated(existingTemplate.getCreated());
		}

		template.setTemplateId(templateId);
		// TODO: Tags, support will be added later
		Set<QuestionTemplate> templates = new LinkedHashSet<>();
		for (QuestionDTO questionDTOto : templateElements)
		{
			templates.add(QuestionDTO.from(questionDTOto));
		}
		template.setQuestionTemplates(templates);

		templateService.save(template);

		return Map.of("template_id", templateId);
	}

	@GetMapping(path = "/template/restore/{template_id}", produces =
	{ MediaType.APPLICATION_JSON_VALUE })
	public QuestionDTO[] restoreTemplate(@PathVariable(name = "template_id") String templateId)
	{
		Template template = templateService.findByTemplateId(templateId);
		// change set to array or at least consider it
		Set<QuestionDTO> questionsDTOSet = new LinkedHashSet<>();
		if (template != null)
		{
			for (QuestionTemplate questionTemplate : template.getQuestionTemplates())
			{
				questionsDTOSet.add(QuestionDTO.to(questionTemplate));
			}
		}
		QuestionDTO[] questionDTOs = new QuestionDTO[questionsDTOSet.size()];
		return questionsDTOSet.toArray(questionDTOs);
	}

	@GetMapping(path = "/template/ids", produces =
	{ MediaType.APPLICATION_JSON_VALUE })
	public Set<String> getTemplateIds()
	{
		return templateService.findAllTemplateIds();
	}
}

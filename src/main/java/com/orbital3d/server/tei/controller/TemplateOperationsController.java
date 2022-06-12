package com.orbital3d.server.tei.controller;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import com.orbital3d.server.tei.security.permissiom.TEIPermissions;
import com.orbital3d.server.tei.service.TemplateService;
import com.orbital3d.server.tei.type.template.AnswerType;
import com.orbital3d.server.tei.type.template.QuestionType;
import com.orbital3d.server.tei.type.unresolved.IndexedOption;

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
		@JsonProperty("index")
		private Integer index;
		@JsonProperty("value")
		private String value;
		@JsonProperty("text")
		private String text;

		private OptionDTO()
		{
			// Default
		}

		private OptionDTO(Integer index, String value, String text)
		{
			this.index = index;
			this.value = value;
			this.text = text;
		}

		/**
		 * Convert to {@link IndexedOption}.
		 * 
		 * @param optionDTO Convert from
		 * @return Converted instance
		 */
		private static IndexedOption to(OptionDTO optionDTO)
		{
			return IndexedOption.of(optionDTO.index, optionDTO.value, optionDTO.text);
		}

		/**
		 * Convert from {@link IndexedOption}. This is also a static factory method.
		 * 
		 * @param indexedOption Convert from
		 * @return Converted instance
		 */
		private static OptionDTO from(IndexedOption indexedOption)
		{
			return new OptionDTO(indexedOption.getIndex(), indexedOption.getValue(), indexedOption.getText());
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
		@JsonProperty("index")
		private int index;

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
			questionDTO.index = questionTemplate.getIndex();
			// Options are optional
			if (questionTemplate.getOptions() != null)
			{
				for (IndexedOption option : questionTemplate.getOptions())
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
			questionTemplate.setIndex(questionDTO.index);
			// Options are optional
			if (questionDTO.options != null)
			{
				Set<IndexedOption> optionSet = new HashSet<>();
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

	/**
	 * Store the template elements (questions) with given template id. If the
	 * template id exists if will be overwritten. If the id is "generate" one will
	 * be generated for the template.
	 * 
	 * @param templateElements Array of {@link QuestionDTO} in the request body
	 * @param templateId       Template id
	 * @return The template id used for storing; can be generated
	 */
	@PostMapping(path = "/template/store/{template_id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	@RequiresPermissions(TEIPermissions.Templates.STORE)
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

	/**
	 * Restore template with the given template id.
	 * 
	 * @param templateId Template id to identify the template
	 * @return Array of {@link QuestionDTO}
	 */
	@GetMapping(path = "/template/restore/{template_id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	@RequiresPermissions(TEIPermissions.Templates.RESTORE)
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

	/**
	 * Retrieve all available template ids. Will be restricted by group in the
	 * future.
	 * 
	 * @return {@link Set} of available id {@link String}s
	 */
	@GetMapping(path = "/template/ids", produces = { MediaType.APPLICATION_JSON_VALUE })
	@RequiresPermissions(TEIPermissions.Templates.IDS)
	public Set<String> getTemplateIds()
	{
		return templateService.findAllTemplateIds();
	}

	/**
	 * Retrieve all tags for template id.
	 * 
	 * @param templateId Template id which tags to retrieve
	 * @return {@link Set} of tags or null if no tags present
	 */
	@GetMapping(path = "/template/tags/{templateid}", produces = { MediaType.APPLICATION_JSON_VALUE })
	@RequiresPermissions(TEIPermissions.Templates.TAGS)
	public Set<String> getTags(@PathVariable("templateid") String templateId)
	{
		return templateService.findTags(templateId);
	}

	/**
	 * Store tags for the template. Takes the tags as {@link Set} of tags in the
	 * request body.
	 * 
	 * @param tags       {@link Set} of tags
	 * @param templateId Template id to identify the template for which the tags are
	 *                   stored
	 */
	@PostMapping("/template/tags/{template_id}")
	@RequiresPermissions(TEIPermissions.Templates.STORE_TAGS)
	public void storeTags(@RequestBody(required = false) Set<String> tags, @PathVariable("template_id") String templateId)
	{
		Template template = templateService.findByTemplateId(templateId);
		template.setTags(tags);
		templateService.save(template);
	}
}

app.controller('templateController', function($scope, $http, $rootScope)
{
	$scope.template_container = {
		template_elements: [],
		template_id: '',
		available_ids: [],
		tags: [],
		/**
		 * Add new question into the template.
		 * @param question_type Question type
		 */
		add_question_element: function(question_type = 'open')
		{
			var question = null;
			switch (question_type)
			{
				case 'open':
					question = $scope.template_container.create_open_question();
					break;
				case 'closed':
					question = $scope.template_container.create_closed_question();
					break;
			}
			// Set the index before pushing the element into array.
			question.index = $scope.template_container.template_elements.length;
			$scope.template_container.template_elements.push(question);
		},
		/**
		 * Remove question from the template and fix the indices.
		 * @param element Element to remove
		 */
		remove_question_element: function(element)
		{
			// Fix the index values, and remove the selected element. Seriously no remove? his is javascript way
			$scope.template_container.template_elements = $scope.template_container.template_elements.filter(array_element =>
			{
				if (array_element !== element)
				{
					if (array_element.index > element.index)
					{
						array_element.index -= 1;
					}
					return array_element;
				}
			});
		},
		/**
		 * Remove option from qrustion elements options.
		 * @param element Question element to remove option from; must be closed question
		 * @param option Option to remove
		 */
		remove_option: function(element, option)
		{
			// Fix indices
			element.options = element.options.filter(array_element => array_element !== option);
		},
		/**
		 * Create new empty template.
		 */
		create_new_template: function()
		{
			$scope.template_container.template_elements = [];
			$scope.template_container.tags = [];
			$scope.template_container.template_id = '';
		},
		/**
		 * Add new option into question element.
		 * @param elemnt Question element; must be closed question
		 */
		add_option: function(element)
		{
			var new_option = { 'index' : element.options.length, 'value' : '', 'text' : ''};
			element.options.push(new_option);
		},
		/**
		 * Creates new open question element JSON.
		 */
		create_open_question: function()
		{
			return {
				'type' : 'open',
				'question' : '',
				'answer_type' : 'text',
				'index' : '0'
			};
		},
		/**
		 * Creates new closed question elemnt JSON.
		 */
		create_closed_question: function()
		{
			return {
				'type' : 'closed',
				'question' : '',
				'index' : '0',
				'options' : []
			};
		},
		/**
		 * Debug function for looking at the template element array content.
		 */
		debug: function()
		{
			console.log($scope.template_container.template_elements);
		},
		/**
		 * Stores the current template in the server side.
		 */
		store_template: function()
		{
			var template_id = $scope.template_container.template_id;
			// If the id field s empty relace it with "generate" to generate id in the server side.
			if (template_id === '')
			{
				template_id = 'generate';
			}
			$http.post('/template/store/' + template_id, $scope.template_container.template_elements).then(function(response)
			{
				$scope.template_container.template_id = response.data.template_id;
				if ($scope.template_container.available_ids.indexOf(response.data.template_id) == -1)
				{
					$scope.template_container.available_ids.push(response.data.template_id);
				}
				$rootScope.information.show('Template stored with id ' + $scope.template_container.template_id);
				$scope.template_container.save_tags(response.data.template_id);
			});
		},
		/**
		 * Restores the stored template from the server side.
		 * TODO: Figure out template id
		 */
		restore_template: function()
		{
			$http.get('/template/restore/' + $scope.template_container.template_id).then(function(response)
			{
				$scope.template_container.template_elements = response.data;
				$scope.template_container.get_tags($scope.template_container.template_id);
				document.getElementById('tag-to-add').value = '';
				// Sort template elements by their index
				$scope.template_container.template_elements.sort(orderByIndex);
				// Sort optons by their index
				for (var i = 0; i < $scope.template_container.template_elements.length; i++)
				{
					if ($scope.template_container.template_elements[i].options !== undefined)
					{
						$scope.template_container.template_elements[i].options.sort(orderByIndex);
					}
				}
			});
		},
		/**
		 * Rettrives all available ids from the server.
		 */
		get_available_ids: function()
		{
			$http.get('/template/ids').then(function(response)
			{
				$scope.template_container.available_ids = response.data;
			});
		},
		/**
		 * Retrieve tags for template id.
		 * @param temlate_id Template id for which the tags are retrieved
		 */
		get_tags: function(template_id)
		{
			$http.get('/template/tags/' + template_id).then(function(response)
			{
				$scope.template_container.tags = response.data;
				if (Array.isArray($scope.template_container.tags))
				{
					$scope.template_container.tags.sort();
				}
			});
		},
		/**
		 * Save tags for template id.
		 * @param template_id Template id for which the tags are saved for
		 */
		save_tags(template_id, show_information = false)
		{
			$http.post('/template/tags/' + template_id, $scope.template_container.tags).then(function(response)
			{
				if (show_information == true && response.status == 200)
				{
					$rootScope.information.show('tags saved');
				}
			});
		},
		/**
		 * Adds tag to the collection of tags.
		 */
		add_tag: function()
		{
			var tags = document.getElementById('tag-to-add').value.split(',');
			if (!Array.isArray($scope.template_container.tags))
			{
				$scope.template_container.tags = [];
			}
			for (var i = 0; i < tags.length; i++)
			{
				var tag = tags[i].trim();
				// TODO: Inform if tag exists
				if ($scope.template_container.tags.indexOf(tag) == -1)
				{
					$scope.template_container.tags.push(tag);
					$scope.template_container.tags.sort();
					document.getElementById('tag-to-add').value = '';
				}
			}
		},
		/**
		 * Move the element up in the array. The direction in the function name is from the array index perspective.
		 *
		 * @param element Element to move
		 */
		move_element_up: function(element)
		{
			// Find the elements index in the array
			var elementIndex = $scope.template_container.template_elements.indexOf(element);
			var length = $scope.template_container.template_elements.length - 1;
			if (elementIndex < length)
			{
				// Store the elements
				var currentElement = $scope.template_container.template_elements[elementIndex];
				var nextElement = $scope.template_container.template_elements[elementIndex + 1];
				// Swap the index values
				currentElement.index += 1;
				nextElement.index -= 1;
				// Swap the elements in the array
				$scope.template_container.template_elements[elementIndex + 1] = currentElement;
				$scope.template_container.template_elements[elementIndex] = nextElement;
			}
		},
		/**
		 * Move the element down in the array. The direction in the function name is from the array index perspective.
		 *
		 * @param element Element to move
		 */
		move_element_down: function(element)
		{
			// Find the elements index in the array
			var elementIndex = $scope.template_container.template_elements.indexOf(element);
			if (elementIndex > 0)
			{
				// Store the elements
				var currentElement = $scope.template_container.template_elements[elementIndex];
				var previousElement = $scope.template_container.template_elements[elementIndex - 1];
				// Swap the index values
				currentElement.index -= 1;
				previousElement.index += 1;
				// Swap the elements in the array
				$scope.template_container.template_elements[elementIndex - 1] = currentElement;
				$scope.template_container.template_elements[elementIndex] = previousElement;
			}
		},
		option_up: function(element, option)
		{
			if (element.options.indexOf(option) < (element.options.length - 1))
			{
				var index = element.options.indexOf(option);
				var currentOption = element.options[index];
				var nextOption = element.options[index + 1];
				// Swap indices
				currentOption.index += 1;
				nextOption.index -= 1;
				// Swap options
				element.options[index] = nextOption;
				element.options[index + 1] = currentOption;
			}
		},
		option_down: function(element, option)
		{
			if (element.options.indexOf(option) > 0)
			{
				var index = element.options.indexOf(option);
				var currentOption = element.options[index];
				var previousOption = element.options[index - 1];
				// Swap indices
				currentOption.index -= 1;
				previousOption.index += 1;
				// Swap options
				element.options[index] = previousOption;
				element.options[index - 1] = currentOption;
			}
		}
	};
	// Load the available ids
	$scope.template_container.get_available_ids();
});
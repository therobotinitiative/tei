app.controller('templateController', function($scope, $http, $rootScope)
{
	$scope.template_container = {
		template_elements: [],
		template_id: '',
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
			$scope.template_container.template_elements.push(question);
		},
		/**
		 * Remove question from the template.
		 * @param element Element to remove
		 */
		remove_question_element: function(element)
		{
			// Seriously? Doesn't javascript have remove funtion?
			$scope.template_container.template_elements = $scope.template_container.template_elements.filter(array_element => array_element !== element);
		},
		/**
		 * Remove option from qrustion elements options.
		 * @param element Question element to remove option from; must be closed question
		 * @param option Option to remove
		 */
		remove_option: function(element, option)
		{
			element.options = element.options.filter(array_element => array_element !== option);
		},
		/**
		 * Create new empty template.
		 */
		create_new_template: function()
		{
			$scope.template_container.template_elements = [];
			$scope.template_container.template_id = '';
		},
		/**
		 * Add new option into question element.
		 * @param elemnt Question element; must be closed question
		 */
		add_option: function(element)
		{
			var new_option = { 'value': '', 'text' : ''};
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
				'answer_type' : 'text'
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
		 * TODO: Figure out template id
		 */
		store: function()
		{
			$http.post('/template/store/' + $scope.template_container.template_id, $scope.template_container.template_elements).then(function(response)
			{
				$rootScope.information.show('Template stored');
			});
		},
		/**
		 * Restores the stored template from the server side.
		 * TODO: Figure out template id
		 */
		restore: function()
		{
			$http.get('/template/restore/' + $scope.template_container.template_id).then(function(response)
			{
				$scope.template_container.template_elements = response.data;
			});
		}
	};
});
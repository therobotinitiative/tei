var app = angular.module('tei', ['ngRoute', 'checklist-model']);

/**
 * Intercept HTTP request error and use root scope error dialog.
 * @returns Interceptor
 */
function interceptHttpError($q, $rootScope)
{
	return {
		'responseError': function(error)
		{
			switch(error.status)
			{
				case 401:
					window.location.href = '/login';
					return; // No response
				case 408:
					$rootScope.errorDialog.show('Request timedout', 'Server was unable to answer your request');
					break;
				case 413:
					$rootScope.errorDialog.show('Too large file', 'Ask for upload size limit');
					break;
				case 404:
					$rootScope.errorDialog.show('Infamous 404', 'Something was not found');
					break;
				default:
					if (error.data != null)
					{
						$rootScope.errorDialog.show(error.data.message, error.data.description);
					}
					else
					{
						$rootScope.errorDialog.show('Something went wrong', 'Something wrong do not know what: ' + error.status);
					}
			}
			$rootScope.error.show();
			return $q.reject(error);
		}
	}
}

/**
 * Routes
 */
app.config(function ($routeProvider, $httpProvider)
{
	$httpProvider.interceptors.push(interceptHttpError);
	
	// Define routes
	$routeProvider.when('/dashboard', {
		templateUrl: '/templates/dashboard'
	})
	.when('/view', {
		templateUrl: '/templates/view'
	})
	.when('/template', {
		templateUrl: '/templates/template'
	})
	.when('/send', {
		templateUrl: '/templates/send'
	})
	.when('/admin', {
		templateUrl: 'templates/admin'
	});
});

/**
 * Prepare root scope.
 */
app.run(function($rootScope, $timeout)
{
	/**
	 * Error dialog
	 */
	 $rootScope.errorDialog = {
		visible: false,
		message: 'error',
		description: '',
		/**
		 * Show the errr dialof.
		 * @param error The error message, default is empty
		 * @oaram description Error description, default is empty
		 */
		show: function(error = '', description = '')
		{
			$rootScope.errorDialog.message = error;
			$rootScope.errorDialog.description = description;
			$rootScope.errorDialog.visible = true;
		},
		/**
		 * Hide the error dialog.
		 */
		hide: function()
		{
			$rootScope.errorDialog.visible = false;
		}
	};
	
	/**
	 * Information dialog, can be timed.
	 */
	$rootScope.information = {
		visible: false,
		message: '',
		/**
		 * Show the information dialog.
		 
		 * @param message Message to display, default is empty
		 * @param timout Set the automatic dialog hide time in milliseconds, below zero (usually -1) disables the automatic hiding
		 */
		show: function(message = '', timeout = 4000)
		{
			$rootScope.information.message = message;
			$rootScope.information.visible = true;
			// Set the dialog timeout
			if (timeout > -1)
			{
				$timeout(function()
				{
					$rootScope.information.hide();
				}, timeout)
			}
		},
		/**
		 * Hide the information dialog.
		 */
		hide: function()
		{
			$rootScope.information.visible = false;
		}
	};

});

/**
 * Controller
 */
function teiController($scope)
{
	// TODO: Implement as needed
}

app.controller("abc", teiController);


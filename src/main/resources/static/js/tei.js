var app = angular.module('tei', ['ngRoute', 'checklist-model']);

/**
 * Routes
 */
app.config(function ($routeProvider)
{
	//$httpProvider.interceptors.push(interceptorName);
	
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
 * Controller
 */
function teiController($scope)
{
	// TODO: Implement as needed
}

app.controller("abc", teiController);


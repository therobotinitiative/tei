app.controller('adminUserController', function($scope, $http, $rootScope)
{
	// All users from server side
	$scope.users = {};
	// All available permissions from server side
	$scope.all_permissions = [];
	// The current user alll operations will target
	$scope.current_user = {
		user: {},
		permissions: {}
	};
	
	/**
	 * Add user container. Contains operations and visibility of yje add user section.
	 */
	$scope.add_user_container = {
		visible: false,
		show: function()
		{
			$scope.add_user_container.visible = true;
		},
		hide: function()
		{
			$scope.add_user_container.visible = false;
		}
	};
	
	/**
	 * Get all users.
	 */
	$scope.get_users = function()
	{
		$http.get('/admin/user').then( function(response)
		{
			$scope.users = response.data;
			// Select the first user by default
			$scope.select_user($scope.users[0].userName);
			$scope.user_permissions();
		});
	};
	/**
	 * Add new user.
	 */
	$scope.add_user = function()
	{
		var element_id = 'new_username';
		var new_user = document.getElementById(element_id).value;
		$http.post('/admin/users/' + new_user).then(function(response)
		{
			if (response.status == 200)
			{
				$scope.users.push(response.data);
				$rootScope.information.show('User ' + new_user + ' added');
			}
		});
		document.getElementById(element_id).value = '';
	};
	/**
	 * Change selected uers password.
	 */
	$scope.change_password = function()
	{
		var user_name = $scope.current_user.user.userName;
		var element_id = 'user-' + user_name + '-pwd';
		var new_password = document.getElementById(element_id).value;
		$http.put('/admin/users/'+user_name+'/'+new_password).then(function(response)
		{
			if (response.status == 200)
			{
				$rootScope.information.show('Password changed for ' + user_name);
			}
		});
		document.getElementById(element_id).value = '';
	};
	/**
	 * Get selected users permissions.
	 */
	$scope.user_permissions = function()
	{
		$http.get('/admin/user/permissions/' + $scope.current_user.user.userName).then(function(response)
		{
			$scope.current_user.permissions = {};
			if (response.data !== undefined && response.data !=='')
			{
				$scope.current_user.permissions = response.data;
			}
		});
	};
	/**
	 * Update selected users permissions.
	 */
	$scope.update_permissions = function()
	{
		$http.put('/admin/perm/' + $scope.current_user.user.userName, $scope.current_user.permissions).then(function(response)
		{
			if (response.status == 200)
			{
				$rootScope.information.show('Permissions for ' + $scope.current_user.user.userName + ' updated')
			}
		});
	};
	/**
	 * Select current user.
	 * @param User name to select
	 */
	$scope.select_user = function(user_name)
	{
		// Find the user from users
		var user = $scope.users.find(element => element.userName == user_name);
		$scope.current_user.user = user;
		$scope.user_permissions();
	};
	/**
	 * Get all available permissions.
	 */
	$scope.get_permissions = function()
	{
		$http.get('/admin/perm/all').then(function(response)
		{
			$scope.all_permissions = response.data;
		});
	};
	/**
	 * Delete current user.
	 */
	$scope.delete_user = function()
	{
		$http.delete('/admin/user/' + $scope.current_user.user.userName).then(function(response)
		{
			if (response.status == 200)
			{
				$rootScope.information.show('User ' + $scope.current_user.user.userName + ' deleted');
				$scope.get_users();
			}
		});
	};
	/**
	 * Update the user data.
	 */
	$scope.update_userdata = function()
	{
		var put_data = {
			'firstName' : $scope.current_user.user.userData.firstName,
			'lastName' : $scope.current_user.user.userData.lastName,
			'email' : $scope.current_user.user.userData.email,
		};
		$http.put('/admin/userdata/' + $scope.current_user.user.userName, put_data).then(function(response)
		{
			if (response.status == 200)
			{
				$rootScope.information.show('User information for ' + $scope.current_user.user.userName + ' updated');
			}
		});
		
	}
	// Invoked when script loaded.
	$scope.get_users();
	$scope.get_permissions();
});
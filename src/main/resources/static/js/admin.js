app.controller('adminUserController', function($scope, $http)
{
	// All users from server side
	$scope.users = [];
	// All available permissions from server side
	$scope.all_permissions = [];
	// The current user alll operations will target
	$scope.current_user = {
		user_name: '',
		permissions: {}
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
			$scope.current_user.user_name = $scope.users[0].userName;
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
			$scope.users.push(response.data);
		});
		document.getElementById(element_id).value = '';
	};
	/**
	 * Change selected uers password.
	 */
	$scope.change_password = function()
	{
		var user_name = $scope.current_user.user_name;
		var element_id = 'user-' + user_name + '-pwd';
		var new_password = document.getElementById(element_id).value;
		$http.put('/admin/users/'+user_name+'/'+new_password).then(function(response)
		{
			// TODO : infobox
			alert('password set');
		});
		document.getElementById(element_id).value = '';
	};
	/**
	 * Get selected users permissions.
	 */
	$scope.user_permissions = function()
	{
		$http.get('/admin/user/permissions/' + $scope.current_user.user_name).then(function(response)
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
		$http.put('/admin/perm/' + $scope.current_user.user_name, $scope.current_user.permissions).then(function(response)
		{
			// TODO : infobox
			alert('permissions updated');
		});
	};
	/**
	 * Select current user.
	 * @param User name to select
	 */
	$scope.select_user = function(user_name)
	{
		$scope.current_user.user_name = user_name;
		$scope.user_permissions();
	};
	/**
	 * Get all available permissions.
	 */
	$scope.get_permissions = function()
	{
		$http.get('admin/perm/all').then(function(response)
		{
			$scope.all_permissions = response.data;
		});
	};
	/**
	 * Delete current user.
	 */
	$scope.delete_user = function()
	{
		$http.delete('/admin/user/' + $scope.current_user.user_name).then(function(response)
		{
			if (response.status == 200)
			{
				alert('User deleted');
				$scope.get_users();
			}
		});
	};
	// Invoked when script loaded.
	$scope.get_users();
	$scope.get_permissions();
});
app.controller('adminUserController', function($scope, $http)
{
	$scope.users = [];
	$scope.getUsers = function()
	{
		$http.get('/admin/user').then( function(response)
		{
			$scope.users = response.data;
		});
	};
	$scope.addUser = function()
	{
		var new_user = document.getElementById('new_username').value;
		$http.post('/admin/users/' + new_user).then(function(response)
		{
			$scope.users.push(response.data);
		});
		document.getElementById('new_username').value = '';
	};
	$scope.changePassword = function(userName)
	{
		var new_password = document.getElementById('user-' + userName + '-pwd').value;
		$http.put('/admin/users/'+userName+'/'+new_password).then(function(response)
		{
			// TODO : Show info box
		});
		document.getElementById('user-' + userName + '-pwd').value = '';
	};
	$scope.getUsers();
});
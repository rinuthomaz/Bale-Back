brtaApp.service('userService', function($http, applicationContextURL) {
	return {
		getLoggedInUserDetails : function() {
			return $http.get(applicationContextURL + "/common/getdetails/user")
					.then(function(response) {
						console.log(JSON.stringify(response));
						return response.data;

					}, function(errResponse) {

						console.error('Error while retrieving user details');

					});

		},

		addUser : function(driverSupplierDTO) {
			return $http.post(applicationContextURL + '/userMgmt/add/user',
					driverSupplierDTO).then(function(response) {
				// console.log('TRF Created successfully'+response);
				// console.log(response.data);
				return response.data;

			}, function(errResponse) {
				console.error("Error while adding user");
				return $q.reject(errResponse);

			});

		},
		editUser : function(user) {
			return $http.post(applicationContextURL + '/userMgmt/edit/user',
					user).then(function(response) {
				// console.log('TRF Created successfully'+response);
				// console.log(response.data);
				return response.data;

			}, function(errResponse) {
				console.error("Error while adding user");
				return $q.reject(errResponse);

			});

		},

		getAllUsers : function() {
			return $http.get(applicationContextURL + '/userMgmt/get/user/all')
					.then(function(response) {
						// console.log('TRF Created successfully'+response);
						// console.log(response.data);
						return response.data;

					}, function(errResponse) {
						console.error("Error while getting all users");
						return $q.reject(errResponse);

					});

		},

		getFilteredUsers : function(user) {
			return $http.post(
					applicationContextURL + '/userMgmt/get/user/byfilters',
					user).then(function(response) {
				// console.log('TRF Created successfully'+response);
				// console.log(response.data);
				return response.data;

			}, function(errResponse) {
				console.error("Error while getting all users");
				return $q.reject(errResponse);

			});

		},
		editUser : function(user) {
			return $http.post(applicationContextURL + '/userMgmt/edit/user',
					user).then(function(response) {
				// console.log('TRF Created successfully'+response);
				// console.log(response.data);
				return response.data;

			}, function(errResponse) {
				console.error("Error while adding user");
				return $q.reject(errResponse);

			});

		},
		checkUniqueMobileNo : function(mobileNo) {
			return $http.post(
					applicationContextURL + '/userMgmt/check/mobileNumber',
					mobileNo).then(function(response) {
				console.log(response.data);
				return response.data;

			}, function(errResponse) {
				console.error("Error while check mobole number");
				return $q.reject(errResponse);

			});

		},
		checkUniqueEmail : function(emailId) {
			return $http.post(
					applicationContextURL + '/userMgmt/check/emailIdUnique',
					emailId).then(function(response) {
				console.log(response.data);
				return response.data;

			}, function(errResponse) {
				console.error("Error while check emailId unique");
				return $q.reject(errResponse);

			});

		},

		getAllUsersForSupplierSite : function(supplierSite) {
			return $http.post(applicationContextURL + "/userMgmt/get/user/all",
					supplierSite).then(function(response) {

				return response.data;

			}, function(errResponse) {

				console.error('Error while retrieving users');

			});
		},
		getAllActiveUsersForSupplierSite : function(supplierSite) {
			return $http.post(applicationContextURL + "/userMgmt/get/user/active",
					supplierSite).then(function(response) {

				return response.data;

			}, function(errResponse) {

				console.error('Error while retrieving users');

			});

		},
		userUploadFromExcel : function(userList){
		
		console.log("---userList-" + JSON.stringify(userList));

			return $http.post(applicationContextURL
					+ "/userMgmt/upload/usersFromExcel", userList)
			then(function(response) {
				return response.data;

			}, function(errResponse) {

				console.error('Error while adding new driver');

			});

		}
	}
});
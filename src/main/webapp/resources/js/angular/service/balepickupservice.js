brtaApp.service('balepickupService', function($http, applicationContextURL) {

	return {
		getAllPickups : function(configData) {
			return $http.post(applicationContextURL + "/common/getAll/pickUps",
					configData)
			then(function(response) {
				return response.data;

			}, function(errResponse) {

				console.error('Error while adding configuration data');

			});

		},

		addNewDriver : function(assignment) {
			return $http.post(applicationContextURL + "/assignMgmt/add/newdriver",
					assignment)
			then(function(response) {

				return response.data;

			}, function(errResponse) {

				console.error('Error while adding new driver');

			});
		},

		removeNewDriver : function(assignment) {
			return $http.post(applicationContextURL + "/assignMgmt/remove/newdriver",
					assignment)
			then(function(response) {

				return response.data;

			}, function(errResponse) {

				console.error('Error while removing new driver');

			});
		},

		editBalePickup : function(balePickup) {
			return $http.post(applicationContextURL
					+ "/tripMgmt/add/tripdetails", balePickup)
			then(function(response) {
				return response.data;
			}, function(errResponse) {

				console.error('Error while adding configuration data');

			});

		},
		addNewBalePickup : function(balePickup) {
			return $http.post(applicationContextURL
					+ "/tripMgmt/add/tripdetails", balePickup)
			then(function(response) {
				return response.data;

			}, function(errResponse) {

				console.error('Error while adding configuration data');

			});

		},
		getAllDestination : function(storeName) {

			return $http.post(applicationContextURL + "/assignMgmt/get/destination",
					storeName)
			then(function(response) {
				return response.data;

			}, function(errResponse) {

				console.error('Error while adding configuration data');

			});

		},
		getDestinationForUserAndDate : function(setDto) {

			return $http.post(applicationContextURL + "/assignMgmt/pickups",
					setDto)
			then(function(response) {
				return response.data;

			}, function(errResponse) {

				console.error('Error while adding configuration data');

			});

		},

		getBalePickUpImages : function(tripId) {

			return $http.post(applicationContextURL + "/tripMgmt/pickUp/images", tripId)
			then(function(response) {
				return response.data;

			}, function(errResponse) {

				console.error('Error while adding configuration data');

			});
		},
		
		pickupUploadFromExcel : function(pickUpList) {

			console.log("---pickUpList-" + JSON.stringify(pickUpList));

			return $http.post(applicationContextURL
					+ "/tripMgmt/upload/pickUpsFromExcel", pickUpList)
			then(function(response) {
				return response.data;

			}, function(errResponse) {

				console.error('Error while adding new driver');

			});
		},

		completeBalePickup : function(pickUp) {

			return $http.post(applicationContextURL
					+ "/tripMgmt/update/completetrip", pickUp)
			then(function(response) {
				return response.data;
			}, function(errResponse) {

				console.error('Error while adding new driver');

			});
		},

	}

});
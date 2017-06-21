brtaApp.service('storeConfigService', function($http,applicationContextURL){
	
	return {
		addConfigurationData: function(configData){
		return $http.post(applicationContextURL+"/add/suppliermaterialconfiguration",configData)	
	then(function(response){
	console.log(JSON.stringify(response));
	return response.data;
		
	},function(errResponse){
		
		console.error('Error while adding onfiguration data');
		
	});
		
		}
	}
	
	
});



brtaApp.service('pickupAssignmentService', function($http,applicationContextURL){
	
	return {
		get: function(configData){
		return $http.post(applicationContextURL+"/add/suppliermaterialconfiguration",configData)	
	then(function(response){
	console.log(JSON.stringify(response));
	return response.data;
		
	},function(errResponse){
		
		console.error('Error while adding configuration data');
		
	});
		
		},
		
		getAssignmentsByCustomerSite: function(customerSite){
			return $http.post(applicationContextURL+"/assignMgmt/get/assignments/bycustomerSite",customerSite)	
		then(function(response){
		console.log(JSON.stringify(response));
		return response.data;
			
		},function(errResponse){
			
			console.error('Error while getting assignments by customerSite');
			
		});
			
			},
		
		addAssignmentList: function(listOfAssignments){
			return $http.post(applicationContextURL+"/assignMgmt/add/pickupassignments",listOfAssignments)	
			then(function(response){
			console.log(JSON.stringify(response));
			return response.data;
				
			},function(errResponse){
				return response.data;
				console.error('Error while adding assignments');
				
			});
			
		},
		getAllAssignments:function(assignmentFilterDTO){
			return $http.post(applicationContextURL+"/assignMgmt/getAll/pickupassignments",assignmentFilterDTO)	
			then(function(response){
			
			return response.data;
				
			},function(errResponse){
				
				console.error('Error while getting assignments');
				
			});
		},
		assignActivityToUser:function(assigmnentActivityList){
			return $http.post(applicationContextURL+"/assignMgmt/update/pickupassignments",assigmnentActivityList)	
			then(function(response){
			console.log(response.data);
			return response.data;
				
			},function(errResponse){
				return response.data;
				console.error('Error while adding assignments');
				
			}); 
		}
	}
	
	
	
	
});
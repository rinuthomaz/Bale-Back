brtaApp.service('reportService', function($http,applicationContextURL){
	
	return {
		
		getAllIncidentTypes: function(){
		return $http.post(applicationContextURL+"/common/get/incidenttype/all")	
	then(function(response){
	console.log(JSON.stringify(response));
	return response.data;
	
	
		
	},function(errResponse){
		
		console.error('Error while adding configuration data');
		
	});
		
		},
		getAllPickups: function(configData){
			return $http.post(applicationContextURL+"/common/getAll/pickUps",configData)	
		then(function(response){
		console.log(JSON.stringify(response));
		return response.data;
			
		},function(errResponse){
			
			console.error('Error while adding configuration data');
			
		});
		
	},
		getPendingPickups: function(configData){
		return $http.post(applicationContextURL+"/reportMgmt/getAll/pendingReports",configData)	
	then(function(response){
	return response.data;
		
	},function(errResponse){
		
		console.error('Error while adding configuration data');
		
	});
		
		}
	
	}
	
});


brtaApp.service('incidentTypeService',function($http,applicationContextURL){
	
	return {
		addIncidentType: function(incidentType){
			return $http.post(applicationContextURL+'/incidentMgmt/add/incidenttype',
					incidentType).then(function(response){
					//	console.log('TRF Created successfully'+response);
					//	console.log(response.data);
				        return response.data;
				        
					},function(errResponse){
						console.error("Error while adding incident type");
						return $q.reject(errResponse);
					
					});
		
			
		},
		
		editIncidentType: function(incidentType){
			return $http.post(applicationContextURL+'/incidentMgmt/edit/incidenttype',
					incidentType).then(function(response){
					//	console.log('TRF Created successfully'+response);
					//	console.log(response.data);
				        return response.data;
				        
					},function(errResponse){
						console.error("Error while adding incident type");
						return $q.reject(errResponse);
					
					});
		
			
		},
		
		getAllIncidentTypes: function(){
			return $http.get(applicationContextURL+'/common/get/incidenttype/all').then(function(response){
					//	console.log('TRF Created successfully'+response);
					//	console.log(response.data);
				        return response.data;
				        
					},function(errResponse){
						console.error("Error while getting all incident types");
						return $q.reject(errResponse);
					
					});
			
		},
		
		checkIncidentUnique: function(incidentType){
			return $http.post(applicationContextURL+'/incidentMgmt/check/incidenttype',incidentType).then(function(response){
				        return response.data;
				        
					},function(errResponse){
						console.error("Error while check incident types");
						return $q.reject(errResponse);
					
					});
			
		},
		
		
		
		}
	
})
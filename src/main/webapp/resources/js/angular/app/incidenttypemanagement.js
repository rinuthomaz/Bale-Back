brtaApp.controller("incidentTypeManagementController",function(applicationContextURL,$scope,$http,incidentTypeService){
	
	 $scope.showaddincidenttype = function(){
		   $scope.addIncidentType = true;
		   $scope.incidentType = {};
	   }
	 
	 $scope.editingData = [];
	 incidentTypeService.getAllIncidentTypes().then(function(response){
		 $scope.incidentTypes = response;
		 for (var i = 0, length = $scope.incidentTypes.length; i < length; i++) {
		      $scope.editingData[$scope.incidentTypes[i].incidentTypeId] = false;
		    }
		 
		 console.log(JSON.stringify($scope.incidentTypes));
	 })
	
	
	$scope.checkIncidentUnique = function(incidentType){
	
	if(incidentType != undefined){
	
	incidentTypeService.checkIncidentUnique(incidentType).then(function(response){
$scope.incidentTypeUnique= response.status
   if(  response.status == true){
      $scope.incident_unique_error= true
   }				});
	
	}
	} 
	 
	 $scope.addIncidentTypeDetails  = function(incidentType){
			$scope.error = false;
			if($scope.incidentType.incidentDescription == "" || $scope.incidentType.incidentDescription== null )
	           {
				$scope.incident_des_error = true;
				$scope.error = true;

	            }
			
			
			 if(  $scope.incidentTypeUnique == true){
      $scope.incident_unique_error= true;
	  				$scope.error = true;

   }
			
			
			if(!$scope.error){
			if(incidentType.enabled=='Active'){
				incidentType.enabled = true;
			}
				else{
					incidentType.enabled = false;
					}
				incidentTypeService.addIncidentType(incidentType).then(function(response){
					$scope.incidentTypes = response;
					console.log(JSON.stringify($scope.incidentTypes));
					if($scope.incidentTypes != null && $scope.incidentTypes != ""){
						$scope.addIncidentType = false;
						incidentType = {};
					}
				});
			}
	 }
	 
	 
	 $scope.checkIncidentUniqueForEdit = function(incidentType, incidentId){
	
	if((incidentType != $scope.selectedIncidentDescription) && (incidentType != undefined)){
	
	incidentTypeService.checkIncidentUnique(incidentType).then(function(response){
	
   if(  response.status == true){
   
   var errorMsgName= "incident_unique_error_" + incidentId;
   console.log("===errorMsgName==="+errorMsgName);
		$scope[errorMsgName] = true;
   }	
   });
	
	}
	}
	
	var originalIncidentTypeWithoutEdit =  {};
	 
	 $scope.modify = function(tableData){
	 
	     $scope.incidentType = tableData;
			originalIncidentTypeWithoutEdit = angular.copy(tableData);

		 		 
    	if(tableData.enabled == true){
    		$scope.incidentType.enabled = 'Active';
    	}
    	else{
    		$scope.incidentType.enabled = 'Inactive';
    	}
		
		
		$scope.selectedIncidentDescription =tableData.incidentDescription;
        $scope.editingData[tableData.incidentTypeId] = true;
		
		
		
	 var errorMsgName= "incident_unique_error_" + tableData.incidentTypeId;
   console.log("===errorMsgName==="+errorMsgName);
		$scope[errorMsgName] = false;
		
		
    };
    
    
    $scope.cancel = function(tableData){
	
	for(var i=0;i< $scope.incidentTypes.length;i++){
	
	if($scope.incidentTypes[i].incidentTypeId == tableData.incidentTypeId){
	$scope.incidentTypes[i] =originalIncidentTypeWithoutEdit
	break;
	}
	
	}
	
	       
        $scope.editingData[tableData.incidentTypeId] = false;
    };
    
  $scope.editIncentiveTypeDetails = function(incidentType){
    	
	$scope.error = false;
     var incidentId= "incident_unique_error_" + incidentType.incidentTypeId;

	if($scope.incidentType.incidentDescription == "" || $scope.incidentType.incidentDescription== null )
	           {
				 var incidentId= "incident_des_error_" + incidentType.incidentTypeId;
		$scope[incidentId] = true;
		$scope.error = true;

	            }
	
	
	if(  $scope[incidentId] == true){
		$scope[incidentId] = true;
		$scope.error = true;
			}
			
			
			
			
	if(!$scope.error){

	if(incidentType.enabled =='Active'){
    	incidentType.enabled = true;
	}
	else{
		incidentType.enabled = false;
	}
		
		incidentTypeService.editIncidentType(incidentType).then(function(response){
		$scope.incidentTypes = response;
    	  $scope.editingData[incidentType.incidentTypeId] = false;
    })				
						
						
	}

    	
    }
    
	
});
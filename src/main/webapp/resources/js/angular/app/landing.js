brtaApp.controller("sideMenuController",function(applicationContextURL,$rootScope,$scope,$http,userService){
	
	userService.getLoggedInUserDetails().then(function(response){
		$scope.user = response;
		
		console.log(JSON.stringify(response));
		if($scope.user.role=='ROBRCSR')
			{
			
			$scope.showstoreManagement = true;
			
			$scope.userManagement = true;
			$scope.storeAssignment = true;
			$scope.balePickup = true;
			$scope.reports = true;
			$scope.supplierManagement =true;

			}
		if($scope.user.role=='ROBRMANAGER'){
			
			$scope.showincidentManagement = true;
			
			$scope.incidentManagement = true;
			$scope.reports = true;
			$scope.userManagement = true;
			$scope.storeAssignment = true;
			$scope.balePickup = true;
		}
	})
	
	$scope.backClickFun=function(){

		$rootScope.backClick=!$rootScope.backClick;
	}
	
	
});


brtaApp.controller("welcomeUserController",function(applicationContextURL,$scope,$http,userService){

        	$scope.greetingMessage = "";
			
        	var currDate = new Date();
			
        	if(currDate.getHours()>=0 && currDate.getHours()<12){
        		$scope.greetingMessage = "Good Morning";
        	}
        	if(currDate.getHours()>=12 && currDate.getHours()<16){
        		$scope.greetingMessage = "Good Afternoon";
        	}
        	
        	if(currDate.getHours()>=16 && currDate.getHours()<24){
        		$scope.greetingMessage = "Good Evening";
        	}
        	
        	console.log($scope.greetingMessage);
        	
        	
        

      
});

var brtaApp = angular.module('brtaApp',['ngRoute','xeditable','changeStatus','showDay','convertToString',
                                        'ui.bootstrap','angularUtils.directives.dirPagination','angular-js-xlsx'
                                        ,'bootstrapLightbox','naif.base64','base64']).constant('applicationContextURL','/brta/app');


brtaApp.directive("limitTo", [function() {
    return {
        restrict: "A",
        link: function(scope, elem, attrs) {
            var limit = parseInt(attrs.limitTo);
            angular.element(elem).on("keypress", function(e) {
                if (this.value.length == limit) e.preventDefault();
            });
        }
    }
}]);

brtaApp.directive('onlyNumeric', function() {
  return {
    require: 'ngModel',
    link: function (scope, element, attr, ngModelCtrl) {
      function fromUser(text) {
        var transformedInput = text.replace(/[^0-9]/g, '');
        console.log(transformedInput);
        if(transformedInput !== text) {
            ngModelCtrl.$setViewValue(transformedInput);
            ngModelCtrl.$render();
        }
        return transformedInput;
      }
      ngModelCtrl.$parsers.push(fromUser);
    }
  }; 
});



brtaApp.service('userDetails', function($http,applicationContextURL){
	return {
		getLoggedInUserDetails: function(){
		return $http.get(applicationContextURL+"/common/getdetails/user").	
	then(function(response){
	console.log(JSON.stringify(response));
	return response.data;
		
	},function(errResponse){
		
		console.error('Error while retrieving user details');
		
	});
		
		}
	}
});

angular.module('changeStatus', []).filter('filterStatus', function() {
	return function(status){
	if(status==true){
		return 'Active';
	}
	else{
		return 'Inactive';
	}
	}
})


angular.module('showDay', []).filter('filterShowDay', function() {

	return function(frequency,weekNumber,dayNumber){
		console.log(dayNumber);
		var str = "" ;
		if(frequency==1){
			str= str+'Weekly ';
			str = str + 'Week '+weekNumber
		}
		
		if(frequency==2){
			str = str+'Monthly ';
			str = str + 'Week '+weekNumber
		}
		
	
	if(dayNumber==1){
		return str+'Monday';
	}
	else if(dayNumber==2){
		return str+'Tuesday';
	}
	else if(dayNumber==3){
		return str+'Wednesday';
	}
	else if(dayNumber==4){
		return str+'Thursday';
	}
	else if(dayNumber==5){
		return str+'Friday';
	}
	else if(dayNumber==6){
		return str+'Saturday';
	}
	else if(dayNumber==7){
		return str+'Sunday';
	}
	}
})



angular.module('convertToString', []).filter('convertArrayToString', function() {
	return function(array){
	  if(array!=null && array.length!=0){
		  return array.join(",");
	  }
	}
})

brtaApp.config(function($routeProvider) {
    $routeProvider

        // route for the home page
        .when('/users', {
            templateUrl : 'views/usermanagement.html',
            controller  : 'userManagementController',
			resolve: {
            message: function(){
                return 'Driver';
					}
					}
        })
		
		.when('/driver', {
            templateUrl : 'views/usermanagement.html',
            controller  : 'userManagementController',
			resolve: {
            message: function(){
                return 'Driver';
					}
					}
        })
		
		
		.when('/suppliers', {
            templateUrl : 'views/usermanagement.html',
            controller  : 'userManagementController',
			resolve: {
            message: function(){
                return 'Supplier';
					}
					}
        })
        
        .when('/incidentmanagement', {
            templateUrl : 'views/incidenttypemanagement.html',
            controller  : 'incidentTypeManagementController'
        })
        
        .when('/storemanagement', {
            templateUrl : 'views/storemanagement.html',
            controller  : 'storeManagementController'
        })
        
        .when('/balepickup', {
            templateUrl : 'views/balepickup.html',
            controller  : 'balepickupController'
        })
        
        .when('/reports', {
            templateUrl : 'views/reports.html',
            controller  : 'reportController'
})

                 
       
}).run(function(userService,$location){
	console.log(JSON.stringify(userService.getLoggedInUserDetails()));
	userService.getLoggedInUserDetails().then(function(response){
		 var user = response;
		 console.log('UserDetails->'+JSON.stringify(user));
		 if(user.role=='ROBRCSR'){
				$location.path('/storemanagement');
			}
		 if(user.role=='ROBRMANAGER'){
				$location.path('/incidentmanagement');
			}
	 });
	
	
	


})


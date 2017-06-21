brtaApp.service('supplierService', function($http,applicationContextURL){
	return {
		getAllSuppliers: function(){
		return $http.get(applicationContextURL+"/get/supplier/all").	
	then(function(response){
	
	return response.data;
		
	},function(errResponse){
		
		console.error('Error while retrieving suppliers');
		
	});
		
		},
		getAllSupplierSites: function(supplier){
			
			return $http.post(applicationContextURL+"/get/suppliersite/bysupplier",supplier).	
		then(function(response){
		
		return response.data;
			
		},function(errResponse){
			
			console.error('Error while retrieving supplier sites');
			
		});
			
			}
	}
});

	
brtaApp.service('customerService', function($http,$rootScope,$q,applicationContextURL){

	return {
		getAllBuyCustomers: function(){
		
		if($rootScope.buyCustomers != undefined){
		return this.populateData($rootScope.buyCustomers);;
		}
		
		return $http.get(applicationContextURL+"/common/get/buycustomers/all").	
	then(function(response){
	$rootScope.buyCustomers=response.data
	return response.data;
		
	},function(errResponse){
		console.error('Error while retrieving buy customers');	
	});
		
		},
	
	
	getCustomerSitesForBuyCustomer: function(storeDto){
		return $http.post(applicationContextURL+"/get/customersites/bybuycustomer",storeDto).
		then(function(response){
			return response.data;
		
	   },function(errResponse){
		
		console.error('Error while retrieving buy customers');
		});
	
},
getSuppliersForBuyCustomer: function(buyCustomer){
	return $http.post(applicationContextURL+"/common/get/supplier/bybuycustomer",buyCustomer).
	then(function(response){
		return response.data;
	
   },function(errResponse){
	
	console.error('Error while retrieving buy customers');
	});

},
getAllSuppliersWithBuyCustomer: function(){

console.log("==$rootScope.suppliersWithCustomer==="+JSON.stringify($rootScope.suppliersWithCustomer));

		if($rootScope.suppliersWithCustomer != undefined){
		return this.populateDataForMap($rootScope.suppliersWithCustomer);
		}

	return $http.get(applicationContextURL+"/common/get/allSupplierWithBuyCustomer").
	then(function(response){
	$rootScope.suppliersWithCustomer= response.data;
		return response.data;
   },function(errResponse){
	
	console.error('Error while retrieving buy customers');
	});

},
getMatrialsForBuyCustomer: function(buyCustomer){
	return $http.post(applicationContextURL+"/common/get/material/bycustomer",buyCustomer).
	then(function(response){
		return response.data;
   },function(errResponse){
	
	console.error('Error while retrieving materials for customers');
	});
},
getAllSellCustomersForBuyCustomer: function(buyCustomer){
	return $http.post(applicationContextURL+"/get/sellcustomer/bybuycustomer",buyCustomer).
	then(function(response){
		return response.data;
	
   },function(errResponse){
	
	console.error('Error while retrieving sellcustomers for customers');
	});
},
populateData: function(data) {
    var deferred = $q.defer();
    var items = [];
    for (i = 0; i < data.length; i++) {
        items.push(data[i]);
    }
    deferred.resolve(items);
    return deferred.promise;
},
populateDataForMap: function(data) {

var deferred = $q.defer();
  var items = {};

  angular.forEach( data, function(value,key){
      items[key]=value;
  });

 deferred.resolve(items);
  return deferred.promise;
  
}

}
});
brtaApp.controller("userManagementController",function(applicationContextURL,$scope,$http,userService,supplierService,message){

	 $scope.message = message;
	 console.log("======="+$scope.message);

	//$scope.loaderSaveUserMgmt=false;
	$scope.loaderUserManagement=false;
   supplierService.getAllSuppliers().then(function(response){
		$scope.suppliers = response;
		console.log(JSON.stringify($scope.suppliers));
	});
	
	$scope.addUser = false;
	$scope.editingData = {};
	

	$scope.getFilteredData = function(){
			$scope.loaderUserManagement=true;

		var user = {};
		if($scope.message=='Driver'){
			user.roleId=1;
		}else{
			user.roleId=2;
		}
		user.supplierFilter = $scope.supplierFilter;
		user.statusFilter = $scope.statusFilter;
		var supplier = {};
		supplier.supplierId = user.supplierId;
		user.supplier = supplier;
		
		console.log("====="+JSON.stringify(user))
	userService.getFilteredUsers(user).then(function(response){
			$scope.users = response;
			
			$scope.loaderUserManagement= false;
			console.log(JSON.stringify($scope.users));
			$scope.editingData = {};
		    
		    for (var i = 0, length = $scope.users.length; i < length; i++) {
		      $scope.editingData[$scope.users[i].id] = false;
		    }
					$scope.loaderActiveUserMgmt=false;

		});
		
	}
   
   $scope.showadduser = function(){
	   
	   $scope.addUser = true;
	   $scope.user = {};
	  
	   $scope.user.enabled = 'Active';
   }
   
   $scope.checkUniqueMobileNo= function(mobileNo){
   
   if(mobileNo != undefined ){
    userService.checkUniqueMobileNo(mobileNo).then(function(response){
   
   $scope.mobileNoUnique= response.status
   if(  response.status == true){
      $scope.mobile_number_unique_error= true
   }
   });
   }
 
   }
   
   $scope.checkUniqueEmail= function(emailId){
   
   if(emailId != undefined ){
    userService.checkUniqueEmail(emailId).then(function(response){
   
   $scope.emailIdUnique= response.status
   if(  response.status == true){
      $scope.email_unique_error= true
   }
   });
   }
 
   }
   
   
	$scope.addUser = false;
	$scope.addUserDetails  = function(user){
	$scope.loaderUserManagement=true;
	
		$scope.error = false;
		if($scope.user.firstName == "" || $scope.user.firstName== null )
           {
			$scope.first_name_error = true;
			$scope.error = true;
			$scope.loaderUserManagement=false;

            }
		
		if($scope.user.lastName == "" || $scope.user.lastName== null )
        {
			$scope.last_name_error = true;
			$scope.loaderUserManagement=false;
			$scope.error = true;

         }
		
		if($scope.user.mobilePhone == "" || $scope.user.mobilePhone== null )
        {
			$scope.mobile_number_error = true;
			$scope.error = true;
			$scope.loaderUserManagement=false;

         }
		 
		  if(  $scope.mobileNoUnique == true){
      $scope.mobile_number_unique_error= true;
	  				$scope.error = true;
					$scope.loaderUserManagement=false;

   }

		if((!$scope.user.mobilePhone == "" && !$scope.user.mobilePhone== null ) && ($scope.myForm.$valid == false)){
			if(!checkValidMobileNumber($scope.user.mobilePhone)){
				$scope.mobile_number_error = true;
				$scope.error = true;
				$scope.loaderUserManagement=false;
			}
			
  
		}
				
		if(($scope.user.emailId == "" || $scope.user.emailId== null ) && ($scope.myForm.$valid == false))
        {
			$scope.email_error = true;
			$scope.error = true;
			$scope.loaderUserManagement=false;

         }
		
	if(  $scope.emailIdUnique == true){
      $scope.email_unique_error= true;
	  				$scope.error = true;
					$scope.loaderUserManagement=false;

   }
			
		
		
		
		if($scope.user.supplier.supplierId == "" || $scope.user.supplier.supplierId == null )
        {
			$scope.service_provider_error = true;
			$scope.error = true;
			$scope.loaderUserManagement=false;

         }
		
		
		
		
		if(user.enabled=='Active'){
			user.enabled = true;
		}
			else{
				user.enabled = false;}
		
	/*var supplier = {};
	supplier.supplierId = user.supplierId;
	user.supplier = supplier;*/
	console.log(JSON.stringify(user));
		if(!$scope.error){
		
		console.log("--user--"+JSON.stringify(user))
		
		user.statusFilter = $scope.statusFilter;
		user.supplierFilter = $scope.supplierFilter;
		
		var driverSupplierDTO = {};
		console.log(driverSupplierDTO);
		var role={};
		if($scope.message=='Driver'){
			role.roleId=1;
			role.roleDescription='Driver';
			$scope.userDriver='Driver';
		}else if($scope.message=='Supplier'){
			role.roleId=2;
		role.roleDescription='Supplier';
		$scope.userDriver='Supplier';
		}
		driverSupplierDTO.user =user;
		driverSupplierDTO.role =role;
		
		console.log("==driverSupplierDTO==="+JSON.stringify(driverSupplierDTO))
		
		userService.addUser(driverSupplierDTO).then(function(response){
			$scope.addUser = false;
			$scope.users = response;
			$scope.loaderUserManagement=false;
			console.log(JSON.stringify($scope.users));
			if($scope.users != null){
				
				user = {};
			}
			$scope.loaderUserManagement=false;

		});
	/*	userService.addUser(driverSupplierDTO).then(function(response){
			$scope.users = response;
			console.log(JSON.Stringify($scope.users));
		});*/
	}
	}
	
	

	$scope.setDefaultValue= function(name, id){
	
	var value= name + "_" + id;
	
	return value;
	}
	
	$scope.setDefaultValueFalse= function(name, id){
	var value= name + "_" + id;
	$scope[value]= false;
	}

	
	var originalUserDataWithoutChanges={};
     // make rows editable
    $scope.modify = function(tableData){
	
	     $scope.user = tableData;
		originalUserDataWithoutChanges=angular.copy(tableData);

    	if(tableData.enabled == true){
    		$scope.user.enabled = 'Active';
    	}
    	else{
    		$scope.user.enabled = 'Inactive';
    	}
    	$scope.user.supplierId= tableData.supplierId;
		$scope.userMobileNo= tableData.mobilePhone;
		$scope.userEmailId= tableData.emailId;

		 console.log("===originalUserDataWithoutChanges===="+JSON.stringify(originalUserDataWithoutChanges));

        $scope.editingData[tableData.userId] = true;

    };
	
	 $scope.checkUniqueMobileNoForEdit= function(mobileNo, user){
   
   if(mobileNo != $scope.userMobileNo ){
   
   userService.checkUniqueMobileNo(mobileNo).then(function(response){

   $scope.mobileNoUniqueEdit= response.status
   if(  response.status == true){
    var mobileNo= "mobile_number_unique_error_" + user.userId
		$scope[mobileNo] = true;
   }
   });
   }else{
    var mobileNo= "mobile_number_unique_error_" + user.userId
		$scope[mobileNo] = false;
   }
 
   }
   
   
   $scope.checkUniqueEmailIdForEdit= function(emailId, user){

   if(emailId != $scope.userEmailId ){
   
   userService.checkUniqueEmail(emailId).then(function(response){

   $scope.emailIdUniqueEdit= response.status
   if(  response.status == true){
    var emailId= "email_unique_error_" + user.userId
		$scope[emailId] = true;
   }
   });
   }else{
    var emailId= "email_unique_error_" + user.userId
		$scope[emailId] = false;
   }
 
   }
    
    
    $scope.cancel = function(tableData){
	
	
	for(var i=0;i< $scope.users.length;i++){
	
	if($scope.users[i].userId == tableData.userId){
	$scope.users[i] =originalUserDataWithoutChanges
	break;
	}
	
	}
		//tableData = originalUserDataWithoutChanges;
        $scope.editingData[tableData.userId] = false;

    };
    
    
    $scope.editUserDetails = function(user){
    	
		$scope.loaderActiveUserMgmt=true;

		
    	var supplier = {};
    	supplier.supplierId = user.supplier.supplierId;
    	user.supplier = {};
    	user.supplier = supplier;
    console.log(JSON.stringify(user));
    $scope.error = false;
	
			console.log("===originalUserDataWithoutChanges===="+JSON.stringify(originalUserDataWithoutChanges));

	if($scope.user.firstName == "" || $scope.user.firstName== null )
       {
	   var firstName= "first_name_edit_error_" + user.userId
		$scope[firstName] = true;
		$scope.error = true;

        }
	
	if($scope.user.lastName == "" || $scope.user.lastName== null )
    {
	var lastName= "last_name_edit_error_" + user.userId
		$scope[lastName] = true;
		$scope.error = true;

     }
	
	if(($scope.user.mobilePhone == "" || $scope.user.mobilePhone== null) &&( $scope.myEditForm.$valid  == false))
    {
	var mobileNo= "mobile_number_error_" + user.userId
		$scope[mobileNo] = true;
		$scope.error = true;

     }
	if(!$scope.user.mobilePhone == "" && !$scope.user.mobilePhone== null ){
		if(!checkValidMobileNumber($scope.user.mobilePhone)){
			
	var mobileNo= "mobile_number_error_" + user.userId
		$scope[mobileNo] = true;
		$scope.error = true;
		}
	}
	
	if(  $scope.mobileNoUniqueEdit == true){
     var mobileNo= "mobile_number_unique_error_" + user.userId
		$scope[mobileNo] = true;
		$scope.error = true;

   }
	
	if(($scope.user.emailId == "" || $scope.user.emailId== null ) &&( $scope.myEditForm.$valid  == false))
    {
		var email= "email_edit_error_" + user.userId
		$scope[email] = true;
		$scope.error = true;
		
     }
	 
	 if(  $scope.emailIdUniqueEdit == true){
     var email= "email_unique_error_" + user.userId
		$scope[email] = true;
		$scope.error = true;

   }
	
	
	if($scope.user.supplier.supplierId == "" || $scope.user.supplier.supplierId == null )
    {
		var serviceProvider= "edit_service_provider_error_" + user.userId
		$scope[serviceProvider] = true;
		$scope.error = true;

     }
	
   
    if(!$scope.error)
    	{
		
		 if(user.enabled =='Active'){
		user.enabled = true;
	}
	else{
		user.enabled = false;
	}
    user.supplierFilter = $scope.supplierFilter;
    user.statusFilter = $scope.statusFilter;
    userService.editUser(user).then(function(response){
    	$scope.users = response;
    	  $scope.editingData[user.userId] = false;
    	 $scope.getFilteredData();
    })
						$scope.loaderActiveUserMgmt=false;

    	}
    	
    }
    
    $scope.uploadFile=function(files){
  
  
  $scope.$apply (function (){
  
  $scope.message="";
  $scope.selectedFileForUpload= files[0];
  
 
  
  })
  
  }
    
    
     //Parse Excel Data 
    $scope.ParseExcelDataAndSave = function () {
	
		$scope.loaderUserManagement=true;

	
	$scope.loaderActiveBalePickup=true;
        var file = $scope.selectedFileForUpload;
        if (file) {
            var reader = new FileReader();
            reader.onload = function (e) {
			
                var data = e.target.result;
                //XLSX from js-xlsx library , which I will add in page view page
                var workbook = XLSX.read(data, { type: 'binary' });
										
                var sheetName = workbook.SheetNames[0];
                var excelData = XLSX.utils.sheet_to_row_object_array(workbook.Sheets[sheetName]);
                if (excelData.length > 0) {
				
                    //Save data 
                    $scope.saveData(excelData);
                }
                else {
					$scope.loaderActiveBalePickup=false;
                    $scope.Message = "No data found";
                }
            }
            reader.onerror = function (ex) {
                console.log(ex);
            }
 
            reader.readAsBinaryString(file);
        }
    }
    
     $scope.saveData=function(excelData){
  
  console.log("===excelData==="+JSON.stringify(excelData));
  
  console.log("====supplierFilter=="+JSON.stringify($scope.supplierFilter));
  var supplier= {};
  supplier.supplierId=$scope.supplierFilter
   var userList={};
   
   for(var i=0;i<excelData.length;i++){
   excelData[i].supplier=supplier
   }
   
  userList.usersFromExcelList=excelData;
  
  
  userService.userUploadFromExcel(userList).then(function(response){
  $scope.uploadExcelMessage = response.data.message;
  $scope.selectedFileForUpload='';
  $scope.loaderUserManagement=false;


	});
  
	}
     
     $scope.getUserType=function(){
    	 if($scope.message=='Driver'){
 			return 'Add New Driver';
 		}else{
 			return 'Add New Supplier';
 		}
     }
	 
	 
	 
	 
	  $scope.typeUser=function(){
    	 if($scope.message=='Driver'){
 			return 'Driver';
 		}else{
 			return 'Supplier';
 		}
     }
	
});

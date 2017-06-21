brtaApp.controller("balepickupController",function(applicationContextURL,$scope,$rootScope,$http,userService,balepickupService,customerService,
		supplierService,storeConfigService,pickupAssignmentService,incidentTypeService,Lightbox)
	{
	
	var columns=[];
	$scope.editingData = [];
	$scope.commodities=[];
	$scope.storeListView = true;
	
	$scope.currentPage=1;
	$scope.pageSize =10;
	
	$scope.pageChangeHandler = function(num) {
	      $scope.currentPage= num;
	  };
	
	$scope.loaderActiveBalePickup=false;
	$scope.disableSubmitBtn=true;
	$scope.disableAddPickupBtn=true;
	
	$scope.checkSubmitBtn=function(){
	
	if(($scope.startDate  !=undefined ) && ($scope.endDate  !=undefined )){
	$scope.disableSubmitBtn=false;
	$scope.disableAddPickupBtn=false;

	}
	}
	
	customerService.getAllBuyCustomers().then(function(response){
		$scope.buyCustomers = response;
	});
	
	customerService.getAllSuppliersWithBuyCustomer().then(
							function(response) {
							$scope.suppliersForCustomerSelected=[];
								$scope.suppliersWithCustomer = response;
								angular.forEach($scope.suppliersWithCustomer, function(value, key) {
									$scope.suppliersForCustomerSelected.push(value);
								$scope.suppliersForCustomerSelected=	[].concat.apply([], $scope.suppliersForCustomerSelected);
									});
							});	


	
	
	$scope.getBuyCustomerDetails = function(buyCustomer)
	{
	
	
	console.log("==buyCustomer===="+buyCustomer)
	
	$scope.loaderBalePickup= true;
	
	if(buyCustomer!=null){
	angular.forEach($scope.suppliersWithCustomer, function(value, key) {
					if(key ==  buyCustomer.customerId){
						$scope.suppliersForCustomerSelected= value;
					}
	});
	
	
	customerService.getMatrialsForBuyCustomer(buyCustomer).then(function(response){
		$scope.commodities=response;
	});	
	
	}else{
	$scope.suppliersForCustomerSelected=$rootScope.allSuppliers;
	}
	
	$scope.checkSubmitBtn();
	
	$scope.loaderBalePickup= false;
	};
	
	$scope.getUsersFromSupplier= function(supplierSelected){
	$scope.disableAddPickupBtn=false;
	$scope.checkSubmitBtn();
	userService.getAllUsersForSupplierSite(supplierSelected).then(function(response){
		$scope.driverList=response;
		});	
	}
	
	$scope.getDriverPhoneNo=function(driver){
	$scope.pickup.driverPhoneNo=driver.mobilePhone;
		$scope.pickup.userId=driver.userId;
	}
	
	
	$scope.getDestinationForUser=function(userId, pickupDate){

		var pickupDateList=[];
		pickupDateList.push(convertDateToString(pickupDate));
	
	
	var setDto={};
	setDto.userId=userId;
	setDto.dates=pickupDateList;
			
	balepickupService.getDestinationForUserAndDate(setDto).then(function(response){
	
		$scope.destinationList = response.data;
		$scope.storeNameList=[];
		$scope.pickupDetails=[];
		$scope.destinationNames=[];

		for(var i=0;i<$scope.destinationList.length;i++){
				$scope.destinationNames.push($scope.destinationList[i].name);
				var pickupDetails=$scope.destinationList[i].pickupDetails;
				for(var j=0;j<pickupDetails.length;j++){

				$scope.pickupDetails.push(pickupDetails[j]);
				$scope.storeNameList.push(pickupDetails[j].storeName);
		}
		}
    	});
		
		}
	
	
	$scope.addresses = [
	                    {'state':'AL'},
	                    {'state':'AK'},
	                    {'state':'AZ'},
	                    {'state':'AR'},
	                    {'state':'CA'},
	                    {'state':'CO'},
	                    {'state':'CT'},
	                    {'state':'DE'},
	                    {'state':'FL'},
	                    {'state':'GA'},
	                    {'state':'HI'},
	                    {'state':'ID'},
	                    {'state':'IL'},
	                    {'state':'IN'},
	                    {'state':'IA'},
	                    {'state':'KS'},
	                    {'state':'KY'},
	                    {'state':'LA'},
	                    {'state':'ME'},
	                    {'state':'MD'},
	                    {'state':'MA'},
	                    {'state':'MI'},
	                    {'state':'MN'},
	                    {'state':'MS'},
	                    {'state':'MO'},
	                    {'state':'MT'},
	                    {'state':'NE'},
	                    {'state':'NV'},
	                    {'state':'NH'},
	                    {'state':'NJ'},
	                    {'state':'NM'},
	                    {'state':'NY'},
	                    {'state':'NC'},
	                    {'state':'ND'},
	                    {'state':'OH'},
	                    {'state':'OK'},
	                    {'state':'OR'},
	                    {'state':'PA'},
	                    {'state':'RI'},
	                    {'state':'SC'},
	                    {'state':'SD'},
	                    {'state':'TN'},
	                    {'state':'TX'},
	                    {'state':'UT'},
	                    {'state':'VT'},
	                    {'state':'VA'},
	                    {'state':'WA'},
	                    {'state':'WV'},
	                    {'state':'WI'},
	                    {'state':'WY'}
	                  ];

	                $scope.lov_state = [
	                    {'lookupCode':'AL',	'description': 'Alabama - AL'},
						{'lookupCode':'AK',	'description': 'Alaska - AK'},
						{'lookupCode':'AZ',	'description': 'Arizona - AZ'},
						{'lookupCode':'AR',	'description': 'Arkansas - AR'},
						{'lookupCode':'CA',	'description': 'California - CA'},
						{'lookupCode':'CO',	'description': 'Colorado - CO'},
						{'lookupCode':'CT',	'description': 'Connecticut - CT'},
						{'lookupCode':'DE',	'description': 'Delaware - DE'},
						{'lookupCode':'FL',	'description': 'Florida - FL'},
						{'lookupCode':'GA',	'description': 'Georgia - GA'},
						{'lookupCode':'HI',	'description': 'Hawaii - HI'},
						{'lookupCode':'ID',	'description': 'Idaho - ID'},
						{'lookupCode':'IL',	'description': 'Illinois - IL'},
						{'lookupCode':'IN',	'description': 'Indiana - IN'},
						{'lookupCode':'IA',	'description': 'Iowa - IA'},
						{'lookupCode':'KS',	'description': 'Kansas - KS'},
						{'lookupCode':'KY',	'description': 'Kentucky - KY'},
						{'lookupCode':'LA',	'description': 'Louisiana - LA'},
						{'lookupCode':'ME',	'description': 'Maine - ME'},
						{'lookupCode':'MD',	'description': 'Maryland - MD'},
						{'lookupCode':'MA',	'description': 'Massachusetts - MA'},
						{'lookupCode':'MI',	'description': 'Michigan - MI'},
						{'lookupCode':'MN',	'description': 'Minnesota - MN'},
						{'lookupCode':'MS',	'description': 'Mississippi - MS'},
						{'lookupCode':'MO',	'description': 'Missouri - MO'},
						{'lookupCode':'MT',	'description': 'Montana - MT'},
						{'lookupCode':'NE',	'description': 'Nebraska - NE'},
						{'lookupCode':'NV',	'description': 'Nevada - NV'},
						{'lookupCode':'NH',	'description': 'New Hampshire - NH'},
						{'lookupCode':'NJ',	'description': 'New Jersey - NJ'},
						{'lookupCode':'NM',	'description': 'New Mexico - NM'},
						{'lookupCode':'NY',	'description': 'New York - NY'},
						{'lookupCode':'NC',	'description': 'North Carolina - NC'},
						{'lookupCode':'ND',	'description': 'North Dakota - ND'},
						{'lookupCode':'OH',	'description': 'Ohio - OH'},
						{'lookupCode':'OK',	'description': 'Oklahoma - OK'},
						{'lookupCode':'OR',	'description': 'Oregon - OR'},
						{'lookupCode':'PA',	'description': 'Pennsylvania - PA'},
						{'lookupCode':'RI',	'description': 'Rhode Island - RI'},
						{'lookupCode':'SC',	'description': 'South Carolina - SC'},
						{'lookupCode':'SD',	'description': 'South Dakota - SD'},
						{'lookupCode':'TN',	'description': 'Tennessee - TN'},
						{'lookupCode':'TX',	'description': 'Texas - TX'},
						{'lookupCode':'UT',	'description': 'Utah - UT'},
						{'lookupCode':'VT',	'description': 'Vermont - VT'},
						{'lookupCode':'VA',	'description': 'Virginia - VA'},
						{'lookupCode':'WA',	'description': 'Washington  - WA'},
						{'lookupCode':'WV',	'description': 'West Virginia - WV'},
						{'lookupCode':'WI',	'description': 'Wisconsin - WI'},
						{'lookupCode':'WY',	'description': 'Wyoming - WY'}
	                ];
	
		
		$scope.setPickupAssignmentId= function (storeName){
		var destinationId=undefined;
				for(var i=0;i<$scope.pickupDetails.length;i++){
				if($scope.pickupDetails[i].storeName == storeName){
				destinationId= $scope.pickupDetails[i].destination.destinationId
				$scope.pickup.balePickupAssignmentId=$scope.pickupDetails[i].assignmentId;
				$scope.pickup.storeId=$scope.pickupDetails[i].storeId;
				}
				}
				
				if(destinationId != undefined)
				{
				$scope.destinationNames=[];
						for(var j=0;j<$scope.destinationList.length;j++){
						
						if($scope.destinationList[j].destinationId == destinationId)
								$scope.destinationNames.push($scope.destinationList[j].name);
						}
				}		
		}
	

	
	 incidentTypeService.getAllIncidentTypes().then(function(response){
		 $scope.incidentTypeList = response;
		 $scope.incidentTypes = [];
		 		 
		 for (var i=0;i <$scope.incidentTypeList.length ;i++){
		 if($scope.incidentTypeList[i].enabled == true){
		 		 $scope.incidentTypes.push($scope.incidentTypeList[i].incidentDescription);
		 }
		 }
		 
 
	 });
	 
	 $scope.getIncidentObject = function(incidentName){
	 
	 
	 var incidentObj={};
	 for (var i=0;i <$scope.incidentTypeList.length ;i++){
	 
	 if($scope.incidentTypeList[i].incidentDescription == incidentName){
	 incidentObj=$scope.incidentTypeList[i];
	 break;
	 }
		
		 }
		 
		 return incidentObj;
	 }
	 
	  $scope.getDestinationobject= function(name){
	 
	 var destinationObj={};
	 for (var i=0;i <$scope.destinationList.length ;i++){
	 
	 
	 if($scope.destinationList[i].name == name){
	 destinationObj=$scope.destinationList[i];
	 break;
	 }
		
		 }
		 
		 return destinationObj;
	 }
	
	
	var originalBalePickupWithoutEdit={};
	
	$scope.modify = function(balePickup){
       $scope.editingData[balePickup.tripId] = true;
	   
	   originalBalePickupWithoutEdit= angular.copy(balePickup);
	  $scope.getDestinationForUser(balePickup.driver, balePickup.pickupDate);
   };
   
    $scope.cancel = function(balePickup){

	for(var i=0;i< $scope.balePickupList.length;i++){
	
	if($scope.balePickupList[i].tripId == balePickup.tripId){
	$scope.balePickupList[i] =originalBalePickupWithoutEdit
	break;
	}
	
	}
	
	
	        $scope.editingData[balePickup.tripId] = false;
	    };
	    
   
    $scope.getBalePickups = function(buyCustomer,supplier,startDate, endDate,state){
	
		$scope.loaderActiveBalePickup=true;

    	var balePickupFilterDTO = {};
    	balePickupFilterDTO.buyCustomer = buyCustomer;
    	balePickupFilterDTO.supplier = supplier;
    	balePickupFilterDTO.startDate = startDate;
    	balePickupFilterDTO.endDate = endDate;
    	balePickupFilterDTO.state = state;
    		
    	balepickupService.getAllPickups(balePickupFilterDTO).then(function(response){
    		$scope.balePickupList = response.data;
					$scope.loaderActiveBalePickup=false;

    	});
    };
	
	//////////////////////////
	
	
	 $scope.showAddNewPickupForm = function(){
		   $scope.addNewBalePickupView = true;
		   $scope.pickup = { };
	   }
	   
	   var convertDateToString = function (date){
	   var date=new Date(date); 		 
		 var dateStr=(date.getMonth() + 1) + '/' + date.getDate() + '/' +  date.getFullYear()
		 return dateStr;
	   }
	 

	 $scope.saveNewBalePickup  = function(balePickup){
	 		$scope.loaderActiveBalePickup=true;
			$scope.error = false;
			
		if(balePickup.driverName == "" || balePickup.driverName== null )
           { 
			$scope.driverName_error = true;
			$scope.error = true;
			$scope.loaderActiveBalePickup=false;
            }
			
			if(balePickup.driverPhoneNo == "" || balePickup.driverPhoneNo== null )
           {
			$scope.driverPhoneNo_error = true;
			$scope.error = true;
			$scope.loaderActiveBalePickup=false;
            }
			
			if(balePickup.storeName == "" || balePickup.storeName== null )
           {
			$scope.storeName_error = true;
			$scope.error = true;
			$scope.loaderActiveBalePickup=false;

            }
			
			if(balePickup.pickupDate == "" || balePickup.pickupDate== null )
           {
			$scope.pickupDate_error = true;
			$scope.error = true;
		    $scope.loaderActiveBalePickup=false;

            }
			
			if(balePickup.destination == "" || balePickup.destination== null )
           {
			$scope.destination_error = true;
			$scope.error = true;
            $scope.loaderActiveBalePickup=false;

            }
		
			
			if(balePickup.commodities == "" || balePickup.commodities== null )
           {
			$scope.commodity_error = true;
			$scope.error = true;
            }
			
			if(balePickup.balePicked == "" || balePickup.balePicked== null )
           {
			$scope.balePicked_error = true;
			$scope.error = true;
	        $scope.loaderActiveBalePickup=false;

            }
			
				if(balePickup.baleRemaining == "" || balePickup.baleRemaining== null )
           {
			$scope.baleRemaining_error = true;
			$scope.error = true;
	        $scope.loaderActiveBalePickup=false;

            }
			
		if(!$scope.error){
		var commodities=[];
		
		var commodityObj={};
		commodityObj.commodityId=balePickup.commodities.materialId
		commodityObj.name=balePickup.commodities.description
		commodityObj.balesPicked=balePickup.balePicked
		commodityObj.balesRemaining=balePickup.baleRemaining
		commodities.push(commodityObj);
		
		
		var obj={};
		obj.assignmentId=balePickup.balePickupAssignmentId;
		
		
		obj.userId=balePickup.userId;
			obj.storeId=balePickup.storeId;
			obj.storeName=balePickup.storeName;
			obj.address=balePickup.destination;
			obj.pickupDate=convertDateToString(balePickup.pickupDate); 
			obj.balesPicked=balePickup.balePicked;
			obj.balesRemaining=balePickup.baleRemaining;
			obj.comments=balePickup.incidentComment;
			obj.destination=$scope.getDestinationobject(balePickup.destination);;
			obj.commodities=commodities;
			obj.incidentType=$scope.getIncidentObject(balePickup.incidentType);
		
		if (Object.keys(obj.incidentType).length == 0) {
				obj.incident=false;
				obj.incidentType=null;
		}
			obj.bol="0";
			
				console.log("====obj======"+JSON.stringify(obj));

			
			balepickupService.addNewBalePickup(obj).then(function(response){
				if(response != null && response != ""){
				
				var completeTripInputPayLoad={};
				completeTripInputPayLoad.userId=balePickup.userId
				completeTripInputPayLoad.date=convertDateToString(balePickup.pickupDate);
				
				var completePickups=[];
				completePickups.push(obj);
				
				completeTripInputPayLoad.completePickups=completePickups;
				
				console.log("====completeTripInputPayLoad======"+JSON.stringify(completeTripInputPayLoad));
				
				balepickupService.completeBalePickup(completeTripInputPayLoad).then(function(response){
				console.log("---completeBalePickup---"+JSON.stringify(response));
				});
				
					$scope.addNewBalePickupView = false;
					$scope.pickup = {};
					$scope.getBalePickups($scope.buyCustomerSelected ,$scope.supplierSelected , $scope.startDate, $scope.endDate);
				}
			});
		}
		
		$scope.loaderActiveBalePickup=false;

 }
	
	
	    $scope.editBalePickup = function(balePickup){
	   $scope.loaderActiveBalePickup=true;
		var commodities=[];
		
		for (var i=0;i<$scope.commodities.length;i++){
		
		if($scope.commodities[i].description == balePickup.materialName){
		
		var commodityObj={};
		commodityObj.commodityId=$scope.commodities[i].materialId
		commodityObj.name=$scope.commodities[i].description
		commodityObj.balesPicked=balePickup.balesPicked
		commodityObj.balesRemaining=balePickup.balesRemaining

		commodities.push(commodityObj);

		}
		}
		
				
		var obj={};
		obj.assignmentId=balePickup.balePickupAssignmentId
		
		obj.userId=balePickup.driver
			obj.storeId=balePickup.buyCustomerSiteId;
			obj.storeName=balePickup.buyCustomerSiteName;
			obj.address=balePickup.sellCustomerSiteName;
			obj.pickupDate=convertDateToString(balePickup.pickupDate);
			obj.balesPicked=balePickup.balesPicked;
			obj.balesRemaining=balePickup.balesRemaining;
			obj.comments=balePickup.comments;
			obj.destination=$scope.getDestinationobject(balePickup.sellCustomerSiteName);
			obj.commodities=commodities;
			obj.incidentType=$scope.getIncidentObject(balePickup.incidentName);
			obj.bol=balePickup.bol;
			
			if (Object.keys(obj.incidentType).length == 0) {
				obj.incident=false;
				obj.incidentType=null;
		}
	        balepickupService.editBalePickup(obj).then(function(response){
				if(response != null && response != ""){
					$scope.editingData[balePickup.tripId] = false;
					$scope.loaderActiveBalePickup=false;
				}
				
	        })
        	
	        };
		
   $scope.StartDateFun = function() {
  $scope.StartDate.opened = true;
    $scope.dateOptionsStart = {
            datepickerMode:'day',
            minMode:'day',
           // maxDate: new Date(2020, 5, 22),
            //minDate: new Date(2017, 4, 22),
            startingDay: 1,
            showWeeks:false,
            formatMonth:'MMM',
            formatYear: 'yyyy',
            monthColumns:4,
     }
	   
  };

  $scope.StartDate = {
    opened: false
  };
  	
		 $scope.EndDateFun = function() {
       $scope.EndDate.opened = true;
    $scope.dateOptionEnd = {
            datepickerMode:'day',
            minMode:'day',
           // maxDate: new Date(2020, 5, 22),
            //minDate: new Date(2017, 4, 22),
            startingDay: 1,
            showWeeks:false,
            formatMonth:'MMM',
            formatYear: 'yyyy',
            monthColumns:4,
     }
  };

  $scope.EndDate = {
    opened: false
	
  };
  
  $scope.PickupDateFun = function() {
	
    $scope.PickupDate.opened = true;
    $scope.dateOptionPickup = {
            datepickerMode:'day',
            minMode:'day',
           // maxDate: new Date(2020, 5, 22),
            //minDate: new Date(2017, 4, 22),
            startingDay: 1,
            showWeeks:false,
            formatMonth:'MMM',
            formatYear: 'yyyy',
            monthColumns:4,
     }
	 
	 
	
	 
	 
  };

  $scope.PickupDate = {
    opened: false
	
  };
  
  $scope.uploadFile=function(files){
  
  
  $scope.$apply (function (){
  
  $scope.message="";
  $scope.selectedFileForUpload= files[0];
  
 
  
  })
  
  }
   //Parse Excel Data 
    $scope.ParseExcelDataAndSave = function () {
	
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
  
  var pickUpList={};
  pickUpList.picupExcelList=excelData;
  
  
  balepickupService.pickupUploadFromExcel(pickUpList).then(function(response){
  $scope.uploadExcelMessage = response.data.message;
  $scope.selectedFileForUpload='';
$scope.loaderActiveBalePickup=false;

	});
  
  
  }
  
  
  
  
   $scope.downloadExcel = function(){
   
   var blob = new Blob([document.getElementById('balePickupDiv').innerHTML], {
            type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8"
        });
        saveAs(blob, "Report.xls");

 }
   $scope.downloadSampleExcel = function() {
	   
	   var blob = new Blob([document.getElementById('balePickupSampleDiv').innerHTML], {
	            type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8"
	        });
	        saveAs(blob, "PickupSampleUploadFormat.xls");

	 }
   
   $scope.downloadPdf = function(){
	  
	   var marginTop;
	 	var doc = new jsPDF('l', 'pt');

	   var table = document.getElementById('balePickupTable');
	  	   
	 	var rows = tableToJson(table);
		
	 	doc.autoTable(columns, rows, {
			styles : {
				overflow : 'linebreak'
			},
			theme : 'grid',
			headerStyles : {
				fillColor : [ 160, 160, 160 ]
			},
		});
doc.save('report.pdf');
	 	
		
	   }
 
 function tableToJson(table) {
	var data = [];

	
	// first row needs to be headers
	columns = [];
	
	for (var i = 0; i < table.rows[0].cells.length; i++) {
		columns.push(table.rows[0].cells[i].innerHTML);
	}

	var headers = [];
	for (var i = 0; i < table.rows[0].cells.length; i++) {
		headers[i] = table.rows[0].cells[i].innerHTML.toLowerCase().replace(
				/ /gi, '');
	}

	// go through cells
	for (var i = 1; i < table.rows.length; i++) {

		var tableRow = table.rows[i];
		var rowData = [];

		for (var j = 0; j < tableRow.cells.length; j++) {
			if (headers[j] == 'time') {

				if (!tableRow.cells[j].childNodes[0].text) {
					rowData.push(tableRow.cells[j].childNodes[0].data);
				} else {
					rowData.push(tableRow.cells[j].childNodes[0].innerHTML);

				}
			} else {
				rowData.push(tableRow.cells[j].innerHTML);
			}

		}

		data.push(rowData);
	}

	return data;
}
 
 
 
 $scope.openLightboxModal = function (tripId) {
	 
	 $scope.images=[];
    balepickupService.getBalePickUpImages(tripId).then(function(response){
                    
                    // console.log("====="+JSON.stringify(response))
                    
                    for(var i=0;i < response.data.length ; i++){
                                    
                                    
                    var urlObject={
                                                    'url' : 'data:image/gif;base64,' + response.data[i]
                    }              
                                    
                    $scope.images.push(urlObject);
                    }
                    
                    if($scope.images.length > 0){
                                                                    Lightbox.openModal($scope.images, 0);
                    }else{
                    console.log("=====no image found---");
                    }
    });
    
    };
    
    
    
});



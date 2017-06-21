brtaApp.controller("reportController",function(applicationContextURL,$scope,$http,reportService,customerService,incidentTypeService)
	{
	$scope.selectAllIncidentTypeObj={
				"incidentTypeId":"1234",
				"incidentDescription":"All Incidents"
					}

	$scope.editingData = [];
	//$scope.boundary-links=false;
	$scope.detailedActivityReportTab = true;
	$scope.currentPage=1;
	$scope.pageSize =10;
	$scope.pageChangeHandler = function(num) {
	      $scope.currentPage= num;
	  };
	  
	  $scope.currentPageRMT=1;
	  $scope.pageSizeRMT=10;
	  $scope.pageChangeHandlerRMT = function(numrmt) {
	      $scope.currentPageRMT= numrmt;
	  };
	  $scope.currentPagePending=1;
	  $scope.pageSizePending=10;
	  $scope.pageChangeHandlerPending = function(numpending) {
	      $scope.currentPagePending= numpending;
	  };
	
	$scope.disableSubmitBtn=true;
	$scope.disableSubmitBtnRMT=true;
	$scope.disableSubmitBtnPR=true;

	$scope.disableAddPickupBtn=true;
	
	$scope.checkSubmitBtn=function(){
		if(($scope.buyCustomerSelected  !=undefined )&&  ($scope.supplierSelected  !=undefined )&& ($scope.startDate  !=undefined ) && ($scope.endDate  !=undefined )){
			$scope.disableSubmitBtn=false;
			}
		if(($scope.buyCustomerSelectedRMT  !=undefined )&&  ($scope.supplierSelectedRMT  !=undefined )&& ($scope.startDateRMT  !=undefined ) && ($scope.endDateRMT  !=undefined )){
			$scope.disableSubmitBtnRMT=false;
			}
			
			if(($scope.buyCustomerSelectedPR  !=undefined )&&  ($scope.supplierSelectedPR  !=undefined )){
		$scope.disableSubmitBtnPR=false;
		}
		}
	customerService.getAllBuyCustomers().then(function(response){
		$scope.buyCustomers = response;
	});
	incidentTypeService.getAllIncidentTypes().then(function(response){
		$scope.incidentTypes = response;
		
		var c=$scope.incidentTypes.length+1;
		var incident= new String('All Incidents' + c)
		var incident = {};
		incident.incidentDescription = "All Incidents";
		$scope.incidentTypes.splice(0,0,incident);
		});
	$scope.getBuyCustomerDetails = function(buyCustomer)
	{
	$scope.loaderDetailedReport=true;
	$scope.loaderRmtReport = true;
	$scope.loaderPendingReport=true;
		customerService.getSuppliersForBuyCustomer(buyCustomer).then(function(response){
			$scope.suppliersForCustomerSelected = response;	
				$scope.loaderDetailedReport= false;
					$scope.loaderRmtReport = false;
						$scope.loaderPendingReport= false;



			
			});	
		};
		$scope.getBalePickups = function(buyCustomer ,supplier ,incidentType ,startDate, endDate){
		$scope.loaderDetailedReport=true;
			if($scope.selectAllIncident == true){
				incidentType=$scope.selectAllIncidentTypeObj
				}
			var balePickupFilterDTO = {};
    	balePickupFilterDTO.buyCustomer = buyCustomer;
    	balePickupFilterDTO.supplier = supplier;
		balePickupFilterDTO.incidentType = incidentType;
		
		balePickupFilterDTO.startDate = startDate;
    	balePickupFilterDTO.endDate = endDate;
    	reportService.getAllPickups(balePickupFilterDTO).then(function(response){
    		$scope.balePickupList = response.data;
			$scope.loaderDetailedReport=false;

			
    		});
    };
    $scope.getBalePickupsRMT = function(buyCustomer ,supplier ,startDate, endDate){
	    $scope.loaderRmtReport=true;
    	var balePickupFilterDTO = {};
    	balePickupFilterDTO.buyCustomer = buyCustomer;
    	balePickupFilterDTO.supplier = supplier;
    	balePickupFilterDTO.startDate = startDate;
    	balePickupFilterDTO.endDate = endDate;
    	reportService.getAllPickups(balePickupFilterDTO).then(function(response){
    		$scope.balePickupListRMT = response.data;
		    $scope.loaderRmtReport= false;

    	});
    };
	
	
	
    $scope.getPendingBalePickups = function(buyCustomerId,supplierId){
	
	console.log("------buyCustomerId---"+JSON.stringify(buyCustomerId))
	console.log("------supplierId---"+JSON.stringify(supplierId))
	$scope.loaderPendingReport = true;

    	var PendingStoreReportDTO = {};
    	PendingStoreReportDTO.buyCustomerId = buyCustomerId.customerId;
    	PendingStoreReportDTO.supplierId = supplierId.supplierId;
    	reportService.getPendingPickups(PendingStoreReportDTO).then(function(response){
    	$scope.balePickupLists = response.data;
		$scope.loaderPendingReport = false;
    	});
    };
    $scope.EndDateFun = function() {
    	$scope.EndDate.opened = true;
    	$scope.dateOptionsss = {
            datepickerMode:'day',
            minMode:'day',
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
   $scope.StartDateFun = function() {
  $scope.StartDate.opened = true;
    $scope.dateOptionsStart = {
            datepickerMode:'day',
            minMode:'day',
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
   $scope.EndDateFunRMT = function(){
	   $scope.EndDateRMT.opened = true;
    $scope.dateOptionsEnd = {
            datepickerMode:'day',
            minMode:'day',
            startingDay: 1,
            showWeeks:false,
            formatMonth:'MMM',
            formatYear: 'yyyy',
            monthColumns:4,
     }
  };
  $scope.EndDateRMT = {
    opened: false
    };
   $scope.StartDateFunRMT = function() {
  $scope.StartDateRMT.opened = true;
    $scope.dateOptionsStart = {
            datepickerMode:'day',
            minMode:'day',
            startingDay: 1,
            showWeeks:false,
            formatMonth:'MMM',
            formatYear: 'yyyy',
            monthColumns:4,
     }
    };
    $scope.StartDateRMT = {
    opened: false
  };
    $scope.downloadExcel = function(){
    	var blob = new Blob([document.getElementById('rmtReportDiv').innerHTML], {
            type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8"
        });
        saveAs(blob, "Report.xls");

 }
   
   
   $scope.downloadPdf = function(){
	   console.log("---download Pdf---");
	   
	   
	   var marginTop;
	 	var doc = new jsPDF('l', 'pt');

	   var table = document.getElementById('rmtReportTable');
	  	   
		   
		   	   console.log("---table ---"+table);

		   
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
 
 
 $scope.reportExcel = function(){
	   
	   var blob = new Blob([document.getElementById('detailedReportDiv').innerHTML], {
	            type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8"
	        });
	        saveAs(blob, "Report.xls");

	 }
	   
	   
	   $scope.reportPdf = function(){
		   console.log("---download Pdf---");
		   
		   
		   var marginTop;
		 	var doc = new jsPDF('l', 'pt');

		   var table = document.getElementById('detailedReportTable');
		  	   
			   
			   	   console.log("---table ---"+table);

			   
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
	 
	 
	 
	 $scope.pendingreportExcel = function(){
		   
		   var blob = new Blob([document.getElementById('pendingReportDiv').innerHTML], {
		            type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8"
		        });
		        saveAs(blob, "Report.xls");

		 }
		   
		   
		   $scope.pendingreportPdf = function(){
			   console.log("---download Pdf---");
			   
			   
			   var marginTop;
			 	var doc = new jsPDF('l', 'pt');

			   var table = document.getElementById('pendingReportTable');
			  	   
				   
				   	   console.log("---table ---"+table);

				   
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
		
		$scope.changeLongToStringDate = function(balePickup){	
		
		var divertDate=balePickup.divertDate;
		var pickupDate=balePickup.pickupDate;
		
		
		
		if(divertDate != null ){
		var date = new Date(divertDate);
		var month=date.getMonth() + 1;
		var day=date.getDate();
		var year=date.getFullYear();
		if(month <10){
		month='0' + month;
		}
		
		if(day <10){
		day='0' + day;
		}
		var strDate = month + '/' + day+ '/' +  year;
		console.log("================divertedDate=="+strDate);
		console.log("+++++++++++++++++++++++++pickupDate++"+pickupDate);
		if(strDate == pickupDate){
				return balePickup.divertTripDriverFirstName + ' '+ balePickup.divertTripDriverLastName;
		}
		
		}
		
		return balePickup.tripDriverFirstName + ' ' +balePickup.tripDriverLastName;

		}
		
		
		$scope.getBuyCustomerCode= function(balePickup){
		
		var returnValue= balePickup.buyCustomerSiteAlternativeSearchReference != '' ? balePickup.buyCustomerSiteAlternativeSearchReference : balePickup.buyCustomerAlternativeSearchReference
		return returnValue;
		
		}
		
		$scope.getSellCustomerSiteCode= function(balePickup){
		
		var returnValue=balePickup.sellCustomerSiteAlternativeSearchReference != '' ? balePickup.sellCustomerSiteAlternativeSearchReference : balePickup.sellCustomerAlternativeSearchReference;
		return returnValue;
		
		}
		
		

	});


brtaApp
		.controller(
				"storeManagementController",
				function(applicationContextURL, $scope, $rootScope, $http,
						$filter, userService, customerService, supplierService,
						balepickupService, storeConfigService,
						pickupAssignmentService) {

					$scope.storeFilteredItems=[];
					$scope.storeListView = true;
					$scope.storeListCheckAllBtn=true;
					$scope.storeListAllAssignBtn=true;
					$scope.setFrequencyPopup = false;
					$scope.loaderActiveStoreManagement = false;
					$scope.loaderActiveActivityList = false;
					$scope.supplierSite = {};
					$scope.material = {};
					$scope.setFrequencyBtn = true;
					$scope.setDataApplyBtn= true;
					$scope.assignBtn = true;
					$scope.assignActivityPopupBtn = true;
					$scope.storeListViewBtn = true;
					$scope.assignActivityBtn = true;
						
					customerService.getAllBuyCustomers().then(
							function(response) {
								$scope.buyCustomers = response;
								$rootScope.buyCustomers=response;
							});
							
					customerService.getAllSuppliersWithBuyCustomer().then(
							function(response) {
							$scope.suppliersForCustomerSelected=[];
								$scope.suppliersWithCustomer = response;
								angular.forEach($scope.suppliersWithCustomer, function(value, key) {
									$scope.suppliersForCustomerSelected.push(value);
								$scope.suppliersForCustomerSelected=[].concat.apply([], $scope.suppliersForCustomerSelected);
								$rootScope.allSuppliers=$scope.suppliersForCustomerSelected;
									});
							});	

							

					$scope.getBuyCustomerDetails = function(buyCustomer) {
						$scope.loaderStoreListView = true;
						$scope.loaderAssignActivity = true;
						$scope.dropdown.errorDropDownVendorFlag = false;
						
						if(buyCustomer != null){
						if($scope.suppliersWithCustomer != undefined){
						angular.forEach($scope.suppliersWithCustomer, function(value, key) {
						if(key ==  buyCustomer.customerId){
						$scope.suppliersForCustomerSelected= value;
						}
						});
						
							}	else{
							
							customerService.getAllSuppliersWithBuyCustomer().then(
							function(response) {
								$scope.suppliersWithCustomer = response;
								angular.forEach($scope.suppliersWithCustomer, function(value, key) {
						if(key ==  buyCustomer.customerId){
						$scope.suppliersForCustomerSelected= value;
						}
						});
							});	
							}
						}else{
							$scope.suppliersForCustomerSelected=$rootScope.allSuppliers;
						}
						
						$scope.loaderStoreListView = false;
						$scope.loaderAssignActivity = false;
						
					}
					$scope.selectedCustomerSites = [];
					$scope.addToSelectedCustomerSites = function(customerSite) {
					
						if (customerSite.checked){
							$scope.selectedCustomerSites.push(customerSite);
							$scope.setFrequencyBtn= false;
							}
						else if($scope.selectall == true){
							$scope.selectedCustomerSites.splice(
									$scope.selectedCustomerSites
											.indexOf(customerSite), 1);
											$scope.setFrequencyBtn= false;
					}else{
					$scope.selectedCustomerSites.splice(
									$scope.selectedCustomerSites
											.indexOf(customerSite), 1);
											$scope.setFrequencyBtn=true;
					}
					}
					$scope.getAllUsersForSupplier = function() {
						userService.getAllUsersForSupplierSite(
								$scope.supplierSelectedAct).then(
								function(response) {
									$scope.drivers = response;
								})
					}

					/*$scope.week1 = {};
					$scope.week2 = {};
					$scope.week3 = {};
					$scope.week4 = {};
					$scope.week5 = {};*/
					$scope.day = {};
					$scope.day.weeklymon = false;
					$scope.day.weeklytue = false;
					$scope.day.weeklywed = false;
					$scope.day.weeklythu = false;
					$scope.day.weeklyfri = false;
					$scope.day.weeklysat = false;
					$scope.day.weeklysun = false;

					/*
					 * $scope.frequency =
					 * {"week1":{"day1":false,"day2":false,"day3":false,"day4":false,"day5":false,"day6":false,"day7":false},
					 * "week2":{"day1":false,"day2":false,"day3":false,"day4":false,"day5":false,"day6":false,"day7":false},
					 * "week3":{"day1":false,"day2":false,"day3":false,"day4":false,"day5":false,"day6":false,"day7":false},
					 * "week4":{"day1":false,"day2":false,"day3":false,"day4":false,"day5":false,"day6":false,"day7":false},
					 * "week5":{"day1":false,"day2":false,"day3":false,"day4":false,"day5":false,"day6":false,"day7":false} }
					 */

					$scope.dropdown = {};
					$scope.errorDropdown = false;

					$scope.checkAssignDataPopupBtn = function() {
						if (($scope.popParams.driverSelectedinPopup != undefined)
								&& ($scope.popParams.destinatonSelectedinPopup != undefined)
								&& ($scope.popParams.destinatonSiteSelectedinPopup != undefined)) {
							$scope.assignActivityPopupBtn = false;
						}else if(($scope.popParams.driverSelectedinPopup == undefined)
								|| ($scope.popParams.destinatonSelectedinPopup == undefined)
								|| ($scope.popParams.destinatonSiteSelectedinPopup == undefined)){
								$scope.assignActivityPopupBtn = true;
								}
					}
					$scope.setSupplierSiteValue = [];
					$scope.setMaterialValue = [];
					$scope.checkSetDataPopupBtn = function() {

						if (($scope.setDatapopup.supplierSiteSelected != undefined && $scope.setDatapopup.supplierSiteSelected.length > 0)
								&& ($scope.setDatapopup.materialSelected != undefined && $scope.setDatapopup.materialSelected.length > 0)
								&& ($scope.setDatapopup.balePickupStartDate != undefined)) {

							var btnName = "setDataApplyBtn_"
									+ $scope.customerSiteIdForBtn
							$scope[btnName] = false;
						}else if(($scope.setDatapopup.supplierSiteSelected == undefined || $scope.setDatapopup.supplierSiteSelected.length == 0)
								|| ($scope.setDatapopup.materialSelected == undefined || $scope.setDatapopup.materialSelected.length == 0)
								|| ($scope.setDatapopup.balePickupStartDate = undefined)){
						var btnName = "setDataApplyBtn_"
									+ $scope.customerSiteIdForBtn
							$scope[btnName] = true;
							}
							}
					$scope.checkStoreListViewBtn = function() {
						if (($scope.buyCustomerSelected != undefined)
								&& ($scope.supplierSelected != undefined)) {
							$scope.storeListViewBtn = false;
						}
					}
					$scope.checkAssignActivitySubmitBtn = function() {

						if (($scope.buyCustomerSelectedAct != undefined)
								&& ($scope.supplierSelectedAct != undefined)) {
							$scope.assignActivityBtn = false;
						//	$scope.assignBtn = false;
						}
					}
					$scope.getCustomerSites = function(buyCustomer, supplier,states) {
						$scope.loaderActiveStoreManagement = true;
						$scope.setFrequencyBtn = true;
						$scope.selectall == false;
						$scope.storeListCheckAllBtn=true;
						var storeDto={};
						storeDto.buyCustomer=buyCustomer;
						storeDto.state=states;
						
						customerService.getCustomerSitesForBuyCustomer(storeDto).then(function(response) {

							$scope.loaderActiveStoreManagement = false;
						     $scope.storeListCheckAllBtn=false;

							$scope.customerSites = response;

						});
						supplierService.getAllSupplierSites(supplier).then(
								function(response) {
									$scope.supplierSites = response;

								});
						customerService.getMatrialsForBuyCustomer(buyCustomer)
								.then(function(response) {
									$scope.materials = response;

								})
					}
					$scope.pageSize = 10;
					$scope.currentPage = 1;
					$scope.pageChangeHandlerStore = function(num) {
						$scope.currentPage = num;
						$scope.selectall=false;
					};
					
					

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
					
					
					
					
					
					$scope.checkAll = function() {
					console.log("===value==="+$scope.searchTextStoreManagementGrid);
					console.log("===hello====="+$scope.storeFilteredItems.length);
						
						$scope.customerSitesSorted =[];
						if($scope.searchTextStoreManagementGrid != undefined && $scope.searchTextStoreManagementGrid != ''){
						$scope.customerSitesSorted = $filter('orderBy')(
						$scope.storeFilteredItems, 'siteName');

						}else{
								$scope.customerSitesSorted = $filter('orderBy')(
								$scope.customerSites, 'siteName');
						}
						var customerSiteLength=customerSiteLength=$scope.customerSitesSorted.length;		
						var startRange = ($scope.currentPage - 1) * 10;
						var endRange = ($scope.currentPage) * 10
						if(endRange >= customerSiteLength){
							endRange= customerSiteLength;
						}
						for (var i = 0; i < $scope.customerSitesSorted.length; i++) {

							if ((i >= ($scope.currentPage - 1) * 10)
									&& (i < ($scope.currentPage * 10))) {
								if ($scope.selectall == true) {
									$scope.setFrequencyBtn= false;
									
								
								
								if($scope.customerSitesSorted[i].balePickupStartDate !=null 
								&& $scope.customerSitesSorted[i].configuredSupplierSites !=''
								&& $scope.customerSitesSorted[i].configuredSupplierSites !='' ){
								$scope.customerSitesSorted[i].checked = true;
									$scope.addToSelectedCustomerSites($scope.customerSitesSorted[i]);
								}

								} else  {
									$scope.customerSitesSorted[i].checked = false;
									$scope.selectall = false;
									$scope.setFrequencyBtn= true;
								    $scope.addToSelectedCustomerSites($scope.customerSitesSorted[i]);

								}
							}
						}
						console.log("===length===="+JSON.stringify($scope.selectedCustomerSites.length));
						
						$scope.selectedCustomerSites=$scope.removeDuplicateObj($scope.selectedCustomerSites,"customerSiteId");
						console.log("===length===="+JSON.stringify($scope.selectedCustomerSites.length));
						};

					
					$scope.removeDuplicateObj= function(collection,keyname){
					var output = [], 
                  keys = [];
				  angular.forEach(collection, function(item) {
                  var key = item[keyname];
                  if(keys.indexOf(key) === -1) {
                      keys.push(key);
                      output.push(item);
                  }
              });
			return output;
					}
					$scope.pageSize = 10;
					$scope.currentPageAssign = 1;
					$scope.pageChangeHandlerAssign = function(numa) {
						$scope.currentPageAssign = numa;
					};
					$scope.checkAllAssign = function() {
			//		console.log("===valuegrid===="+$scope.searchTextActivityGrid);
			//		console.log("===hello====="+$scope.assignFilteredItems.length);
					$scope.baleAssignmentsSortedAssign =[];
						if($scope.searchTextActivityGrid != undefined && $scope.searchTextActivityGrid != ''){
								$scope.baleAssignmentsSortedAssign = $filter('orderBy')(
								$scope.assignFilteredItems, 'buyCustomerSite.siteName');

						}else{
								$scope.baleAssignmentsSortedAssign = $filter('orderBy')(
								$scope.baleAssignments, 'buyCustomerSite.siteName');
						}
								var baleAssignmentsSortedLength = $scope.baleAssignmentsSortedAssign.length;
								var start=($scope.currentPageAssign - 1) * 10;
								var end = ($scope.currentPageAssign) * 10;
								if(end >= baleAssignmentsSortedLength){
								end= baleAssignmentsSortedLength;
            }
						for (var i = 0; i < $scope.baleAssignmentsSortedAssign.length; i++) {
							if ((i >= start)
									&& (i < end)) {
								if ($scope.selectallassign == true) {
								
								
									$scope.baleAssignmentsSortedAssign[i].checked = true;
									$scope.assignBtn=false;
									$scope.addToAssignmentList($scope.baleAssignmentsSortedAssign[i]);

								} else {
									$scope.baleAssignmentsSortedAssign[i].checked = false;
									$scope.selectallassign = false;
									$scope.assignBtn= true;
									$scope.addToAssignmentList($scope.baleAssignmentsSortedAssign[i]);
								}
							}
						}
					console.log("===length===="+JSON.stringify($scope.assignmentList.length));
				//	console.log("===objects coming first console===="+JSON.stringify($scope.assignmentList));
						
						
						$scope.assignmentList=$scope.removeDuplicates($scope.assignmentList,"balePickupAssignmentId");
						console.log("===length===="+JSON.stringify($scope.assignmentList.length));
			//			console.log("===objects coming last console===="+JSON.stringify($scope.assignmentList));
					};
						$scope.removeDuplicates= function(data,value){
					var assign = [], 
                  assignmentkeys = [];
				  angular.forEach(data, function(item) {
                  var key = item[value];
                  if(assignmentkeys.indexOf(key) === -1) {
                      assignmentkeys.push(key);
                      assign.push(item);
                  }
              });
			return assign;
					}

					$scope.frequency = {};
					$scope.generateFrequeny = function() {

						$scope.listOfAssignments = [];
						
						$scope.customerSiteIdList=[];
						
						angular
								.forEach(
										$scope.selectedCustomerSites,
										function(value, key) {
										
										$scope.customerSiteIdList.push(value.customerSiteId);

											if ($scope.frequency.weeklySelected == true) {
												var weeklySelected = $scope.frequency.weekly
												var weeklySelectedDays = [];
												for ( var key in weeklySelected) {
													if (weeklySelected[key] == false) {
														delete weeklySelected[key]
													}
												}
												weeklySelectedDays = Object
														.keys(weeklySelected);
												for (var k = 0; k < weeklySelectedDays.length; k++) {
													for (var i = 1; i <= 5; i++) {
														var assignment = {};
														assignment.weekNumber = i;
														assignment.frequency = 1;
														assignment.day = weeklySelectedDays[k];
														assignment.buyCustomerSite = value;
														assignment.supplier = $scope.supplierSelected;
														$scope.listOfAssignments
																.push(assignment);
													}
												}
											}
											if ($scope.frequency.monthlySelected == true) {
												var monthlySelected = $scope.frequency.monthlySelected

												if ($scope.frequency.monthWeek1Selected == true) {

													if ($scope.frequency.monthWeek1 != undefined) {
														var weeklySelectedDays = $scope
																.getSelectedDays($scope.frequency.monthWeek1);
														$scope
																.createAssignmentObj(
																		weeklySelectedDays,
																		1, 2,
																		value)
													}
												}
												if ($scope.frequency.monthWeek2Selected == true) {
													var weeklySelectedDays = $scope
															.getSelectedDays($scope.frequency.monthWeek2);
													$scope.createAssignmentObj(
															weeklySelectedDays,
															2, 2, value)
												}
												if ($scope.frequency.monthWeek3Selected == true) {
													var weeklySelectedDays = $scope
															.getSelectedDays($scope.frequency.monthWeek3);
													$scope.createAssignmentObj(
															weeklySelectedDays,
															3, 2, value)
												}

												if ($scope.frequency.monthWeek4Selected == true) {
													var weeklySelectedDays = $scope
															.getSelectedDays($scope.frequency.monthWeek4);
													$scope.createAssignmentObj(
															weeklySelectedDays,
															4, 2, value)
												}

												if ($scope.frequency.monthWeek5Selected == true) {
													var weeklySelectedDays = $scope
															.getSelectedDays($scope.frequency.monthWeek5);
													$scope.createAssignmentObj(
															weeklySelectedDays,
															5, 2, value)
												}
											}											
											if ($scope.frequency.oncall) {
												var assignment = {};
												assignment.weekNumber = -1;
												assignment.frequency = 3;
												assignment.day = -1;
												assignment.buyCustomerSite = value;
												assignment.supplier = $scope.supplierSelected;
												$scope.listOfAssignments
														.push(assignment);
											}
										});

						var balePickupAssignmentDTO = {};

						if ($scope.editOperation == true) {
							balePickupAssignmentDTO.action = "delete";
							balePickupAssignmentDTO.oldAssignments = $scope.balePickupAssignments;
							balePickupAssignmentDTO.assignments = $scope.listOfAssignments;
							balePickupAssignmentDTO.customerSiteIdList = $scope.customerSiteIdList;
							
						} else if ($scope.editOperation == false
								|| $scope.editOperation == undefined) {
							balePickupAssignmentDTO.assignments = $scope.listOfAssignments;
							balePickupAssignmentDTO.action = "set";
							balePickupAssignmentDTO.oldAssignments = [];
							balePickupAssignmentDTO.customerSiteIdList = $scope.customerSiteIdList;
						}
						 pickupAssignmentService
								 .addAssignmentList(balePickupAssignmentDTO)
							.then(
										 function(response) {

											 $scope.setFrequencyclose();
											 $scope.showAddFrequencySuccessMessage = true;
											 $scope.getCustomerSites(
													 $scope.buyCustomerSelected,
													 $scope.supplierSelected);

										});
					}
					$scope.getSelectedDays = function(selectedDays) {
						var weeklySelectedDays = [];
						for ( var key in selectedDays) {
							if (selectedDays[key] == false) {
								delete selectedDays[key]
							}
						}
						weeklySelectedDays = Object.keys(selectedDays);

						return weeklySelectedDays;
					}
					
					$scope.createAssignmentObj = function(weeklySelectedDays,
							weekNo, frequency, buyCustomerSite) {
						for (var k = 0; k < weeklySelectedDays.length; k++) {
							var assignment = {};
							assignment.weekNumber = weekNo;
							assignment.frequency = frequency;
							assignment.day = weeklySelectedDays[k];
							assignment.buyCustomerSite = buyCustomerSite;
							assignment.supplier = $scope.supplierSelected;
							$scope.listOfAssignments.push(assignment);

						}
					}
					$scope.editConfigurationData = function(customerSite) {
						/*
						 * angular.forEach(customerSite.configuredSuppliers,function(value,key){
						 *  })
						 */

					}

					$scope.balePickUpSupplierSiteConfig = {};
					$scope.balePickUpMaterialConfig = {}

					$scope.setDatapopup = {};

					$scope.applyConfigurationData = function() {
						/* $rootScope.BodyOverflow = false; */
						var configData = {}
						var supplierSite = {};

						$scope.balePickupSupplierSiteConfigList = [];
						$scope.materialConfigList = [];
						$scope.error = {};

						
						$scope.errorPopupFlag = false;

						if ($scope.setDatapopup.balePickupStartDate == undefined
								|| $scope.setDatapopup.balePickupStartDate == null
								|| $scope.setDatapopup.balePickupStartDate == "") {
							$scope.error.errorDateFlag = true;
							$scope.errorPopupFlag = true;

						}
						if ($scope.setDatapopup.supplierSiteSelected == undefined
								|| $scope.setDatapopup.supplierSiteSelected == null
								|| $scope.setDatapopup.supplierSiteSelected == "") {
							$scope.error.errorServiceProviderFlag = true;
							$scope.errorPopupFlag = true;
							

						}
						if ($scope.setDatapopup.materialSelected == undefined
								|| $scope.setDatapopup.materialSelected == null
								|| $scope.setDatapopup.materialSelected == "") {
							
							$scope.error.errorMaterialFlag = true;
							$scope.errorPopupFlag = true;
						}
						if (!$scope.errorPopupFlag) {
							
							$scope.error.errorDateFlag = false;
							$scope.error.errorServiceProviderFlag = false;
							$scope.error.errorMaterialFlag = false;

							$scope.setDatapopup.balePickupStartDate = $filter(
									'date')
									(
											new Date(
													$scope.setDatapopup.balePickupStartDate),
											'MM-dd-yyyy')
							
							for (var i = 0; i < $scope.setDatapopup.supplierSiteSelected.length; i++) {
								$scope.balePickUpSupplierSiteConfig = {};
								$scope.balePickUpSupplierSiteConfig.supplierSite = $scope.setDatapopup.supplierSiteSelected[i];
								$scope.customerSite.balePickupStartDate = new Date(
								$scope.setDatapopup.balePickupStartDate);
								$scope.balePickUpSupplierSiteConfig.customerSite = $scope.customerSite;

								$scope.balePickUpSupplierSiteConfig.customerSite.buyCustomerSiteId = $scope.customerSite.customerSiteId;
								$scope.balePickupSupplierSiteConfigList
										.push($scope.balePickUpSupplierSiteConfig);
							}
							for (var i = 0; i < $scope.setDatapopup.materialSelected.length; i++) {
								$scope.balePickUpMaterialConfig = {};
								$scope.balePickUpMaterialConfig.material = $scope.setDatapopup.materialSelected[i];
								$scope.balePickUpMaterialConfig.customerSite = $scope.customerSite;
								$scope.balePickUpMaterialConfig.avgBaleWeight = $scope.setDatapopup.materialSelected[i].avgBaleWeight;
								$scope.balePickUpMaterialConfig.customerSite.buyCustomerSiteId = $scope.customerSite.customerSiteId;
								$scope.materialConfigList
										.push($scope.balePickUpMaterialConfig);
							}
							configData.supplierSiteConfig = $scope.balePickupSupplierSiteConfigList;
							configData.materialConfig = $scope.materialConfigList;
							configData.editAction = $scope.editAction;
							storeConfigService
									.addConfigurationData(configData)
									.then(
											function(response) {
												$scope.setDatapopup.materialSelected = [];
												$scope.setDatapopup.supplierSiteSelected = [];
												$scope.setDataPopupContentClose();
												$scope.showAddDataSuccessMessage = true;
												$scope.getCustomerSites(
																$scope.buyCustomerSelected,
																$scope.supplierSelected);

											})
						}
					}
					$scope.setFrequency = function() {
						$scope.setFrequencyPopupContent = true;
					};

					$scope.setfrequencyPopupRow = function(customerSiteRow,
							action) {

						$scope.frequency = {};
						$scope.frequency.weekly = {};
						$scope.day = {};
						
						$scope.editOperation = false;
						if (action == 'set') {
							$scope.setFrequencyPopupContent = true;
							$scope.customerSiteRow = customerSiteRow;
							$scope.selectedCustomerSites = [];
							$scope.selectedCustomerSites.push(customerSiteRow);
						}

						if (action == 'edit') {
							$scope.editOperation = true;

							$scope.customerSiteRow = customerSiteRow;
							$scope.selectedCustomerSites = [];
							$scope.selectedCustomerSites.push(customerSiteRow);
							$scope.frequency.weekly = {};
							$scope.frequency.monthWeek1Selected = {};
							$scope.frequency.monthWeek2Selected = {};
							$scope.frequency.monthWeek3Selected = {};
							$scope.frequency.monthWeek4Selected = {};
							$scope.frequency.monthWeek5Selected = {};
							$scope.frequency.monthWeek1 = {};
							$scope.frequency.monthWeek2 = {};
							$scope.frequency.monthWeek3 = {};
							$scope.frequency.monthWeek4 = {};
							$scope.frequency.monthWeek5 = {};

							
							pickupAssignmentService
									.getAssignmentsByCustomerSite(
											customerSiteRow)
									.then(
											function(response) {
												balePickupAssignments = response.data;

												$scope.balePickupAssignments = balePickupAssignments;

												angular
														.forEach(
																balePickupAssignments,
																function(value,
																		key) {

																	if (value.frequency == 1) {

																		$scope.frequency.weeklySelected = true;

																		if (value.day == 1) {
																			$scope.frequency.weekly[1] = true;
																		}
																		if (value.day == 2) {
																			$scope.frequency.weekly[2] = true;
																		}
																		if (value.day == 3) {
																			$scope.frequency.weekly[3] = true;
																		}
																		if (value.day == 4) {
																			$scope.frequency.weekly[4] = true;
																		}
																		if (value.day == 5) {
																			$scope.frequency.weekly[5] = true;
																		}
																		if (value.day == 6) {
																			$scope.frequency.weekly[6] = true;
																		}
																		if (value.day == 7) {
																			$scope.frequency.weekly[7] = true;
																		}

																	}
																	if (value.frequency == 2) {

																		$scope.frequency.monthlySelected = true;

																		if (value.weekNumber == 1
																				&& value.day == 1) {
																			$scope.frequency.monthWeek1Selected = true;
																			$scope.frequency.monthWeek1[1] = true;
																		}

																		if (value.weekNumber == 1
																				&& value.day == 2) {
																			$scope.frequency.monthWeek1Selected = true;
																			$scope.frequency.monthWeek1[2] = true;
																		}
																		if (value.weekNumber == 1
																				&& value.day == 3) {
																			$scope.frequency.monthWeek1Selected = true;
																			$scope.frequency.monthWeek1[3] = true;
																		}
																		if (value.weekNumber == 1
																				&& value.day == 4) {
																			$scope.frequency.monthWeek1Selected = true;
																			$scope.frequency.monthWeek1[4] = true;
																		}
																		if (value.weekNumber == 1
																				&& value.day == 5) {
																			$scope.frequency.monthWeek1Selected = true;
																			$scope.frequency.monthWeek1[5] = true;
																		}
																		if (value.weekNumber == 1
																				&& value.day == 6) {
																			$scope.frequency.monthWeek1Selected = true;
																			// $scope.frequency.monthly.monthWeek1[5]
																			// =
																			// true;
																			$scope.frequency.monthWeek1[6] = true;
																		}
																		if (value.weekNumber == 1
																				&& value.day == 7) {
																			$scope.frequency.monthWeek1Selected = true;
																			// $scope.frequency.monthly.monthWeek1[7]
																			// =
																			// true;
																			$scope.frequency.monthWeek1[7] = true;
																		}
																		
																		

																		// Week
																		// 2
																		if (value.weekNumber == 2
																				&& value.day == 1) {
																			$scope.frequency.monthWeek2Selected = true;
																			// $scope.frequency.monthly.monthWeek2[1]=
																			// true;
																			$scope.frequency.monthWeek2[1] = true;

																		}
																		if (value.weekNumber == 2
																				&& value.day == 2) {
																			$scope.frequency.monthWeek2Selected = true;
																			// $scope.frequency.monthly.monthWeek2[2]
																			// =
																			// true;
																			$scope.frequency.monthWeek2[2] = true;

																		}
																		if (value.weekNumber == 2
																				&& value.day == 3) {
																			$scope.frequency.monthWeek2Selected = true;
																			// $scope.frequency.monthly.monthWeek2[3]
																			// =
																			// true;
																			$scope.frequency.monthWeek2[3] = true;

																		}
																		if (value.weekNumber == 2
																				&& value.day == 4) {
																			$scope.frequency.monthWeek2Selected = true;
																			// $scope.frequency.monthly.monthWeek2[4]
																			// =
																			// true;
																			$scope.frequency.monthWeek2[4] = true;

																		}
																		if (value.weekNumber == 2
																				&& value.day == 5) {
																			$scope.frequency.monthWeek2Selected = true;
																			// $scope.frequency.monthly.monthWeek2[5]
																			// =
																			// true;
																			$scope.frequency.monthWeek2[5] = true;

																		}
																		if (value.weekNumber == 2
																				&& value.day == 6) {
																			$scope.frequency.monthWeek2Selected = true;
																			// $scope.frequency.monthly.monthWeek2[6]
																			// =
																			// true;
																			$scope.frequency.monthWeek2[6] = true;

																		}
																		if (value.weekNumber == 2
																				&& value.day == 7) {
																			$scope.frequency.monthWeek2Selected = true;
																			// $scope.frequency.monthly.monthWeek2[7]=
																			// true;
																			$scope.frequency.monthWeek2[7] = true;

																		}

																		// Week
																		// 3
																		if (value.weekNumber == 3
																				&& value.day == 1) {
																			$scope.frequency.monthWeek3Selected = true;
																			$scope.frequency.monthWeek3[1] = true;

																		}
																		if (value.weekNumber == 3
																				&& value.day == 2) {
																			$scope.frequency.monthWeek3Selected = true;
																			$scope.frequency.monthWeek3[2] = true;

																		}
																		if (value.weekNumber == 3
																				&& value.day == 3) {
																			$scope.frequency.monthWeek3Selected = true;
																			$scope.frequency.monthWeek3[3] = true;

																		}
																		if (value.weekNumber == 3
																				&& value.day == 4) {
																			$scope.frequency.monthWeek3Selected = true;
																			$scope.frequency.monthWeek3[4] = true;

																		}
																		if (value.weekNumber == 3
																				&& value.day == 5) {
																			$scope.frequency.monthWeek3Selected = true;
																			$scope.frequency.monthWeek3[5] = true;

																		}
																		if (value.weekNumber == 3
																				&& value.day == 6) {
																			$scope.frequency.monthWeek3Selected = true;
																			$scope.frequency.monthWeek3[6] = true;

																		}
																		if (value.weekNumber == 3
																				&& value.day == 7) {
																			$scope.frequency.monthWeek3Selected = true;
																			$scope.frequency.monthWeek3[7] = true;

																		}

																		// Week4
																		if (value.weekNumber == 4
																				&& value.day == 1) {
																			$scope.frequency.monthWeek4Selected = true;
																			$scope.frequency.monthWeek4[1] = true;

																		}
																		if (value.weekNumber == 4
																				&& value.day == 2) {
																			$scope.frequency.monthWeek4Selected = true;
																			$scope.frequency.monthWeek4[2] = true;

																		}
																		if (value.weekNumber == 4
																				&& value.day == 3) {
																			$scope.frequency.monthWeek4Selected = true;
																			$scope.frequency.monthWeek4[3] = true;
																		}
																		if (value.weekNumber == 4
																				&& value.day == 4) {
																			$scope.frequency.monthWeek4Selected = true;
																			$scope.frequency.monthWeek4[4] = true;

																		}
																		if (value.weekNumber == 4
																				&& value.day == 5) {
																			$scope.frequency.monthWeek4Selected = true;
																			$scope.frequency.monthWeek4[5] = true;

																		}
																		if (value.weekNumber == 4
																				&& value.day == 6) {
																			$scope.frequency.monthWeek4Selected = true;
																			$scope.frequency.monthWeek4[6] = true;

																		}
																		if (value.weekNumber == 4
																				&& value.day == 7) {
																			$scope.frequency.monthWeek4Selected = true;
																			$scope.frequency.monthWeek4[7] = true;

																		}

																		// Week5
																		if (value.weekNumber == 5
																				&& value.day == 1) {
																			$scope.frequency.monthWeek5Selected = true;
																			$scope.frequency.monthWeek5[1] = true;

																		}
																		if (value.weekNumber == 5
																				&& value.day == 2) {
																			$scope.frequency.monthWeek5Selected = true;
																			$scope.frequency.monthWeek5[2] = true;

																		}
																		if (value.weekNumber == 5
																				&& value.day == 3) {
																			$scope.frequency.monthWeek5Selected = true;
																			$scope.frequency.monthWeek5[3] = true;

																		}
																		if (value.weekNumber == 5
																				&& value.day == 4) {
																			$scope.frequency.monthWeek5Selected = true;
																			$scope.frequency.monthWeek5[4] = true;

																		}
																		if (value.weekNumber == 5
																				&& value.day == 5) {
																			$scope.frequency.monthWeek5Selected = true;
																			$scope.frequency.monthWeek5[5] = true;

																		}
																		if (value.weekNumber == 5
																				&& value.day == 6) {
																			$scope.frequency.monthWeek5Selected = true;
																			$scope.frequency.monthWeek5[6] = true;

																		}
																		if (value.weekNumber == 5
																				&& value.day == 7) {
																			$scope.frequency.monthWeek5Selected = true;
																			$scope.frequency.monthWeek5[7] = true;

																		}

																	}
																	if (value.frequency == 3) {
																	$scope.frequency.oncall = true;
																			}
																})

												
											});

							$scope.setFrequencyPopupContent = true;
						}
					}
					$scope.setFrequencyDataPopup = function() {

						$scope.setFrequencyPopup = true;
					}
					$scope.setdataPopup = function(customerSite, action) {

						$rootScope.BodyOverflow = true;

						$scope.customerSiteIdForBtn = customerSite.customerSiteId
						var btnName = "setDataApplyBtn_"+ $scope.customerSiteIdForBtn
						$scope[btnName] = true;

						if (action == 'set') {
							$scope.editAction = false;
						}
						if (action == 'edit') {
							$scope.setDatapopup.balePickupStartDate = customerSite.balePickupStartDate;
							$scope.editAction = true;
							angular
									.forEach(
											$scope.supplierSites,
											function(value, key) {
												angular
														.forEach(
																customerSite.configuredSupplierSites,
																function(
																		valueChild,
																		keyChild) {
																	if (value.siteName == valueChild) {
																		value.checked = true;
																		$scope.updateSupplierSite(value);
																	}

																})
											})

							angular
									.forEach(
											$scope.materials,
											function(value, key) {
												angular
														.forEach(
																customerSite.configuredMaterials,
																function(
																		valueChild,
																		keyChild) {
																	if (valueChild
																			.includes(value.description)) {
																		value.checked = true;
																		var init = valueChild
																				.indexOf('(');
																		var fin = valueChild
																				.indexOf(' lbs');
																		value.avgBaleWeight = valueChild
																				.substr(
																						init + 1,
																						fin
																								- init
																								- 1);
																		console
																				.log(value.avgBaleWeight);
																		$scope
																				.updateMaterial(value);

																	}
																})
											})

							$scope.checkSetDataPopupBtn();
						}
						$scope.customerSite = customerSite;
						$scope.setDataPopup = true;
					}
					$scope.setDataPopupContentClose = function() {
						$rootScope.BodyOverflow = false;
						angular.forEach($scope.materials, function(value, key) {
							value.checked = false;
							value.avgBaleWeight = "";
						})
						angular.forEach($scope.supplierSites, function(value,
								key) {
							value.checked = false;
						})
						$scope.setDatapopup.supplierSiteSelected = [];
						$scope.setDatapopup.materialSelected = [];
						$scope.setDataPopup = false; // Popup Close function
					}

					$scope.setfrequencyPopup = function() {
					
							$scope.editOperation = true;
							$scope.frequency = {};
							$scope.day = {};
							$scope.frequency.weekly = {};
							$scope.frequency.monthWeek1Selected = {};
							$scope.frequency.monthWeek2Selected = {};
							$scope.frequency.monthWeek3Selected = {};
							$scope.frequency.monthWeek4Selected = {};
							$scope.frequency.monthWeek5Selected = {};
							$scope.frequency.monthWeek1 = {};
							$scope.frequency.monthWeek2 = {};
							$scope.frequency.monthWeek3 = {};
							$scope.frequency.monthWeek4 = {};
							$scope.frequency.monthWeek5 = {};
						
						    $scope.setFrequencyPopupContent = true;
					};
					$scope.setFrequencyclose = function() {
					$scope.setFrequencyPopupContent = false;

					};

					$scope.selectedStores = [];
					$scope.toggleSelection = function toggleSelection(
							customerSite) {
						var idx = $scope.selectedStores.indexOf(customerSite);
						// Is currently selected
						if (idx > -1) {
							$scope.selectedStores.splice(idx, 1);
						}
						// Is newly selected
						else {
							$scope.selectedStores.push(customerSite);
						}
					};
					// Assign Activity
					$scope.singleSelect1Value = "Select Driver Name";
					$scope.showSingleSelect1 = function() {
					$scope.ss3 = false;
					$scope.ss2 = false;
					$scope.ss1 = !$scope.ss1;
					}
					$scope.singleChange = function(singleSelect1) {
					$scope.singleSelect1Value = singleSelect1;
					}

					$scope.singlecustomerValue = "Select Sell Customer";
					$scope.showSinglecustomer = function() {
					$scope.ss1 = false;
					$scope.ss3 = false;
					$scope.ss2 = !$scope.ss2;
					}
					$scope.singleChangeoncustomer = function(customerlist) {
					$scope.singlecustomerValue = customerlist;
					}
					$scope.singledestinationValue = "Select Destination";
					$scope.selectdestination = function() {
					$scope.ss1 = false;
					$scope.ss2 = false;
					$scope.ss3 = !$scope.ss3;
					}
					$scope.selectdestinationpart = function(destination) {
					$scope.singledestinationValue = destination;
					}

					$scope.assignactivity = function() {

						$scope.ss1 = false;
						$scope.ss2 = false;
						$scope.ss3 = false;
						$scope.AssignActivityPopup = true;
					}
					$scope.assignactivityclose = function() {
					$scope.AssignActivityPopup = false;
					}

					$scope.setFrequencyNumber = function(number) {

						if (number == 1) {
							$scope.frequency.monthly = undefined;
							$scope.frequency.oncall = undefined;

						}

						else if (number == 2) {
							$scope.frequency.weeklyfilter = undefined;
							$scope.frequency.oncall = undefined;
						} else if (number == 3) {
							$scope.frequency.oncall = {
								"3" : true
							};
							$scope.frequency.weeklyfilter = undefined;
							$scope.frequency.monthly = undefined;

						}
					}
					$scope.getAllAssignmentsWithFilters = function() {
						$scope.showSuccessMessageActivity = false;
						$scope.dropdown.errorDropDownAssignVendorFlag = false;
						$scope.assignBtn = true;
						$scope.storeListAllAssignBtn=true;

						$scope.dropdown = {};

						$scope.loaderActiveActivityList = true;

						if ($scope.frequency != undefined) {

							var weekly = $scope.frequency.weeklyfilter
							var monthly =$scope.frequency.monthly
							var oncall = $scope.frequency.oncall

							var dayList = [];
							var monthWeekList = [];
							if (weekly != undefined) {
								var frequency = '1';
								for ( var key in weekly) {
									if (weeklyfilter[key] == false) {
										delete weekly[key]
									}
								}
								dayList = Object.keys(weeklyfilter);
							} else if (monthly != undefined) {

								var frequency = '2';
								for ( var key in monthly) {

									if (monthly[key] == false) {
										delete monthly[key]
									}
								}

								monthWeekList = Object.keys(monthly);

							} else if (oncall != undefined) {
								var frequency = '3';
							}
						}

						var assignmentFilterDTO = {};
						assignmentFilterDTO.buyCustomer = $scope.buyCustomerSelectedAct;
						assignmentFilterDTO.supplier = $scope.supplierSelectedAct;
						assignmentFilterDTO.user = $scope.driverSelected;
						assignmentFilterDTO.destination = $scope.destinatonSelected;
						assignmentFilterDTO.frequency = frequency;
						assignmentFilterDTO.dayList = dayList;
						assignmentFilterDTO.monthWeekList = monthWeekList;
						assignmentFilterDTO.state= $scope.state;
						pickupAssignmentService.getAllAssignments(
								assignmentFilterDTO).then(function(response) {
							$scope.baleAssignments = response.data;
							$scope.loaderActiveActivityList = false;
							$scope.storeListAllAssignBtn=false;
						})
					}
					$scope.assignmentList = [];

					$scope.addToAssignmentList = function(assignment) {
					console.log("==assignment==="+JSON.stringify(assignment))
						if (assignment.checked) {
						
							$scope.assignmentList.push(assignment);
							$scope.assignBtn= false;
						} else if($scope.selectallassign == true){
							$scope.assignmentList.splice($scope.assignmentList
									.indexOf(assignment), 1)
						$scope.assignBtn= false;

						}else{
						$scope.assignmentList.splice($scope.assignmentList
									.indexOf(assignment), 1)
						$scope.assignBtn= true;
						}
					}
					$scope.popParams = {};
					$scope.getDestinationSites = function() {
					$scope.loaderActiveActivityList = true;

						customerService
								.getCustomerSitesForBuyCustomer(
										$scope.popParams.destinatonSelectedinPopup)
								.then(
										function(response) {
											$scope.destinationSitesForDestinationSelected = response;
											$scope.loaderActiveActivityList = false;
										});
					}
					$scope.updateFieldsForAssignActivity = function() {
						$scope.errorAssignPopup = {};
						$scope.errorAssignPopupFlag = false;
						if ($scope.popParams.driverSelectedinPopup == undefined
								|| $scope.popParams.driverSelectedinPopup == null
								|| $scope.popParams.driverSelectedinPopup == "") {
							$scope.errorAssignPopup.errorAssignDriverFlag = true;
							$scope.errorAssignPopupFlag = true;
						}
						if ($scope.popParams.destinatonSelectedinPopup == undefined
								|| $scope.popParams.destinatonSelectedinPopup == null
								|| $scope.popParams.destinatonSelectedinPopup == "") {
							$scope.errorAssignPopup.errorAssignSellCustomerFlag = true;
							$scope.errorAssignPopupFlag = true;
							console.log("------value---"+ $scope.errorAssignPopup.errorAssignSellCustomerFlag);
						}
						if ($scope.popParams.destinatonSiteSelectedinPopup == undefined
								|| $scope.popParams.destinatonSiteSelectedinPopup == null
								|| $scope.popParams.destinatonSiteSelectedinPopup == "") {
							$scope.errorAssignPopup.errorAssignDestinationFlag = true;
							$scope.errorAssignPopupFlag = true;
						}
						if (!$scope.errorAssignPopupFlag) {
							$scope.errorAssignPopup.errorAssignDriverFlag = false;
							$scope.errorAssignPopup.errorAssignSellCustomerFlag = false;
							$scope.errorAssignPopup.errorAssignDestinationFlag = false;
							angular
									.forEach(
											$scope.assignmentList,
											function(value, key) {
												value.user = $scope.popParams.driverSelectedinPopup;
												value.sellCustomerSite = $scope.popParams.destinatonSiteSelectedinPopup;
											})
							pickupAssignmentService
									.assignActivityToUser($scope.assignmentList)
									.then(
											function(response) {
												$scope.assignactivityclose();
												$scope.assignmentList = [];
												$scope.showSuccessMessageActivity = true;
												$scope.getAllAssignmentsWithFilters();

											})
						}
					}
					$scope.StartDateFun = function() {
						$scope.StartDate.opened = true;
						$scope.dateOptionsStart = {
							datepickerMode : 'day',
							minMode : 'day',
							// maxDate: new Date(2020, 5, 22),
							//minDate: new Date(2017, 4, 22),
							startingDay : 1,
							showWeeks : false,
							formatMonth : 'MMM',
							formatYear : 'yyyy',
							monthColumns : 4,
						}
					};
					$scope.StartDate = {
						opened : false
					};
					$scope.PickupDateFun = function() {
						$scope.PickupDate.opened = true;
						$scope.dateOptionPickup = {
							datepickerMode : 'day',
							minMode : 'day',
							// maxDate: new Date(2020, 5, 22),
							//minDate: new Date(2017, 4, 22),
							startingDay : 1,
							showWeeks : false,
							formatMonth : 'MMM',
							formatYear : 'yyyy',
							monthColumns : 4,
						}
					};

					$scope.PickupDate = {
						opened : false
					};
					$scope.showAssignDriverPopup = function(assignment) {
					$scope.assignmentForNewDriver = assignment;
					$scope.assignNewDriver = true;
					$scope.otherdrivers = [];
						userService
								.getAllActiveUsersForSupplierSite(
										$scope.supplierSelectedAct)
								.then(
										function(response) {
											$scope.drivers = response;
											angular
													.forEach(
															$scope.drivers,
															function(value, key) {
																if (assignment.user.userId != value.userId) {
																	$scope.otherdrivers
																			.push(value);
																}
															})
										})
					}
					$scope.closeAssignNewDriverPopup = function() {
					$scope.assignNewDriver = false;
					}
					$scope.addDivertedUserToAssignment = function(assignment) {
											assignment.divertDate = new Date(assignment.nextPickupDate);

						console.log("======"+JSON.stringify(assignment));
						balepickupService.addNewDriver(assignment).then(
								function() {
									$scope.assignNewDriver = false;
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
					

					
					
					
					
					
					/**multi select code start*/

					$scope.OptionsList = [ {
						name : 'Blaza Tony'
					}, {
						name : 'Dsouza Alex'
					}, {
						name : 'Dsouza Crest	'
					}, {
						name : 'Blaza Tony4'
					}, {
						name : 'Blaza Tony5'
					}, {
						name : 'Option6'
					}, {
						name : 'Option7'
					} ];
					$scope.Sellcustomers = [ {
						name : 'Blaza Tony'
					}, {
						name : 'Dsouza Alex'
					}, {
						name : 'Dsouza Crest	'
					}, {
						name : 'Blaza Tony4'
					}, {
						name : 'Blaza Tony5'
					}, {
						name : 'Option6'
					}, {
						name : 'Option7'
					} ];
					$scope.selectdestinations = [ {
						name : 'Blaza Tony'
					}, {
						name : 'Dsouza Alex'
					}, {
						name : 'Dsouza Crest	'
					}, {
						name : 'Blaza Tony4'
					}, {
						name : 'Blaza Tony5'
					}, {
						name : 'Option6'
					}, {
						name : 'Option7'
					} ];

					$scope.supplierSiteCheckbox = {};
					$scope.materialCheckbox = {};
					$scope.setDatapopup.supplierSiteSelected = [];
					$scope.setDatapopup.materialSelected = [];

					$scope.supplierSiteCheckbox.siteName = "Select Supplier Site Name";
					$scope.materialCheckbox.description = "Select Material Profile";

					$scope.showSingleSelect1 = function() {
					$scope.ss3 = false;
					$scope.ss2 = false;
					$scope.ss1 = !$scope.ss1;

					}
					$scope.singleChange = function(singleSelect1) {
						
					$scope.singleSelect1Value = singleSelect1;
					}

					$scope.updateSupplierSite = function(singleSelect1) {

						
						if (singleSelect1.checked == true) {
							$scope.setDatapopup.supplierSiteSelected
									.push(singleSelect1);
						}
						if (singleSelect1.checked == false) {
							$scope.setDatapopup.supplierSiteSelected.splice(
									singleSelect1, 1);
						}

					}

					$scope.updateMaterial = function(singleSelect1) {

						
								
						if (singleSelect1.checked == true) {
							$scope.setDatapopup.materialSelected
									.push(singleSelect1);
						}
						if (singleSelect1.checked == false) {
							angular
									.forEach(
											$scope.setDatapopup.materialSelected,
											function(value, key) {
												if (value.materialId == singleSelect1.materialId) {
													$scope.setDatapopup.materialSelected
															.splice(key, 1);
												}
											})

						}
						console
								.log(JSON
										.stringify($scope.setDatapopup.materialSelected));

					}

					$scope.singlecustomerValue = "Select commodity Type";
					$scope.showSinglecustomer = function() {
					$scope.ss1 = false;
					$scope.ss3 = false;
					$scope.ss2 = !$scope.ss2;

					}
					$scope.singleChangeoncustomer = function(customerlist) {
					$scope.singlecustomerValue = customerlist;
					}

					/**multi select code end**/

					/**Assign popup Start**/
					$scope.OptionsList = [ {
						name : 'Blaza Tony'
					}, {
						name : 'Dsouza Alex'
					}, {
						name : 'Dsouza Crest	'
					}, {
						name : 'Blaza Tony4'
					}, {
						name : 'Blaza Tony5'
					}, {
						name : 'Option6'
					}, {
						name : 'Option7'
					} ];
					$scope.Sellcustomers = [ {
						name : 'Blaza Tony'
					}, {
						name : 'Dsouza Alex'
					}, {
						name : 'Dsouza Crest	'
					}, {
						name : 'Blaza Tony4'
					}, {
						name : 'Blaza Tony5'
					}, {
						name : 'Option6'
					}, {
						name : 'Option7'
					} ];
					$scope.selectdestinations = [ {
						name : 'Blaza Tony'
					}, {
						name : 'Dsouza Alex'
					}, {
						name : 'Dsouza Crest	'
					}, {
						name : 'Blaza Tony4'
					}, {
						name : 'Blaza Tony5'
					}, {
						name : 'Option6'
					}, {
						name : 'Option7'
					} ];

					$scope.singleSelect1Value = "Select Driver Name";

					$scope.showSingleSelect1 = function() {
						$scope.ss3 = false;
						$scope.ss2 = false;
						$scope.ss1 = !$scope.ss1;

					}
					$scope.singleChange = function(singleSelect1) {
						$scope.singleSelect1Value = singleSelect1;
					}

					$scope.singlecustomerValue = "Select Sell Customer";

					$scope.showSinglecustomer = function() {
						$scope.ss1 = false;
						$scope.ss3 = false;
						$scope.ss2 = !$scope.ss2;

					}
					$scope.singleChangeoncustomer = function(customerlist) {
						$scope.singlecustomerValue = customerlist;
					}

					$scope.singledestinationValue = "Select Destination";

					$scope.selectdestination = function() {
						$scope.ss1 = false;
						$scope.ss2 = false;
						$scope.ss3 = !$scope.ss3;
					}
					$scope.selectdestinationpart = function(destination) {
						$scope.singledestinationValue = destination;
					}

					$scope.assignactivity = function() {
						$scope.popParams = {};

						$scope.ss1 = false;
						$scope.ss2 = false;
						$scope.ss3 = false;
						$scope.AssignActivityPopup = true;
					}
					$scope.assignactivityclose = function() {
						$scope.AssignActivityPopup = false;
					}

					$scope.showDeleteNewDriverPopup = function(assignment) {
						$scope.newDriverAssignment = assignment;
						$scope.newDriverDeletePopup = true;

					}

					$scope.closeAssignNewDriverDeletePopup = function() {
						$scope.newDriverDeletePopup = false;
					}

					$scope.removeDivertedUserToAssignment = function() {
						balepickupService.removeNewDriver(
								$scope.newDriverAssignment).then(function() {
							$scope.newDriverDeletePopup = false;
							$scope.getAllAssignmentsWithFilters();
						});
					}

				});


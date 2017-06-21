



<%@page import="com.wm.brta.dto.UserDTO"%>
<html lang="en" ng-app="brtaApp">

<head>
    <!-- meta tag here -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta name="author" content="Site author">
    <meta name="keywords" content="Site keywords">
    <meta name="description" content="Site description">
    <title>WM-Bale Route Tracking </title>
    
    
    
    
    	<% Double versionNo =(Double)session.getAttribute("versionNo"); %>

    <!-- favorite icon -->
    <link type="image/icon" rel="shortcut icon" href="/brta/app/resources/images/favicon.png">
    <!--<link type="image/png" rel="apple-touch-icon" href="/brta/app/resources/images/fav.png"> -->
    <!-- all stylesheets here -->
	 <link rel="shortcut icon" href="/brta/app/resources/images/favicon.png">

	 <link href="/brta/app/resources/css/bootstrap.min.css?v=${versionNo}" rel="stylesheet" type="text/css" />
   <link href="/brta/app/resources/css/fonts/fonts.css?v=${versionNo}" rel="stylesheet" type="text/css" />
  <link href="/brta/app/resources/css/fonts/font-awesome.min.css?v=${versionNo}" rel="stylesheet" type="text/css" />   
	 <link href="/brta/app/resources/css/style.css?v=${versionNo}" rel="stylesheet" type="text/css" />
	 <link href="/brta/app/resources/css/angular-bootstrap-lightbox.css" rel="stylesheet" type="text/css" />
	 
	  <!-- <link href="css/mediaquery.css" rel="stylesheet" type="text/css" media="screen" /> -->
    <!-- <link href="css/font-awesome.min.css" rel="stylesheet" /> -->
    <!--[if lte IE 9]>
      <link href='css/animations-ie-fix.css' rel='stylesheet'>
    <![endif]-->
    <!-- Link Swiper's CSS -->


    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->

    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->
   
    	<script src="/brta/app/resources/js/jquery.min.js" type="text/javascript"></script>
      <script src="/brta/app/resources/js/common.js" type="text/javascript"></script>
		<script src="/brta/app/resources/js/angular/lib/angular.min.js" type="text/javascript"></script>
	    <script src="/brta/app/resources/js/angular/lib/angular-route.min.js" type="text/javascript"></script>
	    <script src="/brta/app/resources/js/angular/lib/xeditable.min.js" type="text/javascript"></script>
	    <script src="/brta/app/resources/js/angular/lib/xlsx.full.min.js" type="text/javascript"></script>
		<script src="/brta/app/resources/js/angular/lib/angular-js-xlsx.js" type="text/javascript"></script>
		<script src="/brta/app/resources/js/angular/lib/FileSaver.js" type="text/javascript"></script>
		<script src="/brta/app/resources/js/angular/lib/jspdf.min.js" type="text/javascript"></script>		
		<script src="/brta/app/resources/js/angular/lib/jspdf.debug.js" type="text/javascript"></script>
		<script src="/brta/app/resources/js/angular/lib/jspdf.plugin.autotable.js" type="text/javascript"></script>
		<script src="/brta/app/resources/js/angular/lib/angular-bootstrap-lightbox.min.js" type="text/javascript"></script>
		<script src="/brta/app/resources/js/angular/lib/angular-base64-upload.min.js" type="text/javascript"></script>
		<script src="/brta/app/resources/js/angular/lib/angular-base64.min.js"></script>
		
		<script src="/brta/app/resources/js/ui-bootstrap-tpls-2.4.0.min.js" type="text/javascript"></script>
		<script src="/brta/app/resources/js/angular/lib/dirPagination.js" type="text/javascript"></script>
      <script src="/brta/app/resources/js/angular/main.js" type="text/javascript"></script>
      <script src="/brta/app/resources/js/angular/app/landing.js" type="text/javascript"></script>
     	
		<script src="/brta/app/resources/js/angular/service/masterservice.js?v=${versionNo} " type="text/javascript"></script>
		<script src="/brta/app/resources/js/angular/service/incidenttypeservice.js?v=${versionNo}" type="text/javascript"></script>
		<script src="/brta/app/resources/js/angular/service/storeservice.js?v=${versionNo}" type="text/javascript"></script>
   		<script src="/brta/app/resources/js/angular/service/balepickupservice.js?v=${versionNo}" type="text/javascript"></script>
		<script src="/brta/app/resources/js/angular/service/reportservice.js?v=${versionNo}" type="text/javascript"></script>
		      <script src="/brta/app/resources/js/angular/service/userservice.js?v=${versionNo}" type="text/javascript"></script>
		      
		
		
		<script src="/brta/app/resources/js/angular/app/usermanagement.js?v=${versionNo}" type="text/javascript"></script>
		<script src="/brta/app/resources/js/angular/app/incidenttypemanagement.js?v=${versionNo}" type="text/javascript"></script>
		<script src="/brta/app/resources/js/angular/app/storemanagement.js?v=${versionNo}" type="text/javascript"></script>
		<script src="/brta/app/resources/js/angular/app/balepickup.js?v=${versionNo}" type="text/javascript"></script>
	    <script src="/brta/app/resources/js/angular/app/reportmanagement.js?v=${versionNo}" type="text/javascript"></script>
	    
		
	
		<script>
		
		$('.dropdownFirst, .dropdownSec').hide();
		$('.custom-select').click(function(){
		$('.dropdownSec').hide();
		$('.dropdownFirst').toggle();
		});
		$('.custom-select > .dropdownFirst >ul > li' ).click(function(event){
			event.stopPropagation();
			
			$('.dropdownSec').hide();
			$('.custom-select > .dropdownFirst >ul > li').removeClass('active');
			$(this).addClass('active');
			$(this).children('.dropdownSec').toggle();
		});
		
		$('.scrollpagehide').on('click', function() {
 $('body').toggleClass('body-popup-opened');
});
	</script>

		</script>
		
		
		
		
		
    <!--[if lte IE 9]>
      <link href='/PATH/TO/FOLDER/css/animations-ie-fix.css' rel='stylesheet'>
    <![endif]-->


    <!--[if lt IE 9]>
    <script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
    <script src="http://css3-mediaqueries-js.googlecode.com/svn/trunk/css3-mediaqueries.js"></script>
    <![endif]-->

    <!--[if IE]>
      <script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script><![endif]-->
    <!--[if lte IE 7]>
      <script src="js/IE8.js" type="text/javascript"></script><![endif]-->
    <!--[if lt IE 7]>

    <link rel="stylesheet" type="text/css" media="all" href="css/ie6.css"/><![endif]-->
	</head>
	<body ng-class="{'leftmenuinactive': backClick, '':!backClick, 'overflowHidden':BodyOverflow, '':!BodyOverflow}" >
	<%UserDTO user = (UserDTO)session.getAttribute("user"); %>


<!--Header Content-->
	<div class="headerContainer">
		<div class="HeaderTopLayer">
			<div class="logo"><img src="/brta/app/resources/images/Waste_Management_Logo.png"></div>
			<div class="page-title">Bale Route Tracking </div>
			<div class="header_nav " uib-dropdown  dropdown-append-to-body>
					 <span uib-dropdown-toggle ng-controller="welcomeUserController">
						 {{greetingMessage}}, <%=user.getFirstName()%> <span class="caret"></span>
					 </Span>
					 <ul class="dropdown-menu" uib-dropdown-menu role="menu" aria-labelledby="single-button" >
						 <!-- <li role="menuitem"><a href="#">Option</a></li>
						 <li role="menuitem"><a href="#">Option 1</a></li>
						 <li role="menuitem"><a href="#">Option 2</a></li>
						 <li class="divider"></li> -->
						 <li role="menuitem"><a href="logout">Logout</a></li>
					 </ul>
					 <div class="WeatherReport">
						  <div class="WR_value"><img src="/brta/app/resources/images/weather.png">75&#176;F<div>
							 <div class="WR_Address">Houston, TX</div> 
						</div>
				 </div>
			</div>
		</div>
	</div>
	  <!--<div class="HeaderBottomLayer">
		<div class="headerBottom_container">
			<div class="back_button">
		
				<img back-click ng-click="backClick=!backClick"  src="/brta/app/resources/images/back_btn.png">
			</div>

			 <div class="breadcrumb_menu">

				<ul>
					<li><i class="fa fa-home" aria-hidden="true"></i><a href="">Home</a></li>
					<li><i class="fa fa-angle-right" aria-hidden="true"></i><a href="">User Management</a></li>
				</ul>

			</div
<div class="form-field search-control" style="float: right;">
					<input type="text" class="input-field search-field" ng-model="searchTextUserGrid" placeholder="Search User ">
					<button class="btn-search"><i class="fa fa-search" aria-hidden="true"></i></button>
			 </div>
			 
		</div>
	</div> -->
</div>
<div class="ContentFullContainer">
<div class="LeftMenu_wrapper" ng-controller="sideMenuController">	
	<div class="main_menu">
	<div class="back_button">
		
				<img back-click="" ng-click="backClickFun()" src="/brta/app/resources/images/back_btn.png">
			</div>
		<ul class="sidebar-menu" >
		

			<li ng-show="storeAssignment" class="store_menu"><a href="#storemanagement" ng-click ="showstoreManagement=true;showuserManagement=false;showbalePickup=false;showreports=false;showsupplierManagement=false;showincidentManagement=false;" ng-class="{active:showstoreManagement}"><span class="menu-icon"></span>Store Assignment</a></li>
			<li ng-show="userManagement" class="user_menu"><a href="#users" ng-click ="showuserManagement=true;showstoreManagement=false;showbalePickup=false;showreports=false;showsupplierManagement=false;showincidentManagement=false;" ng-class="{active:showuserManagement}"><span class="menu-icon"></span>Driver Management</a></li>
			<li ng-show="balePickup" class="pickup_menu"><a href="#balepickup" ng-click ="showbalePickup=true;showuserManagement=false;showstoreManagement=false;showreports=false;showsupplierManagement=false;showincidentManagement=false;" ng-class="{active:showbalePickup}"><span class="menu-icon"></span>Bale Pickup</a></li>
			<li ng-show="supplierManagement" class="supplier_menu"><a  ng-click ="showsupplierManagement=true;showstoreManagement=false;showbalePickup=false;showreports=false;showuserManagement=false;showincidentManagement=false;" ng-class="{active:showsupplierManagement}"href="#suppliers"><span class="menu-icon"></span>Supplier Management</a></li>
			<li ng-show="incidentManagement" class="incident_menu"><a ng-click ="showincidentManagement=true;showreports=false;showbalePickup=false;showuserManagement=false;showstoreManagement=false;"ng-class="{active:showincidentManagement}" href="#incidentmanagement"><span class="menu-icon"></span>Incident Management</a></li>
			<li ng-show="reports" class="report_menu"><a ng-click ="showreports=true;showincidentManagement=false;showstoreManagement=false;showuserManagement=false;showbalePickup=false;showsupplierManagement=false;" ng-class="{active:showreports}" href="#reports"><span class="menu-icon"></span>Reports</a></li>



		</ul>

	</div>
</div>
<!---loader-->
<div id="loader" ng-if="loaderActive=false">
<div class="spinner"> 
<i class="fa fa-spin fa-circle-o-notch"></i> Loading...</div>
</div>
<!--loader end-->



<!--Header Content-->

<div ng-view></div>


</div><!--Full Container End-->


	</body>
		
</html>
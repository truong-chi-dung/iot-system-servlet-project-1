<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="en-US">
	<head>
		<meta charset="utf-8" />
		<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
		<title>Pinion Shaft Graph View</title>
		<meta name="description" content="" />
		<meta name="Author" content="Dorin Grigoras [www.stepofweb.com]" />

		<!-- mobile settings -->
		<meta name="viewport" content="width=device-width, maximum-scale=1, initial-scale=1, user-scalable=0" />

		<!-- WEB FONTS -->
		<link href="https://fonts.googleapis.com/css?family=Open+Sans:300,400,700,800&amp;subset=latin,latin-ext,cyrillic,cyrillic-ext" rel="stylesheet" type="text/css" />

		<!-- CORE CSS -->
		<link href="assets/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
		
		<!-- THEME CSS -->
		<link href="assets/css/essentials.css" rel="stylesheet" type="text/css" />
		<link href="assets/css/layout.css" rel="stylesheet" type="text/css" />
		<link href="assets/css/color_scheme/green.css" rel="stylesheet" type="text/css" id="color_scheme" />

	</head>
	<!--
		.boxed = boxed version
	-->
	<body>


		<!-- WRAPPER -->
		<div id="wrapper">

			<!-- 
				ASIDE 
				Keep it outside of #wrapper (responsive purpose)
			-->
			<aside id="aside">
				<!--
					Always open:
					<li class="active alays-open">

					LABELS:
						<span class="label label-danger pull-right">1</span>
						<span class="label label-default pull-right">1</span>
						<span class="label label-warning pull-right">1</span>
						<span class="label label-success pull-right">1</span>
						<span class="label label-info pull-right">1</span>
				-->
				<nav id="sideNav"><!-- MAIN MENU -->
					<ul class="nav nav-list">
						<li><!-- dashboard -->
							<a class="dashboard" href="dashboard"><!-- warning - url used by default by ajax (if eneabled) -->
								<i class="main-icon fa fa-dashboard"></i> <span>Dashboard</span>
							</a>
						</li>
						<li class="active">
							<a>
								<i class="fa fa-menu-arrow pull-right"></i>
								<i class="main-icon fa fa-bar-chart-o"></i> <span>Graphs</span>
							</a>
							<ul><!-- submenus -->
								<c:forEach items="${machineList}" var="machine" >
									<li><a href="/nidec2/${machine.machineId}GraphView">${machine.machineName}</a></li>
								</c:forEach>
							</ul>
						</li>
						<li>
							<a href="#">
								<i class="fa fa-menu-arrow pull-right"></i>
								<i class="main-icon fa fa-table"></i> <span>Tables</span>
							</a>
							<ul><!-- submenus -->
								<c:forEach items="${machineList}" var="machine" >
									<li><a href="/nidec2/${machine.machineId}TableView">${machine.machineName}</a></li>
								</c:forEach>								
							</ul>
						</li>
					</ul>
				</nav>
				<span id="asidebg"><!-- aside fixed background --></span>
			</aside>
			<!-- /ASIDE -->


			<!-- HEADER -->
			<header id="header">

				<!-- Mobile Button -->
				<button id="mobileMenuBtn"></button>

				<!-- Logo -->
				<span class="logo pull-left">
					<img src="assets/images/Nidec_logo.png" alt="admin panel" height="35" />
				</span>

				<nav>
					<!-- OPTIONS LIST -->
					<ul class="nav pull-right">

						<!-- USER OPTIONS -->
						<li class="dropdown pull-left">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
								<img class="user-avatar" alt="" src="assets/images/noavatar.jpg" height="34" /> 
								<span class="user-name">
									<span class="hidden-xs">
										Admin <i class="fa fa-angle-down"></i>
									</span>
								</span>
							</a>
							<ul class="dropdown-menu hold-on-click">
								<li><!-- my calendar -->
									<a href="calendar.html"><i class="fa fa-calendar"></i> Calendar</a>
								</li>
								<li><!-- my inbox -->
									<a href="#"><i class="fa fa-envelope"></i> Inbox
										<span class="pull-right label label-default">0</span>
									</a>
								</li>
								<li><!-- settings -->
									<a href="page-user-profile.html"><i class="fa fa-cogs"></i> Settings</a>
								</li>

								<li class="divider"></li>

								<li><!-- lockscreen -->
									<a href="page-lock.html"><i class="fa fa-lock"></i> Lock Screen</a>
								</li>
								<li><!-- logout -->
									<a href="page-login.html"><i class="fa fa-power-off"></i> Log Out</a>
								</li>
							</ul>
						</li>
						<!-- /USER OPTIONS -->

					</ul>
					<!-- /OPTIONS LIST -->

				</nav>

			</header>
			<!-- /HEADER -->


			<!-- 
				MIDDLE 
			-->
			<section id="middle">


				<!-- page title -->
				<header id="page-header">
					<h1>Production Charts</h1>
					<ol class="breadcrumb">
						<li><a href="#">Graphs</a></li>
						<li class="active">Pinion Shaft</li>
					</ol>
				</header>
				<!-- /page title -->


				<div id="content" class="padding-20">

					<!-- Sales Chart -->
					<div id="panel-graphs-flot-c1" class="panel panel-default">

						<div class="panel-heading">

							<span class="elipsis"><!-- panel title -->
								<strong>Real time measure value graph</strong>
								<strong id="mcsta"></strong>
							</span>
							
							<!-- right options -->
							<ul class="options pull-right list-inline">	
								<li><a href="#" class="opt panel_colapse" data-toggle="tooltip" title="Colapse" data-placement="bottom"></a></li>
								<li><a href="#" class="opt panel_fullscreen hidden-xs" data-toggle="tooltip" title="Fullscreen" data-placement="bottom"><i class="fa fa-expand"></i></a></li>
								<li><a href="#" class="opt panel_close" data-confirm-title="Confirm" data-confirm-message="Are you sure you want to remove this panel?" data-toggle="tooltip" title="Close" data-placement="bottom"><i class="fa fa-times"></i></a></li>
							</ul>
							<!-- /right options -->

						</div>

						<!-- panel content -->
						<div class="panel-body">
							<div id="realtime-chart"  class="flot-chart height-300"><!-- FLOT CONTAINER --></div>
						</div>
						<!-- /panel content -->

					</div>
					<!-- /Sales Chart -->				
					

					<div class="row">						
						<div class="col-md-6">
							<!-- Sales Chart -->
							<div id="panel-graphs-flot-1" class="panel panel-default">
		
								<div class="panel-heading">
		
									<span class="elipsis"><!-- panel title -->
										<strong>Pinion shaft production</strong>
									</span>
		
									<!-- right options -->
									<ul class="options pull-right list-inline">
										<li><a href="#" class="opt panel_colapse" data-toggle="tooltip" title="Colapse" data-placement="bottom"></a></li>
										<li><a href="#" class="opt panel_fullscreen hidden-xs" data-toggle="tooltip" title="Fullscreen" data-placement="bottom"><i class="fa fa-expand"></i></a></li>
										<li><a href="#" class="opt panel_close" data-confirm-title="Confirm" data-confirm-message="Are you sure you want to remove this panel?" data-toggle="tooltip" title="Close" data-placement="bottom"><i class="fa fa-times"></i></a></li>
									</ul>
									<!-- /right options -->
		
								</div>
		
								<!-- panel content -->
								<div class="panel-body">
									<div id="flot-line-chart" class="flot-chart height-350"><!-- FLOT CONTAINER --></div>
								</div>
								<!-- /panel content -->
				
								<!-- panel footer -->
								<div class="panel-footer">
									<strong>Producing percentage</strong>
		                            <span id="perTitle" class="pull-right text-muted">${completePer}% Complete (${sumOkDay}/${targetDay})</span>
									<div id="completePerBar" class="progress progress-striped active"></div>
								</div>
								<!-- /panel footer -->
		
							</div>
							<!-- /Sales Chart -->							
						</div>
						<!-- LEFT -->
						<div class="col-md-6">
							<!-- Bar Chart Horizontal -->
							<div id="panel-graphs-flot-1" class="panel panel-default">

								<div class="panel-heading">

									<span class="elipsis"><!-- panel title -->
										<strong>NG Rate %</strong>
									</span>

									<!-- right options -->
									<ul class="options pull-right list-inline">
										<!-- range picker -->
										<li><a href="#" class="opt panel_colapse" data-toggle="tooltip" title="Colapse" data-placement="bottom"></a></li>
										<li><a href="#" class="opt panel_fullscreen hidden-xs" data-toggle="tooltip" title="Fullscreen" data-placement="bottom"><i class="fa fa-expand"></i></a></li>
										<li><a href="#" class="opt panel_close" data-confirm-title="Confirm" data-confirm-message="Are you sure you want to remove this panel?" data-toggle="tooltip" title="Close" data-placement="bottom"><i class="fa fa-times"></i></a></li>
									</ul>
									<!-- /right options -->
								</div>

								<!-- panel content -->
								<div class="panel-body nopadding">

									<div id="flot-bar-chart" class="flot-chart height-350"><!-- FLOT CONTAINER --></div>

								</div>
								<!-- /panel content -->
								
								<!-- panel footer -->
								<div class="panel-footer">
									<div class="row">
										<div class="col-md-1 col-sm-1" >
											<strong> From:</strong>
										</div>
										<div class="col-md-4 col-sm-4">
											<input type="text" class="form-control datepicker" data-format="yyyy-mm-dd" data-lang="en" data-RTL="false" id="fromDate">
										</div>
										<div class="col-md-1 col-sm-1">
											<strong>To:</strong>
										</div>
										<div class="col-md-4 col-sm-4">
											<input type="text" class="form-control datepicker" data-format="yyyy-mm-dd" data-lang="en" data-RTL="false" id="toDate">
										</div>
										<div class="col-md-2 col-sm-2">
											<button type="button" class="btn btn-primary" id="executeBtn">Execute</button>
										</div>
									</div>
								</div>
								<!-- /panel footer -->

							</div>
							<!-- /Bar Chart Horizontal -->
						</div>
					</div>


				</div>
			</section>
			<!-- /MIDDLE -->
		</div>



	
		<!-- JAVASCRIPT FILES -->
		<script type="text/javascript">var plugin_path = 'assets/plugins/';</script>
		<script type="text/javascript" src="assets/plugins/jquery/jquery-2.2.3.min.js"></script>
		<script type="text/javascript" src="assets/js/app.js"></script>

		<!-- PAGE LEVEL SCRIPTS -->
		<script type="text/javascript">
			loadScript(plugin_path + "chart.flot/jquery.flot.min.js", function(){
				loadScript(plugin_path + "chart.flot/jquery.flot.resize.min.js", function(){
					loadScript(plugin_path + "chart.flot/jquery.flot.time.min.js", function(){
						loadScript(plugin_path + "chart.flot/jquery.flot.fillbetween.min.js", function(){
							loadScript(plugin_path + "chart.flot/jquery.flot.orderBars.min.js", function(){
								loadScript(plugin_path + "chart.flot/jquery.flot.pie.min.js", function(){
									loadScript(plugin_path + "chart.flot/jquery.flot.tooltip.min.js", function(){									
										// demo js script
										loadScript("assets/js/view/demo.graphs.flot.js");
									});
								});
							});
						});
					});
				});
			});
		</script>
		<script>
	    $(function() {
	        var container = $("#flot-line-chart");
	        var updateInterval = 5000;
	        
	       	function drawChart(jsonObj) {
	
	       		var ng = jsonObj.ngData;
	       		var ok = jsonObj.okData;
	       		var completePerAjax = jsonObj.completePer;
	       		var sumOkDayAjax = jsonObj.sumOkDay;
	       		var targetDayAjax = jsonObj.targetDay;
	       		
	       		series = [
	                {data: ng, label: "NG", color: "#ff0000"}, 
	                {data: ok, label: "OK", color: "#009933"}
	            ];
	       		
	       		function doPlot(series_input) {
	       			$.plot($("#flot-line-chart"), series_input, 
	       				{
	       					xaxis : {
	       						mode : "time"
	       					},
	       					grid: {
	       						hoverable: true, //IMPORTANT! this is needed for tooltip to work
	       						borderWidth : 0
	       					},
	       					tooltip: true,
	       					tooltipOpts : {
	       						content : "%y %s at %x",
	       						defaultTheme : false
	       					}
	       				});
	       		} 
	       		$("#completePerBar").html("<div class='progress-bar progress-bar-success' role='progressbar' aria-valuenow='80' aria-valuemin='0' aria-valuemax='100' style='width:"+completePerAjax+"%''></div>");
	       		$("#perTitle").text(Math.round(completePerAjax)+"% Complete ("+sumOkDayAjax+"/"+targetDayAjax+")");
	
	   			if (jsonObj.mcStatus == 0) {
	   				$("#mcsta").html("<label type='label' class='label label-warning'>Stop</label>");
	   			} else if (jsonObj.mcStatus == 1) {
	   				$("#mcsta").html("<label type='label' class='label label-success'>Running</label>");
	   			} else if (jsonObj.mcStatus == 2) {
	   				$("#mcsta").html("<label type='label' class='label label-danger'>Emergency Stop</label>");
	   			}doPlot(series);       		
	       	}
	       	
	       	function getData(callback){
	       		$.ajax({
		       		url:"/nidec2/ajaxGraphView",
		       		success: function(jsonResponse) {
		       			callback(jsonResponse);
		       		}
		       	});
	       	}
	        		
			$(function updateGraph() {
				getData(drawChart);
		    	setTimeout(updateGraph, updateInterval);	    	
		    });
		
	    });
	    </script>
		<script>
	    $(function() {
	        var container = $("#realtime-chart");
	        var updateInterval = 2000;
	        var realtimeLeftProductData = [];
	        var realtimeRightProductData = [];
	        var lowLimit = [];
	        var highLimit = [];
	        
	       	function drawChart(jsonObj) {
				        	
	        	$.each(jsonObj.leftRealTimeData, function(key, value) {
	        		realtimeLeftProductData.push([key, value]);
	        		lowLimit.push([key, 9.996]);
	    			highLimit.push([key, 10.0]);
	            });
	        	
	        	$.each(jsonObj.rightRealTimeData, function(key, value) {
	        		realtimeRightProductData.push([key, value]);
	            });
	        	        	
	       		series = [
	                {data: realtimeLeftProductData, label: "Measure left", color: "#000066"},
	                {data: realtimeRightProductData, label: "Measure right", color: "#0000cc"},
	                {data: lowLimit, label: "Low limit", color: "#cc0000"},
	                {data: highLimit, label: "High limit", color: "#cc0000"}
	            ];
	       		
	       		       		
	       		function doPlot(series_input) {
	       			$.plot($("#realtime-chart"), series_input, 
	       				{	
		   					yaxis : {
		   						min: 9.991,
		   					    max: 10.003
		   					},
	       					grid: {
	       						hoverable: true, //IMPORTANT! this is needed for tooltip to work
	       						borderWidth : 0
	       					},
	       					tooltip: true,
	       					tooltipOpts : {
	       						//content : "%y %s at %x",
	       						content : "%x: %y",
	       						defaultTheme : false
	       					}
	       				});
	       		}       		
	   			doPlot(series);
	   			realtimeLeftProductData=[];
	   			realtimeRightProductData=[];
	   			lowLimit = [];
	   			highLimit = [];
	       	}
	       	
	       	function getData(callback){
	       		$.ajax({
		       		url:"/nidec2/realtimeProduct",
		       		success: function(jsonResponse) {
		       			callback(jsonResponse);
		       		}
		       	});
	       	}        
			
			$(function updateGraph() {
				getData(drawChart);
		    	setTimeout(updateGraph, updateInterval);		
		    });
		
	    });
	    </script>
	    <script>
	    $(function(){
	    	var barData = [];
	    	var barOptions = {
	    	        series: {
	    	            bars: {
	    	                show: true,
	    	                barWidth: 43200000
	    	            }
	    	        },
	    	        xaxis: {
	    	            mode: "time",
	    	            timeformat: "%m/%d",
	    	            minTickSize: [1, "day"]
	    	        },
	    	        yaxis : {
   					    max: 6
   					},
	    	        grid: {
	    	            hoverable: true
	    	        },
	    	        legend: {
	    	            show: false
	    	        },
	    	        tooltip: true,
	    	        tooltipOpts: {
	    	            content: "%y%"
	    	        }
	    	    };
	    	$("#executeBtn").click(function(){
	    		var params = {
		    		    fromDate: $("#fromDate").val(),
		    		    toDate: $("#toDate").val()
		    		};    			
    			
	    		$.post("ajaxNgRate", $.param(params), function(response) {

	    		    $.each(response.ngRateData, function(key, value) {
	    		    	barData.push([key, value]);
		            });    		    
	    		    
	    		    var barDataGraph = {
		    		        label: "bar",
		    		        data: barData,
		    		        color: "#ff0000"
		    		    };
	    		    
	    		    $.plot($("#flot-bar-chart"), [barDataGraph], barOptions);
	    		});
	    	});
	    });
	    </script>
	</body>
</html>
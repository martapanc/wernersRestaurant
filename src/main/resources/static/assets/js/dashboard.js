$(function() {

	'use strict';
	var turl = "/latest-takeaway-orders";
	// Takeaway Order Table
	$.ajax({
		url : turl,
		type : 'post',
		data : {
			"action" : "list"
		},
		dataType : 'json',
		success : function(data) {
			var $table = $('#ta-table');
			$(function() {
				$table.bootstrapTable({
					columns : [ {
						field : 'id',
						title : 'Order Id'
					}, {
						field : 'orderDate',
						title : 'Order Date'
					}, {
						field : 'customerName',
						title : 'Customer Name'
					}, {
						field : 'address',
						title : 'Address'
					}, {
						field : 'phoneNumber',
						title : 'Telephone'
					}, {
						field : 'guest.id',
						title : 'User Id'
					}, {
						field : 'cost',
						title : 'Order Cost',
						formatter : priceFormatter
					}, {
						field : 'status',
						title : 'Status'
					}, {
						field : 'comment',
						title : 'Comment'
					} ],
					data : data,
					detailView : true,
					onExpandRow : function(index, row, $detail) {
						$detail.html('<table></table>').find('table')
								.bootstrapTable({
									columns : [ {
										field : 'id',
										title : 'Id'
									}, {
										field : 'item.foodClass.name',
										title : 'Food Class'
									}, {
										field : 'item.name',
										title : 'Name'
									}, {
										field : 'quantity',
										title : 'Quantity'
									}, {
										field : 'item.price',
										title : 'Unit Price',
										formatter : priceFormatter
									} ],
									data : row.orderItemList
								});
					}
				});
			});
		}
	});

	//item count
	$.ajax({
		type : 'POST',
		cache : false,
		url : "/restaurantProject/item",
		data : {
			"action" : "list"
		},
		success : function(response) {
			$("#item-count").html(response.length + " items");
		},
		error : function(error) {
			console.log(error);
		}
	});
	
	var orderTot = 0;
	var sepR = 0, octR = 0, novR = 0, decR = 0, janR = 0, febR = 0;
	var sepT = 0, octT = 0, novT = 0, decT = 0, janT = 0, febT = 0;
	//reservation count
	$.ajax({
		type : 'POST',
		cache : false,
		url : "/restaurantProject/reservation",
		data : {
			"action" : "list"
		},
		success : function(response) {
			$("#reservation-count").html(response.length);
			orderTot += response.length;
			response.forEach(function(entry) {
				var d = new Date(entry.startDate);
				switch (d.getMonth()) {
					case 8:  sepR++; break;
					case 9:  octR++; break;
					case 10: novR++; break;
					case 11: decR ++; break;
					case 0:  janR ++; break;
					case 1:  febR ++; break;
				}
			});
			
		},
		error : function(error) {
			console.log(error);
		}
	});
	
	//takeaway count
	$.ajax({
		type : 'POST',
		cache : false,
		url : "/restaurantProject/takeaway",
		data : {
			"action" : "list"
		},
		success : function(response) {
			$("#ta-count").html(response.length);
			orderTot += response.length;
			$("#sales-tot").html(orderTot);
			var revenue = 0;
			response.forEach(function(entry) {
				revenue += entry.cost;
			});
			var cost = revenue * 0.4;
			var profit = revenue - cost;
			$("#tot-revenue").html("€ " + revenue.toFixed(2));
			$("#tot-cost").html("€ " + cost.toFixed(2));
			$("#tot-profit").html("€ " + profit.toFixed(2));
			
			response.forEach(function(entry) {
				var d = new Date(entry.orderDate);
				switch (d.getMonth()) {
					case 8:  sepT++; break;
					case 9:  octT++; break;
					case 10: novT++; break;
					case 11: decT ++; break;
					case 0:  janT ++; break;
					case 1:  febT ++; break;
				}
			});
			drawSalesChart();
		},
		error : function(error) {
			console.log(error);
		}
	});
	
	//foodClass count
	$.ajax({
		type : 'POST',
		cache : false,
		url : "/restaurantProject/foodClass",
		data : {
			"action" : "list"
		},
		success : function(response) {
			$("#food-class-count").html(response.length);	
		},
		error : function(error) {
			console.log(error);
		}
	});
	
	/*
	 * ChartJS ------- Here we will create a few charts using ChartJS
	 */

	// -----------------------
	// - MONTHLY SALES CHART -
	// -----------------------
	// Get context with jQuery - using jQuery's .get() method.
	function drawSalesChart() {
		
		var salesChartId = document.getElementById('salesChart'); //Check if the element exists (it doesn't for some user categories) to avoid errors
		
		if (salesChartId !== null) {
			var salesChartCanvas = $("#salesChart").get(0).getContext("2d");
			// This will get the first returned node in the jQuery collection.
			var salesChart = new Chart(salesChartCanvas);
			var salesChartData = {
				labels : [ "September", "October", "November",
						"December", "January", "February" ],
				datasets : [ {
					label : "Table Reservations",
					fillColor : "rgb(210, 214, 222)",
					strokeColor : "rgb(210, 214, 222)",
					pointColor : "rgb(210, 214, 222)",
					pointStrokeColor : "#c1c7d1",
					pointHighlightFill : "#fff",
					pointHighlightStroke : "rgb(220,220,220)",
					data : [ sepR, octR, novR, decR, janR, febR ]
				}, {
					label : "Takeaway Orders",
					fillColor : "rgba(60,141,188,0.7)",
					strokeColor : "rgba(60,141,188,0.7)",
					pointColor : "#3b8bba",
					pointStrokeColor : "rgba(60,141,188,1)",
					pointHighlightFill : "#fff",
					pointHighlightStroke : "rgba(60,141,188,1)",
					data : [ sepT, octT, novT, decT, janT, febT ]
				} ]
			};

			var salesChartOptions = {
				// Boolean - If we should show the scale at all
				showScale : true,
				// Boolean - Whether grid lines are shown across the chart
				scaleShowGridLines : false,
				// String - Colour of the grid lines
				scaleGridLineColor : "rgba(0,0,0,.05)",
				// Number - Width of the grid lines
				scaleGridLineWidth : 1,
				// Boolean - Whether to show horizontal lines (except X axis)
				scaleShowHorizontalLines : true,
				// Boolean - Whether to show vertical lines (except Y axis)
				scaleShowVerticalLines : true,
				// Boolean - Whether the line is curved between points
				bezierCurve : true,
				// Number - Tension of the bezier curve between points
				bezierCurveTension : 0.3,
				// Boolean - Whether to show a dot for each point
				pointDot : false,
				// Number - Radius of each point dot in pixels
				pointDotRadius : 4,
				// Number - Pixel width of point dot stroke
				pointDotStrokeWidth : 1,
				// Number - amount extra to add to the radius to cater for hit
				// detection outside the drawn point
				pointHitDetectionRadius : 20,
				// Boolean - Whether to show a stroke for datasets
				datasetStroke : true,
				// Number - Pixel width of dataset stroke
				datasetStrokeWidth : 2,
				// Boolean - Whether to fill the dataset with a color
				datasetFill : true,
				// String - A legend template
				legendTemplate : "<ul class=\"<%=name.toLowerCase()%>-legend\"><% for (var i=0; i<datasets.length; i++){%><li><span style=\"background-color:<%=datasets[i].lineColor%>\"></span><%=datasets[i].label%></li><%}%></ul>",
				// Boolean - whether to maintain the starting aspect ratio or not
				// when responsive, if set to false, will take up entire container
				maintainAspectRatio : true,
				// Boolean - whether to make the chart responsive to window resizing
				responsive : true
			};

			// Create the line chart
			salesChart.Line(salesChartData, salesChartOptions);
		}	
		
		
	};
	
	
	// ---------------------------
	// - END MONTHLY SALES CHART -
	// ---------------------------
});

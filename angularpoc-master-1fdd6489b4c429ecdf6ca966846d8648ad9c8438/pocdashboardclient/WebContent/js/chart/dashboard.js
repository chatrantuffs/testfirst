'use strict';

var app = angular.module('app');
angular.module('app').controller('DashboardController', DashboardController)

.constant('CHARTS', {
	lineChart : {
		path : '/lineChart',
		title : 'Line Chart'
	},
	cumulativeLineChart : {
		path : '/cumulativeLineChart',
		title : 'Cumulative Line Chart'
	},
	stackedAreaChart : {
		path : '/stackedAreaChart',
		title : 'Stacked Area Chart'
	},
	multiBarChart : {
		path : '/multiBarChart',
		title : 'MultiBar Chart'
	},
	discreteBarChart : {
		path : '/discreteBarChart',
		title : 'DiscreteBar Chart'
	},
	historicalBarChart : {
		path : '/historicalBarChart',
		title : 'HistoricalBar Chart'
	},
	multiBarHorizontalChart : {
		path : '/multiBarHorizontalChart',
		title : 'MultiBar Horizontal Chart'
	},
	pieChart : {
		path : '/pieChart',
		title : 'Pie Chart'
	},
	scatterChart : {
		path : '/scatterChart',
		title : 'Scatter Chart'
	},
	lineWithFocusChart : {
		path : '/lineWithFocusChart',
		title : 'Line with Focus Chart'
	},
	scatterPlusLineChart : {
		path : '/scatterPlusLineChart',
		title : 'Scatter + Line Chart'
	},
	linePlusBarWithFocusChart : {
		path : '/linePlusBarWithFocusChart',
		title : 'Line + Bar with Focus Chart'
	},
	donutChart : {
		path : '/donutChart',
		title : 'Donut Chart'
	},
	bulletChart : {
		path : '/bulletChart',
		title : 'Bullet Chart'
	},
	sparklinePlus : {
		path : '/sparklinePlus',
		title : 'SparkLine Chart'
	},
	parallelCoordinates : {
		path : '/parallelCoordinates',
		title : 'Parallel Coordinates',
		plunker : 'http://plnkr.co/edit/rCQhcL?p=preview'
	},
	multiChart : {
		path : '/multiChart',
		title : 'Multi Chart'
	},
	candlestickBarChart : {
		path : '/candlestickBarChart',
		title : 'Candlestick Chart'
	},
	sunburstChart : {
		path : '/sunburstChart',
		title : 'Sunburst Chart'
	},
	ohlcBarChart : {
		path : '/ohlcBarChart',
		title : 'OHLC Chart'
	},
	boxPlotChart : {
		path : '/boxPlotChart',
		title : 'Box Plot Chart'
	},
	forceDirectedGraph : {
		path : '/forceDirectedGraph',
		title : 'Force Directed Graph'
	}
})

.directive("dashboardDirective", [function () {
    return {
        restrict: "E",
        controller: "DashboardController",
        scope: {
        	title: "=?",
        	isVisible: "=?"
        },
        compile: function compile(tElement, tAttrs, transclude) {
            return {
                pre: function preLink($scope, $elem, $attr, $controller, iterStartElement) {
                },
                post: function($scope, $elem, $attr, $controller, iterStartElement) {
                	
                }
            };
        }
    };
}]);

DashboardController.$inject = [ '$scope', '$rootScope', '$route', '$location', 'CHARTS', 'graphUtil'];

function DashboardController($scope, $rootScope, $route, $location, CHARTS, graphUtil){
	
	init($rootScope, $route, $location, CHARTS);
	
	$scope.chartMethod = function(chartMethod){
		console.log("Inside method : " + chartMethod);
		var result = '';
		switch (chartMethod) {
	    case 'lineChart':
	    	result = graphUtil.lineChart(chartMethod);
	        break;
	    case 'cumulativeLineChart':
	    	result = graphUtil.cumulativeLineChart(chartMethod);
	        break;
	    case 'stackedAreaChart':
	    	result = graphUtil.stackedAreaChart(chartMethod);
	        break;
	    case 'multiBarChart':
	    	result = graphUtil.multiBarChart(chartMethod);
	        break;
	    case 'discreteBarChart':
	    	result = graphUtil.discreteBarChart(chartMethod);
	        break;
	    case 'historicalBarChart':
	    	result = graphUtil.historicalBarChart(chartMethod);
	        break;
	    case 'multiBarHorizontalChart':
	    	result = graphUtil.multiBarHorizontalChart(chartMethod);
	        break;
	    case 'pieChart':
	    	result = graphUtil.pieChart(chartMethod);
	        break;
	    case 'scatterChart':
	    	result = graphUtil.scatterChart(chartMethod);
	        break;
	    case 'lineWithFocusChart':
	    	result = graphUtil.lineWithFocusChart(chartMethod);
	        break;
	    case 'scatterPlusLineChart':
	    	result = graphUtil.scatterPlusLineChart(chartMethod);
	        break;
	    case 'linePlusBarWithFocusChart':
	    	result = graphUtil.linePlusBarWithFocusChart(chartMethod);
	        break;
	    case 'donutChart':
	    	result = graphUtil.donutChart(chartMethod);
	        break;
	    case 'bulletChart':
	    	result = graphUtil.bulletChart(chartMethod);
	        break;
	    case 'sparklinePlus':
	    	result = graphUtil.sparklinePlus(chartMethod);
	        break;
	    case 'parallelCoordinates':
	    	result = graphUtil.parallelCoordinates(chartMethod);
	        break;
	    case 'multiChart':
	    	result = graphUtil.multiChart(chartMethod);
	        break;
	    case 'candlestickBarChart':
	    	result = graphUtil.candlestickBarChart(chartMethod);
	        break;
	    case 'sunburstChart':
	    	result = graphUtil.sunburstChart(chartMethod);
	        break;
	    case 'ohlcBarChart':
	    	result = graphUtil.ohlcBarChart(chartMethod);
	        break;
	    case 'boxPlotChart':
	    	result = graphUtil.boxPlotChart(chartMethod);
	        break;
	    case 'forceDirectedGraph':
	    	result = graphUtil.forceDirectedGraph(chartMethod);
	        break;
	    default:
	    	result = "No Data";
		} 
		return result;
	};
	
	$scope.showDashBoard = function(url, name){
		$scope.modaltitle = name;
		var result = $scope.chartMethod(url.slice(1));
		$scope.options = result.options;
		$scope.data = result.data;
	};
	
	/*$scope.showDashBoard = function(url, name){
		$scope.modaltitle = name;
		debugger;
		$scope.chartMethod(url.slice(1), function(result){
			if(result.error.errorCode == 0 && result.error.errorDescription == ''){
				$scope.options = result.options;
				$scope.data = result.data;
			}
		});
	};*/
	
	function init($rootScope, $route, $location, CHARTS) {
		$rootScope
				.$on(
						'$viewContentLoaded',
						function() {
							document.body.scrollTop = document.documentElement.scrollTop = 0;
							// configure highlightjs
							setTimeout(function() {
								hljs.initHighlightingOnLoad();
								angular.element('pre code').each(
										function(i, e) {
											hljs.highlightBlock(e);
										});
							}, 300);

							$rootScope.params['selectedChart'] = CHARTS[$route.current.$$route.originalPath
									.replace(/\//g, "")];
						});

		$rootScope.params = {
			route : $route,
			mode : 'basic', // basic, extended
			visible : true,
			disabled : false,
			charts : CHARTS,
			url : window.location.hostname
					.indexOf("test") > -1 ? '/angular-nvd3'
					: ''
		};

		$rootScope.utils = {
			keys : function(obj) {
				return Object.keys(obj);
			},

			selectChart : function(chart) {
				$location.path(chart.path)
			},

			prettyPrint : function(json, prettify) {
				return (prettify) ? JSON.stringify(json, undefined,
						2) : json;
			},

			reload : function() {
				$route.reload();
			},

		};

		/* global events for all nvd3 directives */
		$rootScope.events = {
			'jt.onFunctionChanged' : function(e, $scope) {
				$scope.api.refresh();
			}
		};

		/* subscribe on json-tree enevt */
		$rootScope.$on('onFunctionChanged', function(e) {
			setTimeout(function() {
				$rootScope.$broadcast('jt.onFunctionChanged'); // broadcast
																// event
																// that
																// will
																// be
																// caught
																// by
																// nvd3
																// directive
			}, 50)
		});
	}
};
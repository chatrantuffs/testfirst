'use strict';

var app = angular
		.module(
				'mainApp',
				[ 'mainApp.controllers', 'ngRoute', 'json-tree', 'nvd3',
						'gridster', 'ui.bootstrap' ])

		/**
		 * Config
		 * -------------------------------------------------------------------------
		 */
		.config([ '$routeProvider', 'CHARTS', function($routeProvider, CHARTS) {
			$routeProvider.when('/', {
				templateUrl : 'pages/home.html',
				controller : 'mainCtrl'
			});
			$routeProvider.when('/liveedit', {
				templateUrl : 'pages/liveedit.html',
				controller : 'mainCtrl'
			});

			angular.forEach(CHARTS, function(value, key) {
				$routeProvider.when(value.path, {
					templateUrl : 'pages/liveedit.html',
					controller : key + 'Ctrl'
				});
			});

			$routeProvider.otherwise({
				redirectTo : '/'
			});
		} ])

		/**
		 * Run
		 * -------------------------------------------------------------------------
		 */
		.run(
				function($rootScope, $route, $location, CHARTS) {

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
				})

		/**
		 * Main Ctrl
		 * -------------------------------------------------------------------------
		 */
		.controller(
				'mainCtrl',
				function($scope, $location) {
					$scope.isActive = function(viewLocation) {
						if (viewLocation === '/liveedit')
							return (($location.path() !== '/quickstart')
									&& ($location.path() !== '/')
									&& ($location.path() !== '/feedback') && ($location
									.path() !== '/dashboard'));
						else
							return viewLocation === $location.path();
					};
				})

		/**
		 * Constants
		 * -------------------------------------------------------------------------
		 */
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
		});

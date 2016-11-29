angular.module('app')

.controller('DashboardCtrl', ['$scope', '$timeout','generator',
	function($scope, $timeout,generator) {
		$scope.gridsterOptions = {
			margins: [20, 20],
			columns: 4,
			draggable: {
				handle: 'h3'
			}
		};

		$scope.dashboards = {
			'1': {
				id: '1',
				name: 'Home',
				widgets: [{
					col: 0,
					row: 0,
					sizeY: 1,
					sizeX: 1,
					name: "Widget 1"
				}, {
					col: 2,
					row: 1,
					sizeY: 1,
					sizeX: 1,
					name: "Widget 2"
				}
				, {
					col: 0,
					row: 2,
					sizeY: 2,
					sizeX: 2,
					name: "Pie Chart Widget",
					type: 'pieChart',
					chart: {
					options: generator.pieChart.options(),
					data: generator.pieChart.data(),
					api: {}
					}
				}				
				]
			},
			'2': {
				id: '2',
				name: 'Other',
				widgets: [{
					col: 1,
					row: 1,
					sizeY: 1,
					sizeX: 2,
					name: "Other Widget 1"
				}, {
					col: 1,
					row: 3,
					sizeY: 1,
					sizeX: 1,
					name: "Other Widget 2"
				}]
			}
		};

		$scope.clear = function() {
			$scope.dashboard.widgets = [];
		};

		$scope.addWidget = function() {
			$scope.dashboard.widgets.push({
				name: "New Widget",
				sizeX: 1,
				sizeY: 1
			});
		};

		$scope.$watch('selectedDashboardId', function(newVal, oldVal) {
			if (newVal !== oldVal) {
				$scope.dashboard = $scope.dashboards[newVal];
			} else {
				$scope.dashboard = $scope.dashboards[1];
			}
		});

		// init dashboard
		$scope.selectedDashboardId = '1';

	}
])

.controller('CustomWidgetCtrl', ['$scope', '$modal',
	function($scope, $modal) {

		$scope.remove = function(widget) {
			$scope.dashboard.widgets.splice($scope.dashboard.widgets.indexOf(widget), 1);
		};

		$scope.openSettings = function(widget) {
			$modal.open({
				scope: $scope,
				templateUrl: 'demo/dashboard/widget_settings.html',
				controller: 'WidgetSettingsCtrl',
				resolve: {
					widget: function() {
						return widget;
					}
				}
			});
		};

	}
])

.controller('WidgetSettingsCtrl', ['$scope', '$timeout', '$rootScope', '$modalInstance', 'widget',
	function($scope, $timeout, $rootScope, $modalInstance, widget) {
		$scope.widget = widget;

		$scope.form = {
			name: widget.name,
			sizeX: widget.sizeX,
			sizeY: widget.sizeY,
			col: widget.col,
			row: widget.row
		};

		$scope.sizeOptions = [{
			id: '1',
			name: '1'
		}, {
			id: '2',
			name: '2'
		}, {
			id: '3',
			name: '3'
		}, {
			id: '4',
			name: '4'
		}];

		$scope.dismiss = function() {
			$modalInstance.dismiss();
		};

		$scope.remove = function() {
			$scope.dashboard.widgets.splice($scope.dashboard.widgets.indexOf(widget), 1);
			$modalInstance.close();
		};

		$scope.submit = function() {
			angular.extend(widget, $scope.form);

			$modalInstance.close(widget);
		};

	}
])

// helper code
.filter('object2Array', function() {
	return function(input) {
		var out = [];
		for (i in input) {
			out.push(input[i]);
		}
		return out;
	}
//});
})






















 .factory('generator', function (){
    return {
      pieChart: {
        options: function(){
          return {
            chart: {
              type: 'pieChart',
              margin: {
                top: 0,
                right: 0,
                bottom: 30,
                left: 0
              },
              x: function(d){return d.key;},
              y: function(d){return d.y;},
              showLabels: true,
              labelSunbeamLayout: true,
              donutLabelsOutside: true,
              donutRatio: 0.3,
              donut: true,
              transitionDuration: 500,
              labelThreshold: 0.02,
              legend: {
                margin: {
                  top: 5,
                  right: 35,
                  bottom: 0,
                  left: 0
                }
              }
            }
          }
        },
        data: pieChartData
      },
      boxPlotChart: {
        options: function(){
          return {
            chart: {
              type: 'boxPlotChart',
              margin : {
                top: 20,
                right: 20,
                bottom: 20,
                left: 40
              },
              color:['darkblue', 'darkorange', 'green', 'darkred', 'darkviolet'],
              x: function(d){return d.label;},
              //y: function(d){return d.values.Q3;},
              maxBoxWidth: 55,
              yDomain: [0, 500]
            }
          }
        },
        data: boxPlotChartData
      }
    };

    function pieChartData (){
      return [
        {
          key: "One",
          y: 5
        },
        {
          key: "Two",
          y: 2
        },
        {
          key: "Three",
          y: 9
        },
        {
          key: "Four",
          y: 7
        },
        {
          key: "Five",
          y: 4
        },
        {
          key: "Six",
          y: 3
        },
        {
          key: "Seven",
          y: .5
        }
      ];
    }
    function boxPlotChartData (){
      return [
        {
          label: "A",
          values: {
            Q1: 180,
            Q2: 200,
            Q3: 250,
            whisker_low: 115,
            whisker_high: 400,
            outliers: [50, 100, 425]
          }
        },
        {
          label: "B",
          values: {
            Q1: 300,
            Q2: 350,
            Q3: 400,
            whisker_low: 225,
            whisker_high: 425,
            outliers: [175, 450, 480]
          }
        },
        {
          label: "C",
          values: {
            Q1: 100,
            Q2: 200,
            Q3: 300,
            whisker_low: 25,
            whisker_high: 400,
            outliers: [450, 475]
          }
        },
        {
          label: "D",
          values: {
            Q1: 75,
            Q2: 100,
            Q3: 125,
            whisker_low: 50,
            whisker_high: 300,
            outliers: [450]
          }
        },
        {
          label: "E",
          values: {
            Q1: 325,
            Q2: 400,
            Q3: 425,
            whisker_low: 225,
            whisker_high: 475,
            outliers: [50, 100, 200]
          }
        }
      ];
    }
  });
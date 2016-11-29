'use strict';

angular.module('app.graph',['app.angular.debug'])
.controller('GraphController', ['$scope', 'logger', '$location', '$rootScope',
        function ($scope, logger, $location, $rootScope) {
	init();

    function init() {
    	logger.info("Initialsed the Graph Controller");
    	logger.info($rootScope.pageUrl);
       $scope.data = {
              	availableOptions: [
              	       {id: '1', name: 'BulletChart', description: 'Bullet Chart', isVisible: false, pageUrl: '../graphs/bulletChart.html'},
                       {id: '2', name: 'CumulativeLineChart', description: 'Cumulative Line Chart', isVisible: false, pageUrl: '../graphs/cumulativeLineChart.html'},
                       {id: '3', name: 'LineChart', description: 'Line Chart', isVisible: false, pageUrl: '../graphs/lineChart.html'},
                       {id: '4', name: 'LineChartZoom', description: 'Line Chart Zoom', isVisible: false, pageUrl: '../graphs/lineChartSVGResize.html'},
                       {id: '5', name: 'LineWithFocusChart', description: 'Line With Focus Chart', isVisible: false, pageUrl: '../graphs/lineWithFocusChart.html'},
                       {id: '6', name: 'LinePlusBarChart', description: 'Line Plus Bar Chart', isVisible: false, pageUrl: '../graphs/linePlusBarChart.html'},
                       {id: '7', name: 'DiscreteBarChart', description: 'Discrete Bar Chart', isVisible: false, pageUrl: '../graphs/discreteBarChart.html'},
                       {id: '8', name: 'HistoricalBarChart', description: 'Historical Bar Chart', isVisible: false, pageUrl: '../graphs/historicalBarChart.html'}
                       ],
                       selectedOption: {id: '1', name: 'BulletChart', description: 'Bullet Chart', isVisible: false, pageUrl: '../graphs/bulletChart.html'} //This sets the default value of the select in the ui
                 }; 
    }
    
    $scope.showChart = function(){
    	console.log($scope.data.selectedOption.name);
    	alert("Select Page : {{$scope.optdata.selectedOption.pageUrl}}");
    	$scope.isVisible = $scope.data.selectedOption.isVisible = true;
    		$location.path('../graphs/{{$scope.optdata.selectedOption.pageUrl}}');
       };
}]);
        
  /*  angular.module('app')
        .controller('GraphController', GraphController)
        .directive("GraphDirective", [function () {
        return {
            restrict: "A",
            controller: "GraphController",
            scope: {
            	graphSelect: "&",
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

    GraphController.$inject = ["$scope"];
    function GraphController($scope) {
       
        init();

        function init() {
           console.log("Initialsed the Graph Controller");
        }
                    
        $scope.data = {
        	availableOptions: [
        	     {id: '1', name: 'BulletChart', description: 'Bullet Chart', isVisible: false, pageUrl: 'bulletChart.html'},
                 {id: '2', name: 'CumulativeLineChart', description: 'Cumulative Line Chart', isVisible: false, pageUrl: 'cumulativeLineChart.html'},
                 {id: '3', name: 'LineChart', description: 'Line Chart', isVisible: false, pageUrl: 'lineChart.html'},
                 {id: '4', name: 'LineChartZoom', description: 'Line Chart Zoom', isVisible: false, pageUrl: 'lineChartSVGResize.html'},
                 {id: '5', name: 'LineWithFocusChart', description: 'Line With Focus Chart', isVisible: false, pageUrl: 'lineWithFocusChart.html'},
                 {id: '6', name: 'LinePlusBarChart', description: 'Line Plus Bar Chart', isVisible: false, pageUrl: 'linePlusBarChart.html'},
                 {id: '7', name: 'DiscreteBarChart', description: 'Discrete Bar Chart', isVisible: false, pageUrl: 'discreteBarChart.html'},
                 {id: '8', name: 'HistoricalBarChart', description: 'Historical Bar Chart', isVisible: false, pageUrl: 'historicalBarChart.html'}
                 ],
                 selectedOption: {id: '1', name: 'BulletChart', description: 'Bullet Chart', isVisible: false, pageUrl: 'bulletChart.html'} //This sets the default value of the select in the ui
           }; 
                      
      $scope.showChart = function(){
    	  console.log($scope.data.selectedOption.name);
    	  $scope.isVisible = $scope.data.selectedOption.isVisible = true;
                    	  if($scope.selectedId == '3'){
                    		  $location.path('/histogram');
                    	  };
                      };

    };*/
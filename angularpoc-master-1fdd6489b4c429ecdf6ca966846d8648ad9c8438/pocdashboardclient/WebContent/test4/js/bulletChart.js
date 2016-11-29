'use strict';

angular.module('mainApp.controllers')

    .controller('bulletChartCtrl', function($scope){

        $scope.options = {
            chart: {
                type: 'bulletChart',
                duration: 500
            }
        };

        $scope.data = {
            "title": "Revenue",
            "subtitle": "US$, in thousands",
            "ranges": [250,325,400],
            "measures": [320],
            "markers": [350]
        };
    });
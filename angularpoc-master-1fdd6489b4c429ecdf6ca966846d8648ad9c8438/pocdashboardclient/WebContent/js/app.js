"use strict";

/* App Module */
var app = angular.module('app', [ 'ngRoute', 'ngCookies', 'app.angular.utilities', 'app.angular.debug',
                                  'json-tree', 'nvd3', 'gridster', 'ui.bootstrap']);
app.value('auth_token', '');
app.config(['$routeProvider', '$locationProvider', 'CHARTS', function($routeProvider, $locationProvider, CHARTS){
	$routeProvider.when('/home', {
		controller : 'HomeController',
		templateUrl : 'js/home/home.view.html',
		controllerAs : 'vm'
	})

	.when('/login', {
		controller : 'LoginController',
		templateUrl : 'js/login/login.view.html'
	})

	.when('/register', {
		controller : 'RegisterController',
		templateUrl : 'js/register/register.view.html',
		controllerAs : 'vm'
	})

	.when('/liveedit', {
		templateUrl : 'js/chart/liveedit.html',
		controller : 'mainCtrl'
	})

	.when('/dashboard', {
		templateUrl : 'js/chart/dashboard.html',
		controller : 'DashboardController'
	});
	
	angular.forEach(CHARTS, function(value, key) {
				console.log("Test url");
				$routeProvider.when(value.path, {
					templateUrl : 'js/chart/liveedit.html',
					controller : key + 'Ctrl'
				});
	});
	
	$routeProvider.otherwise({
		redirectTo : '/login'
	});

}]);
app.controller("appController", ["$scope", "$http", "languageFactory", "logger", '$rootScope', '$location', 
                                 function($scope, $http, languageFactory, logger, $rootScope, $location){

	init();
	
	$scope.logout = function($event){
		$event.preventDefault();
		$scope.loginStatus = false;
    	$location.path('/login');
    	return true;
	};
	
	function init(){
		var initJson = {};
		logger.info("Data Initialised");
		$scope.loginStatus = false;
		$rootScope.authToken = '';
			
		intialiseLanguageJSON();
		
		$scope.loginStatusCallback = function (loginStatus) {
            logger.info("[loginStatusCallback] user loggedin status");
            $scope.loginStatus = loginStatus;
            logger.info($scope.loginStatus);
        };
        
        $scope.logoutStatusCallback = function (logoutStatus) {
            logger.info("[logoutStatusCallback] user loggedout status");
            $scope.loginStatus = logoutStatus;
            $rootScope.authToken = '';
            logger.info($scope.loginStatus);
        };
        
        $scope.authToken = function (authToken) {
            logger.info("[authToken] Auth Token");
            $rootScope.authToken = authToken;
            console.log(authToken);
        };
        
	}
	
	function intialiseLanguageJSON(){
		$http.get('./app-language/en-US.json')
		.then(function(data) {
			logger.info(data);
			$scope.data = data.data;
			logger.info($scope.data);
		});
	}
	
}]);
app.run(['$rootScope', '$location', '$cookieStore', '$http', 
	function($rootScope, $location, $cookieStore, $http){
		$rootScope.globals = $cookieStore.get('globals') || {};
		if ($rootScope.globals.currentUser) {
			$http.defaults.headers.common['Authorization'] = 'Basic '
						+ $rootScope.globals.currentUser.authdata; 
		}
		
		/*$rootScope.$on("$routeChangeSuccess", function(userInfo) {
		    console.log(userInfo);
		  });

		  $rootScope.$on("$routeChangeError", function(event, current, previous, eventObj) {
		    if (eventObj.authenticated === false) {
		      $location.path("/login");
		    }
		  });*/
		  
		$rootScope.$on('$locationChangeStart', function(event, next, current) {
			// redirect to login page if not logged in and trying to access a
			// restricted page
			var restrictedPage = $.inArray($location.path(), [ '/', '/login', '/register' ]) === -1;
				var loggedIn = $rootScope.globals.currentUser;
				if (restrictedPage && !loggedIn) {
					$location.path('/login');
				}
		});
}])
.run();
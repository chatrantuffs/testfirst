'use strict';

angular.module('app').controller('LoginController', LoginController)
					   .directive("LoginDirective", [function () {
        return {
            restrict: "A",
            controller: "LoginController",
            scope: {
            	loginStatusCallback: "&",
            	authToken: "&"
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

LoginController.$inject = [ '$location', 'AuthenticationService',
		'FlashService', '$scope', 'auth_token', '$cookies'];
function LoginController($location, AuthenticationService, FlashService, $scope, auth_token) {

	$scope.initController = function () {
		// reset login status
		AuthenticationService.ClearCredentials();
	};

	$scope.login = function ($event) {
		$event.preventDefault();
		$scope.dataLoading = true;
		$scope.loginSucess = true;
		AuthenticationService.Login($scope.username, $scope.password,
				function(data, status, headers, config) {
					console.log(data);
					auth_token = headers()['auth_token'];
					console.log(auth_token);
					if (data.error.errorCode == 0 && data.error.errorDescription == '') {
						console.log("SUCCESSFUL LOGIN");
						AuthenticationService.SetCredentials($scope.username,
								$scope.password);
						$location.path('/home');
					} else {
						FlashService.Error(data.error.errorDescription);
						$scope.dataLoading = false;
						$scope.loginSucess = false;
					}
					$scope.loginStatusCallback($scope.loginSucess);
					$scope.authToken(auth_token);
				});
	};
	
}

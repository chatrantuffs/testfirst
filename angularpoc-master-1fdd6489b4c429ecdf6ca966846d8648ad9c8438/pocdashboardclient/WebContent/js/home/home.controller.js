'use strict';

    angular
        .module('app')
        .controller('HomeController', HomeController)
        .directive("LogoutDirective", [function () {
        return {
            restrict: "A",
            controller: "HomeController",
            scope: {
            	logoutStatusCallback: "&",
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

    HomeController.$inject = ['UserService', '$rootScope', "$scope", '$location'];
    function HomeController(UserService, $rootScope, $scope, $location) {
        var vm = this;

        vm.user = null;
        vm.allUsers = [];
        vm.deleteUser = deleteUser;

        init();

        /*$scope.logout = function(){
        	$scope.logoutStatusCallback(false);
        	$location.path('/login');
        	return true;
    	};*/
    	
        function init() {
            loadCurrentUser();
            loadAllUsers();
        }

        function loadCurrentUser() {
            UserService.GetByUsername($rootScope.globals.currentUser.username)
                .then(function (user) {
                    vm.user = user;
                });
        }

        function loadAllUsers() {
            UserService.GetAll()
                .then(function (users) {
                    vm.allUsers = users;
                });
        }

        function deleteUser(id) {
            UserService.Delete(id)
            .then(function () {
                loadAllUsers();
            });
        }
      
    };
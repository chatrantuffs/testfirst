(function() {
	'use strict';
	var app = angular.module('app');
	app.provider(
			'contextProvider',
			function() {
				var locationpath = location.protocol + "//"
						+ location.hostname;
				var port = "8080";
				var contextPath = "/pocdashboard";
				var serviceContextPath = "/v1/services";

				var applicationServiceUrl = locationpath + ":" + port + contextPath + serviceContextPath;
				
				this.getContextPathUrl = function(url) {
					return this.contextPath + serviceContextPath + url;
				};
				this.getServiceUrl = function(url) {
					return applicationServiceUrl + url;
				};

				this.$get = function() {
					return this;
				};

			});

	app.factory('contextFactory', ['$filter', 'contextProvider', function ($filter, contextProvider, $rootScope) {
        return {
            getContextPathUrl: function (url) {
            	return contextProvider.getContextPathUrl(url);
            },
            getServiceUrl: function (url){
            	return contextProvider.getServiceUrl(url);
            },
            getDataServiceRequest: function (url, data, token){
            	return {
                    method: 'GET',
                    url: contextProvider.getServiceUrl(url),
                    data: JSON.stringify(data),
                    headers: {
                        'auth_token': token
                    }
                };
            },
            getDataServiceRequest: function (url, token){
            	return {
                    method: 'GET',
                    url: contextProvider.getServiceUrl(url),
                    data: JSON.stringify(''),
                    headers: {
                        'auth_token': token
                    }
                };
            },
            postDataServiceRequest: function (url, data, token){
            	return {
                    method: 'POST',
                    url: contextProvider.getServiceUrl(url),
                    data: JSON.stringify(data),
                    headers: {
                        'auth_token': token
                    }
                };
            }
        };
}]);

})();
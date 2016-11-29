"use strict";

var app = angular.module("app.angular.services", [ "app.angular.debug", "app.angular.ajax.service", "app.angular.context"]);

app.factory('AppDataServices', [ "$http", "logger", "$filter", "AjaxService", "appContext", 'contextFactory', function($http, logger, $filter, AjaxService, appContext, contextFactory) {
	logger.info("inside Data Services");
	
	var returnedObject = {
			
	};
    return returnedObject;

}]).factory('AppWebServices', [ "$http", "logger", "$filter", "appContext", 'contextFactory', function($http, logger, $filter, appContext, contextFactory) {
	logger.info("inside Web Services");
	
	var returnedObject = {
            loadWithParameter: function (url, data, callback) {
                var ajaxAction = new AjaxService(appContext.getWebServiceUrl(url));
                ajaxAction.loadByPost(data, callback);
                return ajaxAction;
            },//Dummy load method for Agent or jsp call for testin with externalFnolSubmit. This will later be moved to the rstnWeb factory for Agent calls
            loadWithParameterAgent: function (url, data, callback) {
                var ajaxAction = new AjaxService(url);
                ajaxAction.loadByPost(data, callback);
                return ajaxAction;
            },
            testService: function (url) {
               logger.log("Call to app service method testService");
                return {"testData":"Ok tested"};
            }
	
		};
    return returnedObject;
}]);
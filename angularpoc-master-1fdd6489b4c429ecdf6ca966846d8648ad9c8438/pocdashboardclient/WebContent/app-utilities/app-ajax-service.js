'use strict';

angular.module("app.angular.ajax.service", [])
    .factory("AjaxService", ['$http', function ($http) {
        return function AjaxService(url) {
            this.url = url;
            this.data = null;
            this.error = null;
            this.result = null;

            this.reset = function () {
                this.data = null;
                this.error = null;
                this.result = null;
            };

            this.AjaxResult = function (data, status, headers, config) {
                if (angular.isDefined(data.faultString)) {
                    data = "Fault Code (" + data.faultCode + ") " + data.faultString;
                }
                return {
                    data: data,
                    status: status,
                    headers: headers,
                    config: config
                }
            };

            this.processSuccessAjaxResult = function (data, status, headers, config, callback) {
                this.result = this.AjaxResult(data, status, headers, config);
                this.data = (callback && callback.setData) ? callback.setData(data, this) : data;
                if (callback && callback.success && (angular.isUndefined(callback.stopProcessSuccess) || callback.stopProcessSuccess == false)) {
                    callback.success(this, data, status, headers, config);
                }
            };

            this.processErrorAjaxResult = function (data, status, headers, config, callback) {
                this.error = this.AjaxResult(data, status, headers, config);
                if (callback && callback.error) {
                    callback.error(this, data, status, headers, config);
                }
            };

            this.get = function (url, callback) {
                this.reset();
                if (url) {
                    var thisObj = this;
                    $http.get(url).success(function (data, status, headers, config) {
                        thisObj.processSuccessAjaxResult(data, status, headers, config, callback);
                    }).error(function (data, status, headers, config) {
                        thisObj.processErrorAjaxResult(data, status, headers, config, callback);
                    });
                } else {
                    alert("[AjaxService] URL to call is not set");
                }
            };

            this.post = function (url, formData, callback) {
                this.reset();
                if (url) {
                    var thisObj = this;
                    $http.post(url, formData).success(function (data, status, headers, config) {
                        thisObj.processSuccessAjaxResult(data, status, headers, config, callback);
                    }).error(function (data, status, headers, config) {
                        thisObj.processErrorAjaxResult(data, status, headers, config, callback);
                    });
                } else {
                    alert("[AjaxService] URL to call is not set");
                }
            };

            this.load = function (callback) {
                this.get(this.url, callback);
            };

            this.loadByPost = function (formData, callback) {
                this.post(this.url, formData, callback);
            };

            this.update = function (formData, callback) {
                this.post(this.url, formData, callback);
            };
        }
    }]);
"use strict";

var app = angular.module("app.angular.save.engine", [ "app.angular.debug", "app.angular.services" ]);

app.factory("SavingTools", [ "$http", "logger", "$filter", "$q", function($http, logger, $filter, $q) {
	logger.info("inside Data Services");
	var returnedObject = {
            getNewItemsFromArray: function (array) {
                var newItems = [];
                for (var i = 0; i < array.length; i++) {
                    /**
                     * A new item is not marked as deleted And doesn't have and AutoId (not yet already saved)
                     */
                    if (!array[i].isDeleted && !array[i].AutoId) {
                        newItems.push(array[i]);
                    }
                }
                return newItems;
            },
            getUpdatedItemsFromArray: function (array) {
                var updatedItems = [];
                for (var i = 0; i < array.length; i++) {
                    /**
                     * An updated item is not marked as deleted And is already saved, so it has an AutoId
                     */
                    if (!array[i].isDeleted && array[i].AutoId) {
                        updatedItems.push(array[i]);
                    }
                }
                return updatedItems;
            },
            getDeletedItemsFromArray: function (array) {
                var deletedItems = [];
                for (var i = 0; i < array.length; i++) {
                    /**
                     * A deleted item is marked is deleted And as a precaution we are not deleting any approved
                     * items So, the item should not have an AutoId
                     */
                    if (array[i].isDeleted && array[i].AutoId) {
                        deletedItems.push(array[i]);
                    }
                }
                return deletedItems;
            },
            /**
             * This will be used to determine records to be deleted ,those records which come from web service and
             * dont have Auto id example policy list.Deletion is based only on the isDeleted flag
             */
            getDeletedRecordsFromArray: function (array) {
                var deletedItems = [];
                for (var i = 0; i < array.length; i++) {
                    /**
                     * A deleted item is marked is deleted And as a precaution we are not deleting any approved
                     * items So, the item should not have an AutoId
                     */
                    if (array[i].isDeleted) {
                        deletedItems.push(array[i]);
                    }
                }
                return deletedItems;
            },
            createPromise: function (func, arrayParameter) {
                var d = $q.defer();
                func(arrayParameter, {
                    success: function (returnedData) {
                        d.resolve(returnedData);
                    },
                    error: function (o, failureReason, total, success, failure) {
                        d.reject({
                            "o": o,
                            "failureReason": failureReason,
                            "total": total,
                            "success": success,
                            "failure": failure
                        });
                    }
                });
                return d.promise;
            },
            scanUpdateResponse: function (returnedData) {
                var total = 0, failure = 0, success = 0, i = 0, technicalMessageIndex = 0;
                var failureReason = "";
                if (returnedData.result) {
                    if (returnedData.result.total) {
                        total = returnedData.result.total;
                    }
                    if (returnedData.result.failure) {
                        failure = returnedData.result.failure;
                    }
                    if (returnedData.result.success) {
                        success = returnedData.result.success;
                    }

                    if (total != success && failure && returnedData.data) {
                        for (i = 0; i < returnedData.data.length; i++) {
                            // We are going to get the first failure reason and show it to the user
                            failureReason = returnedData.data[i]["-FAILURE_REASON"];
                            if (failureReason) {
                                technicalMessageIndex = failureReason.indexOf(" at");
                                failureReason = failureReason.substring(0, technicalMessageIndex != -1 ? technicalMessageIndex : failureReason.length);
                                break;
                            }
                        }
                    }
                }

                return {
                    "failureReason": failureReason,
                    "total": total,
                    "success": success,
                    "failure": failure
                };
            },
            abstractSaveFunctionCall: function (func, array, memberName, callback) {
                if (array && !objectEngine.isEmpty(array)) {
                    func(array, memberName, {
                        success: function (returnedData) {
                            logger.log("[savingTools] [abstractSaveFunctionCall] result:");
                            logger.log(returnedData.data);
                            var updateResponseDetails = returnedObject.scanUpdateResponse(returnedData.data);
                            if ((updateResponseDetails.total != updateResponseDetails.success) && updateResponseDetails.failure) {
                                if (callback && callback.error) {
                                    callback.error(returnedData, updateResponseDetails.failureReason, updateResponseDetails.total,
                                        updateResponseDetails.success, updateResponseDetails.failure);
                                }
                            }
                            else {
                                if (callback && callback.success) {
                                    callback.success(returnedData);
                                }
                            }
                        },
                        error: function (o) {
                            if (callback && callback.error) {
                                callback.error(o);
                            }
                        }
                    });
                } else {
                    if (callback && callback.success) {
                        callback.success([]);
                    }
                }
            }
        };

        return returnedObject;

}]) 

.factory("DummySaver", ["SavingTools", "AppDataServices", "logger",
                              function (savingTools, AppDataServices, logger) {
    return {
        dummySaveVariables: function (variableArray, memberName, callback) {
            logger.log("[processSaver] [saveVariables] start");
            savingTools.abstractSaveFunctionCall(AppDataServices.saveProcessVariable, variableArray, memberName, callback);
            logger.log("[processSaver] [saveVariables] end");
        },
        dummySaveFields: function (fieldsArray, memberName, callback) {
            logger.log("[processSaver] [saveFields] start");
            savingTools.abstractSaveFunctionCall(AppDataServices.saveProcessFields, fieldsArray, memberName, callback);
            logger.log("[processSaver] [saveFields] end");
        }
    };
}])
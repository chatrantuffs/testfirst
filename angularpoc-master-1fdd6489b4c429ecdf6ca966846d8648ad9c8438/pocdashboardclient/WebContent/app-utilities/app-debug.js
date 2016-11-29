/**
 *
 * This is a debugging module with all the required debugging functions.
 * You can set the debugging on and off using the LOGGER_ACTIVE constant
 *
 */

angular.module("app.angular.debug", [])
    .constant("LOGGER_ACTIVE", true)
    .constant("LOGGER_DEBUG", true)
    .factory("logger" , ["LOGGER_ACTIVE", "LOGGER_DEBUG", function(LOGGER_ACTIVE, LOGGER_DEBUG) {
        return {
            log: function(message) {
                if (window.console && window.console.log && LOGGER_ACTIVE) {
                    window.console.log(message);
                }
            },
            error: function(error) {
                if (window.console && window.console.error && LOGGER_ACTIVE) {
                    window.console.error(error);
                }
            },
            info: function(message) {
                if (window.console && window.console.info && LOGGER_ACTIVE) {
                    window.console.info(message);
                }
            },
            warn: function(message) {
                if (window.console && window.console.info && LOGGER_ACTIVE) {
                    window.console.info(message);
                }
            },
            debug: function(message) {
                if (window.console && window.console.log && LOGGER_DEBUG) {
                    window.console.log(message);
                }
            }
        };
    }]);

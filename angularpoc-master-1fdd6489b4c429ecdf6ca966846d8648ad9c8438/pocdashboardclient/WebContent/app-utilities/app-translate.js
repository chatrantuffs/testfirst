angular.module("app.angular.translate", [ "pascalprecht.translate" ]).config(
		function($translateProvider) {
			$translateProvider.useStaticFilesLoader({
				prefix : '../app-language/',
				suffix : '.json'
			});

			if (navigator.language && navigator.language == "tr")
				$translateProvider.preferredLanguage("tr-tr");
			else
				$translateProvider.preferredLanguage("en-us");

			$translateProvider.useSanitizeValueStrategy('escaped');
		})

.factory("TranslateService", [ "$translate", function($translate) {
	return {
		translate : function(langKey) {
			$translate.use(langKey);
		}
	};
} ]);
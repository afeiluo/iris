angular.module("myApp", []).controller('MyController',
		function($scope, $parse) {
			$scope.$watch('expr', function(newVal, oldVal, scope) {
				if (newVal !== oldVal) {
					// 用该表达式设置parseFun
					var parseFun = $parse(newVal);
					// 获取经过解析后表达式的值
					$scope.parsedValue = parseFun(scope);
				}
			});
		});
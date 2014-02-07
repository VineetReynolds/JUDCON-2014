
angular.module('simpleeventbv').controller('NewEventController', function ($scope, $location, locationParser, EventResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.event = $scope.event || {};
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/Events/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        EventResource.save($scope.event, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/Events");
    };
});
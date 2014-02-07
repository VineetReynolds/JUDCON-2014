
angular.module('bidieagerevent').controller('NewSessionController', function ($scope, $location, locationParser, SessionResource , EventResource) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.session = $scope.session || {};
    
    $scope.eventList = EventResource.queryAll(function(items){
        $scope.eventSelectionList = $.map(items, function(item) {
            return ( {
                value : item.id,
                text : item.name
            });
        });
    });
    $scope.$watch("eventSelection", function(selection) {
        if ( typeof selection != 'undefined') {
            $scope.session.event = {};
            $scope.session.event.id = selection.value;
        }
    });
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/Sessions/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        SessionResource.save($scope.session, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/Sessions");
    };
});

angular.module('bidieagerevent').controller('NewEventController', function ($scope, $location, locationParser, EventResource , SessionResource) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.event = $scope.event || {};
    
    $scope.sessionsList = SessionResource.queryAll(function(items){
        $scope.sessionsSelectionList = $.map(items, function(item) {
            return ( {
                value : item.id,
                text : item.sessionName
            });
        });
    });
    $scope.$watch("sessionsSelection", function(selection) {
        if (typeof selection != 'undefined') {
            $scope.event.sessions = [];
            $.each(selection, function(idx,selectedItem) {
                var collectionItem = {};
                collectionItem.id = selectedItem.value;
                $scope.event.sessions.push(collectionItem);
            });
        }
    });
    

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
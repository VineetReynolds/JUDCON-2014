

angular.module('bidieagerevent').controller('EditEventController', function($scope, $routeParams, $location, EventResource , SessionResource) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.event = new EventResource(self.original);
            SessionResource.queryAll(function(items) {
                $scope.sessionsSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        id : item.id
                    };
                    var labelObject = {
                        value : item.id,
                        text : item.sessionName
                    };
                    if($scope.event.sessions){
                        $.each($scope.event.sessions, function(idx, element) {
                            if(item.id == element.id) {
                                $scope.sessionsSelection.push(labelObject);
                                $scope.event.sessions.push(wrappedObject);
                            }
                        });
                        self.original.sessions = $scope.event.sessions;
                    }
                    return labelObject;
                });
            });
        };
        var errorCallback = function() {
            $location.path("/Events");
        };
        EventResource.get({EventId:$routeParams.EventId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.event);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.event.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/Events");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/Events");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.event.$remove(successCallback, errorCallback);
    };
    
    $scope.sessionsSelection = $scope.sessionsSelection || [];
    $scope.$watch("sessionsSelection", function(selection) {
        if (typeof selection != 'undefined' && $scope.event) {
            $scope.event.sessions = [];
            $.each(selection, function(idx,selectedItem) {
                var collectionItem = {};
                collectionItem.id = selectedItem.value;
                $scope.event.sessions.push(collectionItem);
            });
        }
    });
    
    $scope.get();
});
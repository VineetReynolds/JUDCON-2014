

angular.module('bidieagerevent').controller('EditSessionController', function($scope, $routeParams, $location, SessionResource , EventResource) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.session = new SessionResource(self.original);
            EventResource.queryAll(function(items) {
                $scope.eventSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        id : item.id
                    };
                    var labelObject = {
                        value : item.id,
                        text : item.name
                    };
                    if($scope.session.event && item.id == $scope.session.event.id) {
                        $scope.eventSelection = labelObject;
                        $scope.session.event = wrappedObject;
                        self.original.event = $scope.session.event;
                    }
                    return labelObject;
                });
            });
        };
        var errorCallback = function() {
            $location.path("/Sessions");
        };
        SessionResource.get({SessionId:$routeParams.SessionId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.session);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.session.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/Sessions");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/Sessions");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.session.$remove(successCallback, errorCallback);
    };
    
    $scope.$watch("eventSelection", function(selection) {
        if (typeof selection != 'undefined') {
            $scope.session.event = {};
            $scope.session.event.id = selection.value;
        }
    });
    
    $scope.get();
});
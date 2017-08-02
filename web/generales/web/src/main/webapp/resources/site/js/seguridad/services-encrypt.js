angular.module('services', ['ngResource'], function ($provide) {
    $provide.factory('Data', function ($resource) {
        return $resource('/assets/config', {}, {
            query: {method: 'GET', isArray: false, crypt: true},
            queryNoCrypt: {method: 'GET'},
            save: {method: 'POST', params: {}, crypt: true},
            saveNoCrypt: {method: 'POST', params: {}}
        });
    });
});
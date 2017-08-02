/**
 * 
 */
/* global angular */
(function() {
  
  'use strict';

  var app = angular.module('formlyExample', ['formly', 'formlyBootstrap']);
  

  app.controller('MainCtrl', function MainCtrl(formlyVersion, $timeout) {
    var vm = this;
    // funcation assignment
    vm.onSubmit = onSubmit;

    // variable assignment
    vm.author = { // optionally fill in your info below :-)
      name: 'Kent C.',
      url: 'Dodds' // a link to your twitter/github/blog/whatever
    };
    vm.exampleTitle = 'model property'; // add this
    vm.env = {
      angularVersion: angular.version.full,
      formlyVersion: formlyVersion
    };

    vm.model = {
      name: {
        first: 'Gandalf',
        last: 'The Gray'
      },
      email: 'gandalf@example.com',
      username: 'yoiamgandalf'
    };
    
    vm.fields = [
                 {
                     type: "checkbox",
                     key: "checkThis",
                     templateOptions: {
                   label: "Check this box",
                   id:'mcheckbox1'
                     }
                 },                 
      {
        key: 'first',
        type: 'input',
        model: vm.model.name,
        templateOptions: {
          label: 'First Name'
        }
      },
      {
        key: 'last',
        type: 'input',
        model: vm.model.name,
        templateOptions: {
          label: 'Last Name'
        }
      },
      {
        key: 'email',
        type: 'input',
        templateOptions: {
          label: 'Email Address',
          type: 'email'
        }
      },
      {
        key: 'username',
        type: 'input',
        templateOptions: {
          label: 'Username'
        }
      },
      {
        key: 'other',
        type: 'input',
        templateOptions: {
          label: 'Other model'
        },
        expressionProperties: {
          'templateOptions.disabled': '!options.data.modelLoaded'
        },
        data: {
          modelLoaded: false
        }
      }
    ];
    
    $timeout(function() {
    }, 1000).then(function() {
      var field = vm.fields[4];
      console.log('hi');
      field.model = {
        other: 'some value'
      };
      field.data.modelLoaded = true;
      field.runExpressions(); // re-run the expression properties
    });
    
    vm.originalFields = angular.copy(vm.fields);

    // function definition
    function onSubmit() {
      alert(JSON.stringify(vm.model), null, 2);
    }
  });

})();
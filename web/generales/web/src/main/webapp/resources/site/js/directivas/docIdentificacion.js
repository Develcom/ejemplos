 
 App.directive('campoTexto', ['validaciones',function(validaciones){
    return {
      restrict: 'AE',
	  require: 'ngModel',
      //transclude: true,
      scope: {
        //ruta : '@',
		validacion : '@',
		//regExp : '@',
		modelo : '=ngModel'
		
      },
      controller: ['$scope', '$attrs', '$http', function($scope, $attrs, $http, $element, ngModel) {
        var vm = this;
		vm.validacion = validaciones[$scope.validacion];
        vm.onkeypress = function(event){
			if (event.key === "Backspace" || event.key === "Tab")
				return;
			var patron = new RegExp(vm.validacion.expReg);
			vm.errorTecla = false;
			if(!patron.test(event.key)){
				vm.errorTecla = true;
				event.preventDefault();
			}      	
        }
        vm.onBlur = function() {
			vm.errorTecla = false;
		}
        
      }],
      controllerAs : 'vm',
	  template : '<div class="col-sm-12" ng-class=""has-error": modelo == undefined , "has-success":  modelo != undefined">'
	  +'<div class="input-group col-sm-12" uib-popover="{{vm.validacion.mensaje}}" popover-trigger="none" popover-is-open="vm.errorTecla">'
	  +'<input type="text" ng-keydown = "vm.onkeypress($event)" ng-model = "modelo" ng-maxlength="vm.validacion.maximo" ng-minlength="vm.validacion.minimo" ng-blur = "vm.onBlur()" class="input-sm form-control" >'
	  +'<span class="input-group-addon" id="basic-addon1">'
	  +'<span class="glyphicon glyphicon-asterisk" ng-hide="modelo != undefined">'
	  +'</span>'
	  +'<span class="glyphicon glyphicon-ok" ng-show="modelo != undefined">'
	  +'</span>'
	  +'</span>'
	  +'</div>'
	  +'</div>'
    };
    
}]);
//	<div uib-popover="{{options.data.popover.mensaje}}" popover-title="{{options.data.popover.titulo}}" popover-trigger="none" popover-is-open="options.data.errorTecla"
//		class="input-group" ng-class="{'has-error': model[options.key] === undefined || model[options.key] === '','has-success':  model[options.key] !== undefined && model[options.key] !== ''}">


//var constantes = angular.module('constantes', [])
	


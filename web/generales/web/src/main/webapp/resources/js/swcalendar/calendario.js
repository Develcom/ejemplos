/**
 * 
 */
var app=angular.module('calendario',[]);

app.constant('mes31',[{name : '1', value:1},{name : '3', value:3},{name : '5', value:5},{name : '7', value:7},{name : '8', value:8},{name : '10', value:10},{name : '12', value:12}]);
app.constant('mes30'[{name:'4',value:4},{name:'6',value:6},{name:'4',value:9},{name:'11',value:11}]);
app.constant('mes',[{name : '1', value:1},{name : '2', value:2},{name : '3', value:3},{name : '4', value:4},{name : '5', value:5},{name : '6', value:6},{name : '7', value:7},{name : '8', value:8},{name : '9', value:9},{name : '10', value:10},{name : '11', value:11},{name : '12', value:12}])
app.constant('mes31Array',[1,3,5,7,8,10,12]);


app.controller('diaController',function($scope,mes31Array){
	
	$scope.fechaActual = new Date();
	$scope.dias = [];
	$scope.anoBisiesto = false;
	$scope.dia = "";
	$scope.mes = "";
	$scope.ano = "";
	
	$scope.cargarDias = function(){
		var dias = [];
		var limite = 31;
		
		if($scope.mes !== "" && mes31Array.indexOf($scope.mes) === -1 && $scope.mes !== 2)
			limite = 30
		else if($scope.mes == 2){
			limite = 28;
			if($scope.anoBisiesto)
				limite = 29;
		}
		for(i=1;i<=limite;i++){
			var mDia={};
			mDia.name=''+i;
			mDia.value=i;
			dias.push(mDia);
		}
		return dias;
	}
	
	$scope.esAnioBisiesto = function(anio){
		if(anio % 100 === 0 && anio % 100 === 0)
			return true;
		if(anio % 4 === 0 && anio % 100 !== 0)
			return true;
		return false;
	}
	
	$scope.dias = $scope.cargarDias();
	  $scope.options.templateOptions.options=$scope.dias;
	  $scope.$watch(function() {return $scope.options.data.mMes;},
		function(newValue, oldValue) {
			if (newValue !== oldValue) {
				$scope.mes = newValue;
				if(newValue === 2 && $scope.model.dia > 28){
					console.log('El valor del dia no es válido para el mes de Feberero');
					$scope.options.data.errorTecla = true;
					$scope.options.data.popover.mensaje = 'El valor del dia no es válido para el mes de Feberero';
				}
				if(mes31Array.indexOf(newValue) === -1 && $scope.model.dia > 30)
					console.log('El valor del dia no es valido pàra el mes seleccionado');
				$scope.options.templateOptions.options = $scope.cargarDias();
				$scope.evaluarMayor18();
				$scope.evaluarFechaFutura();
			}
		});
	  
	  $scope.$watch(function() {return $scope.options.data.mAno;},
		function(newValue, oldValue) {
			if (newValue !== oldValue) {
				$scope.ano = newValue;
				$scope.anoBisiesto = $scope.esAnioBisiesto(newValue);
				$scope.evaluarMayor18();
				$scope.evaluarFechaFutura();
			}
			$scope.options.templateOptions.options = $scope.cargarDias();
		});

	  $scope.$watch(function() {return $scope.options.data.mDia;},
		function(newValue, oldValue) {
			if (newValue !== oldValue) {
				$scope.dia = newValue;
				$scope.evaluarMayor18();
				$scope.evaluarFechaFutura();
			}
		});
	  
	  $scope.evaluarMayor18 = function(){
			var today = new Date();
			var dd = today.getDate();
			var mm = today.getMonth()+1;
			var yyyy = today.getFullYear();		
			var esMayor18 = false;
			var fecha = new Date($scope.model.mes + '/' + $scope.dia + '/' + $scope.ano);
			var fecha18 = new Date(mm + '/' + dd + '/' + (yyyy - 18));
			if((fecha > fecha18) && ($scope.model.rol == 'TA1' || $scope.model.rol == 'TA2' || $scope.model.rol == 'TDM1' || $scope.model.rol == 'TDM2' || $scope.model.rol == 'TDP1' || $scope.model.rol == 'TDP2')){
				$scope.options.data.errorTecla = true;
				$scope.options.data.popover.mensaje = 'El testigo debe ser mayor de 18 a\u00f1os';
				$scope.form.$invalid = true;
				return false;
			}
			$scope.options.data.errorTecla = false;
			return true;
	  }
	  
	  $scope.evaluarFechaFutura = function(){
			var fecha = new Date($scope.model.mes + '/' + $scope.dia + '/' + $scope.ano);
			if(fecha > $scope.fechaActual){
				$scope.options.data.errorTecla = true;
				$scope.options.data.popover.mensaje = 'La fecha no es v\u00e1lida';
				$scope.form.$invalid = true;
				return true;
			}
			$scope.options.data.errorTecla = false;
			return false;
	  }
});

	app.controller('mesController',function($scope){
		  $scope.meses = [];
		  for(i=1;i<=12;i++){
		  	var mMes={};
		  	mMes.name=''+i;
		  	mMes.value=i;
		     $scope.meses.push(mMes);
		  }
		  $scope.options.templateOptions.options = $scope.meses;
	});
	
	app.controller('anoController', function($scope){

		var today = new Date();
		var dd = today.getDate();
		var mm = today.getMonth()+1;
		var yyyy = today.getFullYear();		
		$scope.anos = [];
		  for(i=yyyy - 100; i<=yyyy; i++){
		  	var mAno={};
		  	mAno.name=''+i;
		  	mAno.value=i;
		     $scope.anos.push(mAno);
		  }
		  $scope.options.templateOptions.options=$scope.anos;
	});
function renderController($scope, response){
	var campos=response.data.fields;
	for(i=0;i<campos.length;i++){
		for(var key in campos[i]){
			console.log("campo "+i+" "+key);
			if(key=="controller"){
				myFunc=campos[i].controller;
				console.log("este es el objeto: "+myFunc);
				funcion = new Function("return " + myFunc)();
				response.data.fields[i].controller=funcion;
			}
		}
	}
	/*if($scope.userFields != undefined){
		for(var i = 0; i < response.data.fields.length; i++){
			$scope.userFields.push(response.data.fields[i]);
		}
	}
	
	if($scope.user != undefined){
		for(var i = 0; i < response.data.modelo; i++){
			$scope.user.push(response.data.modelo[i]);
		}
	}
	if($scope.userFields == undefined){
		$scope.userFields=response.data.fields;
	}
	if($scope.user != undefined){
		$scope.user=response.data.modelo;
	}*/
	$scope.userFields=response.data.fields;
	$scope.user=response.data.modelo;
}
function iniciarModulo($http, response, $state, $stateParams, $scope){
	renderController($scope, response);
}				

function nacimientoModulo($http, response, $state, $stateParams, $scope){
	renderController($scope, response);
	$scope.tag = response.data.tittle.titulo;
	
}
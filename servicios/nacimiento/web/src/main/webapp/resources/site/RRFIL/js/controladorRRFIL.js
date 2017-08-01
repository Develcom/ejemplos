App.controller('controladorRRFIL', function($scope,$http,$uibModal,$rootScope) {

$scope.tituloWizard = "Registrar recomposición de filiación";
 var vm = this;
 //Guarda en un array las vistas presentadas durante el proceso
 vm.vistas = [];
 //arreglo de datos que han sido presentados en pantalla
 vm.modelos = [];
 //representa los datos actualmente presentes en pantalla
 vm.modelo = {};
 vm.titulos = [];

 vm.activo = -1;
 //estado del proceso
 vm.paso = 0;
 
 vm.estado = "iniciarTramite";
 vm.actaPrimigenia = false;
 
	$scope.vPatron = {
	        AlfaNumerico: /^[A-Za-z0-9]*$/,
			Alfa: /^[a-zA-Z]*$/,
			FormatoNombres: /^((([a-zA-Z ñÑáéíóúÁÉÍÓÚäëïöüÄËÏÖÜ]))|([-'])\1?(?!\4))+$/,
			//FormatoNombres:  /^[a-zA-Z ñÑáéíóúÁÉÍÓÚäëïöüÄËÏÖÜ'-]*$/,
			//FormatoCementerio:  /^[a-zA-Z0-9 ñÑáéíóúÁÉÍÓÚäëïöüÄËÏÖÜ'-]*$/,
			FormatoCementerio: /^((([a-zA-Z0-9 ñÑáéíóúÁÉÍÓÚäëïöüÄËÏÖÜ]))|([-'])\1?(?!\4))+$/,
			AlfaText: 'Este campo solo permite letras, di&eacute;resis, ap&oacute;strofes, guiones y acentos',
			Pcementerio: 'Este campo solo permite letras, di&eacute;resis, ap&oacute;strofes, guiones, acentos y n&uacute;meros',
			FnumeroCertf: 'Este campo admite solo n&uacute;meros',
			TipoCed: /^[V|v|E|e]/,
			Cedula: /^[0-9]/,
			cedulaMaxLength: "9",
			cedulaMinLength: "6",
			nombreMaxlength: "50",
			nombreMinlength: "2",
			FormatoFolio: "Este campo permite solo n&uacute;meros, con un m&iacute;nimo de 5 caracteres y un m&aacute;ximo de 5 caracteres",
			FormatoActa: "Este campo permite solo n&uacute;meros, con un m&iacute;nimo de 20 caracteres y un m&aacute;ximo de 20 caracteres",
			FormatoTomo: "Este campo permite solo n&uacute;meros, con un m&iacute;nimo de 2 caracteres y un m&aacute;ximo de 2 caracteres",
			CedulaText: "Este campo permite solo n&uacute;meros, con un m&iacute;nimo de 6 caracteres y un m&aacute;ximo de 9 caracteres",
			NumeroDocumento: /^[0-9]{6,11}$/,
			excepciones : ['Backspace', 'Tab', ' ', 'ArrowLeft', 'ArrowRight', 'Delete', 'Caps Lock', 'Shift', 'Control'],
			capTuraEvento : function(event, patron){
				
			}
		
	      };
 
 
 /**
  *identifica el titulo de la etapa del proceso y el indice activo para 
  *resaltarlo con el color azul 
  **/
 vm.presentarTitulo = function(mTitulo){
  vm.activo = vm.titulos.indexOf(mTitulo);
  if(vm.activo == -1){
   vm.titulos.push(mTitulo);
   vm.activo = vm.titulos.length-1;
  }
 }
 /**
  * Maneja como se muestran las vistas.
  */
 vm.presentarVista = function($scope, vm, response){
  vm.vista = response.data.vista;
  vm.modelo = response.data.modelo;
  
  var arrayCedula = new Array();
  var dataCed =  null;
  
  dataCed = vm.modelo['MAD'].documentoIdentidad[0].numero;
  
  arrayCedula = dataCed.split('-');
 
  vm.modelo['MAD'].documentoIdentidad[0].numero = arrayCedula[1];
  
  vm.estado = "vistaDataHijos";
  
  vm.presentarTitulo(response.data.modelo.titulo);
 }
 
 vm.presentarVistaHijos = function($scope, vm, response){
	  vm.vista = response.data.vista;
	  vm.modelo = response.data.modelo;
	  
	  dataCed = vm.modelo['HIJ'].documentoIdentidad[0].numero;
	  
	  arrayCedula = dataCed.split('-');
	 
	  vm.modelo['HIJ'].documentoIdentidad[0].numero = arrayCedula[1];
	  
	  vm.estado = "vistaDatosActa";
	  
	  vm.presentarTitulo(response.data.modelo.titulo);
	 }
 
 vm.presentarVistaActa = function($scope, vm, response){
	  vm.vista = response.data.vista;
	  vm.modelo = response.data.modelo;
	  
	  vm.actaPrimigenia = true;
	  
	  vm.presentarTitulo(response.data.modelo.titulo);
	 }
 
 vm.actaPrimigeniaNoEncontrada = function(){
	 console.log("----MODAL----");
	 
	 vm.abrirModalActa();

	// vm.modalOkError($scope,$uibModal,vm.okModalActa,vm.errorFuncion,'Los datos del acta no fueron encontrados. El acta primigenia ha sido cambiada a estatus “Sin Efecto“. ¿Desea editar la información suministrada?',$rootScope.tituloWizard,'s');
	// vm.modalOkError('Los datos del acta no fueron encontrados. El acta primigenia ha sido cambiada a estatus “Sin Efecto“. ¿Desea editar la información suministrada?',$rootScope.tituloWizard,'s');
	 	 
	 }
 
 vm.finalizar = function(){
	 console.log("fin");
	 $rootScope.cancelar1();
 }
 
  
 vm.okModalActa = function(){
	 console.log("---ENTRO OKMODALACTA----");

 }

 vm.errorFuncion = function(){
	 console.log("----MODAL error----");
	 llamadaAjax($http, $scope, vm, '/web-nacimiento/RRFIL_validarRecaudo', vm.modelo, vm.presentarValidarRecaudo, "POST");	 

 }

	
	 vm.confirmarSalidaModulo = function(){
		 presentarModal($scope,$uibModal,vm.salirModulo,'¿Desea cancelar la solicitud?',$rootScope.tituloWizard,'s');
	 }
 
 vm.presentarValidarRecaudo = function($scope, vm, response){
	 
	 console.log("----bwvacsgghcsdfgh-----" +vm.actaPrimigenia);
	  vm.vista = response.data.vista;
	  vm.modelo = response.data.modelo;
	  
	  vm.actaPrimigenia = false;
	  	  
	  vm.presentarTitulo(response.data.modelo.titulo);


	  vm.guardarDatosActa();
	  
 }
 
 vm.guardarDatosActa = function(){
	 console.log("----ACTUALIZAR ESTADO---- "+vm.actaPrimigenia);
	 llamadaAjax2($http, $scope, vm, '/web-nacimiento/guardarDatosActaRRFIL', vm.modelo, vm.cambiarEstado, "POST");	 

 }
 vm.cambiarEstado = function(){
	 
	 
	 vm.estado = "actualizarEstado";	 

 }

 /**
  * Carga los datos iniciales del proceso
  */
 vm.estados = {
     iniciarTramite: {ruta: '/web-nacimiento/iniciarRRFIL', funcion: vm.presentarVista, metodo : "POST"},
     vistaDataHijos :{ruta : '/web-nacimiento/RRFIL_hijo', funcion : vm.presentarVistaHijos, metodo : "POST"},
     vistaDatosActa: {ruta : '/web-nacimiento/RRFIL_datos_acta', funcion : vm.presentarVistaActa, metodo : "POST"},
     //guardarDatosActa: {ruta : '/web-nacimiento/guardarDatosActaRRFIL', funcion : vm.actualizarEstado, metodo : "POST"}
     actualizarEstado: {ruta : '/web-nacimiento/actualizarEstadoRRFIL', funcion : vm.finalizar, metodo : "POST"}
    };

 //Datos a ser enviados al momento de cargar el modal  
 vm.modelo={id:$rootScope.objectSelected.numeroSolicitud, paso:0,
		 statu:$rootScope.objectSelected.codigoEstadoSolicitud };
 
 llamadaAjax($http, $scope, vm, vm.estados[vm.estado].ruta, vm.modelo, vm.estados[vm.estado].funcion, vm.estados[vm.estado].metodo);
 
 vm.siguiente = function(){
	 vm.paso++;

  //guarda en la pila la vista actual
  vm.vistas.push(vm.vista);
  vm.modelos.push(vm.modelo);
 
  if(vm.actaPrimigenia){
	 // console.log("-----CONSULTA ACTA----");
	  vm.consultaActa();
	  return;
  }
  
  llamadaAjax($http, $scope, vm, vm.estados[vm.estado].ruta, vm.modelo, vm.estados[vm.estado].funcion, vm.estados[vm.estado].metodo);
 
 }
 
 	vm.regresar = function(){
 		vm.paso--;
		console.log("-----fffffff----");
		vm.vista = vm.vistas[vm.vistas.length-1];
		vm.modelo = vm.modelos[vm.modelos.length-1];
		vm.modelos.splice(vm.modelos.length-1,1);
		vm.vistas.splice(vm.vistas.length-1,1);
		vm.disabled = vm.vistas.length == 0;
		vm.activo = vm.titulos.indexOf(vm.modelo.titulo);
		
		if(vm.activo < vm.titulos.length-1){
			vm.titulos.splice(vm.activo+1, vm.titulos.length-vm.activo-1);
		}
		vm.activo = vm.titulos.indexOf(vm.modelo.titulo);		
	}
 	
 	 //cierra wizard desde el boton x
	vm.cancel = function(){
		vm.confirmarSalidaModulo();
	}
//cierra wizard desde el boton cancelar
	vm.cancelar = function (){
		vm.confirmarSalidaModulo();
	}

 vm.consultaActa = function(){
	 
	 console.log("------ENTRO A CONSULTAR ACTA----");
	   $http({
	    method: 'GET',
	    url: '/web-nacimiento/existeActaRRFIL/'+vm.modelo.numeroActa
	   }).then(function successCallback(numeroActa) {
		   
		   console.log("----n_acta---- " + numeroActa.data);
		   
		   if(numeroActa.data == "0"){
			   vm.actaPrimigeniaNoEncontrada();
		   }else{
			   console.log("-----NO ES CERO----");
		   }
	    
	   
	   }, function errorCallback(error) {
	    console.log("error: "+error.data); 
	   });
	  }
 
 ////////////////////////Esto es para llamar al primer modal/////////////////////
 abrirModalActaDataVerificacion = function(){
		
		var modalInstance = $uibModal.open({
			backdrop: 'static',
			keyboard: false,
			animation: $scope.animationsEnabled,
			templateUrl: '/web-nacimiento/RRFIL/page/modal/datosActaConfirmacion.html',
			controller: 'validarActaCtrl',
			resolve: {
				
			}
		});

		modalInstance.result.then(function () {
			
			
			//eventoBotonFooter();
			//$parent.cont=true;
			//vm.cancelByEV14=true;

		}, function () {
			
		});
		
	}
 
	vm.abrirModalActa =function(){	
		var modalInstance = $uibModal.open({
			templateUrl: '/web-generales/pages/templates/imprimir/modalConfirmacion.html',
			controller: function($scope,$uibModalInstance){
				$scope.tipo = 'c';
				 $scope.mensaje='Los datos del acta no fueron encontrados. El acta primigenia ha sido cambiada a estatus “Sin Efecto“. ¿Desea editar la información suministrada? ';
				$scope.titulo = $scope.tituloWizard;;
			
				 $scope.cancel = function () {
				        $uibModalInstance.dismiss('cancel');
				        vm.errorFuncion();
				    };
				    $scope.ok = function () {
				    	    $uibModalInstance.close(true);
//				    	    llamadaAjax2($http, $scope, vm,
//									'/web-nacimiento/guardarActaPrim', vm.modelo, vm.f, "POST");
//				    	    vm.otraModalActa();
				    	   
				    };
				    $scope.cancelar = function () {
			    	    $uibModalInstance.close(true);
			    	    vm.errorFuncion();
			    	   // vm.volverActa();
				    }
			}
		});
		modalInstance.result.then(function () {
	
		}, function () {
			console.log("************VENGO DE CANCELAR");
			
		});
		
	}
 
 
 
 
 llamarDisabled = function() {
		/////////////MAD
	if(vm.modelo['MAD']!= null){
		   if(vm.modelo['MAD'].primerNombre!=null){
			   $scope.diasabled=true;
//			   console.log(vm.modelo['MAD'].primerNombre);
		   }else{
			   $scope.diasabled=false;
		      }
			if(vm.modelo['MAD'].segundoNombre!=null){
			   $scope.diasabled=true;
//			   console.log(vm.modelo['MAD'].segundoNombre);
		   }else{
			   $scope.diasabled=false;
		      }
			if(vm.modelo['MAD'].primerApellido!=null){
			   $scope.diasabled=true;
		   }else{
			   $scope.diasabled=false;
		      }
			if(vm.modelo['MAD'].segundoApellido!=null){
			   $scope.diasabled=true;
		   }else{
			   $scope.diasabled=false;
		      }
			if(vm.modelo['MAD'].documentoIdentidad[0].numero!=null){
//				console.log(vm.modelo['MAD'].documentoIdentidad[0].numero);
			   $scope.diasabled=true;
		   }else{
			   $scope.diasabled=false;
		      }
			if(vm.modelo['MAD'].documentoIdentidad[0].tipoDocumento.nombre!=null){
//			   console.log(vm.modelo['MAD'].documentoIdentidad[0].tipoDocumento.nombre);
				$scope.diasabled=true;
		   }else{
			   $scope.diasabled=false;
		      }
			}
     /////////////HIJ
	if(vm.modelo['HIJ']!= null){
		   if(vm.modelo['HIJ'].primerNombre!=null){
			   $scope.disabled=true;
//			   console.log(vm.modelo['HIJ'].primerNombre);
		   }else{
			   $scope.disabled=false;
		      }
			if(vm.modelo['HIJ'].segundoNombre!=null){
			   $scope.disabled=true;
//			   console.log(vm.modelo['HIJ'].segundoNombre);
		   }else{
			   $scope.disabled=false;
		      }
			if(vm.modelo['HIJ'].primerApellido!=null){
			   $scope.disabled=true;
		   }else{
			   $scope.disabled=false;
		      }
			if(vm.modelo['HIJ'].segundoApellido!=null){
			   $scope.disabled=true;
		   }else{
			   $scope.disabled=false;
		      }
			if(vm.modelo['HIJ'].documentoIdentidad[0].numero!=null){
//				console.log(vm.modelo['HIJ'].documentoIdentidad[0].numero);
			   $scope.disabled=true;
		   }else{
			   $scope.disabled=false;
		      }
			if(vm.modelo['HIJ'].documentoIdentidad[0].tipoDocumento.nombre!=null){
//			   console.log(vm.modelo['HIJ'].documentoIdentidad[0].tipoDocumento.nombre);
				$scope.disabled=true;
		   }else{
			   $scope.disabled=false;
		      }
			}

	}

 $scope.buscarPais = function(){
		
		$http({
			method: 'POST',
			url: '/web-generales/consultarPaisTodos',
			data:{}
		}).then(function successCallback(pais) {
			$scope.paises = pais.data;
			$scope.estados=$scope.paises.estados;
						
		}, function errorCallback(error) {
			console.log("error: "+error.data);
		});

	}
 $scope.buscarPais();
 
 $scope.buscarEstado = function(){
	 $scope.disabled = false;
		if (vm.modelo.pais.codigo == "VEN"){
		$http({
			method: 'POST',
			url: '/web-generales/consultarEstadoPorPais',
			data:{codigo:vm.modelo.pais.codigo}
			
		}).then(function successCallback(estado) {
			$scope.disabled = false
			$scope.estados = estado.data;
			$scope.municipios=$scope.estados.municipios;
		}, function errorCallback(error) {
			console.log("error: "+error.data);
		});
		}else{
			$scope.disabled = true;
			vm.clearFields();
		}
	}	
 $scope.buscarMunicipio = function(){
		
		$http({
			method: 'POST',
			url: '/web-generales/consultarMunicipioPorEstado',
			data:{codigo:vm.modelo.estado.codigo}
		}).then(function successCallback(municipio) {
			$scope.municipios = municipio.data;
			$scope.parroquias=$scope.municipios.parroquias;
		}, function errorCallback(error) {
			console.log("error: "+error.data);
		});

	}	
 $scope.buscarParroquia = function(){
		
		$http({
			method: 'POST',
			url: '/web-generales/consultarParroquiaPorMunicipio',
			data:{codigo:vm.modelo.municipio.codigo}
		}).then(function successCallback(parroquia) {
			$scope.parroquias = parroquia.data;
			//$scope.parroquias=$scope.municipios.parroquias;
		}, function errorCallback(error) {
			console.log("error: "+error.data);
		});

	}
 $scope.buscarOficinas = function(){
		
		$http({
			method: 'POST',
			url: '/web-generales/consultarOficinasTodos',
			data:{}
		}).then(function successCallback(oficina) {
			$scope.oficinas = oficina.data;
			//$scope.estados=$scope.paises.estados;
		}, function errorCallback(error) {
			console.log("error: "+error.data);
		});

	}
 $scope.buscarOficinas();

 $scope.MostrarTipoDoc=function(variable){
		if (variable = 'Si')
			  $scope.tipoDoc=true;
		  else
			  $scope.tipoDoc=false;
		 }
 vm.cancelar = function(){
		$rootScope.cancelar1();
	}
 
 vm.clearFields = function () {
     vm.modelo.estado = null;
     vm.modelo.municipio = null;
     vm.modelo.parroquia = null;
     return;
 }
 
 
 //------- METODOS EXEPCION Y VALIDACION DE CAMPOS-------
 
	$scope.onKeyDownAlfa = function(event, validacion, id){
		   var excepciones = $scope.vPatron.excepciones;
		   for(var i = 0; i < excepciones.length; i++){
		    if (event.key === excepciones[i])
		    return;
		   }
		   var patron = $scope.vPatron.FormatoNombres;
		   if (!patron.test(event.key)) {
		    event.preventDefault();
		    $scope.errorTecla[id] = true;
		    return;
		   }
		   $scope.errorTecla[id]= false;
	}
	
	$scope.onKeyDownCedula = function(event, validacion, id){
		   var excepciones = $scope.vPatron.excepciones;
		   for(var i = 0; i < excepciones.length; i++){
		    if (event.key === excepciones[i])
		    return;
		   }
		   var patron = $scope.vPatron.Cedula;
		   if (!patron.test(event.key)) {
		    event.preventDefault();
		    $scope.errorTecla[id] = true;
		    return;
		   }
		   $scope.errorTecla[id]= false;
	}
	
 
 
 
 
});
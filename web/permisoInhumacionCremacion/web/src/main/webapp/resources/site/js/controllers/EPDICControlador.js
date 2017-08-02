
App.controller('controladorEPDIC', function($scope,$http,$uibModal,$rootScope) {

	
	var vm = this;
	vm.paso = 0;
	//$scope.validaciones = validaciones;
	vm.errorTecla = {};
	 //Guarda en un array las vistas presentadas durante el proceso
	 vm.vistas = [];
	 //arreglo de datos que han sido presentados en pantalla
	 vm.modelos = [];
	 //representa los datos actualmente presentes en pantalla
	 vm.modelo = {};
	 vm.titulos = [];
	 vm.activo = -1;
	 vm.confirmarDatosForm = false;
	 vm.modelo.numeroCertificado = "false";
	 //$scope.alfa= /^[a-zA-Z]*$/;^[a-zA-Z ñÑáéíóúÁÉÍÓÚ]*$

 
	 //validaciones de patrones
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
				CedulaText: "Este campo permite solo n&uacute;meros, con un m&iacute;nimo de 6 caracteres y un m&aacute;ximo de 9 caracteres <br/> Ejemplo: 12345678",
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
	  vm.modelo.titulo = (response.data.modelo.titulo);
	  vm.presentarTitulo(vm.modelo.titulo);
	  

						if (vm.paso >= 2) {
							vm.confirmarDatosForm = true;
						}

					}

					vm.presentarVista2 = function($scope, vm, response) {
						vm.vista = response.data.vista;
						vm.modelo = response.data.modelo;

						switch (vm.modelo.PermisoInhCre.tipoPermiso) {
						case "Permiso de inhumacion":
							$scope.permiso = "Permiso de inhumación";
							break;
						case "Permiso de cremacion":
							$scope.permiso = "Permiso de cremación";
							break;
						case "Permiso de inhumacion y cremacion":
							$scope.permiso = "Permiso de inhumación y cremación";
							break;
						}

						vm.presentarTitulo($scope.permiso);

						if (vm.paso >= 2) {
							vm.confirmarDatosForm = true;
						}

		 }
	 
	 vm.modalNumeroCertificado = function(){
		 presentarModal($scope,$uibModal,vm.okNumCretificado,'El certificado médico EV-14 N'+vm.modelo.numeroCertificadoDef+', ya se encuentra registrado <br>¿Es correcto?',$rootScope.tituloWizard,'s');
	 }
	 vm.okNumCretificado = function(){
		 llamadaAjax($http, $scope, vm, '/web-permisoInhumacionCremacion/actualizarEstado', vm.modelo, vm.guardarDatos, "POST");
	 }
	 vm.modalDatosConforme = function(){
		 presentarModal($scope,$uibModal,vm.okDatosConforme,'Por favor confirmar los datos y al finalizar presione el botón Continuar',$rootScope.tituloWizard,'a');
	 }
	 vm.okDatosConforme = function(){
		 console.log("okDatosConforme");
		 vm.datosConfirmados = true;
	 }
	 vm.modalActualizarEstado = function(){
		 presentarModal($scope,$uibModal,vm.guardarDatos,'¿Los datos ingresados son correctos?',$rootScope.tituloWizard,'s');
	 }
	 vm.okActualizarEstadoSolicitud = function(){
		 llamadaAjax($http, $scope, vm, '/web-permisoInhumacionCremacion/actualizarEstado', vm.modelo, vm.finalizar, "POST");
	 }	 
	 vm.guardarDatos = function(){
		 console.log("guardar");
		 llamadaAjax($http, $scope, vm, '/web-permisoInhumacionCremacion/guardarDatosForm', vm.modelo, vm.okActualizarEstadoSolicitud, "POST");	 
		 
	 }
	 vm.finalizar = function(){
		 console.log("fin");
		 $rootScope.cancelar1();
	 }
	 vm.confirmarSalidaModulo = function(){
		 presentarModal($scope,$uibModal,vm.salirModulo,'¿Desea cancelar la solicitud?',$rootScope.tituloWizard,'s');
	 }
	 vm.salirModulo = function(){
		 $rootScope.cancelar1();
	 }
//	 vm.resolve = function (){
//			return numCertificado;
//	 }
	 
	
	 /**
	  * Carga los datos iniciales del proceso
	  */
	 vm.rutas = [
	       {ruta : '/web-permisoInhumacionCremacion/iniciarTramiteEPDIC', funcion : vm.presentarVista, metodo: 'POST'},
	       {ruta : '/web-permisoInhumacionCremacion/tipoPermiso', funcion : vm.presentarVista, metodo: 'POST'},
	       {ruta : '/web-permisoInhumacionCremacion/permisoInhumacionCremacion', funcion : vm.presentarVista2, metodo: 'POST'},
	   ];

	 //Datos a ser enviados al momento de cargar el modal  
	 vm.modelo={id:$rootScope.objectSelected.numeroSolicitud, paso:0, 
			 statu:$rootScope.objectSelected.codigoEstadoSolicitud, 
			 tramite : $rootScope.objectSelected.tramite.codigo,
			 numeroCertificado : vm.modelo.numeroCertificado,
			 
			 PermisoInhCre : {segundoNombreAutoriza : '',
			 numSolicitud: $rootScope.objectSelected.numeroSolicitud,
			 segundoApellidoAutoriza : '',
			 pais : 'VENEZUELA'}};
	 llamadaAjax($http, $scope, vm, vm.rutas[vm.paso].ruta, vm.modelo, vm.rutas[vm.paso].funcion, vm.rutas[vm.paso].metodo);
	 
	
	 vm.siguiente = function(){
	  //guarda en la pila la vista actual
	  vm.vistas.push(vm.vista);
	  vm.modelos.push(vm.modelo);
	  	  
	  if(vm.confirmarDatosForm){
		  console.log("confirmarDatosForm");
		  vm.modalDatosConforme();
		  vm.confirmarDatosForm = false;
	  return;
	  }else if(vm.datosConfirmados){
		  console.log("datosConfirmados");
		  vm.modalActualizarEstado();
		  vm.datosConfirmados = false;
		  vm.confirmarDatosForm = true;
		  return;
	  }
	  vm.paso++;
	  llamadaAjax($http, $scope, vm, vm.rutas[vm.paso].ruta, vm.modelo, vm.rutas[vm.paso].funcion, vm.rutas[vm.paso].metodo);
	  
	 }
	 
     vm.regresar = function() {
         vm.confirmarDatosForm = false;
         vm.datosConfirmados = false;
         vm.vista = vm.vistas[vm.vistas.length - 1];
         vm.modelo = vm.modelos[vm.modelos.length - 1];
         vm.modelos.splice(vm.modelos.length - 1, 1);
         vm.vistas.splice(vm.vistas.length - 1, 1);
         vm.disabled = vm.vistas.length == 0;
         vm.disabled = vm.vistas.length == 0;
         vm.paso--;
         
         vm.activo = vm.titulos.indexOf(vm.modelo.titulo);
         if (vm.activo < vm.titulos.length) {
             vm.titulos.splice(vm.activo + 1, vm.titulos.length
                 - vm.activo - 1);
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
	

		

		$scope.buscarEstadoCombo = function (){
			
			var mPais = "VEN";

	
			$http({
				method: 'POST',
				url: '/web-generales/consultarEstadoPorPais/',
				data:{codigo : mPais}
			}).then(function successCallback(estado) {
				//$scope.disabled = false
				console.log(estado.data);
				$scope.estados = estado.data;
				$scope.municipios=$scope.estados.municipios;
			}, function errorCallback(error) {
				console.log("error: "+error.data);	
			});

					}

					$scope.buscarEstadoCombo();

					vm.clearFields = function() {
						vm.modelo.PermisoInhCre.estado = null;
						vm.modelo.PermisoInhCre.municipio = null;
						vm.modelo.PermisoInhCre.parroquia = null;
						return;
					}

					$scope.buscarMunicipioCombo = function() {
						var mEstado;
						for (i = 0; i < $scope.estados.length; i++) {
							if ($scope.estados[i].nombre === vm.modelo.PermisoInhCre.estado) {
								mEstado = $scope.estados[i].codigo;

							}

			$http({
				method: 'GET',
				url: '/web-permisoInhumacionCremacion/consultarMunicipios/'+mEstado

			}).then(function successCallback(municipio) {
				console.log(municipio.data);
				$scope.municipios = municipio.data;
				$scope.parroquias=$scope.municipios.parroquias;
			}, function errorCallback(error) {
				console.log("error: "+error.data);	
			});
		}
		}
		
		$scope.buscarParroquiaCombo = function(){

			var mParroquia;
			for (i= 0; i < $scope.municipios.length; i++ ){
				if($scope.municipios[i].nombre === vm.modelo.PermisoInhCre.municipio){
					mParroquia = $scope.municipios[i].codigo;
					
				}	
				
			$http({
				method: 'GET',
				url: '/web-permisoInhumacionCremacion/consultarParroquias/'+mParroquia
			}).then(function successCallback(parroquia) {
				console.log(parroquia.data);
				$scope.parroquias = parroquia.data;
				
			}, function errorCallback(error) {
				console.log("error: "+error.data);
			});
			}
		}
		
		
		$scope.validarCert= function(){
			$http({
				method: 'GET',
				url: '/web-permisoInhumacionCremacion/validarCertificadoEV/'+vm.modelo.PermisoInhCre.numeroCertificadoDef
			}).then(function successCallback(numeroCertificadoDef) {
				
				console.log(numeroCertificadoDef.data);
				$scope.certificado = numeroCertificadoDef.data;
				
				
				
				if($scope.certificado == true){
					console.log('niooo');
					vm.modelo.numeroCertificado = "true";
					vm.modalNumeroCertificado();  
				}
			}, function errorCallback(error) {
				console.log("error: "+error.data);
			});
		}
		
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
		
		$scope.onKeyDownCementerio = function(event, validacion, id){
			   var excepciones = $scope.vPatron.excepciones;
			   for(var i = 0; i < excepciones.length; i++){
			    if (event.key === excepciones[i])
			    return;
			   }
			   var patron = $scope.vPatron.FormatoCementerio;
			   if (!patron.test(event.key)) {
			    event.preventDefault();
			    $scope.errorTecla[id] = true;
			    return;
			   }
			   $scope.errorTecla[id]= false;
		}
		
		$scope.onKeyDownCertDef = function(event, validacion, id){
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
		
		  $scope.onBlur = function(){
			   $scope.errorTecla[id] = false;
			  }

})


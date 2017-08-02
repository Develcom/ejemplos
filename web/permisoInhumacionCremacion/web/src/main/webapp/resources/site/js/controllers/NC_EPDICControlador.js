
App.controller('controladorEPDIC_NC', function($scope,$http,$uibModal,$rootScope,validaciones) {
	
	var vm = this;
	var mun = this;
	$scope.validaciones = validaciones;
	vm.errorTecla = {};
	vm.paso = 0;
	 //Guarda en un array las vistas presentadas durante el proceso
	 vm.vistas = [];
	 //arreglo de datos que han sido presentados en pantalla
	 vm.modelos = [];
	 //representa los datos actualmente presentes en pantalla
	 vm.modelo = {};
	 vm.titulos = [];
	 vm.activo = -1;
	 $scope.alfa= /^[a-zA-Z ñÑáéíóúÁÉÍÓÚäëïöüÄËÏÖÜ'-]*$/
	 $scope.disabled = false;
	 vm.modelo.numeroCertificado = "false";
	 
		$scope.vPatron = {
		        AlfaNumerico: /^[A-Za-z0-9]*$/,
				Alfa: /^[a-zA-Z]*$/,
				FormatoNombres: /^((([a-zA-Z ñÑáéíóúÁÉÍÓÚäëïöüÄËÏÖÜ]))|([-'])\1?(?!\4))+$/,
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
	
	 //estado del proceso
	// vm.estado = 'iniciarTramiteEDPIC';
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
        vm.presentarTitulo(response.data.modelo.titulo);
        console.log("vm.modelo.titulo" + vm.modelo.titulo);

        if (vm.modelo.PermisoInhCre.pais == "VENEZUELA" && vm.modelo.PermisoInhCre.estado != null &&
            vm.modelo.PermisoInhCre.municipio != null) {

            $scope.funcionSeteoDataCombo();
        } else {
            
            //vm.onKeydown();
        }

//		vm.onKeydown = function(event) {
//			if (event.key === "Backspace" || event.key === "Tab")
//				return;
//			var patron = new RegExp($scope.alfa);
//
//			if (!patron.test(event.key)) {
//				event.preventDefault();
//				//$scope.options.data.errorTecla = true;
//				return;
//			}

//		}

        if (vm.paso >= 1) {
            vm.confirmarDatosForm = true;
        }

    }

    vm.presentarVista2 = function ($scope, vm, response) {

        vm.vista = response.data.vista;
        vm.modelo = response.data.modelo;
//		  vm.modelo.titulo = (response.data.modelo.titulo);


       

        if (vm.modelo.PermisoInhCre.pais == "VENEZUELA" && vm.modelo.PermisoInhCre.estado != null &&
            vm.modelo.PermisoInhCre.municipio != null) {

            $scope.funcionSeteoDataCombo();
        } else {
            
            //vm.onKeydown();
        }

//			vm.onKeydown = function(event) {
//				if (event.key === "Backspace" || event.key === "Tab")
//					return;
//				var patron = new RegExp($scope.alfa);
        //
//				if (!patron.test(event.key)) {
//					event.preventDefault();
//					//$scope.options.data.errorTecla = true;
//					return;
//				}

//			}

        if (vm.paso >= 1) {
            vm.confirmarDatosForm = true;
        }
        switch (vm.modelo.PermisoInhCre.tipoPermiso) {
            case "Permiso de inhumacion":
                $scope.permiso = "Permiso de inhumación";
                break;
            case "Permiso de cremacion":
                $scope.permiso = "Permiso de cremación";
                break;
            case "Permiso de inhumacion y cremacion":
                $scope.permiso = "Permiso de cremación";
                break;
        }
       
        vm.presentarTitulo($scope.permiso);
    }

    vm.modalDatosConforme = function () {
        presentarModal($scope, $uibModal, vm.okDatosConforme, 'Por favor confirmar los datos y al finalizar presione el botón Continuar', $rootScope.tituloWizard, 'a');
    }
    vm.okDatosConforme = function () {
       
        vm.datosConfirmados = true;
    }
    vm.modalActualizarEstado = function () {
        presentarModal($scope, $uibModal, vm.guardarDatos, '¿Los datos ingresados son correctos?', $rootScope.tituloWizard, 's');
    }
    vm.okActualizarEstadoSolicitud = function () {
        llamadaAjax($http, $scope, vm, '/web-permisoInhumacionCremacion/actualizarEstado', vm.modelo, vm.finalizar, "POST");
    }
    vm.modalNumeroCertificado = function () {
        presentarModal($scope, $uibModal, vm.okNumCretificado, 'El certificado medico EV-14 N' + vm.modelo.numeroCertificadoDef + ', ya se encuentra registrado <br>¿Es correcto?', $rootScope.tituloWizard, 's');
    }
    vm.okNumCretificado = function () {
       
        vm.okActualizarEstadoSolicitud();
    }
    vm.guardarDatos = function () {
        console.log("guardar");
        llamadaAjax($http, $scope, vm, '/web-permisoInhumacionCremacion/guardarDatosForm', vm.modelo, vm.okActualizarEstadoSolicitud, "POST");
    }

    vm.finalizar = function () {
        console.log("fin");
        $rootScope.cancelar1();
    }
    vm.confirmarSalidaModulo = function () {
        presentarModal($scope, $uibModal, vm.salirModulo, '¿Desea cancelar la solicitud?', $rootScope.tituloWizard, 's');
    }
    vm.salirModulo = function () {
        $rootScope.cancelar1();
    }

    /**
     * Carga los datos iniciales del proceso
     */
    vm.rutas = [
        {ruta:'/web-permisoInhumacionCremacion/iniciarTramiteEPDIC', funcion:vm.presentarVista, metodo:'POST'},
        {ruta:'/web-permisoInhumacionCremacion/permisoInhumacionCremacion', funcion:vm.presentarVista2, metodo:'POST'},
    ];

    //Datos a ser enviados al momento de cargar el modal
    vm.modelo = {id:$rootScope.objectSelected.numeroSolicitud, paso:0,
        statu:$rootScope.objectSelected.codigoEstadoSolicitud,
        tramite:$rootScope.objectSelected.tramite.codigo,
        numeroCertificado:vm.modelo.numeroCertificado,
        PermisoInhCre:{segundoNombreAutoriza:'',
            numSolicitud:$rootScope.objectSelected.numeroSolicitud,
            segundoApellidoAutoriza:''}};
    llamadaAjax($http, $scope, vm, vm.rutas[vm.paso].ruta, vm.modelo, vm.rutas[vm.paso].funcion, vm.rutas[vm.paso].metodo);


    vm.siguiente = function () {
       // $scope.buscarPais();
        //guarda en la pila la vista actual
        vm.vistas.push(vm.vista);
        vm.modelos.push(vm.modelo);

        if (vm.confirmarDatosForm) {
            console.log("confirmarDatosForm");
            vm.modalDatosConforme();
            vm.confirmarDatosForm = false;
            return;
        } else if (vm.datosConfirmados) {
            console.log("datosConfirmados");
            vm.modalActualizarEstado();
            //vm.modalActualizarEstado();
            vm.datosConfirmados = false;
            vm.confirmarDatosForm = true;
            return;
        }
        vm.paso++;
        llamadaAjax($http, $scope, vm, vm.rutas[vm.paso].ruta, vm.modelo, vm.rutas[vm.paso].funcion, vm.rutas[vm.paso].metodo);

    }

    //boton regresar
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
    vm.cancel = function () {
        vm.confirmarSalidaModulo();
    }
    //cierra wizard desde el boton cancelar
    vm.cancelar = function () {
        vm.confirmarSalidaModulo();
    }

//        ---------funciones internas ------------      //
//        ---------llamados servicios ------------      //

    /*$scope.buscarPais = function () {
        $http({
            method:'GET',
            url:'/web-permisoInhumacionCremacion/consultarPais'
        }).then(function successCallback(pais) {
                //console.log("paissss codigo"+pais.codigo);
                $scope.paises = pais.data;
                $scope.estados = $scope.paises.estados;

            }, function errorCallback(error) {
                console.log("error: " + error.data);
            });
    }     */

//		$scope.buscarPais();


    $scope.buscarEstado = function (parametro) {
        var mPais = null;
        if (parametro == "VEN") {

            mPais = parametro;

           

            $scope.nombreEstado = vm.modelo.PermisoInhCre.estado;
            $scope.disabled = false;
            $http({
                method:'POST',
                url:'/web-generales/consultarEstadoPorPais/',
                data:{codigo:mPais}
            }).then(function successCallback(estado) {

                    
                    //$scope.disabled = false
                    console.log(estado.data);
                    $scope.estados = estado.data;
                    $scope.municipios = $scope.estados.municipios;
                    
                    for (var i = 0; i < $scope.estados.length; i++) {
                        console.log(i + "   **************en el for***************** estados[i].nombre------->  " + $scope.estados[i].nombre + "$scope.nombreEstado---->" + $scope.nombreEstado);
                        if ($scope.estados[i].nombre == $scope.nombreEstado) {
                            console.log("interacion num " + i + " estado--> " + $scope.estados[i].nombre);
                            $scope.eEstado = $scope.estados[i].nombre;
                            console.log("----state---"+$scope.eEstado+"---");
                            $scope.cEstado = $scope.estados[i].codigo;
                            vm.modelo.PermisoInhCre.estado = $scope.eEstado;
                           
                            break;
                        }
                    }

                }, function errorCallback(error) {
                    console.log("error: " + error.data);
                });

        } else {
            vm.clearFields();
            $scope.disabled = true;

        }

    }

    vm.clearFields = function () {
        vm.modelo.estado = null;
        vm.modelo.municipio = null;
        vm.modelo.parroquia = null;
        return;
    }

    $scope.buscarMunicipio = function (parametro) {
        var mEstado = null;
        if (parametro == "parametro") {

            for (var i = 0; i < $scope.estados.length; i++) {
                if ($scope.estados[i].nombre == vm.modelo.PermisoInhCre.estado) {
                    mEstado = $scope.estados[i].codigo;
                    break;
                }
            }
        } else if (parametro != "parametro") {
            mEstado = parametro;
            $scope.nombreMunicipio = vm.modelo.PermisoInhCre.municipio;
            $http({
                method:'GET',
                url:'/web-permisoInhumacionCremacion/consultarMunicipios/' + mEstado

            }).then(function successCallback(municipio) {
                    console.log(municipio.data);
                    $scope.municipios = municipio.data;
                    $scope.parroquias = $scope.municipios.parroquias;

                    for (var i = 0; i < $scope.municipios.length; i++) {
                        console.log(i + "data municipio ----- " + $scope.municipios[i].nombre);
                        if ($scope.municipios[i].nombre == $scope.nombreMunicipio) {

                            mun.mMunicipio = $scope.municipios[i].nombre;
                            mun.cMunicipio = $scope.municipios[i].codigo;
                            //vm.modelo.PermisoInhCre.municipio = $scope.mMunicipio;
                            vm.modelo.PermisoInhCre.municipio = mun.mMunicipio;
                            //console.log("---municipioooo seteado pal combo---- " + mun.cMunicipio);
                            //console.log("---codigo mun seteado pal combo---- " + cMunicipio);
                            break;
                        }

                    }
                    $scope.buscarParroquia(mun.cMunicipio);

                }, function errorCallback(error) {
                    
                });

        }
    }

    $scope.buscarParroquia = function (parametro) {
        var mParroquia = null;
        if (parametro == "parametro") {
            //	var mParroquia;
            for (var i = 0; i < $scope.municipios.length; i++) {
                if ($scope.municipios[i].nombre == vm.modelo.PermisoInhCre.municipio) {
                    mParroquia = $scope.municipios[i].codigo;
                    break;
                }
            }
        } else if (parametro != "parametro") {
            mParroquia = parametro;
            $scope.nombreParroquia = vm.modelo.PermisoInhCre.parroquia;
            $http({
                method:'GET',
                url:'/web-permisoInhumacionCremacion/consultarParroquias/' + mParroquia

            }).then(function successCallback(parroquia) {
                    console.log(parroquia.data);
                    $scope.parroquias = parroquia.data;

                    for (var i = 0; i < $scope.parroquias.length; i++) {
                        console.log(i + "data parroquia ----- " + $scope.parroquias[i].nombre);
                        if ($scope.parroquias[i].nombre == $scope.nombreParroquia) {

                            $scope.mParroquia = $scope.parroquias[i].nombre;
                            $scope.cParroquia = $scope.parroquias[i].codigo;
                            //vm.modelo.PermisoInhCre.municipio = $scope.mMunicipio;
                            vm.modelo.PermisoInhCre.parroquia = $scope.mParroquia;
                            console.log("---parroquia seteado pal combo---- " + vm.modelo.PermisoInhCre.parroquia);
                            //console.log("---codigo mun seteado pal combo---- " + cMunicipio);
                            break;
                        }

                    }


                }, function errorCallback(error) {
                    
                });

        }
    }


    $scope.funcionSeteoDataCombo = function () {
       

        //$scope.buscarPais();
        $scope.buscarEstado("VEN");
       
        $scope.buscarMunicipio($scope.cEstado);
        //console.log("municipio.codigo----- " + mun.cMunicipio);

    }

    $scope.validarCert = function () {
        $http({
            method:'GET',
            url:'/web-permisoInhumacionCremacion/validarCertificadoEV/' + vm.modelo.PermisoInhCre.numeroCertificadoDef
        }).then(function successCallback(numeroCertificadoDef) {

                console.log(numeroCertificadoDef.data);
                $scope.certificado = numeroCertificadoDef.data;

               

                if ($scope.certificado == true) {
                    console.log('niooo');
                    vm.modelo.numeroCertificado = "true";
                    vm.modalNumeroCertificado();
                }
            }, function errorCallback(error) {
                console.log("error: " + error.data);
            });
    }

    //------------------------------------------------------------------------------//

    $scope.buscarEstadoCombo = function () {
        console.log("-----BUSCAR ESTADO COMBO------");

        //var mPais;
        var mPais = "VEN"
        $http({
            method:'POST',
            url:'/web-generales/consultarEstadoPorPais/',
            data:{codigo:mPais}
        }).then(function successCallback(estado) {
                //$scope.disabled = false
                console.log(estado.data);
                $scope.estados = estado.data;
                $scope.municipios = $scope.estados.municipios;
            }, function errorCallback(error) {
                console.log("error: " + error.data);
            });


    }
    //$scope.buscarEstadoCombo();
    
    $scope.buscarMunicipioCombo = function () {

        var mEstado = null;
        for (var i = 0; i < $scope.estados.length; i++) {
            if ($scope.estados[i].nombre === vm.modelo.PermisoInhCre.estado) {
            	mEstado = $scope.estados[i].codigo;

            }

            $http({
                method:'GET',
                url:'/web-permisoInhumacionCremacion/consultarMunicipios/' + mEstado
            }).then(function successCallback(municipio) {
                    console.log(municipio.data);
                    $scope.municipios = municipio.data;
                    $scope.parroquias = $scope.municipios.parroquias;

                }, function errorCallback(error) {
                    console.log("error: " + error.data);
                });
        }
    }


    $scope.buscarParroquiaCombo = function () {

        var mParroquia = null;
        for (var i = 0; i < $scope.municipios.length; i++) {
            if ($scope.municipios[i].nombre === vm.modelo.PermisoInhCre.municipio) {
                mParroquia = $scope.municipios[i].codigo;

            }

            $http({
                method:'GET',
                url:'/web-permisoInhumacionCremacion/consultarParroquias/' + mParroquia
            }).then(function successCallback(parroquia) {
                    console.log(parroquia.data);
                    $scope.parroquias = parroquia.data;

                }, function errorCallback(error) {
                    console.log("error: " + error.data);
                });
        }
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

    vm.onBlur = function () {
        vm.errorTecla[id] = false;
    }

	
})


App.controller('NCA_RRFILControlador', function ($scope, $http, $uibModal, $rootScope, validaciones) {
	
	

		        $scope.tituloWizard = "Registrar recomposición de filiación";
		        var vm = this;

		        $scope.validaciones = validaciones;
		        // Guarda en un array las vistas presentadas durante el proceso
		        vm.vistas = [];
		        // arreglo de datos que han sido presentados en pantalla
		        vm.modelos = [];
		        // representa los datos actualmente presentes en pantalla
		        vm.modelo = {};
		        vm.titulos = [];

		        vm.activo = 0;
		        vm.noConforme = false;
		        $scope.disabled = false;
		        $scope.disabled2 = false;
		        vm.imprime=false;
		        $scope.impresion=false;

		        vm.estado = "iniciarTramiteRRFIL";


		        $scope.vPatron = {
		            AlfaNumerico:/^[A-Za-z0-9]*$/,
		            Alfa:/^[a-zA-Z]*$/,
		            FormatoNombres:/^((([a-zA-Z ñÑáéíóúÁÉÍÓÚäëïöüÄËÏÖÜ]))|([-'])\1?(?!\4))+$/,
		            FormatoCementerio:/^((([a-zA-Z0-9 ñÑáéíóúÁÉÍÓÚäëïöüÄËÏÖÜ]))|([-'])\1?(?!\4))+$/,
		            AlfaText:'Este campo solo permite letras, di&eacute;resis, ap&oacute;strofes, guiones y acentos',
		            Pcementerio:'Este campo solo permite letras, di&eacute;resis, ap&oacute;strofes, guiones, acentos y n&uacute;meros',
		            Fnumero:'Este campo admite solo n&uacute;meros',
		            Facta:'Este campo admite solo n&uacute;meros y tiene un minimo y maximo de 20 caracteres',
		            Ffolio:'Este campo admite solo n&uacute;meros y tiene un minimo y maximo de 5 caracteres',
		            Ftomo:'Este campo admite solo n&uacute;meros y tiene un minimo y maximo de 2 caracteres',
		            TipoCed:/^[V|v|E|e]/,
		            Numerico:/^[0-9]/,
		            cedulaMaxLength:"9",
		            cedulaMinLength:"6",
		            pasaporteMaxLength:"11",
		            pasaporteMinLength:"6",
		            nombreMaxlength:"50",
		            nombreMinlength:"2",
		            CedulaText:"Este campo permite solo n&uacute;meros, con un m&iacute;nimo de 6 caracteres y un m&aacute;ximo de 9 caracteres <br/> Ejemplo: 12345678",
		            NumeroDocumento:/^[0-9]{6,11}$/,
		            excepciones:[ 'Backspace', 'Tab', ' ', 'ArrowLeft',
		                'ArrowRight', 'Delete', 'Caps Lock', 'Shift',
		                'Control' ],
		            capTuraEvento:function (event, patron) {

		            }

		        };
		        vm.onKeyDown = function (event, validacion, id) {
		            var excepciones = validaciones[validacion].excepciones;
		            for (var i = 0; i < excepciones.length; i++) {
		                if (event.key === excepciones[i])
		                    return;
		            }
		            var patron = new RegExp(validaciones[validacion].expReg);
		            if (!patron.test(event.key)) {
		                event.preventDefault();
		                vm.errorTecla[id] = true;
		                return;
		            }
		            vm.errorTecla[id] = false;
		        }

		        vm.onBlur = function () {
		            vm.errorTecla[id] = false;
		        }

		        vm.hola = function (valor) {
		            console.log(valor);
		        }

		        $scope.onKeyDownAlfa = function (event, validacion, id) {
		            var excepciones = $scope.vPatron.excepciones;
		            for (var i = 0; i < excepciones.length; i++) {
		                if (event.key === excepciones[i])
		                    return;
		            }
		            var patron = $scope.vPatron.FormatoNombres;
		            if (!patron.test(event.key)) {
		                event.preventDefault();
		                $scope.errorTecla[id] = true;
		                return;
		            }
		            $scope.errorTecla[id] = false;
		        }

		        $scope.prueba = function (id) {
		            console.log(id);

		        }


		        $scope.onKeyDownCementerio = function (event, validacion, id) {
		            var excepciones = $scope.vPatron.excepciones;
		            for (var i = 0; i < excepciones.length; i++) {
		                if (event.key === excepciones[i])
		                    return;
		            }
		            var patron = $scope.vPatron.FormatoCementerio;
		            if (!patron.test(event.key)) {
		                event.preventDefault();
		                $scope.errorTecla[id] = true;
		                return;
		            }
		            $scope.errorTecla[id] = false;
		        }

		        $scope.onKeyDownNumerico = function (event, validacion, id) {
		            var excepciones = $scope.vPatron.excepciones;
		            for (var i = 0; i < excepciones.length; i++) {
		                if (event.key === excepciones[i])
		                    return;
		            }
		            var patron = $scope.vPatron.Numerico;
		            if (!patron.test(event.key)) {
		                event.preventDefault();
		                $scope.errorTecla[id] = true;
		                return;
		            }
		            $scope.errorTecla[id] = false;
		        }

		        $scope.onBlur = function () {
		            $scope.errorTecla[id] = false;
		        }


		        /**
		         * identifica el titulo de la etapa del proceso y el indice activo
		         * para resaltarlo con el color azul
		         */
		        vm.presentarTitulo = function (mTitulo) {
		            vm.activo = vm.titulos.indexOf(mTitulo);
		            if (vm.activo == -1) {
		                vm.titulos.push(mTitulo);
		                vm.activo = vm.titulos.length - 1;
		            }
		        }
		        /**
		         * Maneja como se muestran las vistas.
		         */
		        vm.presentarVista = function ($scope, vm, response) {

		            vm.vista = response.data.vista;
		            vm.modelo = response.data.modelo;

		            vm.presentarTitulo(response.data.modelo.titulo);
		            $scope.fA = null;
		            $scope.sA = null;
		            //vm.modelo['HIJ']
		            $scope.fA = vm.modelo['MAD'].primerApellido;
		            if (vm.modelo['MAD'].segundoApellido == undefined) {
		                $scope.sA = vm.modelo['MAD'].primerApellido;
		            }
		            $scope.sA = vm.modelo['MAD'].segundoApellido
		            vm.modelo['HIJ'].primerApellido = $scope.fA;
		            vm.modelo['HIJ'].segundoApellido = $scope.sA;

		            vm.estado = "datosCertificado";
		            console.log(vm.estado);
		            console.log('INICIO DE LA PRIMERA VISTA LOCO ');
		            llamarDisabled();
		        }

		        vm.presentarVista2 = function ($scope, vm, response) {

		            vm.vista = response.data.vista;
		            vm.modelo = response.data.modelo;

		            vm.presentarTitulo(response.data.modelo.titulo);

		            vm.estado = "datosMadre";
		            console.log(vm.estado);
		            console.log('presentar vista 2');
		            llamarDisabled();
		        }

		        vm.presentarVista3 = function ($scope, vm, response) {

		            vm.vista = response.data.vista;
		            vm.modelo = response.data.modelo;

		            vm.presentarTitulo(response.data.modelo.titulo);

		            vm.calcEdad1();
		            vm.estado = "datosDeclarante";
		            console.log(vm.estado);
		            console.log('presentar vista 3');
		            llamarDisabled();
		        }

		        vm.presentarVista4 = function ($scope, vm, response) {
		            vm.vista = response.data.vista;
		            vm.modelo = response.data.modelo;
		            vm.presentarTitulo(response.data.modelo.titulo);
		            vm.estado = "datosTestigo";
		        }

		        vm.presentarVista5 = function ($scope, vm, response) {
		            vm.vista = response.data.vista;
		            vm.modelo = response.data.modelo;

		            //vm.modelo['TA1'].fechaNacimiento,edad
		            vm.calcEdadT1();
		            vm.calcEdadT2();
		            //$scope.t1();
		            //$scope.t2();

		            vm.presentarTitulo(response.data.modelo.titulo);

		            vm.estado = "decisionJudicial";
		        }
		        /*
		         $scope.t1=function(){
		         return  vm.calcEdad(vm.modelo['TA1'],vm.modelo['TA1'].fechaNacimiento,vm.modelo['TA1'].edad);
		         }
		         $scope.t2=function(){
		         return vm.calcEdad(vm.modelo['TA2'],vm.modelo['TA2'].fechaNacimiento,vm.modelo['TA2'].edad);
		         }  */
		        vm.presentarVista6 = function ($scope, vm, response) {
		            vm.vista = response.data.vista;
		            vm.modelo = response.data.modelo;

		            vm.presentarTitulo(response.data.modelo.titulo);

		            vm.estado = "circunstanciaEspecial";
		        }

		        vm.presentarVista7 = function ($scope, vm, response) {
		            console.log('estoy en esta vista 7, cambio estatus');
		            vm.vista = response.data.vista;
		            vm.modelo = response.data.modelo;

		            vm.presentarTitulo(response.data.modelo.titulo);

		            vm.estado = "documentosPresentados";

		            console.log('estado de vista 7 ' + vm.estado);
		        }

		        vm.presentarVista8 = function ($scope, vm, response) {
		            vm.vista = response.data.vista;
		            vm.modelo = response.data.modelo;

		            vm.presentarTitulo(response.data.modelo.titulo);

		            vm.estado = "RRFIL_imprimirActa";
		            console.log('estamos en ' + vm.estado + ' Y ES LA QUE ES PUES.');
		        }

		        vm.presentarVista9 = function ($scope, vm, response) {
		            vm.vista = response.data.vista;
		            vm.modelo = response.data.modelo;
		            console.log("VISTA PREVIA DEL ACTA");
		            console.log("estamos en la vista 9");

		            vm.imprime=true;
		            vm.presentarTitulo(response.data.modelo.titulo);
		            vm.estado = "verificacionConforme";
		        }

		        vm.presentarVistaBorrador = function ($scope, vm, response) {
		            vm.vista = response.data.vista;
		            vm.modelo = response.data.modelo;
		            console.log("VISTA PREVIA DEL ACTA");
		            console.log("estamos en la vista 9");

		            $scope.impresion=true;
		            vm.imprime=false;
		            vm.presentarTitulo(response.data.modelo.titulo);
		            vm.estado = "verificacionConforme";
		        }

		        vm.presentarVista10 = function ($scope, vm, response) {
		            vm.vista = response.data.vista;
		            vm.modelo = response.data.modelo;
		            $scope.impresion=false;
		            vm.imprime=false;
		            
		            vm.modelo.titulo = "Verificación del declarante";
		            
		            vm.presentarTitulo(vm.modelo.titulo);

//		            vm.estado = "actualizarEstadoFinalRRFIL";
//		            console.log("ESTAMOS EN LA VISTA PERSENTAR 10 Y EL ESTATUS ES " + vm.estado);
		        }
		        vm.presentarVista11 = function ($scope, vm, response) {
		            vm.vista = response.data.vista;
		            vm.modelo = response.data.modelo;

		            vm.presentarTitulo(response.data.modelo.titulo);

		            vm.estado = "x";
		        }
		        vm.presentarVistaNC=function($scope, vm, response){
		            vm.vista = response.data.vista;
		            vm.modelo = response.data.modelo;
		            $scope.impresion=false;
		            vm.imprime=false;
		            vm.presentarTitulo(response.data.modelo.titulo);

		            vm.estado = "iniciarTramiteRRFIL";
		        }

		        vm.finalizarActaRRFIL = function ($scope, vm, response) {
		            vm.finalizar();
		        }


		        /**
		         * Carga los datos iniciales del proceso
		         */
		        vm.estados = {
		            iniciarTramiteRRFIL:{
		                ruta:'/web-nacimiento/iniciarRRFIL',
		                funcion:vm.presentarVista,
		                metodo:"POST"
		            },
		            datosCertificado:{
		                ruta:'/web-nacimiento/datosCertificado',
		                funcion:vm.presentarVista2,
		                metodo:"POST"
		            },
		            datosMadre:{
		                ruta:'/web-nacimiento/datosMadre',
		                funcion:vm.presentarVista3,
		                metodo:"POST"
		            },
		            datosDeclarante:{
		                ruta:'/web-nacimiento/datosDeclarante',
		                funcion:vm.presentarVista4,
		                metodo:"POST"
		            },
		            datosTestigo:{
		                ruta:'/web-nacimiento/datosTestigo',
		                funcion:vm.presentarVista5,
		                metodo:"POST"
		            },
		            decisionJudicial:{
		                ruta:'/web-nacimiento/decisionJudicial',
		                funcion:vm.presentarVista6,
		                metodo:"POST"
		            },
		            circunstanciaEspecial:{
		                ruta:'/web-nacimiento/circunstanciaEspecial',
		                funcion:vm.presentarVista7,
		                metodo:"POST"
		            },
		            documentosPresentados:{
		                ruta:'/web-nacimiento/documentosPresentados',
		                funcion:vm.presentarVista8,
		                metodo:"POST"
		            },
		            RRFIL_imprimirActa:{
		                ruta:'/web-nacimiento/RRFIL_imprimirActa',
		                funcion:vm.presentarVista9,
		                metodo:"POST"
		            },
		            RRFIL_imprimirActaB:{
		                ruta:'/web-nacimiento/RRFIL_imprimirActa',
		                funcion:vm.presentarVistaBorrador,
		                metodo:"POST"
		            },
		            verificacionConforme:{
		                ruta:'/web-nacimiento/verificacionConforme',
		                funcion:vm.presentarVista10,
		                metodo:"POST"
		            },
		            verificacionNoConforme:{
		                ruta:'/web-nacimiento/verificacionNoConforme',
		                funcion:vm.presentarVistaNC,
		                metodo:"POST"
		            },
		            actualizarEstadoFinalRRFIL:{
		                ruta:'/web-nacimiento/actualizarEstadoRRFIL',
		                funcion:vm.finalizarActaRRFIL,
		                metodo:"POST"
		            }
		        };

		        // Datos a ser enviados al momento de cargar el modal
		        vm.modelo = {
		            id:$rootScope.objectSelected.numeroSolicitud,
		            statu:$rootScope.objectSelected.codigoEstadoSolicitud
		        };

		        llamadaAjax2($http, $scope, vm, vm.estados[vm.estado].ruta,
		            vm.modelo, vm.estados[vm.estado].funcion,
		            vm.estados[vm.estado].metodo);

		        vm.siguiente = function () {

		            // guarda en la pila la vista actual
		            vm.vistas.push(vm.vista);
		            vm.modelos.push(vm.modelo);


		            if(vm.imprime){
		                vm.abrirmodalBorrador();
		                vm.imprime=false;
		            }
		            else
		            if (vm.impreso) {
		                console.log("si ta fina la impresion");
		                vm.abrirmodalConfirmar();
		            } else
		            if(vm.modelo.permiso1=="conforme"){
		                vm.abrirModalActualizar();
		            }else
		            if (vm.noConforme) {
		                vm.abrirmodalNoConforme();
		            } else {
		                llamadaAjax2($http, $scope, vm, vm.estados[vm.estado].ruta,
		                    vm.modelo, vm.estados[vm.estado].funcion,
		                    vm.estados[vm.estado].metodo);
		            }


		        }

		        vm.regresar = function () {

		            if (vm.estado == "x") {
		                console.log('log 1 x' + vm.estado);
		                vm.estado = "circunstanciaEspecial";
		                console.log('estatus antes de llamada ajax ' + vm.estado);
		                llamadaAjax2($http, $scope, vm, vm.estados[vm.estado].ruta,
		                    vm.modelo, vm.estados[vm.estado].funcion,
		                    vm.estados[vm.estado].metodo);
		                console.log('estatus dps de llamada ajax ' + vm.estado);

		                console.log('esto es vm estado dps de docu ' + vm.estado);

		            }
		            else if (vm.estado == "documentosPresentados") {
		                console.log('log 2 documentosPresentados');
		                vm.estado = "decisionJudicial";
		                llamadaAjax2($http, $scope, vm, vm.estados[vm.estado].ruta,
		                    vm.modelo, vm.estados[vm.estado].funcion,
		                    vm.estados[vm.estado].metodo);
		                console.log('esto es vm estado dps de log2' + vm.estado);

		            }
		            else if (vm.estado == "circunstanciaEspecial") {
		                console.log('log 3 circunstanciaEspecial');
		                vm.estado = "datosTestigo";
		                llamadaAjax2($http, $scope, vm, vm.estados[vm.estado].ruta,
		                    vm.modelo, vm.estados[vm.estado].funcion,
		                    vm.estados[vm.estado].metodo);
		                console.log('esto es vm estado dps de log2' + vm.estado);

		            }
		            else if (vm.estado == "decisionJudicial") {
		                console.log('log 3 circunstanciaEspecial');
		                vm.estado = "datosDeclarante";
		                llamadaAjax2($http, $scope, vm, vm.estados[vm.estado].ruta,
		                    vm.modelo, vm.estados[vm.estado].funcion,
		                    vm.estados[vm.estado].metodo);
		                console.log('esto es vm estado dps de log2' + vm.estado);

		            }
		            else if (vm.estado == "datosTestigo") {
		                console.log('log 3 circunstanciaEspecial');
		                vm.estado = "datosMadre";
		                llamadaAjax2($http, $scope, vm, vm.estados[vm.estado].ruta,
		                    vm.modelo, vm.estados[vm.estado].funcion,
		                    vm.estados[vm.estado].metodo);
		                console.log('esto es vm estado dps de log2' + vm.estado);

		            }
		            else if (vm.estado == "datosDeclarante") {
		                console.log('log 3 circunstanciaEspecial');
		                vm.estado = "datosCertificado";
		                llamadaAjax2($http, $scope, vm, vm.estados[vm.estado].ruta,
		                    vm.modelo, vm.estados[vm.estado].funcion,
		                    vm.estados[vm.estado].metodo);
		                console.log('esto es vm estado dps de log2' + vm.estado);

		            }
		            else if (vm.estado == "datosMadre") {
		                console.log('LOG 3 DE DATOS MADRE');
		                vm.estado = "iniciarTramiteRRFIL";
		                llamadaAjax2($http, $scope, vm, vm.estados[vm.estado].ruta,
		                    vm.modelo, vm.estados[vm.estado].funcion,
		                    vm.estados[vm.estado].metodo);
		                vm.estado = "datosCertificado";
		                console.log('ESTE ES EL PENULTIMO TRANS ' + vm.estado);

		            }
		            else if (vm.estado == "RRFIL_imprimirActa") {
		                console.log('LOG RRFIL IMPRIMIR ACTA');
		                vm.estado = "circunstanciaEspecial";
		                llamadaAjax2($http, $scope, vm, vm.estados[vm.estado].ruta,
		                    vm.modelo, vm.estados[vm.estado].funcion,
		                    vm.estados[vm.estado].metodo);
		                vm.estado = "datosCertificado";
		                console.log('imprimir acta ' + vm.estado);
		            }
		            else if (vm.estado == "verificacionConforme") {
		                console.log('LOG RRFIL verificacion conforme');
		                vm.estado = "documentosPresentados";
		                llamadaAjax2($http, $scope, vm, vm.estados[vm.estado].ruta,
		                    vm.modelo, vm.estados[vm.estado].funcion,
		                    vm.estados[vm.estado].metodo);

		                console.log('imprimir acta ' + vm.estado);
		            }
		            else if (vm.estado == "finalizarTramiteRRFIL") {
		                console.log('LOG RRFIL verificacion conforme');
		                vm.estado = "RRFIL_imprimirActa";
		                llamadaAjax2($http, $scope, vm, vm.estados[vm.estado].ruta,
		                    vm.modelo, vm.estados[vm.estado].funcion,
		                    vm.estados[vm.estado].metodo);

		                console.log('imprimir acta ' + vm.estado);
		            }

		            if(vm.estado=="RRFIL_imprimirActaB"){
		                //vm.estado="datosCertificado";
		                vm.estado = "RRFIL_imprimirActa";
		                llamadaAjax2($http, $scope, vm, vm.estados[vm.estado].ruta,
		                    vm.modelo, vm.estados[vm.estado].funcion,
		                    vm.estados[vm.estado].metodo);
		                console.log('imprimir acta ' + vm.estado);
		            }

		        }




		        // Combo de Ocupaciones PARA RRFIL
		        $scope.buscarOcupacion = function () {
		            $http({
		                method:'GET',
		                url:'/web-nacimiento/consultarOcupacion'
		            }).then(function successCallback(ocupacion) {
		                    // /console.log(ocupacion.data);
		                    $scope.ocupaciones = ocupacion.data;
		                }, function errorCallback(error) {
		                    console.log("error: " + error.data);
		                });
		        }
		        $scope.buscarOcupacion();

		        // /Nacionalidad condicionada por el tipo de Doc
		        $scope.buscarNacionalidad = function () {
		            $http({
		                method:'GET',
		                url:'/web-nacimiento/cosultarNacionalidad'
		            }).then(function successCallback(nacionalidad) {
		                    // console.log(nacionalidad.data);
		                    $scope.nacionalidades = nacionalidad.data;
		                }, function errorCallback(error) {
		                    console.log("error: " + error.data);
		                });
		        }
		        $scope.buscarNacionalidad();

		        // Combo comunidad indigenas
		        $scope.buscarComunidades = function () {
		            $http({
		                method:'GET',
		                url:'/web-nacimiento/consultarComunidadIndigenaTodos'
		            }).then(function successCallback(comunidad) {
		                    // /console.log(ocupacion.data);
		                    $scope.comunidades = comunidad.data;
		                }, function errorCallback(error) {
		                    console.log("error: " + error.data);
		                });
		        }
		        $scope.buscarComunidades();

		        //funciones de pais para PRESENTADO


		        $scope.buscarPais = function () {

		            $http({
		                method:'POST',
		                url:'/web-generales/consultarPaisTodos',
		                data:{}
		            }).then(function successCallback(pais) {
		                    console.log(pais.data);
		                    $scope.paises = pais.data;
		                    $scope.estados = $scope.paises.estados;
		                }, function errorCallback(error) {
		                    console.log("error: " + error.data);
		                });

		        }

		        $scope.buscarPais();

		        $scope.buscarEstado = function () {
		            if (vm.modelo['HIJ'].pais.codigo == "VEN") {
		                $http({
		                    method:'POST',
		                    url:'/web-generales/consultarEstadoPorPais',
		                    data:{
		                        codigo:vm.modelo['HIJ'].pais.codigo
		                    }

		                }).then(function successCallback(estado) {
		                        $scope.disa = false
		                        console.log(estado.data);

		                        $scope.estados = estado.data;
		                        $scope.municipios = $scope.estados.municipios;
		                    }, function errorCallback(error) {
		                        console.log("error: " + error.data);
		                    });
		            } else {
		                $scope.disa = true;
		                $scope.estados = null;
		                $scope.municipios = null;
		                $scope.parroquias = null;
		            }
		        }


		        $scope.buscarMunicipio = function () {

		            $http({
		                method:'POST',
		                url:'/web-generales/consultarMunicipioPorEstado',
		                data:{
		                    codigo:vm.modelo['HIJ'].estado.codigo
		                }
		            }).then(function successCallback(municipio) {
		                    console.log(municipio.data);
		                    $scope.municipios = municipio.data;
		                    $scope.parroquias = $scope.municipios.parroquias;
		                }, function errorCallback(error) {
		                    console.log("error: " + error.data);
		                });

		        }

		        $scope.buscarParroquia = function () {

		            $http({
		                method:'POST',
		                url:'/web-generales/consultarParroquiaPorMunicipio',
		                data:{
		                    codigo:vm.modelo['HIJ'].municipio.codigo
		                }
		            }).then(function successCallback(parroquia) {
		                    console.log(parroquia.data);
		                    $scope.parroquias = parroquia.data;
		                    // $scope.parroquias=$scope.municipios.parroquias;
		                }, function errorCallback(error) {
		                    console.log("error: " + error.data);
		                });

		        }


		        //funciones de pais para MADRE

		        $scope.buscarEstadoMadre = function () {
		            if (vm.modelo['MAD'].pais.codigo == "VEN") {
		                $http({
		                    method:'POST',
		                    url:'/web-generales/consultarEstadoPorPais',
		                    data:{
		                        codigo:vm.modelo['MAD'].pais.codigo
		                    }

		                }).then(function successCallback(estado) {
		                        $scope.disaMam = false
		                        console.log(estado.data);

		                        $scope.estadosMadre = estado.data;
		                        $scope.municipiosMadre = $scope.estadosMadre.municipiosMadre;
		                    }, function errorCallback(error) {
		                        console.log("error: " + error.data);
		                    });
		            } else {
		                $scope.disaMAD = true
		                $scope.estadosMadre = null;
		                $scope.municipiosMadre = null;
		                $scope.parroquiasMadre = null;
		            }
		        }
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
		                    //$scope.estados = estado.data;
		                    //$scope.municipios = $scope.estados.municipios;
		                    //-----------
		                    $scope.disaMAD = false;
		                    console.log(estado.data);

		                    $scope.estadosMadre = estado.data;
		                    $scope.municipiosMadre = $scope.estadosMadre.municipiosMadre;
		                    //-----------------
		                }, function errorCallback(error) {
		                    console.log("error: " + error.data);
		                });
		        }
		        $scope.buscarMunicipioCombo = function () {
		            var mEstado = null;
		            console.log("-----state: "+JSON.stringify($scope.estadosMadre));
		            //for (var i = 0; i < $scope.estadosMadre.length; i++) {
		            angular.forEach($scope.estadosMadre,function(i){
		                console.log("--sss: "+JSON.stringify(vm.modelo['MAD'].estado));
		                console.log("dd :"+ i.nombre);
		                console.log("---**state:  "+vm.modelo['MAD'].estado);
		                if (i.nombre === vm.modelo['MAD'].estado) {
		                    mEstado = i.codigo;
		                    console.log("mm****--: "+mEstado);
		                }
		                  console.log("mm--: "+mEstado);
		                $http({
		                    method:'GET',
		                        url:'/web-nacimiento/consultarMunicipios/' + mEstado
		                    //url:'/web-generales/consultarMunicipioPorEstado'

		                }).then(function successCallback(municipio) {
		                       // console.log(municipio.data);
		                        console.log("*****sdsdf*: "+JSON.stringify(municipio.data));
		                        $scope.municipiosMadre = municipio.data;
		                        $scope.parroquiasMadres = $scope.municipiosMadre.parroquiasMadre;
		                    }, function errorCallback(error) {
		                        console.log("error: " + error.data);
		                    });
		            });

		          }



		        $scope.buscarParroquiaCombo = function () {

		            var mParroquia = null;
		            console.log("-----state: "+JSON.stringify($scope.municipiosMadre));
		            //for (var i = 0; i < $scope.municipios.length; i++) {
		            angular.forEach($scope.municipiosMadre,function(i){
		                if (i.nombre === vm.modelo['MAD'].municipio) {
		                    mParroquia = i.codigo;

		                }

		                $http({
		                    method:'GET',
		                    url:'/web-nacimiento/consultarParroquias/' + mParroquia
		                }).then(function successCallback(parroquia) {
		                        console.log(parroquia.data);
		                        $scope.parroquiasMadre = parroquia.data;

		                    }, function errorCallback(error) {
		                        console.log("error: " + error.data);
		                    });
		            });
		        }

		        $scope.buscarMunicipioMadre = function () {

		            $http({
		                method:'POST',
		                url:'/web-generales/consultarMunicipioPorEstado',
		                data:{
		                    codigo:vm.modelo['MAD'].estado.codigo
		                }
		            }).then(function successCallback(municipio) {
		                    console.log(municipio.data);
		                    $scope.municipiosMadre = municipio.data;
		                    $scope.parroquiasMadre = $scope.municipiosMadre.parroquiasMadre;
		                }, function errorCallback(error) {
		                    console.log("error: " + error.data);
		                });

		        }

		        $scope.buscarParroquiaMadre = function () {

		            $http({
		                method:'POST',
		                url:'/web-generales/consultarParroquiaPorMunicipio',
		                data:{
		                    codigo:vm.modelo['MAD'].municipio.codigo
		                }
		            }).then(function successCallback(parroquia) {
		                    console.log(parroquia.data);
		                    $scope.parroquiasMadre = parroquia.data;
		                    // $scope.parroquias=$scope.municipios.parroquias;
		                }, function errorCallback(error) {
		                    console.log("error: " + error.data);
		                });

		        }

		        //funciones de pais para DECLARANTE

		        $scope.buscarEstadoDec = function () {
		            if (vm.modelo['MAD'].pais.codigo == "VEN") {
		                $http({
		                    method:'POST',
		                    url:'/web-generales/consultarEstadoPorPais',
		                    data:{
		                        codigo:vm.modelo['DEC'].pais.codigo
		                    }

		                }).then(function successCallback(estado) {
		                        $scope.disaDec = false
		                        console.log(estado.data);

		                        $scope.estadosDec = estado.data;
		                        $scope.municipiosDec = $scope.estadosDec.municipiosDec;
		                    }, function errorCallback(error) {
		                        console.log("error: " + error.data);
		                    });
		            } else {
		                $scope.disaDec = true
		                $scope.estadosDec = null;
		                $scope.municipiosDec = null;
		                $scope.parroquiasDec = null;
		            }
		        }

		        $scope.buscarMunicipioDec = function () {

		            $http({
		                method:'POST',
		                url:'/web-generales/consultarMunicipioPorEstado',
		                data:{
		                    codigo:vm.modelo['MAD'].estado.codigo
		                }
		            }).then(function successCallback(municipio) {
		                    console.log(municipio.data);
		                    $scope.municipiosDec = municipio.data;
		                    $scope.parroquiasDec = $scope.municipiosDec.parroquiasDec;
		                }, function errorCallback(error) {
		                    console.log("error: " + error.data);
		                });

		        }

		        $scope.buscarParroquiaDec = function () {

		            $http({
		                method:'POST',
		                url:'/web-generales/consultarParroquiaPorMunicipio',
		                data:{
		                    codigo:vm.modelo['MAD'].municipio.codigo
		                }
		            }).then(function successCallback(parroquia) {
		                    console.log(parroquia.data);
		                    $scope.parroquiasDec = parroquia.data;
		                    // $scope.parroquias=$scope.municipios.parroquias;
		                }, function errorCallback(error) {
		                    console.log("error: " + error.data);
		                });

		        }

		        //funcion de cerrado de modal de proceso

		        vm.cancelar = function () {
		            // $rootScope.cancelar1();
		            vm.confirmarSalidaModulo();
		        }

		        vm.cancel = function () {
		            vm.confirmarSalidaModulo();
		            // $rootScope.cancelar1();
		        }

		        vm.confirmarSalidaModulo = function () {
		            presentarModal($scope, $uibModal, vm.salirModulo,
		                '¿Desea cancelar la solicitud?',
		                $rootScope.tituloWizard, 's');
		        }

		        vm.salirModulo = function () {
		            $rootScope.cancelar1();
		        }
		        //funciones para imprimir
		        vm.imprimir = function () {
		            vm.windowPrintBrowser();
		            presentarModal($scope, $uibModal, vm.okF,
		                '¿La impresión es satisfactoria?',
		                $rootScope.tituloWizard, 's');
		        }

		        vm.windowPrintBrowser = function () {

		            document.getElementById("plugin").focus();

		            document.getElementById("plugin").contentWindow.print();

		        }

		        vm.abrirmodalBorrador = function () {
		            console.log("modalconfirmar");
		            presentarModal($scope, $uibModal, vm.okRespuestaB, '¿Usted desea imprimir el acta en formato borrador?', $rootScope.tituloWizard, 's');
		           // $scope.impresion=false;
		        }

		        vm.abrirmodalConfirmar = function () {
		            console.log("modalconfirmar");
		            presentarModal($scope, $uibModal, vm.okRespuesta, '¿Se confirma la impresión?', $rootScope.tituloWizard, 's');
		            vm.impreso = false;
		        }

		        vm.abrirModalActualizar = function () {
		            console.log("modalconfirmar");
		            presentarModal($scope, $uibModal, vm.okActualizarStatus, '¿Se confirma que esta conforme?', $rootScope.tituloWizard, 's');
		            vm.impreso = false;
		        }
		        vm.okActualizarStatus=function(){
		            vm.cambiarEstatusSolicitudRRFIL();
		        }

		        vm.okRespuestaB = function () {
		            console.log("confirmado");
		            vm.estado = "RRFIL_imprimirActaB";
		            vm.imprime=false;
		            vm.impreso = false;
		           // vm.presentarVistaBorrador($scope, vm, response);
		            llamadaAjax2($http, $scope, vm, vm.estados[vm.estado].ruta, vm.modelo, vm.estados[vm.estado].funcion, vm.estados[vm.estado].metodo);
		        }
		        vm.okRespuesta = function () {
		            console.log("confirmado");
		            vm.estado = "verificacionConforme";
		            vm.imprime=false;
		            llamadaAjax2($http, $scope, vm, vm.estados[vm.estado].ruta, vm.modelo, vm.estados[vm.estado].funcion, vm.estados[vm.estado].metodo);
		        }
		        vm.okF = function () {
		            console.log("entroooo");
		            vm.impreso = true;
		        }

		        vm.conformeRRFIL = function () {

		            presentarModal($scope, $uibModal, vm.cambiarEstatusSolicitudRRFIL, 'Verificación del declarante exitosa', $rootScope.tituloWizard, 's');
		        }

		        vm.cambiarEstatusSolicitudRRFIL = function () {

		            console.log("estamos en final de conforme por la solicitud");
		            llamadaAjax($http, $scope, vm, '/web-nacimiento/actualizarEstadoRRFIL', vm.modelo, vm.finalizar, "POST");	 


		        }

		        $scope.mostrarCampoObservacion = function () {

		            $scope.campoObservacion = true;
		            // vm.abrirModalNoConforme();
		            vm.confirmarDatosForm = true;
		            vm.noConforme = true;
		            vm.modelo.observaciones=null;
		        }

		        $scope.ocultarCampoObservacion = function () {

		            $scope.campoObservacion = false;
		            // vm.abrirModalConforme();
		            vm.confirmarDatosForm = true;
		            vm.noConforme = false;
		            vm.modelo.observaciones="conforme";
		        }

		        $scope.limpiarPasaporte = function () {
		            $scope.vm.modelo['CMN'].documentoIdentidad[0].numero = null;
		            $scope.pasaporteMaxLength=11;
		            $scope.pasaporteMinLength=6;
		            $scope.cedulaMaxLength=9;
		            $scope.cedulaMinLength=6;
		        }


		        vm.cambiarEstadoSolicitud = function () {
		            console.log("Estado dentro de cambiarEstadoSolicitud " + vm.estado);
		            llamadaAjax2($http, $scope, vm, vm.estados[vm.estado].ruta, vm.modelo, vm.estados[vm.estado].funcion, vm.estados[vm.estado].metodo);
		            console.log("Luego de hacer la llamada ajax2 en cambiarEstadoSolicitud");

		        }

		        vm.finalizar = function () {
		            // vm.cancelar();
		            $rootScope.cancelar1();
		        }

		        vm.abrirmodalNoConforme = function () {
		            presentarModal($scope, $uibModal, vm.verObsevaciones, '¿se confirma que no esta conforme?', $rootScope.tituloWizard, 's');
		            vm.noConforme = false;
		        }

		        vm.verObsevaciones=function(){
		            vm.estado = "verificacionNoConforme";
		            llamadaAjax2($http, $scope, vm, vm.estados[vm.estado].ruta, vm.modelo, vm.estados[vm.estado].funcion, vm.estados[vm.estado].metodo);
		        }
		        //fin funciones para imprimir

		        vm.calcEdad = function (model, dateBird, modelAge) {
		            if (model != null) {
		                var fnacM = new Date(
		                    dateBird)
		                console.log(fnacM);
		                var hoy = Date.now()
		                edM = parseInt((hoy - fnacM) / 365 / 24 / 60 / 60
		                    / 1000)
		                modelAge = edM
		                if (modelAge != null) {
		                    $scope.disabled = true;
		                } else {
		                    $scope.disabled = false;
		                }
		// console.log("MAM"+edM);
		// console.log($scope.modelo['MAM'].Edad);
		            }
		        }

		        vm.calcEdadT1 = function () {
		            if (vm.modelo['TA1'] != null) {
		                var fnacM = new Date(
		                    vm.modelo['TA1'].fechaNacimiento)
		                console.log(fnacM);
		                var hoy = Date.now()
		                edM = parseInt((hoy - fnacM) / 365 / 24 / 60 / 60
		                    / 1000)
		                vm.modelo['TA1'].edad = edM
		                if (vm.modelo['TA1'].edad != null) {
		                    $scope.disabled = true;
		                } else {
		                    $scope.disabled = false;
		                }
		// console.log("MAM"+edM);
		// console.log($scope.modelo['MAM'].Edad);
		            }
		        }

		        vm.calcEdadT2 = function () {
		            if (vm.modelo['TA2'] != null) {
		                var fnacM = new Date(
		                    vm.modelo['TA2'].fechaNacimiento)
		                console.log(fnacM);
		                var hoy = Date.now()
		                edM = parseInt((hoy - fnacM) / 365 / 24 / 60 / 60
		                    / 1000)
		                vm.modelo['TA2'].edad = edM
		                if (vm.modelo['TA2'].edad != null) {
		                    $scope.disabled = true;
		                } else {
		                    $scope.disabled = false;
		                }
		// console.log("MAM"+edM);
		// console.log($scope.modelo['MAM'].Edad);
		            }
		        }

		        vm.calcEdad1 = function () {
		            if (vm.modelo['MAD'] != null) {
		                var fnacM = new Date(
		                    vm.modelo['MAD'].fechaNacimiento)
		                console.log(fnacM);
		                var hoy = Date.now()
		                edM = parseInt((hoy - fnacM) / 365 / 24 / 60 / 60
		                    / 1000)
		                vm.modelo['MAD'].Edad = edM
		                if (vm.modelo['MAD'].Edad != null) {
		                    $scope.disabled = true;
		                } else {
		                    $scope.disabled = false;
		                }
		// console.log("MAM"+edM);
		// console.log($scope.modelo['MAM'].Edad);
		            }
		        }

		        llamarDisabled = function() {
		            // ///////////HIJ
		            if (vm.modelo['HIJ'] != null) {
		                console.log("verifica q estoy entrando");
		                if (vm.modelo['HIJ'].primerNombre != null) {

		                    $scope.disabled = true;
		                    console.log("prIMER NOMBRE");
		                }  else {
		                    $scope.disabled = false;
		                }
		                if (vm.modelo['HIJ'].segundoNombre != null) {
		                    $scope.disabled = true;

		                } else {
		                    $scope.disabled = false;
		                }
		                if (vm.modelo['HIJ'].primerApellido != null) {
		                    $scope.disabled = true;
		                } else {
		                    $scope.disabled = false;
		                }
		                if (vm.modelo['HIJ'].segundoApellido != null) {
		                    $scope.disabled = true;
		                } else {
		                    $scope.disabled = false;
		                }
		                if (vm.modelo['HIJ'].documentoIdentidad[0].numero != null) {
		                    // console.log(vm.modelo['HIJ'].documentoIdentidad[0].numero);
		                    $scope.disabled = true;
		                } else {
		                    $scope.disabled = false;
		                }
		                if (vm.modelo['HIJ'].documentoIdentidad[0].tipoDocumento.nombre != null) {
		                    // console.log(vm.modelo['MAM'].documentoIdentidad[0].tipoDocumento.nombre);
		                    $scope.disabled = true;
		                } else {
		                    $scope.disabled = false;
		                }

		            }
		            //DATOS DE LA MADRE
		            if (vm.modelo['MAD'] != null) {
		                console.log("verifica q estoy entrando");
		                if (vm.modelo['MAD'].primerNombre != null) {

		                    $scope.disabled = true;
		                    console.log("prIMER NOMBRE");
		                }  else {
		                    $scope.disabled = false;
		                }
		                if (vm.modelo['MAD'].segundoNombre != null) {
		                    $scope.disabled = true;

		                } else {
		                    $scope.disabled = false;
		                }
		                if (vm.modelo['MAD'].primerApellido != null) {
		                    $scope.disabled = true;
		                } else {
		                    $scope.disabled = false;
		                }
		                if (vm.modelo['MAD'].segundoApellido != null) {
		                    $scope.disabled = true;
		                } else {
		                    $scope.disabled = false;
		                }
		                if (vm.modelo['MAD'].documentoIdentidad[0].numero != null) {
		                    // console.log(vm.modelo['HIJ'].documentoIdentidad[0].numero);
		                    $scope.disabled = true;
		                } else {
		                    $scope.disabled = false;
		                }
		                if (vm.modelo['MAD'].documentoIdentidad[0].tipoDocumento.nombre != null) {
		                    // console.log(vm.modelo['MAM'].documentoIdentidad[0].tipoDocumento.nombre);
		                    $scope.disabled = true;
		                } else {
		                    $scope.disabled = false;
		                }

		            }

		            if (vm.modelo['CMN'] != null) {
		                console.log("verifica q estoy entrando");
		                if (vm.modelo['CMN'].numCert != null) {

		                    $scope.disabled2 = true;
		                    console.log("DATOS CERTIFICADO");
		                }  else {
		                    $scope.disabled2 = false;

		                }
		                if (vm.modelo['CMN'].documentoIdentidad[0].numero != null) {

		                    $scope.disabled2 = true;
		                    console.log("DATOS CERTIFICADO");
		                }  else {
		                    $scope.disabled2 = false;

		                }
		            }

		        }
		    
	
});
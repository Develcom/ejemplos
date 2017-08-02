//Controlador encargado de cargar la pantalla con el menu para la apertura o cierre de libro
//Este controlador tiene la funcion operacionLibro de invocar otra pantalla
App.controller('cargarIndexControlador', function ($rootScope, $scope, $http, $location, $window, $timeout, $state, $stateParams, registrarRutasService) {

    $scope.operacionLibroCivil;
    $scope.mostrarChequeo = 0;
    $rootScope.panel = 0;
    $scope.operacionLibro = function (operacion) {
        //Se invoca al controlador para cargar la tabla con todos los libros de la oficina
        invocarControlador('cargarTramite', operacion, $scope, $stateParams, $state, $http, registrarRutasService);
    }
});

//Controlador encargado de cargar la pantalla con la tabla de libros para aperturar o cerrar libro civil
App.controller('cargarTramiteControlador', function ($rootScope, $scope, $http, $location, $window, $timeout, $state, $stateParams, registrarRutasService) {

    $scope.BlnLoad = 1;
    $scope.radioOperacion;
    $scope.operacion = $stateParams.operacion;
    $scope.librosCivil = $stateParams.lista;
    $scope.heightPerformPeticionDatos = (($window.outerHeight - $("#header").outerHeight() - $('nav.navbar.navbar-inverse.navbar-sarc').outerHeight() - $('footer').height()) / 1.1) + 'px;';

    //funcion encargada de continuar con el flujo de las pantallas
    $scope.continuarPantalla = function (accion) {
        accionBotones(accion, $scope, $timeout, $rootScope, $window, $stateParams, $state, $http, registrarRutasService);
    }

    //funcion encarga de volver atras en la pantalla
    $scope.atrasPantalla = function (accion) {
        $stateParams.token = $rootScope.token;
        $state.transitionTo('indexLibro', $stateParams);
    }

    $scope.obtenerTramite = function (datos) {
        $scope.data = {};
        $scope.data.estatus = datos.estatus;
        $scope.data.funcionarioApertura = datos.funcionarioApertura.primerNombre;
        $scope.data.tipoLibro = datos.tipoLibro.nombre;
        $scope.data.tipoLibro_codigo = datos.tipoLibro.codigo;
        $scope.data.tipoLibro_descripcion = datos.tipoLibro.descripcion;
        $scope.data.tipoDocumento_codigo = datos.funcionarioApertura.documentoIdentidad[0].tipoDocumento.codigo;
        $scope.data.documentoIdentidad_numero = datos.funcionarioApertura.documentoIdentidad[0].numero;

        $scope.data.codigo_oficina = datos.oficina.codigo;
        $scope.data.descripcion_oficina = datos.oficina.descripcion;
        $scope.data.nombre_oficina = datos.oficina.nombre;

        $scope.data.numeroTomo = datos.numeroTomo;
        $scope.data.numeroActa = datos.numeroActa;
        $scope.data.numeroFolio = datos.numerofolio;
        $scope.data.numeroAnio = datos.numeroAnio;

        $scope.data.operacion = datos.operacion;
        $scope.tramiteEjecutado = datos.tipoLibro.nombre;
    }
});

//Controlador encargado de cargar la pantalla con la Acta de apertura o cierre de libro
App.controller('actaControlador', function ($rootScope, $scope, $http, $location, $window, $timeout, $state, $stateParams, registrarRutasService) {

    $scope.heightPerform = (($window.outerHeight - $("#header").outerHeight() - $('nav.navbar.navbar-inverse.navbar-sarc').outerHeight() - $('footer').height()) / 1.1) + 'px;';
    $rootScope.panel = 1;
    $scope.BlnLoad = 1;
    $scope.html = $stateParams.html;
    $scope.operacion = $stateParams.operacion;
    $scope.ruta = $stateParams.ruta;

    //funcion encargada de continuar con el flujo de las pantallas
    $scope.continuarPantalla = function (accion) {
        accionBotones(accion, $scope, $timeout, $rootScope, $window, $stateParams, $state, $http, registrarRutasService);
    }

    //funcion encarga de volver atras en la pantalla
    $scope.atrasPantalla = function (accion) {
        invocarControlador('cargarTramite', null, $scope, $stateParams, $state, $http, registrarRutasService);
    }
});

App.controller('errorLibro', function ($rootScope, $scope, $http, $location, $window, $timeout, $state, $stateParams) {

    $scope.error_message = $stateParams.errorMsg;

    $scope.finalizar = function () {
        $state.transitionTo('indexLibro');
    }
});

App.controller('satisfactorioLibro', function ($rootScope, $scope, $http, $location, $window, $timeout, $state, $stateParams) {

    $scope.satisfactorio_message = $stateParams.satisfactorioMsg;

    $scope.finalizar = function () {
        $state.transitionTo('indexLibro');
    }
});

function accionBotones(accion, $scope, $timeout, $rootScope, $window, $stateParams, $state, $http, registrarRutasService) {
    $scope.dataLoaded = "0%";
    switch (accion) {
        case  'irVisualizarActa':

            $rootScope.panel = 1;
            $scope.BlnLoad = 0;
            $scope.dataLoaded = "20%";
            $scope.heightPerform = (($window.outerHeight - $("#header").outerHeight() - $('nav.navbar.navbar-inverse.navbar-sarc').outerHeight() - $('footer').height()) / 1.1) + 'px;';
            $scope.dataLoaded = "40%";
            //se realizar la peticion a mi controlador java
            invocarControlador('visualizarActa', null, $scope, $stateParams, $state, $http, registrarRutasService);

            $timeout(function () {
                $scope.BlnLoad = 1;
            }, 4000);


            break;

        case 'irTablaTramite': // se invoca servicio para la apertura/cierre

            $scope.mostrarChequeo = 1;
            $scope.tramite = $scope.tramiteEjecutado;
            invocarControlador('apertua_cierre', null, $scope, $stateParams, $state, $http, registrarRutasService);
            break;
    }
}

//funcion encargada de capturar los errores ocurrido en la aplicacion para mostrarlo en pantalla
function capturarError($stateParams, $state, response) {
    $stateParams.errorSol = response.data.errCode;
    $stateParams.errorMsg = response.data.errMsg;
    $state.transitionTo('error', $stateParams);
}

function capturarSuccess($stateParams, $state, response, $ngSanitize) {
    $stateParams.satisfactorioMsg = response.data.satisfactorioMsg;
    $state.transitionTo('satisfactorio', $stateParams);
}


/**
 * <p>Funci&oacute;n gen&eacute;rica que realiza el llamado ajax a las opercaiones sobre el libro</p>
 * @param accion
 * @param operacion
 * @param $scope
 * @param $stateParams
 * @param $state
 * @param $http
 */
function invocarControlador(accion, operacion, $scope, $stateParams, $state, $http, registrarRutasService) {
    switch (accion) {

        case 'cargarTramite':

            $scope.html = null;
            if ($stateParams.lista == undefined || $stateParams.lista == null) {
                $http({
                    method:'POST',
                    url:registrarRutasService.propiedades['sarc.web.generales.consultarLibro.url'],
                    data:{"operacion":operacion}
                }).then(function (response) {

                        if (response.data.errCode != null) {
                            capturarError($stateParams, $state, response)
                        } else {
                            $scope.librosCivil = response.data.librosList;
                            $stateParams.operacion = operacion;
                            $stateParams.lista = response.data.librosList;
                            $state.transitionTo('cargarTramite', $stateParams);
                        }
                    },
                    function (response) {
                    });

            } else {
                $state.transitionTo('cargarTramite', $stateParams);
            }
            break;

        case 'visualizarActa':
            $scope.html = null;
            var urlacta = registrarRutasService.propiedades['sarc.web.libro.generarActaLibro.url'];
            $scope.data.operacion = $stateParams.operacion;
            $scope.dataLoaded = "80%";
            $http({
                method:'POST',
                url:urlacta,
                data:$scope.data
            }).then(
                function (response) {

                    if (response.data.errCode != null) {
                        capturarError($stateParams, $state, response)
                    } else {
                        ff = response.data.ruta;
                        template = '<embed width=\'900\' height=\'450\' src=\'' + ff + '\' type=\'application/pdf\'></embed>';
                        console.log(template);
                        $scope.ruta = ff;
                        $scope.html = template;
                        $scope.dataLoaded = "100%";
                        $stateParams.html = template;
                        $stateParams.operacion = $scope.operacion;
                        $stateParams.ruta = $scope.ruta;
                        $state.transitionTo('visualizarActa', $stateParams);
                    }
                },
                function (response) {

                });
            break;

        case 'apertua_cierre':
            $http({
                method:'POST',
                url:registrarRutasService.propiedades['sarc.web.libro.operacionLibroAC.url']
            }).then(
                function (response) {
                    if (response.data.errCode != null) {
                        capturarError($stateParams, $state, response)
                    } else {
                        capturarSuccess($stateParams, $state, response)
                    }
                },
                function (response) {

                });


            break;
    }
}
/**
 * 
 */
App
		.controller(
				'RADOP_ActaController',
				function($scope, $http, $rootScope, $uibModal, validaciones) {

					$scope.validaciones = validaciones;
					var vm = this;
					vm.paso = 0;
					// Guarda en un array las vistas presentadas durante el
					// proceso
					vm.vistas = [];
					// arreglo de datos que han sido presentados en pantalla
					vm.modelos = [];
					// representa los datos actualmente presentes en pantalla
					vm.modelo = {};
					vm.titulos = [];
					vm.activo = -1;
					vm.modelo.permiso1 = {};
					// /Variables a usar
					$scope.disabled = false;
					$scope.disabled1 = false;
					$scope.disabled2 = false;
					$scope.disabled3 = false;
					$scope.disabled4 = false;
					vm.impreso = false;
					vm.mostrarImprimir=false;
					vm.errorTecla = {};
					$scope.certificado = " ";
					$scope.campo = "false";
					vm.modelo.variableCertif="false"
					$scope.subtituloWizard = "Elaborar acta";
					vm.banderaIniciarTramite = false;
					// validaciones de patrones
					$scope.vPatron = {
						AlfaNumerico : /^[A-Za-z0-9]*$/,
						Alfa : /^[a-zA-Z]*$/,
						FormatoNombres: /^((([a-zA-Z ñÑáéíóúÁÉÍÓÚäëïöüÄËÏÖÜ]))|([-'])\1?(?!\4))+$/,
						FormatoCementerio: /^((([a-zA-Z0-9 ñÑáéíóúÁÉÍÓÚäëïöüÄËÏÖÜ]))|([-'])\1?(?!\4))+$/,
						FormatoSentencia : /^((([a-zA-Z0-9 ñÑáéíóúÁÉÍÓÚäëïöüÄËÏÖÜ]))|([-.])\1?(?!\4))+$/,
						AlfaText : 'Este campo solo permite letras, di&eacute;resis, ap&oacute;strofes, guiones y acentos',
						Pcementerio : 'Este campo solo permite letras, di&eacute;resis, ap&oacute;strofes, guiones, acentos y n&uacute;meros',
						Ps : 'Este campo solo permite letras, guiones, puntos y n&uacute;meros',
						Fnumero : 'Este campo admite solo n&uacute;meros',
						Facta : 'Este campo admite solo n&uacute;meros y tiene un minimo y maximo de 20 caracteres',
						Ffolio : 'Este campo admite solo n&uacute;meros y tiene un minimo y maximo de 5 caracteres',
						Ftomo : 'Este campo admite solo n&uacute;meros y tiene un minimo y maximo de 2 caracteres',
						TipoCed : /^[V|v|E|e]/,
						Numerico : /^[0-9]/,
						cedulaMaxLength : "9",
						cedulaMinLength : "6",
						nombreMaxlength : "50",
						nombreMinlength : "2",
						CedulaText : "Este campo permite solo n&uacute;meros, con un m&iacute;nimo de 6 caracteres y un m&aacute;ximo de 9 caracteres <br/> Ejemplo: 12345678",
						NumeroDocumento : /^[0-9]{6,11}$/,
						excepciones : [ 'Backspace', 'Tab', ' ', 'ArrowLeft',
								'ArrowRight', 'Delete', 'Caps Lock', 'Shift',
								'Control' ],
						capTuraEvento : function(event, patron) {

						}

					};
					// /Para colocar el titulo al wizard
					$scope.tituloWizard = "Registrar adopción";
					// estado del proceso
					// vm.estado = 'iniciarTramiteEDPIC';
					/**
					 * identifica el titulo de la etapa del proceso y el indice
					 * activo para resaltarlo con el color azul
					 */
					vm.presentarTitulo = function(mTitulo) {
						vm.activo = vm.titulos.indexOf(mTitulo);
						if (vm.activo == -1) {
							vm.titulos.push(mTitulo);
							vm.activo = vm.titulos.length - 1;
						}
					}
					/**
					 * Maneja como se muestran las vistas.
					 */
					vm.presentarVista = function($scope, vm, response) {
						vm.vista = response.data.vista;
						vm.modelo = response.data.modelo;
						// vm.estado = 'mostrarTipoPermiso';
						vm.presentarTitulo(response.data.modelo.titulo);
						llamarDisabled();

						if (vm.paso == 10) {
							vm.confirmarDatosForm = true;
						}

					}
					
					
					vm.presentarVistanoconformepadres = function($scope, vm, response) {
						vm.vista = response.data.vista;
						vm.modelo = response.data.modelo;
						vm.presentarTitulo(response.data.modelo.titulo);
						console.log("presentarVistaNOCONFORME");
						llamarDisabled();
					}
					
					vm.presentarVistaMadre = function($scope, vm, response) {
						vm.vista = response.data.vista;
						vm.modelo = response.data.modelo;
						vm.presentarTitulo(response.data.modelo.titulo);
						console.log("presentarVistaMadre");
						vm.verificarMadre();
						llamarDisabled();
					}

					vm.presentarVistaPadre = function($scope, vm, response) {
						vm.vista = response.data.vista;
						vm.modelo = response.data.modelo;
						vm.presentarTitulo(response.data.modelo.titulo);
						console.log("presentarVistaPadre");
						vm.verificarPadre();
						llamarDisabled();
					}

					vm.onKeyDown = function(event, validacion, id) {
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

					vm.onBlur = function() {
						vm.errorTecla[id] = false;
					}

					/**
					 * llamadaAjax
					 */
					llamadaAjax = function($http, $scope, vm, url, datos,
							accionSatisfactoria, metodo) {
						$http({
							method : metodo,
							url : url,
							data : JSON.stringify(datos)
						}).then(function successCallback(response) {
							vm.vista = response.data.vista;
							vm.modelo = response.data.modelo;
							llamarDisabled();
							console.log(vm.modelo);
							vm.presentarTitulo(response.data.modelo.titulo);
							console.log(vm.modelo);
						}, function errorCallback(response) {
							console.log("error: " + response.data);
						});
					}

					
					llamadaAjax2 = function($http, $scope, vm, url, datos, accionSatisfactoria, metodo){
						datos.ruta = 'http://localhost:8080' + url;
					 $http({
					  method: metodo,
					  url: '/web-generales/direccionesControlador',
					  data: angular.toJson(datos)//JSON.stringify(datos)
					 }).then(function successCallback(response) {
					  accionSatisfactoria($scope, vm, response);
					  llamarDisabled();
					  vm.presentarTitulo(response.data.modelo.titulo);
					 }, function errorCallback(response) {
					  console.log("error: "+response.data);
					 });
					}
					
					vm.funcion = function(parametro) {
						console.log("funcion de conforme o no comforme");
						if (parametro == "conforme") {
							vm.abrirModalConforme();
							console.log("entro conforme");
						} else if (parametro == "noConforme") {
							vm.abrirModalNoConforme();
							console.log("entro no conforme");
						}
					}
					/**
					 * Modales...
					 */
					vm.imprimirborrador = function() {
						console.log("imprimir borrador");
						vm.paso++;
						vm.mostrarImprimir=true;
						llamadaAjax2($http, $scope, vm, vm.rutas[vm.paso].ruta,
								vm.modelo, vm.rutas[vm.paso].funcion,
								vm.rutas[vm.paso].metodo);

					}
					
					vm.noImprimirBorrador = function() {
						vm.paso++;
						vm.mostrarImprimir=false;
						vm.siguiente();

					}
					vm.abrirmodalConfirmar = function() {
						presentarModal($scope, $uibModal, vm.okRespuesta,
								'¿Se confirma la impresión?',
								$rootScope.tituloWizard, 's');
						vm.impreso = false;
					}
					vm.okRespuesta = function() {
						llamadaAjax($http, $scope, vm, vm.rutas[vm.paso].ruta,
								vm.modelo, vm.rutas[vm.paso].funcion,
								vm.rutas[vm.paso].metodo);
					}
					vm.imprimir = function() {
						vm.windowPrintBrowser();
						presentarModal($scope, $uibModal, vm.okF,
								'¿La impresión es satisfactoria?',
								$rootScope.tituloWizard, 's');
					}
					vm.okF = function() {
						vm.impreso=true;
						vm.abrirmodalConfirmar();
						console.log("entroooo");
						vm.impreso = true;
					}
					vm.abrirModalConforme = function() {
						presentarModal($scope, $uibModal, vm.okConforme,
								'Verificación de acta exitosa',
								$rootScope.tituloWizard, 'co');
					}
					vm.abrirModalNoConforme = function() {
						presentarModal($scope, $uibModal, vm.noConforme,
								'Verificación del acta no conforme',
								$rootScope.tituloWizard, 'co');
					}
					vm.okConforme = function() {
						console.log("llamada ajax cambiando estado");
						llamadaAjax($http, $scope, vm,
						'/web-nacimiento/actualizarParticipanteActa', vm.modelo, vm.f, "POST");
						llamadaAjax($http, $scope, vm,
								'/web-nacimiento/actualizarEstado', vm.modelo,
								vm.finalizar, "POST");
						$rootScope.cancelar1();
					}
				//---------------------pantalla observacion------//
					vm.noConforme = function() {
						console.log("----------antes de decrementar ----------" + vm.paso);
						vm.paso = -2;
						console.log("----------decrementando ---------- " + vm.paso);
						vm.confirmarDatosForm = false;
//						vm.paso++;
						
						
						
						console.log("----------vm.paso no confolme JEAN----------" + vm.paso);
						llamadaAjax($http, $scope, vm,
								'/web-nacimiento/verObservacionesRADOP', vm.modelo,
								vm.presentarVista, "POST");
						
						//$rootScope.cancelar1();
					}
//					vm.funcionNoConforme = function() {
//						llamadaAjax($http, $scope, vm,
//								'/web-nacimiento/actualizarEstado', vm.modelo,
//								vm.finalizar, "POST");
//						$rootScope.cancelar1();
//					}
//					$scope.sigo;
					vm.f = function($scope, vm, response){
						
						$scope.sigo = response;
						console.log($scope.sigo);
					}
					vm.finalizar = function() {
						// vm.cancelar();
						$rootScope.cancelar1();
					}

					vm.confirmarSalidaModulo = function() {
						presentarModal($scope, $uibModal, vm.salirModulo,
								'¿Desea cancelar la solicitud?',
								$rootScope.tituloWizard, 's');
					}
					vm.salirModulo = function() {
						$rootScope.cancelar1();
					}

					/**
					 * Carga los datos iniciales del proceso
					 */
					vm.rutas = [ {
						ruta : '/web-nacimiento/iniciarTramiteRADOP',
						funcion : vm.presentarVista,
						metodo : 'POST'
					}, {
						ruta : '/web-nacimiento/RADOP_cert_med',
						funcion : vm.presentarVista,
						metodo : 'POST'
					}, {
						ruta : '/web-nacimiento/RADOP_datos_madre',
						funcion : vm.presentarVistaMadre,
						metodo : 'POST'
					}, {
						ruta : '/web-nacimiento/RADOP_datos_padre',
						funcion : vm.presentarVistaPadre,
						metodo : 'POST'
					}, {
						ruta : '/web-nacimiento/RADOP_datos_testigos',
						funcion : vm.presentarVista,
						metodo : 'POST'
					}, {
						ruta : '/web-nacimiento/RADOP_inscrip_judicial',
						funcion : vm.presentarVista,
						metodo : 'POST'
					}, {
						ruta : '/web-nacimiento/RADOP_especiales',
						funcion : vm.presentarVista,
						metodo : 'POST'
					}, {
						ruta : '/web-nacimiento/RADOP_documentos',
						funcion : vm.presentarVista,
						metodo : 'POST'
					}, {
						ruta : '/web-nacimiento/RADOP_vistaPrevia',
						funcion : vm.presentarVista,
						metodo : 'POST'
					}, {
						ruta : '/web-nacimiento/RADOP_imprimirActa',
						funcion : vm.presentarVista,
						metodo : 'POST'
					}, {
						ruta : '/web-nacimiento/RADOP_vrfPadres',
						funcion : vm.presentarVista,
						metodo : 'POST'
					},
					];

					// Datos a ser enviados al momento de cargar el modal
					vm.modelo = {
						id : $rootScope.objectSelected.numeroSolicitud,
						paso : 0,
						estatu : $rootScope.objectSelected.codigoEstadoSolicitud,
						CM:{
							segundoNombre:null,
							segundoApellido:null,
						},
						IDJ:{
							segundoNombre:null,
							segundoApellido:null,
						}
					};
					llamadaAjax2($http, $scope, vm, vm.rutas[vm.paso].ruta,
							vm.modelo, vm.rutas[vm.paso].funcion,
							vm.rutas[vm.paso].metodo);

					console.log("*****RUTA ARREGLO **** " + vm.rutas[0].ruta);
					console.log("*****RUTA STATU **** " + vm.modelo.id);
					vm.siguiente = function() {
						// guarda en la pila la vista actual
						vm.vistas.push(vm.vista);
						vm.modelos.push(vm.modelo);
						console.log("num de paso" + vm.paso);
						 
						 if (vm.paso===6) {
							console.log("llegamos al paso 1111111 vamos a Drools");
							llamadaAjax($http, $scope, vm,
							'/web-nacimiento/RADOP_consultarDrools', vm.modelo, vm.drools, 'POST');
							console.log(vm.modelo);
							
							///return;
						}
						if(vm.paso == -2){
							 vm.paso=vm.paso+2;
							 llamadaAjax($http, $scope, vm,
										'/web-nacimiento/RADOP_NoConformePadres', vm.modelo,
										vm.presentarVistanoconformepadres, "POST");
							 return;
						 } 

						if (vm.paso === 8) {
							vm.abrirModalImpresion();
							return;
						}
						if (vm.confirmarDatosForm) {
							console.log("vm.modelo.permiso1"
									+ vm.modelo.permiso1);
							vm.funcion(vm.modelo.permiso1);
							return;
						}
//						if (vm.impreso) {
//							vm.abrirmodalConfirmar();
//						}
						vm.paso++;
						llamadaAjax2($http, $scope, vm, vm.rutas[vm.paso].ruta,
								vm.modelo, vm.rutas[vm.paso].funcion,
								vm.rutas[vm.paso].metodo);
					}

					//	  
					// if(vm.paso==5 ){
					// vm.actualizarEstado();
					// return;
					// }
					// llamadaAjax($http, $scope, vm, vm.rutas[vm.paso].ruta,
					// vm.modelo, vm.rutas[vm.paso].funcion,
					// vm.rutas[vm.paso].metodo);
					// }
					//	 
					// vm.actualizarEstado = function(){
					// llamadaAjax($http, $scope, vm,
					// '/web-nacimiento/actualizarEstado', vm.modelo,
					// vm.retortno, "POST");
					// }
					// vm.retorno = function(){
					// console.log("retorno!!!");
					// }

					vm.regresar = function() {
						vm.vista = vm.vistas[vm.vistas.length - 1];
						vm.modelo = vm.modelos[vm.modelos.length - 1];
						vm.modelos.splice(vm.modelos.length - 1, 1);
						vm.vistas.splice(vm.vistas.length - 1, 1);
						vm.paso--;
						if (vm.activo < vm.titulos.length) {
							vm.titulos.splice(vm.activo + 1, vm.titulos.length
									- vm.activo - 1);
						}
						vm.activo = vm.titulos.indexOf(vm.modelo.titulo);

					}

					vm.cancel = function() {
						vm.confirmarSalidaModulo();
						// $rootScope.cancelar1();
					}

					vm.cancelar = function() {
						// $rootScope.cancelar1();
						vm.confirmarSalidaModulo();
					}

					// Combo de Ocupaciones
					$scope.buscarOcupacion = function() {
						$http({
							method : 'GET',
							url : '/web-nacimiento/consultarOcupacion'
						}).then(function successCallback(ocupacion) {
							// /console.log(ocupacion.data);
							$scope.ocupaciones = ocupacion.data;
						}, function errorCallback(error) {
							console.log("error: " + error.data);
						});
					}
					$scope.buscarOcupacion();

					// /Nacionalidad condicionada por el tipo de Doc
					$scope.buscarNacionalidad = function() {
						$http({
							method : 'GET',
							url : '/web-nacimiento/cosultarNacionalidad'
						}).then(function successCallback(nacionalidad) {
							// console.log(nacionalidad.data);
							$scope.nacionalidades = nacionalidad.data;
						}, function errorCallback(error) {
							console.log("error: " + error.data);
						});
					}

					$scope.buscarNacionalidad();

					// Combo comunidad indigenas
					$scope.buscarComunidades = function() {
						$http(
								{
									method : 'GET',
									url : '/web-nacimiento/consultarComunidadIndigenaTodos'
								}).then(function successCallback(comunidad) {
							// /console.log(ocupacion.data);
							$scope.comunidades = comunidad.data;
						}, function errorCallback(error) {
							console.log("error: " + error.data);
						});
					}
					$scope.buscarComunidades();

					llamarDisabled = function() {
						// ///////////MAM
						if (vm.modelo['MAM'] != null) {
							if (vm.modelo['MAM'].primerNombre != null) {
								$scope.disabled = true;
							} else {
								$scope.disabled = false;
							}
							if (vm.modelo['MAM'].segundoNombre != null) {
								$scope.disabled = true;
							} else {
								$scope.disabled = false;
							}
							if (vm.modelo['MAM'].primerApellido != null) {
								$scope.disabled = true;
							} else {
								$scope.disabled = false;
							}
							if (vm.modelo['MAM'].segundoApellido != null) {
								$scope.disabled = true;
							} else {
								$scope.disabled = false;
							}
							if (vm.modelo['MAM'].documentoIdentidad[0].numero != null) {
								// console.log(vm.modelo['MAM'].documentoIdentidad[0].numero);
								$scope.disabled = true;
							} else {
								$scope.disabled = false;
							}
							if (vm.modelo['MAM'].documentoIdentidad[0].tipoDocumento.nombre != null) {
								// console.log(vm.modelo['MAM'].documentoIdentidad[0].tipoDocumento.nombre);
								$scope.disabled = true;
							} else {
								$scope.disabled = false;
							}
						}
						// ///////////ADO
						if (vm.modelo['ADO'] != null) {
							$scope.setApellidosAdo();
							
							if (vm.modelo['ADO'].primerNombre != null) {
								$scope.disabled1 = true;
								// console.log(vm.modelo['ADO'].primerNombre);
							} else {
								$scope.disabled1 = false;
							}
							if (vm.modelo['ADO'].segundoNombre != null) {
								$scope.disabled1 = true;
								// console.log(vm.modelo['ADO'].segundoNombre);
							} else {
								$scope.disabled1 = false;
							}
							if (vm.modelo['ADO'].primerApellido != null) {
								$scope.disabled1 = true;
							} else {
								$scope.disabled1 = false;
							}
							if (vm.modelo['ADO'].segundoApellido != null) {
								$scope.disabled1 = true;
							} else {
								$scope.disabled1 = false;
							}
							if (vm.modelo['ADO'].sexo != null) {
								$scope.disabled1 = true;
							} else {
								$scope.disabled1 = false;
							}
							if (vm.modelo['ADO'].documentoIdentidad != null) {
								$scope.setCedulaAdop();
								if(vm.modelo['ADO'].documentoIdentidad[0].numero!=null && vm.modelo['ADO'].documentoIdentidad[0].tipoDocumento.nombre!=null ){
									$scope.disabled1 = true;
								}else{
									$scope.disabled1 = false;
								}
								}else{
									console.log("**************cedula false...****************");
								$scope.disabled1 = true;
							}
//							if (vm.modelo['ADO'].documentoIdentidad != null) {
//								// console.log(vm.modelo['ADO'].documentoIdentidad[0].tipoDocumento.nombre);
//								$scope.disabled1 = true;
//							} else {
//								$scope.disabled1 = false;
//							}
						}

						$scope.onKeyDownAlfa = function(event, validacion, id) {
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

						$scope.onKeyDownCementerio = function(event, validacion, id) {
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

						$scope.onKeyDownNumerico = function(event, validacion, id) {
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

						$scope.onBlur = function() {
							$scope.errorTecla[id] = false;
						}


						// //PAP
						if (vm.modelo['PAP'] != null) {
							if (vm.modelo['PAP'].primerNombre != null) {
								$scope.disabled2 = true;
								// console.log(vm.modelo['PAP'].primerNombre);
							} else {
								$scope.disabled2 = false;
							}
							if (vm.modelo['PAP'].segundoNombre != null) {
								$scope.disabled2 = true;
								// console.log(vm.modelo['PAP'].segundoNombre);
							} else {
								$scope.disabled2 = false;
							}
							if (vm.modelo['PAP'].primerApellido != null) {
								$scope.disabled2 = true;
							} else {
								$scope.disabled2 = false;
							}
							if (vm.modelo['PAP'].segundoApellido != null) {
								$scope.disabled2 = true;
							} else {
								$scope.disabled2 = false;
							}
							if (vm.modelo['PAP'].documentoIdentidad[0].numero != null) {
								// console.log(vm.modelo['PAP'].documentoIdentidad[0].numero);
								$scope.disabled2 = true;
							} else {
								$scope.disabled2 = false;
							}
							if (vm.modelo['PAP'].documentoIdentidad[0].tipoDocumento.nombre != null) {
								// console.log(vm.modelo['PAP'].documentoIdentidad[0].tipoDocumento.nombre);
								$scope.disabled2 = true;
							} else {
								$scope.disabled2 = false;
							}
						}

						// //TA1
						if (vm.modelo['TA1'] != null) {
							if (vm.modelo['TA1'].primerNombre != null) {
								$scope.disabled3 = true;
								// console.log(vm.modelo['PAP'].primerNombre);
							} else {
								$scope.disabled3 = false;
							}
							if (vm.modelo['TA1'].segundoNombre != null) {
								$scope.disabled3 = true;
								// console.log(vm.modelo['PAP'].segundoNombre);
							} else {
								$scope.disabled3 = false;
							}
							if (vm.modelo['TA1'].primerApellido != null) {
								$scope.disabled3 = true;
							} else {
								$scope.disabled3 = false;
							}
							if (vm.modelo['TA1'].segundoApellido != null) {
								$scope.disabled3 = true;
							} else {
								$scope.disabled3 = false;
							}
							if (vm.modelo['TA1'].documentoIdentidad[0].numero != null) {
								// console.log(vm.modelo['PAP'].documentoIdentidad[0].numero);
								$scope.disabled3 = true;
							} else {
								$scope.disabled3 = false;
							}
							if (vm.modelo['TA1'].documentoIdentidad[0].tipoDocumento.nombre != null) {
								// console.log(vm.modelo['PAP'].documentoIdentidad[0].tipoDocumento.nombre);
								$scope.disabled3 = true;
							} else {
								$scope.disabled3 = false;
							}
						}

						// //TA2
						if (vm.modelo['TA2'] != null) {
							if (vm.modelo['TA2'].primerNombre != null) {
								$scope.disabled4 = true;
								// console.log(vm.modelo['PAP'].primerNombre);
							} else {
								$scope.disabled4 = false;
							}
							if (vm.modelo['TA2'].segundoNombre != null) {
								$scope.disabled4 = true;
								// console.log(vm.modelo['PAP'].segundoNombre);
							} else {
								$scope.disabled4 = false;
							}
							if (vm.modelo['TA2'].primerApellido != null) {
								$scope.disabled4 = true;
							} else {
								$scope.disabled4 = false;
							}
							if (vm.modelo['TA2'].segundoApellido != null) {
								$scope.disabled4 = true;
							} else {
								$scope.disabled4 = false;
							}
							if (vm.modelo['TA2'].documentoIdentidad[0].numero != null) {
								// console.log(vm.modelo['PAP'].documentoIdentidad[0].numero);
								$scope.disabled4 = true;
							} else {
								$scope.disabled4 = false;
							}
							if (vm.modelo['TA2'].documentoIdentidad[0].tipoDocumento.nombre != null) {
								// console.log(vm.modelo['PAP'].documentoIdentidad[0].tipoDocumento.nombre);
								$scope.disabled4 = true;
							} else {
								$scope.disabled4 = false;
							}
						}

						// //////////********EDAD!!!!
						if (vm.modelo['MAM'] != null) {
							var fnacM = new Date(
									vm.modelo['MAM'].fechaNacimiento)
							console.log(fnacM);
							var hoy = Date.now()
							edM = parseInt((hoy - fnacM) / 365 / 24 / 60 / 60
									/ 1000)
							vm.modelo.MAMEdad = edM
							if (vm.modelo.MAMEdad != null) {
								$scope.disabled = true;
							} else {
								$scope.disabled = false;
							}
							 console.log("MAM"+edM);
							 console.log(vm.modelo.MAMEdad);
						}
						if (vm.modelo['PAP'] != null) {
							var fnacP = new Date(
									vm.modelo['PAP'].fechaNacimiento)
							edP = parseInt((hoy - fnacP) / 365 / 24 / 60 / 60
									/ 1000)
							vm.modelo.PAPEdad = edP
							if (vm.modelo.PAPEdad != null) {
								$scope.disabled2 = true;
							} else {
								$scope.disabled2 = false;
							}
						}
						if (vm.modelo['TA1'] != null) {
							var fnacA = new Date(
									vm.modelo['TA1'].fechaNacimiento)
							edT1 = parseInt((hoy - fnacA) / 365 / 24 / 60 / 60
									/ 1000)
							vm.modelo.TA1Edad = edT1
							if (vm.modelo.TA1Edad != null) {
								$scope.disabled3 = true;
							} else {
								$scope.disabled3 = false;
							}
						}
						if (vm.modelo['TA2'] != null) {
							var fnacA = new Date(
									vm.modelo['TA2'].fechaNacimiento)
							edT2 = parseInt((hoy - fnacA) / 365 / 24 / 60 / 60
									/ 1000)
							vm.modelo.TA2Edad = edT2
							if (vm.modelo.TA2Edad != null) {
								$scope.disabled4 = true;
							} else {
								$scope.disabled4 = false;
							}
						}
					}

					// /////////////////Cedula para adoptado
					
					$scope.setApellidosAdo = function() {
						console.log("entrooooooooo a los apellidos")
						var mamap;
						var papap;
						if(vm.modelo['MAM'] != null){
							mamap="presente";
							
						}else{
							mamap="ausente";
						}
					//////////////////////////////////////////////
						if(vm.modelo['PAP'] != null){
							papap="presente";
							
						}else{
							papap="ausente";
						}
						
						if(papap=="presente"){
						vm.modelo['ADO'].primerApellido = vm.modelo['PAP'].primerApellido

						if(mamap=="presente"){
						vm.modelo['ADO'].segundoApellido = vm.modelo['MAM'].primerApellido	
							}else{
								if(vm.modelo['PAP'].segundoApellido!=null){
									vm.modelo['ADO'].segundoApellido = vm.modelo['PAP'].segundoApellido
								}
							}
						}else{
						vm.modelo['ADO'].primerApellido = vm.modelo['MAM'].primerApellido
								if(vm.modelo['MAM'].segundoApellido!=null){
									vm.modelo['ADO'].segundoApellido = vm.modelo['MAM'].segundoApellido
								}
						}
					}
					
				
					$scope.numeroControl = 0;
					$scope.setCedulaAdop = function() {
						if (vm.modelo['ADO'] != null && vm.modelo['ADO'].documentoIdentidad != null 
								&& $scope.numeroControl < 1) {
							$scope.tipoDocumento = vm.modelo['ADO'].documentoIdentidad[0].tipoDocumento.codigo;
							console.log($scope.tipoDocumento);
							if ($scope.tipoDocumento == 'CED') {
								$scope.cedula = vm.modelo['ADO'].documentoIdentidad[0].numero;
								$scope.respuesta = [];
								$scope.respuesta = $scope.cedula.split("-");
								$scope.num = parseInt($scope.respuesta[1]
										.toString());
								$scope.numeroControl++;

							} else {
								$scope.num = vm.modelo['ADO'].documentoIdentidad[0].numero;
								$scope.numeroControl++;
							}
//							vm.modelo['ADO'].documentoIdentidad[0].numero = $scope.num;
//							console.log($scope.num);
//							return $scope.num;
						}
						vm.modelo['ADO'].documentoIdentidad[0].numero = $scope.num;
						console.log($scope.num);
						return $scope.num;
					}

					$scope.numeroControl1 = 0;
					$scope.setCedulaMad = function() {
						console.log(vm.modelo['MAM']);
						if (vm.modelo['MAM'] != null
								&& $scope.numeroControl1 < 1) {
							$scope.tipoDocumento = vm.modelo['MAM'].documentoIdentidad[0].tipoDocumento.codigo;
							if ($scope.tipoDocumento == 'CED') {
								$scope.cedula = vm.modelo['MAM'].documentoIdentidad[0].numero;
								$scope.respuesta = [];
								$scope.respuesta = $scope.cedula.split("-");
								$scope.numm = parseInt($scope.respuesta[1]
										.toString());
								$scope.numeroControl1++;

							} else {
								$scope.numm = vm.modelo['MAM'].documentoIdentidad[0].numero;
								$scope.numeroControl1++;
							}
						}
						vm.modelo['MAM'].documentoIdentidad[0].numero = $scope.numm;
						console.log($scope.numm);
						return $scope.numm;
					}

					$scope.numeroControl2 = 0;
					$scope.setCedulaPad = function() {
						if (vm.modelo['PAP'] != null
								&& $scope.numeroControl2 < 1) {
							$scope.tipoDocumento = vm.modelo['PAP'].documentoIdentidad[0].tipoDocumento.codigo;
							if ($scope.tipoDocumento == 'CED') {
								$scope.cedula = vm.modelo['PAP'].documentoIdentidad[0].numero;
								$scope.respuesta = [];
								$scope.respuesta = $scope.cedula.split("-");
								$scope.nump = parseInt($scope.respuesta[1]
										.toString());
								$scope.numeroControl2++;

							} else {
								$scope.nump = vm.modelo['PAP'].documentoIdentidad[0].numero;
								$scope.numeroControl2++;
							}
						}
						vm.modelo['PAP'].documentoIdentidad[0].numero = $scope.nump;
						console.log($scope.nump);
						return $scope.nump;
					}

					$scope.numeroControl3 = 0;
					$scope.setCedulaTA1 = function() {
						if (vm.modelo['TA1'] != null
								&& $scope.numeroControl3 < 1) {
							$scope.tipoDocumento = vm.modelo['TA1'].documentoIdentidad[0].tipoDocumento.codigo;
							if ($scope.tipoDocumento == 'CED') {
								$scope.cedula = vm.modelo['TA1'].documentoIdentidad[0].numero;
								$scope.respuesta = [];
								$scope.respuesta = $scope.cedula.split("-");
								$scope.num1 = parseInt($scope.respuesta[1]
										.toString());
								$scope.numeroControl3++;

							} else {
								$scope.num1 = vm.modelo['TA1'].documentoIdentidad[0].numero;
								$scope.numeroControl3++;
							}
						}
						vm.modelo['TA1'].documentoIdentidad[0].numero = $scope.num1;
						console.log($scope.num1);
						return $scope.num1;
					}

					$scope.numeroControl4 = 0;
					$scope.setCedulaTA2 = function() {
						if (vm.modelo['TA2'] != null
								&& $scope.numeroControl4 < 1) {
							$scope.tipoDocumento = vm.modelo['TA2'].documentoIdentidad[0].tipoDocumento.codigo;
							if ($scope.tipoDocumento == 'CED') {
								$scope.cedula = vm.modelo['TA2'].documentoIdentidad[0].numero;
								$scope.respuesta = [];
								$scope.respuesta = $scope.cedula.split("-");
								$scope.num2 = parseInt($scope.respuesta[1]
										.toString());
								$scope.numeroControl4++;

							} else {
								$scope.num2 = vm.modelo['TA2'].documentoIdentidad[0].numero;
								$scope.numeroControl4++;
							}
						}
						vm.modelo['TA2'].documentoIdentidad[0].numero = $scope.num2;
						console.log($scope.num2);
						return $scope.num2;
					}

					$scope.buscarPais = function() {

						$http({
							method : 'POST',
							url : '/web-generales/consultarPaisTodos',
							data : {}
						}).then(function successCallback(pais) {
							console.log(pais.data);
							$scope.paises = pais.data;
							$scope.estados = $scope.paises.estados;
						}, function errorCallback(error) {
							console.log("error: " + error.data);
						});

					}

					$scope.buscarPais();
					// $scope.formulario.estado = null;

					$scope.buscarEstado = function() {
						var nPais = null;
						console.log("si estoy aca------>"+JSON.stringify(vm.modelo['ADO'].direccion[0].pais));
						if (vm.modelo['ADO'].direccion[0].pais == "VENEZUELA") {
							nPais = "VEN";
							$http({
								method : 'POST',
								url : '/web-generales/consultarEstadoPorPais',
								data : {
									codigo : nPais
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
							$scope.disa = true
							vm.modelo['ADO'].direccion[0].estado = null;
							vm.modelo['ADO'].direccion[0].municipio = null;
							vm.modelo['ADO'].direccion[0].parroquia = null;
						}
					}

					$scope.buscarMunicipio = function() {
						var cEstado = null;
						for (var i = 0; i < $scope.estados.length; i++) {
							if ($scope.estados[i].nombre == vm.modelo['ADO'].direccion[0].estado) {
								cEstado = $scope.estados[i].codigo;
								break;
							}

						}
						
						$http({
							method : 'POST',
							url : '/web-generales/consultarMunicipioPorEstado',
							data : {
								codigo : cEstado
							}
						}).then(function successCallback(municipio) {
							console.log(municipio.data);
							$scope.municipios = municipio.data;
							$scope.parroquias = $scope.municipios.parroquias;
						}, function errorCallback(error) {
							console.log("error: " + error.data);
						});

					}

					$scope.buscarParroquia = function() {
						var cMunicipio = null;
						for (var i = 0; i < $scope.municipios.length; i++) {
							if ($scope.municipios[i].nombre == vm.modelo['ADO'].direccion[0].municipio) {
								cMunicipio = $scope.municipios[i].codigo;
								break;
							}

						}
						$http(
								{
									method : 'POST',
									url : '/web-generales/consultarParroquiaPorMunicipio',
									data : {
										codigo : cMunicipio
									}
								}).then(function successCallback(parroquia) {
							console.log(parroquia.data);
							$scope.parroquias = parroquia.data;
							// $scope.parroquias=$scope.municipios.parroquias;
						}, function errorCallback(error) {
							console.log("error: " + error.data);
						});

					}

					$scope.buscarPaisMAM = function() {
						
						$http({
							method : 'POST',
							url : '/web-generales/consultarPaisTodos',
							data : {}
						}).then(function successCallback(pais) {
							console.log(pais.data);
							$scope.paises = pais.data;
							$scope.estados = $scope.paises.estados;
						}, function errorCallback(error) {
							console.log("error: " + error.data);
						});

					}
					$scope.buscarPaisMAM();

					$scope.buscarEstadoMAM = function() {
						var nPaisMAM = null;
						if (vm.modelo['MAM'].direccion[1].pais == "VENEZUELA") {
							nPaisMAM = "VEN";
							$http({
								method : 'POST',
								url : '/web-generales/consultarEstadoPorPais',
								data : {
									codigo : nPaisMAM
								}

							}).then(function successCallback(estado) {
								$scope.disaMAM = false
								console.log(estado.data);

								$scope.estados = estado.data;
								$scope.municipios = $scope.estados.municipios;
							}, function errorCallback(error) {
								console.log("error: " + error.data);
							});
						} else {
							$scope.disaMAM = true
							vm.modelo['MAM'].direccion[1].estado = null;
							vm.modelo['MAM'].direccion[1].municipio = null;
							vm.modelo['MAM'].direccion[1].parroquia = null;
						}
					}

					$scope.buscarMunicipioMAM = function() {
						
						var cEstadoMAM = null;
						for (var i = 0; i < $scope.estados.length; i++) {
							if ($scope.estados[i].nombre == vm.modelo['MAM'].direccion[1].estado) {
								cEstadoMAM = $scope.estados[i].codigo;
								break;
							}

						}
						$http({
							method : 'POST',
							url : '/web-generales/consultarMunicipioPorEstado',
							data : {
								codigo : cEstadoMAM
							}
						}).then(function successCallback(municipio) {
							console.log(municipio.data);
							$scope.municipios = municipio.data;
							$scope.parroquias = $scope.municipios.parroquias;
						}, function errorCallback(error) {
							console.log("error: " + error.data);
						});

					}

					$scope.buscarParroquiaMAM = function() {
						var cMunicipioMAM = null;
						for (var i = 0; i < $scope.municipios.length; i++) {
							if ($scope.municipios[i].nombre == vm.modelo['MAM'].direccion[1].municipio) {
								cMunicipioMAM = $scope.municipios[i].codigo;
								break;
							}

						}
						$http(
								{
									method : 'POST',
									url : '/web-generales/consultarParroquiaPorMunicipio',
									data : {
										codigo : cMunicipioMAM
									}
								}).then(function successCallback(parroquia) {
							console.log(parroquia.data);
							$scope.parroquias = parroquia.data;
							// $scope.parroquias=$scope.municipios.parroquias;
						}, function errorCallback(error) {
							console.log("error: " + error.data);
						});

					}

					$scope.buscarPaisPAP = function() {

						$http({
							method : 'POST',
							url : '/web-generales/consultarPaisTodos',
							data : {}
						}).then(function successCallback(pais) {
							console.log(pais.data);
							$scope.paises = pais.data;
							$scope.estados = $scope.paises.estados;
						}, function errorCallback(error) {
							console.log("error: " + error.data);
						});

					}
					$scope.buscarPaisPAP();

					$scope.buscarEstadoPAP = function() {
						var nPaisPAP = null;
						if (vm.modelo['PAP'].direccion[1].pais == "VENEZUELA") {
							nPaisPAP = "VEN";
							$http({
								method : 'POST',
								url : '/web-generales/consultarEstadoPorPais',
								data : {
									codigo : nPaisPAP
								}

							}).then(function successCallback(estado) {
								$scope.disaPAP = false
								console.log(estado.data);

								$scope.estados = estado.data;
								$scope.municipios = $scope.estados.municipios;
							}, function errorCallback(error) {
								console.log("error: " + error.data);
							});
						} else {
							$scope.disaPAP = true
							vm.modelo['PAP'].direccion[1].estado = null;
							vm.modelo['PAP'].direccion[1].municipio = null;
							vm.modelo['PAP'].direccion[1].parroquia = null;
						}
					}

					$scope.buscarMunicipioPAP = function() {
						var cEstadoPAP = null;
						for (var i = 0; i < $scope.estados.length; i++) {
							if ($scope.estados[i].nombre == vm.modelo['PAP'].direccion[1].estado) {
								cEstadoPAP = $scope.estados[i].codigo;
								break;
							}

						}
						$http({
							method : 'POST',
							url : '/web-generales/consultarMunicipioPorEstado',
							data : {
								codigo : cEstadoPAP
							}
						}).then(function successCallback(municipio) {
							console.log(municipio.data);
							$scope.municipios = municipio.data;
							$scope.parroquias = $scope.municipios.parroquias;
						}, function errorCallback(error) {
							console.log("error: " + error.data);
						});

					}

					$scope.buscarParroquiaPAP = function() {
						var cMunicipioPAP = null;
						for (var i = 0; i < $scope.municipios.length; i++) {
							if ($scope.municipios[i].nombre == vm.modelo['PAP'].direccion[1].municipio) {
								cMunicipioPAP = $scope.municipios[i].codigo;
								break;
							}

						}
						$http(
								{
									method : 'POST',
									url : '/web-generales/consultarParroquiaPorMunicipio',
									data : {
										codigo : cMunicipioPAP
									}
								}).then(function successCallback(parroquia) {
							console.log(parroquia.data);
							$scope.parroquias = parroquia.data;
							// $scope.parroquias=$scope.municipios.parroquias;
						}, function errorCallback(error) {
							console.log("error: " + error.data);
						});

					}
///////////////////////////////////////////////////
					
					$scope.buscarPaisTA1 = function() {

						$http({
							method : 'POST',
							url : '/web-generales/consultarPaisTodos',
							data : {}
						}).then(function successCallback(pais) {
							console.log(pais.data);
							$scope.paises = pais.data;
							$scope.estados = $scope.paises.estados;
						}, function errorCallback(error) {
							console.log("error: " + error.data);
						});

					}
					$scope.buscarPaisTA1();

					$scope.buscarEstadoTA1 = function() {
						var nPaisTA1 = null;
						if (vm.modelo['TA1'].direccion[1].pais == "VENEZUELA") {
							nPaisTA1 = "VEN"
							$http({
								method : 'POST',
								url : '/web-generales/consultarEstadoPorPais',
								data : {
									codigo : nPaisTA1
								}

							}).then(function successCallback(estado) {
								$scope.disaTA1 = false
								console.log(estado.data);

								$scope.estadosTA1 = estado.data;
								$scope.municipiosTA1 = $scope.estadosTA1.municipios;
							}, function errorCallback(error) {
								console.log("error: " + error.data);
							});
						} else {
							$scope.disaTA1 = true
							vm.modelo['TA1'].direccion[1].estado = null;
							vm.modelo['TA1'].direccion[1].municipio = null;
							vm.modelo['TA1'].direccion[1].parroquia = null;
						}
					}

					$scope.buscarMunicipioTA1 = function() {
						var cEstadoTA1 = null;
						for (var i = 0; i < $scope.estados.length; i++) {
							if ($scope.estados[i].nombre == vm.modelo['TA1'].direccion[1].estado) {
								cEstadoTA1 = $scope.estados[i].codigo;
								break;
							}

						}
						$http({
							method : 'POST',
							url : '/web-generales/consultarMunicipioPorEstado',
							data : {
								codigo : cEstadoTA1
							}
						}).then(function successCallback(municipio) {
							console.log(municipio.data);
							$scope.municipiosTA1 = municipio.data;
							$scope.parroquiasTA1 = $scope.municipiosTA1.parroquias;
						}, function errorCallback(error) {
							console.log("error: " + error.data);
						});

					}

					$scope.buscarParroquiaTA1 = function() {
						
						var cMunicipioTA1 = null;
						for (var i = 0; i < $scope.municipios.length; i++) {
							if ($scope.municipios[i].nombre == vm.modelo['TA1'].direccion[1].municipio) {
								cMunicipioTA1 = $scope.municipios[i].codigo;
								break;
							}

						}
						$http(
								{
									method : 'POST',
									url : '/web-generales/consultarParroquiaPorMunicipio',
									data : {
										codigo : cMunicipioTA1
									}
								}).then(function successCallback(parroquia) {
							console.log(parroquia.data);
							$scope.parroquiasTA1 = parroquia.data;
							// $scope.parroquias=$scope.municipios.parroquias;
						}, function errorCallback(error) {
							console.log("error: " + error.data);
						});

					}
					
					
					$scope.buscarPaisTA2 = function() {

						$http({
							method : 'POST',
							url : '/web-generales/consultarPaisTodos',
							data : {}
						}).then(function successCallback(pais) {
							console.log(pais.data);
							$scope.paises = pais.data;
							$scope.estados = $scope.paises.estados;
						}, function errorCallback(error) {
							console.log("error: " + error.data);
						});

					}
					$scope.buscarPaisTA2();

					$scope.buscarEstadoTA2 = function() {
						var nPaisTA2 = null;
						if (vm.modelo['TA2'].direccion[1].pais == "VENEZUELA") {
							nPaisTA2 = "VEN"
							$http({
								method : 'POST',
								url : '/web-generales/consultarEstadoPorPais',
								data : {
									codigo : nPaisTA2
								}

							}).then(function successCallback(estado) {
								$scope.disaTA2 = false
								console.log(estado.data);

								$scope.estadosTA2 = estado.data;
								$scope.municipiosTA2 = $scope.estadosTA2.municipios;
							}, function errorCallback(error) {
								console.log("error: " + error.data);
							});
						} else {
							$scope.disaTA2 = true
							vm.modelo['TA2'].direccion[1].estado = null;
							vm.modelo['TA2'].direccion[1].municipio = null;
							vm.modelo['TA2'].direccion[1].parroquia = null;
						}
					}

					$scope.buscarMunicipioTA2 = function() {
						var cEstadoTA2 = null;
						for (var i = 0; i < $scope.estados.length; i++) {
							if ($scope.estados[i].nombre == vm.modelo['TA2'].direccion[1].estado) {
								cEstadoTA2 = $scope.estados[i].codigo;
								break;
							}

						}
						$http({
							method : 'POST',
							url : '/web-generales/consultarMunicipioPorEstado',
							data : {
								codigo : cEstadoTA2
							}
						}).then(function successCallback(municipio) {
							console.log(municipio.data);
							$scope.municipiosTA2 = municipio.data;
							$scope.parroquiasTA2 = $scope.municipiosTA2.parroquias;
						}, function errorCallback(error) {
							console.log("error: " + error.data);
						});

					}

					$scope.buscarParroquiaTA2 = function() {
						var cMunicipioTA2 = null;
						for (var i = 0; i < $scope.municipios.length; i++) {
							if ($scope.municipios[i].nombre == vm.modelo['TA2'].direccion[1].municipio) {
								cMunicipioTA2 = $scope.municipios[i].codigo;
								break;
							}

						}
						$http(
								{
									method : 'POST',
									url : '/web-generales/consultarParroquiaPorMunicipio',
									data : {
										codigo : cMunicipioTA2
									}
								}).then(function successCallback(parroquia) {
							console.log(parroquia.data);
							$scope.parroquiasTA2 = parroquia.data;
							// $scope.parroquias=$scope.municipios.parroquias;
						}, function errorCallback(error) {
							console.log("error: " + error.data);
						});

					}

					
					// ---------funciones internas ------------ //
					$scope.mostrarCampoObservacion = function() {
						
						$scope.campoObservacion = true;
						// vm.abrirModalNoConforme();
						vm.confirmarDatosForm = true;
					}

					$scope.ocultarCampoObservacion = function() {

						$scope.campoObservacion = false;
						// vm.abrirModalConforme();
						vm.confirmarDatosForm = true;
					}
					
					$scope.ocultarCampoObservacion = function() {

						$scope.campoObservacion = false;
						// vm.abrirModalConforme();
						vm.confirmarDatosForm = true;
					}
					
					$scope.limpiarIDJ = function() {
						
					vm.modelo['IDJ'].documentoIdentidad[0].numero = null;
	
					}
					
					$scope.limpiarCM = function() {

					vm.modelo['CM'].documentoIdentidad[0].numero = null;
					}
					
					
					
					
					
					/**
					Validar si vienen solo uno de los padres adoptantes
					
					*/
					vm.verificarPadre = function(){
						if(vm.modelo['PAP']== null && vm.modelo['PAD']== null){
							console.log("Estoy en padre");
							presentarModal($scope,$uibModal, vm.siguiente, 'Los datos del padre no aplican',$rootScope.tituloWizard,'a');
						}
					}
					vm.pasarSiguientePadre = function(){
						console.log("confirmado");
						vm.paso++;
						llamadaAjax2($http, $scope, vm, vm.rutas[vm.paso].ruta,vm.modelo, vm.rutas[vm.paso].funcion,vm.rutas[vm.paso].metodo);	
					}
					
					vm.verificarMadre = function(){
						if(vm.modelo['MAM']== null && vm.modelo['MAD']== null){
							console.log("Estoy en madre");
							presentarModal($scope,$uibModal, vm.siguiente(), 'Los datos de la madre no aplican',$rootScope.tituloWizard,'a');
						}
					}
					
					vm.pasarSiguienteMadre = function(){
						console.log("confirmado");
						vm.paso++;
						llamadaAjax2($http, $scope, vm, vm.rutas[vm.paso].ruta,vm.modelo, vm.rutas[vm.paso].funcion,vm.rutas[vm.paso].metodo);	
					}
					
					$scope.validacioNumCert = function(){
						var str = vm.modelo['CM'].numCert;
						var n = str.length;
						if(n==8)
							$scope.validarCert();
					} 
					
					$scope.validarCert= function(){
						console.log("Num Certf "+vm.modelo['CM'].numCert);
						var numeroCertificadoNac = vm.modelo['CM'].numCert;
						console.log(" ========= numeroCertificadoNac ======== "+numeroCertificadoNac);
						  $http({
						   method: 'GET',
						   url: '/web-nacimiento/validarCertificadoMedico/'+numeroCertificadoNac
						  }).then(function successCallback(numeroCertificadoNac) {

						   console.log(numeroCertificadoNac.data);
						   $scope.certificado = numeroCertificadoNac.data;
						   if($scope.certificado == true){   
						    vm.modelo.variableCertif =  "true";
						    $scope.campo = "false";
						    //vm.modalNumeroCertificado();  
						   $scope.campoMensaje1 = true;
						   }else if($scope.certificado == false){
						    vm.modelo.variableCertif =  "false";
						    $scope.campo = "false";
						   //vm.estado = 'integracionHtml2';
						   // $scope.campoMensaje2 = false;
						    $scope.campoMensaje1 = false;
						   }
						  }, function errorCallback(error) {
						   console.log("error: "+error.data);
						  }); 
						 }
					
					
					
					

					vm.abrirModalImpresion =function(){

						
						var modalInstance = $uibModal.open({
							templateUrl: '/web-generales/pages/templates/imprimir/modalConfirmacion.html',
							controller: function($scope,$uibModalInstance){
								$scope.tipo = 's';
								 $scope.mensaje='¿Usted desea imprimir el  acta en formato borrador?';
								$scope.titulo = $scope.tituloWizard;;
							
								 $scope.cancel = function () {
								        $uibModalInstance.dismiss('cancel');
								        vm.noImprimirBorrador();
								    };
								    $scope.ok = function () {
								    	    $uibModalInstance.close(true);
								    	    vm.imprimirborrador();
								    };
								    $scope.cancelar = function () {
							    	    $uibModalInstance.close(true);
							    	    vm.noImprimirBorrador();
								    }
							}
						});
						modalInstance.result.then(function () {

							
							

						}, function () {
							console.log("************VENGO DE CANCELAR");
							
						});
						
					}
					
					vm.windowPrintBrowser = function () {

				        document.getElementById("plugin").focus();

				        document.getElementById("plugin").contentWindow.print();


				    }
					
				

				});

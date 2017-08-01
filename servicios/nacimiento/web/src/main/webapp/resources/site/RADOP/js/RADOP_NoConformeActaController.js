App
		.controller(
				'RADOP_NoConformeActaController',
				function($scope, $http, $rootScope, $uibModal) {
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
					// /Variables a usar
					vm.padres = false;
					vm.EnviarOficio = false;
					vm.hijos = [];
					// vm.modelo.hijos = [1];
					vm.hijo = 1;
					hijo = {};
					vm.errorTecla = {};
					$scope.datosHijos = true;
					$scope.disabled = false;
					$scope.disabled2 = false;
					$scope.diasabled = false;
					$scope.di = false;

					// validaciones de patrones
					$scope.vPatron = {
						AlfaNumerico : /^[A-Za-z0-9]*$/,
						Alfa : /^[a-zA-Z]*$/,
						FormatoNombres: /^((([a-zA-Z ñÑáéíóúÁÉÍÓÚäëïöüÄËÏÖÜ]))|([-'])\1?(?!\4))+$/,
						FormatoCementerio: /^((([a-zA-Z0-9 ñÑáéíóúÁÉÍÓÚäëïöüÄËÏÖÜ]))|([-'])\1?(?!\4))+$/,
						AlfaText : 'Este campo solo permite letras, di&eacute;resis, ap&oacute;strofes, guiones y acentos',
						Pcementerio : 'Este campo solo permite letras, di&eacute;resis, ap&oacute;strofes, guiones, acentos y n&uacute;meros',
						Fnumero : 'Este campo admite solo n&uacute;meros',
						Facta : 'Este campo admite solo n&uacute;meros y tiene un minimo y maximo de 20 caracteres',
						Ffolio : 'Este campo admite solo n&uacute;meros y tiene un minimo y maximo de 5 caracteres',
						Ftomo : 'Este campo admite solo n&uacute;meros y tiene un minimo y maximo de 2 caracteres',
						TipoCed : /^[V|v|E|e]/,
						fechas : 'Debe ingresar en el siguiente orden (Dia >> Mes >> Año)',
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

//					
					vm.seteo = function(){
						console.log("VAMOS A CALMARNOS");
						if (vm.paso == 1) {
							console.log("ADOPTADO A VER");
							if (vm.modelo['ADO'].direccion[0].pais == "VENEZUELA"
									&& vm.modelo['ADO'].direccion[0].estado != null
									&& vm.modelo['ADO'].direccion[0].municipio != null) {

								$scope.funcionSeteoDataComboADO();
							} else {
								console.log("----NOOOO");
								// vm.onKeydown();
							}
						}
						if (vm.paso == 3) {
							console.log("MAMAAAAA JEAN");
							if (vm.modelo['MAM'].direccion[1].pais == "VENEZUELA"
									&& vm.modelo['MAM'].direccion[1].estado != null
									&& vm.modelo['MAM'].direccion[1].municipio != null
									&& vm.modelo['MAM'].ocupacion != null) {

								$scope.funcionSeteoDataComboMAM();
							} else {
								console.log("----NOOOO");
								// vm.onKeydown();
							}
						}
						if (vm.paso == 4) {
							console.log("PAPAAAAAA");
							if (vm.modelo['PAP'].direccion[1].pais == "VENEZUELA"
									&& vm.modelo['PAP'].direccion[1].estado != null
									&& vm.modelo['PAP'].direccion[1].municipio != null
									&& vm.modelo['PAP'].ocupacion != null) {

								$scope.funcionSeteoDataComboPAP();
							} else {
								console.log("----NOOOO");
								// vm.onKeydown();
							}
						}

						if (vm.paso == 5) {
							console.log("TA1 & TA2");
							if (vm.modelo['TA1'].direccion[1].pais == "VENEZUELA"
									&& vm.modelo['TA1'].direccion[1].estado != null
									&& vm.modelo['TA1'].direccion[1].municipio != null
									&& vm.modelo['TA1'].ocupacion != null
									&& vm.modelo['TA2'].direccion[1].pais == "VENEZUELA"
									&& vm.modelo['TA2'].direccion[1].estado != null
									&& vm.modelo['TA2'].direccion[1].municipio != null
									&& vm.modelo['TA2'].ocupacion != null) {

								$scope.funcionSeteoDataComboTA1();
								$scope.funcionSeteoDataComboTA2();
							} else {
								console.log("----NOOOO");
								// vm.onKeydown();
							}
						}
					}
					vm.presentarVista = function($scope, vm, response) {
						console.log("PASO ADOPTADO--------------- "+vm.paso);
						vm.vista = response.data.vista;
						vm.modelo = response.data.modelo;
						// vm.estado = 'mostrarTipoPermiso';
						vm.presentarTitulo(response.data.modelo.titulo);
						vm.seteo();
						
//						if (vm.paso == 1) {
//							console.log("ADOPTADOOOOOOOOO");
//							if (vm.modelo['ADO'].direccion[0].pais.codigo == "VEN"
//									&& vm.modelo['ADO'].direccion[0].estado != null
//									&& vm.modelo['ADO'].direccion[0].municipio != null) {
//
//								$scope.funcionSeteoDataComboADO();
//							} else {
//								console.log("----NOOOO");
//								// vm.onKeydown();
//							}
//						}
//						if (vm.paso == 3) {
//							console.log("MAMAAAAA JEAN");
//							if (vm.modelo['MAM'].direccion[1].pais.codigo == "VEN"
//									&& vm.modelo['MAM'].direccion[1].estado != null
//									&& vm.modelo['MAM'].direccion[1].municipio != null
//									&& vm.modelo['MAM'].ocupacion != null) {
//
//								$scope.funcionSeteoDataComboMAM();
//							} else {
//								console.log("----NOOOO");
//								// vm.onKeydown();
//							}
//						}
//						if (vm.paso == 4) {
//							console.log("PAPAAAAAA");
//							if (vm.modelo['PAP'].direccion[1].pais.codigo == "VEN"
//									&& vm.modelo['PAP'].direccion[1].estado != null
//									&& vm.modelo['PAP'].direccion[1].municipio != null
//									&& vm.modelo['PAP'].ocupacion != null) {
//
//								$scope.funcionSeteoDataComboPAP();
//							} else {
//								console.log("----NOOOO");
//								// vm.onKeydown();
//							}
//						}
//
//						if (vm.paso == 5) {
//							console.log("TA1 & TA2");
//							if (vm.modelo['TA1'].direccion[1].pais.codigo == "VEN"
//									&& vm.modelo['TA1'].direccion[1].estado != null
//									&& vm.modelo['TA1'].direccion[1].municipio != null
//									&& vm.modelo['TA1'].ocupacion != null
//									&& vm.modelo['TA2'].direccion[1].pais.codigo == "VEN"
//									&& vm.modelo['TA2'].direccion[1].estado != null
//									&& vm.modelo['TA2'].direccion[1].municipio != null
//									&& vm.modelo['TA2'].ocupacion != null) {
//
//								$scope.funcionSeteoDataComboTA1();
//								$scope.funcionSeteoDataComboTA2();
//							} else {
//								console.log("----NOOOO");
//								// vm.onKeydown();
//							}
//						}

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
							vm.presentarTitulo(response.data.modelo.titulo);
							console.log(vm.modelo);
						}, function errorCallback(response) {
							console.log("error: " + response.data);
						});
					}

					/**
					 * Carga los datos iniciales del proceso
					 */
					vm.rutas = [ {
						ruta : '/web-nacimiento/iniciarTramiteRADOP',
						funcion : vm.presentarVista,
						metodo : 'POST'
					},{
						ruta : '/web-nacimiento/RADOP_NC_datos_adop',
						funcion : vm.presentarVista,
						metodo : 'POST'
					},{
						ruta : '/web-nacimiento/RADOP_cert_med',
						funcion : vm.presentarVista,
						metodo : 'POST'
					}, {
						ruta : '/web-nacimiento/RADOP_NC_datos_madre',
						funcion : vm.presentarVista,
						metodo : 'POST'
					}, {
						ruta : '/web-nacimiento/RADOP_NC_datos_padre',
						funcion : vm.presentarVista,
						metodo : 'POST'
					}, {
						ruta : '/web-nacimiento/RADOP_NC_datos_testigos',
						funcion : vm.presentarVista,
						metodo : 'POST'
					}, {
						ruta : '/web-nacimiento/RADOP_NC_inscrip_judicial',
						funcion : vm.presentarVista,
						metodo : 'POST'
					}, {
						ruta : '/web-nacimiento/RADOP_NC_especiales',
						funcion : vm.presentarVista,
						metodo : 'POST'
					}, {
						ruta : '/web-nacimiento/RADOP_NC_documentos',
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
					}, 

					];

					// Datos a ser enviados al momento de cargar el modal
					vm.modelo = {
						id : $rootScope.objectSelected.numeroSolicitud,
						paso : 0,
						estatu : $rootScope.objectSelected.codigoEstadoSolicitud
					};
					llamadaAjax($http, $scope, vm, vm.rutas[vm.paso].ruta,
							vm.modelo, vm.rutas[vm.paso].funcion,
							vm.rutas[vm.paso].metodo);

					vm.siguiente = function() {
						// guarda en la pila la vista actual
						vm.vistas.push(vm.vista);
						vm.modelos.push(vm.modelo);
						vm.paso++;
						vm.seteo();
						
						console.log("num de paso" + vm.paso);
						if (vm.paso > 7) {
							console.log("aqui va a cerrar");
							$rootScope.cancelar1();
							// return;
						}

						if (vm.paso == 4) {
							$scope.consultaActa();
						}
//						 if (vm.paso == 9) {
//						 vm.actualizarEstado();
//						 return;
//						 }
						llamadaAjax($http, $scope, vm, vm.rutas[vm.paso].ruta,
								vm.modelo, vm.rutas[vm.paso].funcion,
								vm.rutas[vm.paso].metodo);
					}

					vm.actualizarEstado = function() {
						llamadaAjax($http, $scope, vm,
								'/web-nacimiento/actualizarEstado', vm.modelo,
								$scope.notificacion, "POST");

					}
					vm.retorno = function() {
						console.log("retorno!!!");
					}

					vm.regresar = function() {
						vm.vista = vm.vistas[vm.vistas.length - 1];
						vm.modelo = vm.modelos[vm.modelos.length - 1];
						vm.modelos.splice(vm.modelos.length - 1, 1);
						vm.vistas.splice(vm.vistas.length - 1, 1);
						console.log("paso antes de restar: " + vm.paso);
						vm.paso--;
						if (vm.activo < vm.titulos.length) {
							vm.titulos.splice(vm.activo + 1, vm.titulos.length
									- vm.activo - 1);
						}
						vm.activo = vm.titulos.indexOf(vm.modelo.titulo);
						console.log("paso despues de restar: " + vm.paso);
					}

					vm.confirmarSalidaModulo = function() {
						presentarModal($scope, $uibModal, vm.salirModulo,
								'¿Desea cancelar la solicitud?',
								$rootScope.tituloWizard, 's');
					}
					vm.salirModulo = function() {
						$rootScope.cancelar1();
					}

					vm.cancel = function() {
						vm.confirmarSalidaModulo();
					}

					vm.cancelar = function() {
						vm.confirmarSalidaModulo();
					}

					// ---------funciones internas ------------ //
					$scope.mostrarCampos = function() {
						$scope.campos = true;
					}

					$scope.ocultarCampos = function() {
						$scope.campos = false;
					}
					
					// ****************************************************************************************
					$scope.buscarOcupacionNCMAM = function() {
						console.log("xq aqui si y los paises no");
						$scope.nombreOcupacion = vm.modelo['MAM'].ocupacion;
						$http({
							method : 'GET',
							url : '/web-nacimiento/consultarOcupacion'
						})
								.then(
										function successCallback(ocupacion) {
											// /console.log(ocupacion.data);
											$scope.ocupaciones = ocupacion.data;
											for (var i = 0; i < $scope.ocupaciones.length; i++) {
												if ($scope.ocupaciones[i].nombre == $scope.nombreOcupacion) {
													$scope.nOcupacion = $scope.ocupaciones[i].nombre;
													vm.modelo['MAM'].ocupacion = $scope.nOcupacion;
													break;
												}
											}
										},
										function errorCallback(error) {
											console.log("error: " + error.data);
										});

					}
					$scope.buscarPaisNCMAM = function() {
						$scope.nombrePais = vm.modelo['MAM'].direccion[1].pais.nombre;
						$http({
							method : 'POST',
							url : '/web-generales/consultarPaisTodos',
							data : {}
						})
								.then(
										function successCallback(pais) {
											console.log(pais.data);
											$scope.paises = pais.data;
											for (var i = 0; i < $scope.paises.length; i++) {
												if ($scope.paises[i].nombre == $scope.nombrePais) {
													$scope.ePais = $scope.paises[i].nombre;
													$scope.cPais = $scope.paises[i].codigo;
													vm.modelo['MAM'].direccion[1].pais = $scope.ePais;
													break;
												}
											}
										},
										function errorCallback(error) {
											console.log("error: " + error.data);
										});

					}
					$scope.buscarEstadoNCMAM = function(parametro) {
						var mPais = null;
						if (parametro == "VEN") {
							mPais = parametro;
							$scope.nombreEstado = vm.modelo['MAM'].direccion[1].estado.nombre;
							$scope.disabled = false;
							$http({
								method : 'POST',
								url : '/web-generales/consultarEstadoPorPais/',
								data : {
									codigo : mPais
								}
							})
									.then(
											function successCallback(estado) {
												$scope.estados = estado.data;
												for (var i = 0; i < $scope.estados.length; i++) {
													if ($scope.estados[i].nombre == $scope.nombreEstado) {
														$scope.eEstado = $scope.estados[i].nombre;
														$scope.cEstado = $scope.estados[i].codigo;
														vm.modelo['MAM'].direccion[1].estado = $scope.eEstado;
														break;
													}
												}
											},
											function errorCallback(error) {
												console.log("error: "
														+ error.data);
											});

						} else {
							vm.clearFields();
							$scope.disabled = true;

						}

					}

					$scope.buscarMunicipioNCMAM = function(parametro) {
						var mEstado = null;
						console.log(parametro);
						if (parametro == "parametro") {
							for (var i = 0; i < $scope.estados.length; i++) {
								if ($scope.estados[i].nombre == vm.modelo['MAM'].direccion[1].estado) {
									mEstado = $scope.estados[i].codigo;
									break;
								}
							}
						} else if (parametro != "parametro") {
							mEstado = parametro;
							$scope.nombreMunicipio = vm.modelo['MAM'].direccion[1].municipio.nombre;
							$http(
									{
										method : 'POST',
										url : '/web-generales/consultarMunicipioPorEstado',
										data : {
											codigo : mEstado
										}
									})
									.then(
											function successCallback(municipio) {
												console.log(municipio.data);
												$scope.municipios = municipio.data;

												for (var i = 0; i < $scope.municipios.length; i++) {
													if ($scope.municipios[i].nombre == $scope.nombreMunicipio) {

														$scope.mMunicipio = $scope.municipios[i].nombre;
														$scope.cMunicipio = $scope.municipios[i].codigo;
														vm.modelo['MAM'].direccion[1].municipio = $scope.mMunicipio;
														break;
													}

												}
												$scope
														.buscarParroquiaNCMAM($scope.cMunicipio);

											},
											function errorCallback(error) {
												console.log("error: "
														+ error.data);
											});

						}
					}

					$scope.buscarParroquiaNCMAM = function(parametro) {
						var mParroquia = null;
						if (parametro == "parametro") {
							// var mParroquia;
							for (var i = 0; i < $scope.municipios.length; i++) {
								if ($scope.municipios[i].nombre == vm.modelo.PermisoInhCre.municipio) {
									mParroquia = $scope.municipios[i].codigo;
									break;
								}
							}
						} else if (parametro != "parametro") {
							mParroquia = parametro;
							$scope.nombreParroquia = vm.modelo['MAM'].direccion[1].parroquia.nombre;
							$http(
									{
										method : 'POST',
										url : '/web-generales/consultarParroquiaPorMunicipio',
										data : {
											codigo : mParroquia
										}
									})
									.then(
											function successCallback(parroquia) {
												$scope.parroquias = parroquia.data;
												for (var i = 0; i < $scope.parroquias.length; i++) {
													if ($scope.parroquias[i].nombre == $scope.nombreParroquia) {
														$scope.mnParroquia = $scope.parroquias[i].nombre;
														$scope.cParroquia = $scope.parroquias[i].codigo;
														vm.modelo['MAM'].direccion[1].parroquia = $scope.mnParroquia;
														break;
													}

												}

											},
											function errorCallback(error) {
												console.log("error: "
														+ error.data);
											});

						}
					}

					$scope.funcionSeteoDataComboMAM = function() {
						console.log("----FUNCION COMBO MAMA JEANnnnnnn-----");

						// $scope.buscarPais();
						$scope.buscarOcupacionNCMAM();
						$scope.buscarPaisNCMAM();
						$scope.buscarEstadoNCMAM("VEN");
						$scope.buscarMunicipioNCMAM(vm.modelo['MAM'].direccion[1].estado.codigo);
						// $scope.buscarMunicipioRVMAM($scope.cEstado);
						// console.log("municipio.codigo----- " +
						// mun.cMunicipio);

					}

					// *******************************************************************************
					$scope.buscarOcupacionNCPAP = function() {
						$scope.nombreOcupacion = vm.modelo['PAP'].ocupacion;
						$http({
							method : 'GET',
							url : '/web-nacimiento/consultarOcupacion'
						})
								.then(
										function successCallback(ocupacion) {
											// /console.log(ocupacion.data);
											$scope.ocupaciones = ocupacion.data;
											for (var i = 0; i < $scope.ocupaciones.length; i++) {
												if ($scope.ocupaciones[i].nombre == $scope.nombreOcupacion) {
													$scope.nOcupacion = $scope.ocupaciones[i].nombre;
													vm.modelo['PAP'].ocupacion = $scope.nOcupacion;
													break;
												}
											}
										},
										function errorCallback(error) {
											console.log("error: " + error.data);
										});

					}
					$scope.buscarPaisNCPAP = function() {
						$scope.nombrePais = vm.modelo['PAP'].direccion[1].pais.nombre;
						$http({
							method : 'POST',
							url : '/web-generales/consultarPaisTodos',
							data : {}
						})
								.then(
										function successCallback(pais) {
											console.log(pais.data);
											$scope.paises = pais.data;
											for (var i = 0; i < $scope.paises.length; i++) {
												if ($scope.paises[i].nombre == $scope.nombrePais) {
													$scope.ePais = $scope.paises[i].nombre;
													$scope.cPais = $scope.paises[i].codigo;
													vm.modelo['PAP'].direccion[1].pais = $scope.ePais;
													break;
												}
											}
										},
										function errorCallback(error) {
											console.log("error: " + error.data);
										});

					}
					$scope.buscarEstadoNCPAP = function(parametro) {
						var mPais = null;
						if (parametro == "VEN") {
							mPais = parametro;
							$scope.nombreEstado = vm.modelo['PAP'].direccion[1].estado.nombre;
							$scope.disabled = false;
							$http({
								method : 'POST',
								url : '/web-generales/consultarEstadoPorPais/',
								data : {
									codigo : mPais
								}
							})
									.then(
											function successCallback(estado) {
												$scope.estados = estado.data;
												for (var i = 0; i < $scope.estados.length; i++) {
													if ($scope.estados[i].nombre == $scope.nombreEstado) {
														$scope.eEstado = $scope.estados[i].nombre;
														$scope.cEstado = $scope.estados[i].codigo;
														vm.modelo['PAP'].direccion[1].estado = $scope.eEstado;
														break;
													}
												}
											},
											function errorCallback(error) {
												console.log("error: "
														+ error.data);
											});

						} else {
							vm.clearFields();
							$scope.disabled = true;

						}

					}

					$scope.buscarMunicipioNCPAP = function(parametro) {
						var mEstado = null;
						console.log(parametro);
						if (parametro == "parametro") {
							for (var i = 0; i < $scope.estados.length; i++) {
								if ($scope.estados[i].nombre == vm.modelo['PAP'].direccion[1].estado) {
									mEstado = $scope.estados[i].codigo;
									break;
								}
							}
						} else if (parametro != "parametro") {
							mEstado = parametro;
							$scope.nombreMunicipio = vm.modelo['PAP'].direccion[1].municipio.nombre;
							$http(
									{
										method : 'POST',
										url : '/web-generales/consultarMunicipioPorEstado',
										data : {
											codigo : mEstado
										}
									})
									.then(
											function successCallback(municipio) {
												console.log(municipio.data);
												$scope.municipios = municipio.data;

												for (var i = 0; i < $scope.municipios.length; i++) {
													if ($scope.municipios[i].nombre == $scope.nombreMunicipio) {

														$scope.mMunicipio = $scope.municipios[i].nombre;
														$scope.cMunicipio = $scope.municipios[i].codigo;
														vm.modelo['PAP'].direccion[1].municipio = $scope.mMunicipio;
														break;
													}

												}
												$scope.buscarParroquiaNCPAP($scope.cMunicipio);

											},
											function errorCallback(error) {
												console.log("error: "
														+ error.data);
											});

						}
					}

					$scope.buscarParroquiaNCPAP = function(parametro) {
						var mParroquia = null;
						if (parametro == "parametro") {
							// var mParroquia;
							for (var i = 0; i < $scope.municipios.length; i++) {
								if ($scope.municipios[i].nombre == vm.modelo.PermisoInhCre.municipio) {
									mParroquia = $scope.municipios[i].codigo;
									break;
								}
							}
						} else if (parametro != "parametro") {
							mParroquia = parametro;
							$scope.nombreParroquia = vm.modelo['PAP'].direccion[1].parroquia.nombre;
							$http(
									{
										method : 'POST',
										url : '/web-generales/consultarParroquiaPorMunicipio',
										data : {
											codigo : mParroquia
										}
									})
									.then(
											function successCallback(parroquia) {
												$scope.parroquias = parroquia.data;
												for (var i = 0; i < $scope.parroquias.length; i++) {
													if ($scope.parroquias[i].nombre == $scope.nombreParroquia) {
														$scope.mnParroquia = $scope.parroquias[i].nombre;
														$scope.cParroquia = $scope.parroquias[i].codigo;
														vm.modelo['PAP'].direccion[1].parroquia = $scope.mnParroquia;
														break;
													}

												}

											},
											function errorCallback(error) {
												console.log("error: "
														+ error.data);
											});

						}
					}

					$scope.funcionSeteoDataComboPAP = function() {
						console.log("----FUNCION COMBO PAPA-----");

						// $scope.buscarPais();
						$scope.buscarOcupacionNCPAP();
						$scope.buscarPaisNCPAP();
						$scope.buscarEstadoNCPAP("VEN");
						$scope.buscarMunicipioNCPAP(vm.modelo['PAP'].direccion[1].estado.codigo);
					}

					// *******************************************************************************
					$scope.buscarPaisNCADO = function() {
						$scope.nombrePais = vm.modelo['ADO'].direccion[0].pais.nombre;
						$http({
							method : 'POST',
							url : '/web-generales/consultarPaisTodos',
							data : {}
						})
								.then(
										function successCallback(pais) {
											console.log(pais.data);
											$scope.paises = pais.data;
											for (var i = 0; i < $scope.paises.length; i++) {
												if ($scope.paises[i].nombre == $scope.nombrePais) {
													$scope.ePais = $scope.paises[i].nombre;
													$scope.cPais = $scope.paises[i].codigo;
													vm.modelo['ADO'].direccion[0].pais = $scope.ePais;
													break;
												}
											}
										},
										function errorCallback(error) {
											console.log("error: " + error.data);
										});

					}
					$scope.buscarEstadoNCADO = function(parametro) {
						var mPais = null;
						if (parametro == "VEN") {
							mPais = parametro;
							$scope.nombreEstado = vm.modelo['ADO'].direccion[0].estado.nombre;
							$scope.disabled = false;
							$http({
								method : 'POST',
								url : '/web-generales/consultarEstadoPorPais/',
								data : {
									codigo : mPais
								}
							})
									.then(
											function successCallback(estado) {
												$scope.estados = estado.data;
												for (var i = 0; i < $scope.estados.length; i++) {
													if ($scope.estados[i].nombre == $scope.nombreEstado) {
														$scope.eEstado = $scope.estados[i].nombre;
														$scope.cEstado = $scope.estados[i].codigo;
														vm.modelo['ADO'].direccion[0].estado = $scope.eEstado;
														break;
													}
												}
											},
											function errorCallback(error) {
												console.log("error: "
														+ error.data);
											});

						} else {
							vm.clearFields();
							$scope.disabled = true;

						}

					}

					$scope.buscarMunicipioNCADO = function(parametro) {
						var mEstado = null;
						console.log(parametro);
						if (parametro == "parametro") {
							for (var i = 0; i < $scope.estados.length; i++) {
								if ($scope.estados[i].nombre == vm.modelo['ADO'].direccion[0].estado) {
									mEstado = $scope.estados[i].codigo;
									break;
								}
							}
						} else if (parametro != "parametro") {
							mEstado = parametro;
							$scope.nombreMunicipio = vm.modelo['ADO'].direccion[0].municipio.nombre;
							$http(
									{
										method : 'POST',
										url : '/web-generales/consultarMunicipioPorEstado',
										data : {
											codigo : mEstado
										}
									})
									.then(
											function successCallback(municipio) {
												console.log(municipio.data);
												$scope.municipios = municipio.data;

												for (var i = 0; i < $scope.municipios.length; i++) {
													if ($scope.municipios[i].nombre == $scope.nombreMunicipio) {

														$scope.mMunicipio = $scope.municipios[i].nombre;
														$scope.cMunicipio = $scope.municipios[i].codigo;
														vm.modelo['ADO'].direccion[0].municipio = $scope.mMunicipio;
														break;
													}

												}
												$scope.buscarParroquiaNCADO($scope.cMunicipio);

											},
											function errorCallback(error) {
												console.log("error: "
														+ error.data);
											});

						}
					}

					$scope.buscarParroquiaNCADO = function(parametro) {
						var mParroquia = null;
						if (parametro == "parametro") {
							// var mParroquia;
							for (var i = 0; i < $scope.municipios.length; i++) {
								if ($scope.municipios[i].nombre == vm.modelo.PermisoInhCre.municipio) {
									mParroquia = $scope.municipios[i].codigo;
									break;
								}
							}
						} else if (parametro != "parametro") {
							mParroquia = parametro;
							$scope.nombreParroquia = vm.modelo['ADO'].direccion[0].parroquia.nombre;
							$http(
									{
										method : 'POST',
										url : '/web-generales/consultarParroquiaPorMunicipio',
										data : {
											codigo : mParroquia
										}
									})
									.then(
											function successCallback(parroquia) {
												$scope.parroquias = parroquia.data;
												for (var i = 0; i < $scope.parroquias.length; i++) {
													if ($scope.parroquias[i].nombre == $scope.nombreParroquia) {
														$scope.mnParroquia = $scope.parroquias[i].nombre;
														$scope.cParroquia = $scope.parroquias[i].codigo;
														vm.modelo['ADO'].direccion[0].parroquia = $scope.mnParroquia;
														break;
													}

												}

											},
											function errorCallback(error) {
												console.log("error: "
														+ error.data);
											});

						}
					}

					$scope.funcionSeteoDataComboADO = function() {
						console.log("----FUNCION COMBO ADOPTADO-----");

						// $scope.buscarPais();
						$scope.buscarPaisNCADO();
						$scope.buscarEstadoNCADO("VEN");
						$scope.buscarMunicipioNCADO(vm.modelo['ADO'].direccion[0].estado.codigo);

					}

					// *******************************************************************************
					$scope.buscarOcupacionNCTA1 = function() {
						$scope.nombreOcupacion = vm.modelo['TA1'].ocupacion;
						$http({
							method : 'GET',
							url : '/web-nacimiento/consultarOcupacion'
						})
								.then(
										function successCallback(ocupacion) {
											// /console.log(ocupacion.data);
											$scope.ocupaciones = ocupacion.data;
											for (var i = 0; i < $scope.ocupaciones.length; i++) {
												if ($scope.ocupaciones[i].nombre == $scope.nombreOcupacion) {
													$scope.nOcupacion = $scope.ocupaciones[i].nombre;
													vm.modelo['TA1'].ocupacion = $scope.nOcupacion;
													break;
												}
											}
										},
										function errorCallback(error) {
											console.log("error: " + error.data);
										});

					}
					$scope.buscarPaisNCTA1 = function() {
						$scope.nombrePais = vm.modelo['TA1'].direccion[1].pais.nombre;
						$http({
							method : 'POST',
							url : '/web-generales/consultarPaisTodos',
							data : {}
						})
								.then(
										function successCallback(pais) {
											console.log(pais.data);
											$scope.paises = pais.data;
											for (var i = 0; i < $scope.paises.length; i++) {
												if ($scope.paises[i].nombre == $scope.nombrePais) {
													$scope.ePais = $scope.paises[i].nombre;
													$scope.cPais = $scope.paises[i].codigo;
													vm.modelo['TA1'].direccion[1].pais = $scope.ePais;
													break;
												}
											}
										},
										function errorCallback(error) {
											console.log("error: " + error.data);
										});

					}
					$scope.buscarEstadoNCTA1 = function(parametro) {
						var mPais = null;
						if (parametro == "VEN") {
							mPais = parametro;
							$scope.nombreEstado = vm.modelo['TA1'].direccion[1].estado.nombre;
							$scope.disabled = false;
							$http({
								method : 'POST',
								url : '/web-generales/consultarEstadoPorPais/',
								data : {
									codigo : mPais
								}
							})
									.then(
											function successCallback(estado) {
												$scope.estados = estado.data;
												for (var i = 0; i < $scope.estados.length; i++) {
													if ($scope.estados[i].nombre == $scope.nombreEstado) {
														$scope.eEstado = $scope.estados[i].nombre;
														$scope.cEstado = $scope.estados[i].codigo;
														vm.modelo['TA1'].direccion[1].estado = $scope.eEstado;
														break;
													}
												}
											},
											function errorCallback(error) {
												console.log("error: "
														+ error.data);
											});

						} else {
							vm.clearFields();
							$scope.disabled = true;

						}

					}

					$scope.buscarMunicipioNCTA1 = function(parametro) {
						var mEstado = null;
						console.log(parametro);
						if (parametro == "parametro") {
							for (var i = 0; i < $scope.estados.length; i++) {
								if ($scope.estados[i].nombre == vm.modelo['TA1'].direccion[1].estado) {
									mEstado = $scope.estados[i].codigo;
									break;
								}
							}
						} else if (parametro != "parametro") {
							mEstado = parametro;
							$scope.nombreMunicipio = vm.modelo['TA1'].direccion[1].municipio.nombre;
							$http(
									{
										method : 'POST',
										url : '/web-generales/consultarMunicipioPorEstado',
										data : {
											codigo : mEstado
										}
									})
									.then(
											function successCallback(municipio) {
												console.log(municipio.data);
												$scope.municipios = municipio.data;

												for (var i = 0; i < $scope.municipios.length; i++) {
													if ($scope.municipios[i].nombre == $scope.nombreMunicipio) {

														$scope.mMunicipio = $scope.municipios[i].nombre;
														$scope.cMunicipio = $scope.municipios[i].codigo;
														vm.modelo['TA1'].direccion[1].municipio = $scope.mMunicipio;
														break;
													}

												}
												$scope.buscarParroquiaNCTA1($scope.cMunicipio);

											},
											function errorCallback(error) {
												console.log("error: "
														+ error.data);
											});

						}
					}

					$scope.buscarParroquiaNCTA1 = function(parametro) {
						var mParroquia = null;
						if (parametro == "parametro") {
							// var mParroquia;
							for (var i = 0; i < $scope.municipios.length; i++) {
								if ($scope.municipios[i].nombre == vm.modelo.PermisoInhCre.municipio) {
									mParroquia = $scope.municipios[i].codigo;
									break;
								}
							}
						} else if (parametro != "parametro") {
							mParroquia = parametro;
							$scope.nombreParroquia = vm.modelo['TA1'].direccion[1].parroquia.nombre;
							$http(
									{
										method : 'POST',
										url : '/web-generales/consultarParroquiaPorMunicipio',
										data : {
											codigo : mParroquia
										}
									})
									.then(
											function successCallback(parroquia) {
												$scope.parroquias = parroquia.data;
												for (var i = 0; i < $scope.parroquias.length; i++) {
													if ($scope.parroquias[i].nombre == $scope.nombreParroquia) {
														$scope.mnParroquia = $scope.parroquias[i].nombre;
														$scope.cParroquia = $scope.parroquias[i].codigo;
														vm.modelo['TA1'].direccion[1].parroquia = $scope.mnParroquia;
														break;
													}

												}

											},
											function errorCallback(error) {
												console.log("error: "
														+ error.data);
											});

						}
					}

					$scope.funcionSeteoDataComboTA1 = function() {
						console.log("----FUNCION COMBO TESTIGO 1-----");

						// $scope.buscarPais();
						$scope.buscarOcupacionNCTA1();
						$scope.buscarPaisNCTA1();
						$scope.buscarEstadoNCTA1("VEN");
						$scope.buscarMunicipioNCTA1(vm.modelo['TA1'].direccion[1].estado.codigo);
						
					}

					// *******************************************************************************
					$scope.buscarOcupacionNCTA2 = function() {
						$scope.nombreOcupacion = vm.modelo['TA2'].ocupacion;
						$http({
							method : 'GET',
							url : '/web-nacimiento/consultarOcupacion'
						})
								.then(
										function successCallback(ocupacion) {
											// /console.log(ocupacion.data);
											$scope.ocupaciones = ocupacion.data;
											for (var i = 0; i < $scope.ocupaciones.length; i++) {
												if ($scope.ocupaciones[i].nombre == $scope.nombreOcupacion) {
													$scope.nOcupacion = $scope.ocupaciones[i].nombre;
													vm.modelo['TA2'].ocupacion = $scope.nOcupacion;
													break;
												}
											}
										},
										function errorCallback(error) {
											console.log("error: " + error.data);
										});

					}
					$scope.buscarPaisNCTA2 = function() {
						$scope.nombrePais = vm.modelo['TA2'].direccion[1].pais.nombre;
						$http({
							method : 'POST',
							url : '/web-generales/consultarPaisTodos',
							data : {}
						})
								.then(
										function successCallback(pais) {
											console.log(pais.data);
											$scope.paises = pais.data;
											for (var i = 0; i < $scope.paises.length; i++) {
												if ($scope.paises[i].nombre == $scope.nombrePais) {
													$scope.ePais = $scope.paises[i].nombre;
													$scope.cPais = $scope.paises[i].codigo;
													vm.modelo['TA2'].direccion[1].pais = $scope.ePais;
													break;
												}
											}
										},
										function errorCallback(error) {
											console.log("error: " + error.data);
										});

					}
					$scope.buscarEstadoNCTA2 = function(parametro) {
						var mPais = null;
						if (parametro == "VEN") {
							mPais = parametro;
							$scope.nombreEstado = vm.modelo['TA2'].direccion[1].estado.nombre;
							$scope.disabled = false;
							$http({
								method : 'POST',
								url : '/web-generales/consultarEstadoPorPais/',
								data : {
									codigo : mPais
								}
							})
									.then(
											function successCallback(estado) {
												$scope.estados = estado.data;
												for (var i = 0; i < $scope.estados.length; i++) {
													if ($scope.estados[i].nombre == $scope.nombreEstado) {
														$scope.eEstado = $scope.estados[i].nombre;
														$scope.cEstado = $scope.estados[i].codigo;
														vm.modelo['TA2'].direccion[1].estado = $scope.eEstado;
														break;
													}
												}
											},
											function errorCallback(error) {
												console.log("error: "
														+ error.data);
											});

						} else {
							vm.clearFields();
							$scope.disabled = true;

						}

					}

					$scope.buscarMunicipioNCTA2 = function(parametro) {
						var mEstado = null;
						console.log(parametro);
						if (parametro == "parametro") {
							for (var i = 0; i < $scope.estados.length; i++) {
								if ($scope.estados[i].nombre == vm.modelo['TA2'].direccion[1].estado) {
									mEstado = $scope.estados[i].codigo;
									break;
								}
							}
						} else if (parametro != "parametro") {
							mEstado = parametro;
							$scope.nombreMunicipio = vm.modelo['TA2'].direccion[1].municipio.nombre;
							$http(
									{
										method : 'POST',
										url : '/web-generales/consultarMunicipioPorEstado',
										data : {
											codigo : mEstado
										}
									})
									.then(
											function successCallback(municipio) {
												console.log(municipio.data);
												$scope.municipios = municipio.data;

												for (var i = 0; i < $scope.municipios.length; i++) {
													if ($scope.municipios[i].nombre == $scope.nombreMunicipio) {

														$scope.mMunicipio = $scope.municipios[i].nombre;
														$scope.cMunicipio = $scope.municipios[i].codigo;
														vm.modelo['TA2'].direccion[1].municipio = $scope.mMunicipio;
														break;
													}

												}
												$scope
														.buscarParroquiaNCTA2($scope.cMunicipio);

											},
											function errorCallback(error) {
												console.log("error: "
														+ error.data);
											});

						}
					}

					$scope.buscarParroquiaNCTA2 = function(parametro) {
						var mParroquia = null;
						if (parametro == "parametro") {
							// var mParroquia;
							for (var i = 0; i < $scope.municipios.length; i++) {
								if ($scope.municipios[i].nombre == vm.modelo.PermisoInhCre.municipio) {
									mParroquia = $scope.municipios[i].codigo;
									break;
								}
							}
						} else if (parametro != "parametro") {
							mParroquia = parametro;
							$scope.nombreParroquia = vm.modelo['TA2'].direccion[1].parroquia.nombre;
							$http(
									{
										method : 'POST',
										url : '/web-generales/consultarParroquiaPorMunicipio',
										data : {
											codigo : mParroquia
										}
									})
									.then(
											function successCallback(parroquia) {
												$scope.parroquias = parroquia.data;
												for (var i = 0; i < $scope.parroquias.length; i++) {
													if ($scope.parroquias[i].nombre == $scope.nombreParroquia) {
														$scope.mnParroquia = $scope.parroquias[i].nombre;
														$scope.cParroquia = $scope.parroquias[i].codigo;
														vm.modelo['TA2'].direccion[1].parroquia = $scope.mnParroquia;
														break;
													}

												}

											},
											function errorCallback(error) {
												console.log("error: "
														+ error.data);
											});

						}
					}

					$scope.funcionSeteoDataComboTA2 = function() {
						console.log("----FUNCION COMBO TESTIGO 2-----");

						// $scope.buscarPais();
						$scope.buscarOcupacionNCTA2();
						$scope.buscarPaisNCTA2();
						$scope.buscarEstadoNCTA2("VEN");
						$scope
								.buscarMunicipioNCTA2(vm.modelo['TA2'].direccion[1].estado.codigo);
						// $scope.buscarMunicipioRVMAM($scope.cEstado);
						// console.log("municipio.codigo----- " +
						// mun.cMunicipio);

					}

					// *******************************************************************************
					
					

					// /////////////////Modales//////////////////////
					vm.abrirModalActa = function() {
						console.log("estoy en el modal");
						presentarModal(
								$scope,
								$uibModal,
								vm.otraModalActa(), // ///esto no va a
								// aca
								'El(La) adoptado(a) no posee un acta de nacimiento registrada. Se procederá a enviar una notificación al Registrador(a) Civil ',
								$rootScope.tituloWizard, 'c');
					}
					vm.otraModalActa = function() {
						console.log("estoy en el otro modal");
						presentarModal(
								$scope,
								$uibModal,
								vm.actualizarEstado,
								'Notificación enviada al(la) Registrador(a) Civil  indicando que el acta primigenia se encuentra "Restringida"',
								$rootScope.tituloWizard, 'c');
					}
					$scope.notificacion = function() {
						console.log("Todo bien si llegamos aca");
						$rootScope.cancelar1();
					}
					/////////////////////////////////////////////////
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
						if (vm.modelo['ADO'].direccion[1].pais.codigo == "VEN") {
							$http({
								method : 'POST',
								url : '/web-generales/consultarEstadoPorPais',
								data : {
									codigo : vm.modelo['ADO'].direccion[1].pais.codigo
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
						}
					}

					$scope.buscarMunicipio = function() {

						$http({
							method : 'POST',
							url : '/web-generales/consultarMunicipioPorEstado',
							data : {
								codigo : vm.modelo['ADO'].direccion[1].estado.codigo
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

						$http(
								{
									method : 'POST',
									url : '/web-generales/consultarParroquiaPorMunicipio',
									data : {
										codigo : vm.modelo['ADO'].direccion[1].municipio.codigo
									}
								}).then(function successCallback(parroquia) {
							console.log(parroquia.data);
							$scope.parroquias = parroquia.data;
							// $scope.parroquias=$scope.municipios.parroquias;
						}, function errorCallback(error) {
							console.log("error: " + error.data);
						});

					}
					
					$scope.setCedulaAdop = function() {
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
						
						if (vm.modelo['ADO'] != null
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
					
					// /////////////////////////////////////////////
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

					// Combo de Ocupaciones
					$scope.buscarOficina = function() {
						$http({
							method : 'GET',
							url : '/web-nacimiento/consultarOficinasTodos'
						}).then(function successCallback(oficina) {
							// /console.log(ocupacion.data);
							$scope.oficinas = oficina.data;
						}, function errorCallback(error) {
							console.log("error: " + error.data);
						});
					}
					$scope.buscarOficina();

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

					llamarDisabled = function() {
						// ///////////MAM
						if (vm.modelo['MAM'] != null) {
							if (vm.modelo['MAM'].primerNombre != null) {
								$scope.diasabled = true;
								// console.log(vm.modelo['MAM'].primerNombre);
							} else {
								$scope.diasabled = false;
							}
							if (vm.modelo['MAM'].segundoNombre != null) {
								$scope.diasabled = true;
								// console.log(vm.modelo['MAM'].segundoNombre);
							} else {
								$scope.diasabled = false;
							}
							if (vm.modelo['MAM'].primerApellido != null) {
								$scope.diasabled = true;
							} else {
								$scope.diasabled = false;
							}
							if (vm.modelo['MAM'].segundoApellido != null) {
								$scope.diasabled = true;
							} else {
								$scope.diasabled = false;
							}
							if (vm.modelo['MAM'].documentoIdentidad[0].numero != null) {
								// console.log(vm.modelo['MAM'].documentoIdentidad[0].numero);
								$scope.diasabled = true;
							} else {
								$scope.diasabled = false;
							}
							if (vm.modelo['MAM'].documentoIdentidad[0].tipoDocumento.nombre != null) {
								// console.log(vm.modelo['MAM'].documentoIdentidad[0].tipoDocumento.nombre);
								$scope.diasabled = true;
							} else {
								$scope.diasabled = false;
							}
						}

						// ///////////ADO
						if (vm.modelo['ADO'] != null) {
							if (vm.modelo['ADO'].primerNombre != null) {
								$scope.disabled = true;
								// console.log(vm.modelo['ADO'].primerNombre);
							} else {
								$scope.disabled = false;
							}
							if (vm.modelo['ADO'].segundoNombre != null) {
								$scope.disabled = true;
								// console.log(vm.modelo['ADO'].segundoNombre);
							} else {
								$scope.disabled = false;
							}
							if (vm.modelo['ADO'].primerApellido != null) {
								$scope.disabled = true;
							} else {
								$scope.disabled = false;
							}
							if (vm.modelo['ADO'].segundoApellido != null) {
								$scope.disabled = true;
							} else {
								$scope.disabled = false;
							}
							if (vm.modelo['ADO'].documentoIdentidad[0].numero != null) {
								// console.log(vm.modelo['ADO'].documentoIdentidad[0].numero);
								$scope.disabled = true;
							} else {
								$scope.disabled = false;
							}
							if (vm.modelo['ADO'].documentoIdentidad[0].tipoDocumento.nombre != null) {
								// console.log(vm.modelo['ADO'].documentoIdentidad[0].tipoDocumento.nombre);
								$scope.disabled = true;
							} else {
								$scope.disabled = false;
							}
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
							// console.log("MAM"+edM);
							// console.log($scope.modelo['MAM'].Edad);
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

					// ng-change... para
					vm.MostrarDatosPadres = function() {
						vm.padres = true;
						vm.EnviarOficio = true;
						// $scope.disabled=false;
					}
					vm.OcultarDatosPadres = function() {
						vm.padres = false;
						vm.EnviarOficio = true;
						// $scope.disabled=true;
					}

					// Agregar formilarios hijos
					$scope.control = 0;
					$scope.h;
					vm.addHijo = function() {
						var hijo = {
							"acta_hijo" : null,
							"tomo_hijo" : null,
							"folio_hijo" : null,
							"oficina_hijo" : null,
							"fecha_hijo" : null,
							"primerNombre_hijo" : null,
							"segundoNombre_hijo" : null,
							"primerApellido_hijo" : null,
							"segundoApellido_hijo" : null,
							"documentoIdentidad[0].tipoDocumento.nombre" : null,
							"documentoIdentidad[0].numero" : null
						// "documentoIdentidad[0].tipoDocumento.nombre" = null,
						// "documentoIdentidad[0].numero" = null,
						};

						vm.hijos.push(hijo);
						// $scope.di = false;

						$scope.control++;
						hijo.ndHijos = $scope.control;
						console.log("*****$scope.control******");
						console.log($scope.control);
						$scope.h = $scope.control;
						console.log("*****H******" + $scope.h);
						// $scope.control == vm.modelo['HijoAdop'].ndHijos;
						// vm.modelo['HijoAdop'].ndHijos == $scope.control;
						// console.log("valos del imput
						// "+vm.modelo['HijoAdop'].ndHijos);
						// ////return $scope.h;

					}
					console.log(vm.hijos);

					// Restar formilarios hijos
					vm.restHijo = function() {
						if ($scope.control > 0) {
							console.log("Entrandoooooo al boton restar!!!!!");
							// console.log($scope.h +" ---"+ $scope.control);
							// if ($scope.control == $scope.h) {
							// $scope.control == vm.modelo['HijoAdop'].ndHijos
							vm.hijos.splice(vm.hijos.length - 1, 1);
							console.log(vm.hijos);
							$scope.control--;
							vm.modelo['HijoAdop'].ndHijos = $scope.control;
							// $scope.h = $scope.h - 1;
							// }
						}
					}

					vm.clearFields = function() {
						// if(form.$name == "myFormAtencion"){ form
						// console.log("Entroooo scope"
						// + vm.modelo['HijoAdop'].ndHijos);
						hijo.ndHijos = null;
						// hijo.n_acta1 = null;
						// hijo.n_folio1 = null;
						hijo.tomo1 = null;
						hijo.oficina1 = null;
						hijo.documentoIdentidad[0].tipoDocumento.nombre = null;
						hijo.documentoIdentidad[0].numero = null;
						hijo.primerNombre_hijo = null;
						hijo.segundoNombre_hijo = null;
						hijo.primerApellido_hijo = null;
						hijo.segundoApellido_hijo = null;
						hijo.acta_hijo = null;
						hijo.folio_hijo = null;
						// hijo.tomo_hijo = null;
						// hijo.oficina_hijo = null;
						// //hijo.documentoIdentidad[0] = null;
						// //hijo.documentoIdentidad[0].numero = null;
						// hijo.primerNombre_hijo = null;
						// hijo.segundoNombre_hijo = null;
						// hijo.primerApellido_hijo = null;
						// hijo.segundoApellido = null;
						// }
					}

					vm.form_cantidad_no = function() {

						$scope.cantidad_hijos = false;

					}
					vm.form_cantidad_si = function() {

						$scope.cantidad_hijos = true;

					}

					vm.clearFieldsUeh = function() {
						vm.modelo.n_actaUeh = null;
						vm.modelo.n_folio = null;
						vm.modelo.tomo = null;
						vm.modelo.oficina = null;
						vm.modelo.fecha = null;
						vm.modelo['CONY'].documentoIdentidad[0].tipoDocumento.nombre = null;
						vm.modelo['CONY'].documentoIdentidad[0].numero = null;
						vm.modelo.nombre = null;
						vm.modelo.nombre2 = null;
						vm.modelo.apellido = null;
						vm.modelo.apellido2 = null;
					}

					$scope.consultaActa = function() {
						n_acta = vm.modelo.n_acta_adop;
						$http(
								{
									method : 'GET',
									url : '/web-nacimiento/existeActa/'
											+ vm.modelo.n_acta_adop
								}).then(function successCallback(n_acta) {
							console.log("n_acta " + n_acta);
							$scope.numAct = n_acta.data;
							if ($scope.numAct[null] == "false") {
								vm.abrirModalActa();
							}
						}, function errorCallback(error) {
							console.log("error: " + error.data);
						});
					}

					console.log(vm.modelo['ADO']);
					$scope.numeroControl = 0;
					$scope.setCedula = function() {
						if (vm.modelo['ADO'] != null
								&& $scope.numeroControl < 1) {
							$scope.tipoDocumento = vm.modelo['ADO'].documentoIdentidad[0].tipoDocumento.codigo;
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
						}
						vm.modelo['ADO'].documentoIdentidad[0].numero = $scope.num;
						return $scope.num;
					}

				})
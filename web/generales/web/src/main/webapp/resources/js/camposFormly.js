/**
 * Módulo con la definición de los campos formly personalizados para la
 * aplicacion
 */
var camposFormly = angular.module('camposFormly', [ 'formly', 'ngMessages',
		'formlyBootstrap', 'ngSanitize', 'ui.bootstrap', 'validaciones',
		'sw.calendar' ]);
camposFormly
		.config(function($provide, formlyConfigProvider) {
			// deshabilita el ng- de maxlenght
			formlyConfigProvider.extras.ngModelAttrsManipulatorPreferUnbound = true;
			formlyConfigProvider
					.setWrapper({
						name : 'horizontalBootstrapLabel',
						template : [
								'<label for="{{::id}}" class="col-sm-{{options.data.columnas_etiqueta}} control-label">',
								'{{to.label}} {{to.required ? "*" : ""}}',
								'</label>',
								'<div class="col-sm-{{options.data.columnas_input}}" ng-class="{\'has-error\': model[options.key] == undefined || model[options.key]==\'\', \'has-success\':  model[options.key] != undefined && model[options.key]!=\'\'}">',
								'<div class="input-group">',
								'<formly-transclude></formly-transclude>',
								'<span class="input-group-addon" id="basic-addon1">',
								'<span class="glyphicon glyphicon-asterisk" ng-hide="model[options.key]!= undefined && model[options.key]!=\'\'"></span>',
								'<span class="glyphicon glyphicon-ok" ng-show="model[options.key]!= undefined && model[options.key]!=\'\'"></span>',
								'</span>', '</div>', '</div>' ].join(' ')
					});

			/**
			 * wrapper que ajusta los campos de seleccion de fecha de nacimiento
			 * a lo exigido en el kit de usabilidad
			 */
			formlyConfigProvider
					.setWrapper({
						name : 'calendarSelect',
						templateUrl : '/web-generales/pages/templates/formly/calendar_wrapper.html'
					});
			/**
			 * wrapper que ajusta los campos de entrada en el modal a los
			 * exigidos en el kit de usabilidad
			 */
			formlyConfigProvider
					.setWrapper({
						name : 'modalKitusabilidad',
						templateUrl : '/web-generales/pages/templates/formly/modal_wrapper.html'
					});

			formlyConfigProvider
					.setType({
						name : 'ui-select',
						extends : 'select',
						template : [
								'<ui-select ng-model="model[options.key]" theme="bootstrap" ng-required="{{to.required}}" ng-disabled="{{to.disabled}}" reset-search-input="false"> <ui-select-match placeholder="{{to.placeholder}}"> {{$select.selected[to.labelProp || \'name\']}} </ui-select-match> <ui-select-choices group-by="to.groupBy" repeat="option[to.valueProp || \'value\'] as option in to.options | filter: $select.search"> <div ng-bind-html="option[to.labelProp || \'name\'] | highlight: $select.search"></div> </ui-select-choices>',
								'</ui-select>' ].join(' '),
						wrapper : [ 'horizontalBootstrapLabel' ]
					});

			formlyConfigProvider
					.setType({
						name : 'mi-select',
						extends : 'select',
						wrapper : [ 'horizontalBootstrapLabel' ],
						defaultOptions : {
							validators : {
								requerido : function(viewValue, modelValue) {
									var valido = false;
									var value = modelValue;
									(modelValue == "" ? valido = false
											: valido = true);
									return valido;
								}
							}
						}
					});

			formlyConfigProvider.setType({
				name : 'horizontalInput',
				extends : 'input',
				wrapper : [ 'horizontalBootstrapLabel' ],
				defaultOptions : {
					validators : {
						cedula : function(viewValue, modelValue) {
							var value = modelValue || viewValue;
							return /^[0-9]*$/.test(value);
						}
					}
				}
			});

			formlyConfigProvider
					.setType({
						name : 'errorPage',
						templateUrl : '/web-autenticarCiudadano/pages/error/pagina_error.html'
					});
			formlyConfigProvider
					.setType({
						name : 'participanteSeleccionado',
						templateUrl : '/web-autenticarCiudadano/pages/templates/seleccionado.html'
					});
			formlyConfigProvider
					.setType({
						name : 'formatoNota',
						templateUrl : '/web-autenticarCiudadano/pages/templates/formatoNota.html'
					});
			formlyConfigProvider
					.setType({
						name : 'successPage',
						templateUrl : '/web-autenticarCiudadano/pages/success/success.html'
					});
			formlyConfigProvider
					.setType({
						name : 'encabezadoLista',
						templateUrl : '/web-autenticarCiudadano/pages/templates/encabezadoLista.html'
					});
			formlyConfigProvider
					.setType({
						name : 'declaracion',
						templateUrl : '/web-autenticarCiudadano/reportes/Declaracion_jurada.html'
					});

			formlyConfigProvider
					.setWrapper({
						name : 'horizontalBootstrapRadio',
						template : [
								'<span class="glyphicon glyphicon-user" style="color:{{options.data.color}}">',
								'</span>',

								'<label style="display:inline" display:none">',
								'{{to.label}}', '</label>', '<div>',
								'<formly-transclude></formly-transclude>',
								'</div>' ].join(' ')
					});
			formlyConfigProvider.setType({
				name : 'horizontalRadio',
				extends : 'radio',
				wrapper : [ 'horizontalBootstrapRadio' ]
			});

			formlyConfigProvider
					.setWrapper({
						name : 'horizontalAlineacionRadio',
						template : [
								'<span class="glyphicon glyphicon-user" style="color:{{options.data.color}}">',
								'</span>',

								'<label>', '{{to.label}}', '</label>', '<div>',
								'<formly-transclude></formly-transclude>',
								'</div>' ].join(' ')
					});
			formlyConfigProvider.setType({
				name : 'alineacionPregunta',
				extends : 'radio',
				wrapper : [ 'horizontalAlineacionRadio' ]
			});

			formlyConfigProvider
					.setWrapper({
						name : 'horizontalBootstrapHijosLabel',
						template : [
								'<label for="{{::id}}" class="col-sm-8 control-label">',
								'{{to.label}}', '</label>',
								'<div class="col-sm-2">',
								'<formly-transclude></formly-transclude>',
								'</div>' ].join(' ')
					});
			formlyConfigProvider.setType({
				name : 'checkHijoParticipante',
				extends : 'checkbox',
				defaultOptions : {
					templateOptions : {
						onClick : function(viewValue, modelValue, $scope) {
							if ($scope.options.key === 'HijoParticipante'
									&& $scope.model.HijoParticipante === true) {
								$scope.$parent.$root.submit();
							} else if (viewValue === false)
								$scope.$parent.$root.anterior();
						}
					}
				},
			});

			formlyConfigProvider
					.setType({
						name : 'radioMultiples',
						extends : 'radio',
						defaultOptions : {
							templateOptions : {
								onClick : function(viewValue, modelValue,
										$scope) {
									for (opcion in $scope.$parent.to.options) {
										$scope.$parent.model[$scope.$parent.to.options[opcion].value] = (viewValue === $scope.$parent.to.options[opcion].value);
									}
								}
							}
						}
					});

			formlyConfigProvider.setType({
				name : 'horizontalInputHijos',
				extends : 'input',
				wrapper : [ 'horizontalBootstrapHijosLabel' ]
			});

			formlyConfigProvider
					.setType({
						name : 'inputNumero',
						templateUrl : '/web-autenticarCiudadano/pages/templates/inputNumero.html',
						defaultOptions : {
							templateOptions : {
								onKeypress : function(viewValue, modelValue) {
									if ($scope.$parent.options.data.habilitado) {
										if ($scope.$parent.options.data.avanzarEnFalso === false && viewValue === "0") {
											$scope.$parent.options.data.habilitado = false;
											$scope.$parent.$root.submit();
										}else {
											$scope.$parent.options.data.habilitado = true;
										}
										return;
									}
								}
							},
							parsers : [ function aEntero(valor) {
								console.log('Convirtiendo a entero: '
										+ parseInt(valor));
								return parseInt(valor);
							} ],
							validators : {
								soloNumero : function(viewValue, modelValue) {
									var value = modelValue || viewValue;
									return /^([1-9][0-9]{0,1})$/.test(value); //return /^[0-9]*$/.test(value);
								}
							}
						}
					});
			
			formlyConfigProvider
			.setType({
				name : 'inputNumero1',
				templateUrl : '/web-autenticarCiudadano/pages/templates/inputNumero1.html',
				defaultOptions : {
					templateOptions : {
						onKeypress : function(viewValue, modelValue) {
							if ($scope.$parent.options.data.habilitado) {
								if ($scope.$parent.options.data.avanzarEnFalso === true && $scope.model.CantidadHijoConID === undefined) {
									$scope.$parent.options.data.habilitado = false;
									$scope.$parent.$root.submit();
								}else {
									$scope.$parent.options.data.habilitado = true;
								}
								return;
							}
						}
					},
					parsers : [ function aEntero(valor) {
						console.log('Convirtiendo a entero: '
								+ parseInt(valor));
						return valor;
					} ],
					validators : {
						soloNumero : function(viewValue, modelValue) {
							var value = modelValue || viewValue;
							return /^(0|[1-9][0-9]{0,1})$/.test(value); //return /^[0-9]*$/.test(value);
						},
						conIdmenorTotal : function(viewValue,
								modelValue, $scope) {
							var value = modelValue || viewValue;
							if ($scope.options.key === 'CantidadHijoConID'
									&& value > $scope.model.HijosFallecido) {
								return false;
							}
							return true;
						}
					}
				}
			});

			formlyConfigProvider
					.setType({
						name : 'validarConyugue',
						extends : 'radio',
						wrapper : [ 'horizontalBootstrapRadio' ],
						defaultOptions : {
							templateOptions : {
								onClick : function(viewValue, modelValue,
										$scope) {
									if ($scope.$parent.options.data.habilitado) {
										if (($scope.$parent.options.data.avanzarEnFalso && viewValue === false)
												|| viewValue === true) {
											$scope.$parent.options.data.habilitado = false;
											$scope.$parent.$root.submit();
										}
										if ($scope.$parent.options.data.regresarEnFalso) {
											$scope.$parent.options.data.habilitarRegreso = true;
										}
										return;
									}
									if ($scope.$parent.options.data.regresarEnFalso
											&& $scope.$parent.options.data.habilitarRegreso
											&& viewValue === false) {
										$scope.$parent.options.data.habilitarRegreso = false;
										$scope.$parent.options.data.habilitado = true;
										$scope.$parent.$root.anterior();
									}
								}
							},
							controller : function($scope) {
								console.log("Controlador del selector: "
										+ $scope.$root.seleccionado);
								if ($scope.$root.seleccionado === "COUND") {
									$scope.model.EstaFallecidoConID = true;
									$scope.options.templateOptions.disabled = true;
									// $scope.$parent.$root.submit();
									return;
								}
							}
						}
					});

			formlyConfigProvider
					.setType({
						name : 'validarParticipantes',
						extends : 'checkbox',
						defaultOptions : {
							templateOptions : {
								onClick : function(viewValue, modelValue,
										$scope) {
									if ($scope.options.key === 'HijoParticipante'
											&& $scope.model.HijoParticipante === true) {
										$scope.$parent.$root.submit();
									} else if ($scope.model.HijoParticipante === false)
										$scope.$parent.$root.anterior();
								}
							}
						},
						controller : function($scope) {
							console.log("Controlador del selector: "
									+ $scope.$root.seleccionado);
							if ($scope.$root.seleccionado === $scope.options.data.acronimo
									&& $scope.options.key === 'HijoParticipante') {
								$scope.model.HijoParticipante = true;
								$scope.options.templateOptions.disabled = true;
								$scope.$parent.$root.submit();
								return;
							}
							if ($scope.$root.seleccionado === $scope.options.data.acronimo) {
								$scope.options.templateOptions.disabled = true;
							}
						}
					});

			formlyConfigProvider
					.setType({
						name : 'inputNumeroEntidad',
						templateUrl : '/web-autenticarCiudadano/pages/templates/inputNumero.html',
						defaultOptions : {
							templateOptions : {
								onKeypress : function(viewValue, modelValue) {
								}
							},
							parsers : [ function aEntero(valor) {
								console.log('Convirtiendo a entero: '
										+ parseInt(valor));
								return parseInt(valor);
							} ],
							validators : {
								soloNumero : function(viewValue, modelValue) {
									var value = modelValue || viewValue;
									return /^[1-9]*$/.test(value);
								}
							}
						}
					});
			formlyConfigProvider
					.setType({
						name : 'selectFecha',
						template : "<br><br><div class='row col-sm-12'><sw-calendar label={{to.label}} horizontal='true' ng-model='model[options.key]'></sw-calendar></div>"
					});
			formlyConfigProvider
					.setType({
						name : 'checkRequerido',
						template : '<label ng-hide="options.data.esconder"><input type="checkbox" ng-click="to.onClick()" ng-required="to.required" ng-model="model[options.key]">&nbsp;&nbsp;{{to.label}}</label><br>'
					});

			formlyConfigProvider
					.setType({
						name : 'mSelect',
						extends : 'select',
						wrapper : [ 'modalKitusabilidad' ],
						defaultOptions : {
							validators : {
								requerido : function(viewValue, modelValue) {
									var valido = false;
									var value = modelValue;
									(modelValue == "" ? valido = false
											: valido = true);
									return valido;
								}
							}
						}
					});

			formlyConfigProvider
					.setType({
						name : 'inputKitModal',
						extends : 'input',
						wrapper : [ 'modalKitusabilidad' ],
						defaultOptions : {
							templateOptions : {
								onKeydown : function(viewValue, modelValue, $scope, event) {
									var excepciones = $scope.options.data.validacion.excepciones;
									for(var i = 0; i < excepciones.length; i++){
										if (event.key === excepciones[i])
											return;
									}
									var patron = new RegExp($scope.options.data.validacion.expReg);
									if (!patron.test(event.key)) {
										event.preventDefault();
										$scope.options.data.errorTecla = true;
										return;
									}
									$scope.options.data.errorTecla = false;
								},
								onBlur : function(viewValue, modelValue,
										$scope, event) {
									$scope.options.data.errorTecla = false;
								}
							},
							controller : function($scope, validaciones){
								$scope.options.data.validacion = validaciones[$scope.options.data.tipoValidacion];
								$scope.to.maxlength = $scope.options.data.validacion.maximo;
								$scope.to.minlength = $scope.options.data.validacion.minimo;
								$scope.to.placeholder = $scope.options.data.validacion.placeholder;
								$scope.options.data.popover.mensaje = $scope.options.data.validacion.mensaje;
							}
						}
					});

			formlyConfigProvider.setType({
				name : 'textareaKitModal',
				extends : 'textarea',
				wrapper : ['modalKitusabilidad'],
				defaultOptions : {
					templateOptions : {
						onKeydown : function(viewValue, modelValue, $scope, event) {
//							if($scope.model[$scope.options.key] !== undefined)
//								$scope.model[$scope.options.key] = $scope.model[$scope.options.key]+'*';
							if (event.key === "Backspace" || event.key === "Tab" || event.key === " ")
								return;
							var patron = new RegExp($scope.options.data.validacion.expReg);
							if (!patron.test(event.key)) {
								event.preventDefault();
								$scope.options.data.errorTecla = true;
								return;
							}
							
						},
						onBlur : function(viewValue, modelValue,
								$scope, event) {
							$scope.options.data.errorTecla = false;
						}
					},
					controller : function($scope, validaciones){
						$scope.options.data.validacion = validaciones[$scope.options.data.tipoValidacion];
						$scope.to.maxlength = $scope.options.data.validacion.maximo;
						$scope.to.minlength = $scope.options.data.validacion.minimo;
						$scope.to.placeholder = $scope.options.data.validacion.placeholder;
						$scope.options.data.popover.mensaje = $scope.options.data.validacion.mensaje;
					}
				}				
			});
			formlyConfigProvider
					.setType({
						name : 'calendarSelect',
						extends : 'select',
						wrapper : [ 'calendarSelect' ],
						defaultOptions : {
							validators : {
								requerido : function(viewValue, modelValue) {
									var valido = false;
									var value = modelValue;
									(modelValue == "" ? valido = false
											: valido = true);
									return valido;
								}
							}
						}
					});
			formlyConfigProvider
					.setType({
						name : 'radioHorizontal',
						templateUrl : '/web-generales/pages/templates/formly/radio_horizontal.html'
					});

			/**
			 * Radiobutton con avance automático OBSERVACION: el template
			 * reconoce los boleanos como cadenas de texto
			 */
			formlyConfigProvider
					.setType({
						name : 'radioSiNoAutoSubmitHorizontal',
						templateUrl : "/web-generales/pages/templates/formly/radio_horizontal.html",
						link : function(scope, el, attrs) {
							scope.mClick = function() {
								if (scope.options.data.habilitado) {
									if ((scope.options.data.avanzarEnFalso && scope.model[scope.options.key] === "false")
											|| scope.model[scope.options.key] === "true") {
										scope.options.data.habilitado = false;
										scope.$root.submit();
										// cambia el valor del modelo a falso
										// para que permanezca en ese valor al
										// regresar
										// scope.model[scope.options.key] =
										// false;
									}
									if (scope.options.data.regresarEnFalso) {
										scope.options.data.habilitarRegreso = true;
									}
									return;
								}
								if (scope.options.data.regresarEnFalso
										&& scope.options.data.habilitarRegreso
										&& scope.model[scope.options.key] === "false") {
									scope.options.data.habilitarRegreso = false;
									scope.options.data.habilitado = true;
									scope.$root.anterior();
								}
							}
						}
					});

			/**
			 * Radiobutton con avance automático
			 */
			formlyConfigProvider
					.setType({
						name : 'radioSiNoAutoSubmit',
						extends : 'radio',
						wrapper : [ 'horizontalBootstrapRadio' ],
						defaultOptions : {
							templateOptions : {
								onClick : function(viewValue, modelValue,
										$scope) {
									if ($scope.$parent.options.data.habilitado) {
										if (($scope.$parent.options.data.avanzarEnFalso && viewValue === false)
												|| viewValue === true) {
											$scope.$parent.options.data.habilitado = false;
											$scope.$parent.$root.submit();
										}
										if ($scope.$parent.options.data.regresarEnFalso) {
											$scope.$parent.options.data.habilitarRegreso = true;
										}
										return;
									}
									if ($scope.$parent.options.data.regresarEnFalso
											&& $scope.$parent.options.data.habilitarRegreso
											&& viewValue === false) {
										$scope.$parent.options.data.habilitarRegreso = false;
										$scope.$parent.options.data.habilitado = true;
										$scope.$parent.$root.anterior();
									}
								}
							}
						}
					});

			formlyConfigProvider
					.setType({
						name : 'validarDeclaranteEnte',
						extends : 'checkbox',
						template : '<label><input type="checkbox" ng-required="to.required" ng-model="model[options.key]">&nbsp;&nbsp;{{to.label}}</label><br>',
						defaultOptions : {
							controller : function($scope) {
								if ($scope.model.Declarante === true) {
									$scope.model.MadreParticipante = true;
									$scope.options.templateOptions.disabled = true;
									return;
								}
							}
						}
					});

			/* Declarantes Abuelos */
			formlyConfigProvider
					.setType({
						name : 'checkboxKLista',
						extends : 'checkbox',
						template : '<label><input type="checkbox" ng-required="to.required" ng-model="model[options.key]">&nbsp;&nbsp;{{to.label}}</label><br>',
						defaultOptions : {
							templateOptions : {
								onClick : function(viewValue, modelValue,
										$scope) {
									if (viewValue === true) {
										console.log(''
												+ $scope.options.data.acronimo);
										$scope.model.lista += $scope.options.data.acronimo;
										$scope.model.lista += ",";
										console.log('Lista de declarantes: '
												+ $scope.model.lista);
									}
									if (viewValue === false
											|| viewValue === undefined) {
										$scope.model.lista = $scope.model.lista
												.replace(
														$scope.options.data.acronimo
																+ ",", "");
										console.log('Lista de declarantes: '
												+ $scope.model.lista);
									}
								}
							}
						}
					});

			/**
			 * Wrapper para los radios horizontales definidos en las vistas de
			 * autenticacion
			 */
			formlyConfigProvider.setWrapper({
				name : 'radioSarc',
				template : [ '<label class="radio-inline">',
						'<formly-transclude></formly-transclude>', '</label>' ]
						.join(' ')
			});

			/**
			 * Select que trae la informacion a partir de la consulta a una
			 * operacion del servicio
			 */
			formlyConfigProvider
					.setType({
						name : 'selectOtros',
						extends : 'select',
						wrapper : [ 'modalKitusabilidad' ],
						defaultOptions : {
							controller : function($scope, $http) {
								$http(
										{
											method : 'POST',
											url : $scope.options.data.ruta,
											data : {
												tramite : $scope.$root.objectSelected.tramite.codigo,
												tipoDeclarante : $scope.$root.objectSelected.solicitante.tipo
											}
										}).then(
										function successCallback(response) {
											$scope.to.options = [];
											console.log("consulta exitosa");
											$scope.to.options = response.data;
										}, function errorCallback(response) {
											console.log("error");
										});
							}
						}
					});

			formlyConfigProvider
			.setType({
				name : 'radioRequerido',
				templateUrl : '/web-generales/pages/templates/formly/radio_requerido.html'
			});
			/**
			 * Select que trae la informacion a partir de la consulta a una
			 * operacion del servicio
			 */
			formlyConfigProvider.setType({
				name : 'selectDocumentos',
				extends : 'select',
				wrapper : [ 'modalKitusabilidad' ],
				defaultOptions : {
					controller : function($scope, $http) {
						$http({
							method : 'POST',
							url : $scope.options.data.ruta,
							data : {}
						}).then(function successCallback(response) {
							console.log("consulta exitosa");

							$scope.to.options = response.data;
						}, function errorCallback(response) {
							console.log("error");
						});
					}
				}
			});

			/**
			 * Select generico que se carga directamente desde el servicio
			 */
			formlyConfigProvider
					.setType({
						name : 'selectServicio',
						extends : 'select',
						wrapper : [ 'modalKitusabilidad' ],
						defaultOptions : {
							controller : function($scope, $http) {
								// $scope.$watch($scope.options.data.referencia,
								// function(newValue,oldValue){
								// if(newValue !== oldValue){
								$http({
									method : 'POST',
									url : $scope.options.data.ruta,
									data : {}
								})
										.then(
												function successCallback(
														response) {
													console
															.log("consulta exitosa");
													// $scope.to.options=response.data;
													for (i = 0; i < response.data.length; i++) {
														var pais = {};
														var objetoPais = response.data[i];
														pais.name = objetoPais[$scope.options.data.name];
														pais.value = objetoPais[$scope.options.data.value];
														$scope.to.options
																.push(pais);
													}
												},
												function errorCallback(response) {
													console.log("error");
												});

								// }
								// });

							}
						}
					});

			/**
			 * Select generico que se carga directamente desde el servicio de
			 * acuerdo al cambio de otro campo del formulario
			 */
			formlyConfigProvider
					.setType({
						name : 'selectServicioReferencia',
						extends : 'select',
						wrapper : [ 'modalKitusabilidad' ],
						controller : function($scope, $http) {
							// $scope.referencia=$scope.model.pais;
							$scope
									.$watch(
											function() {
												return $scope.options.data.referencia;
											},
											function(newValue, oldValue) {
												if (newValue !== oldValue) {
													var datos = {};
													datos.codigo = newValue;
													$http(
															{
																method : 'POST',
																url : $scope.options.data.ruta,
																data : datos
															})
															.then(
																	function successCallback(
																			response) {
																		console
																				.log("consulta exitosa");
																		// $scope.to.options=response.data;
																		$scope.to.options = [];
																		for (i = 0; i < response.data.length; i++) {
																			var pais = {};
																			var objetoPais = response.data[i];
																			pais.name = objetoPais[$scope.options.data.name];
																			pais.value = objetoPais[$scope.options.data.value];
																			// $scope.to.options
																			// .push(pais);
																			$scope.to.options[i] = pais;
																			$scope.to.disabled = false;
																		}
																	},
																	function errorCallback(
																			response) {
																		console
																				.log("error");
																	});
												}
											});

						}
					});

			/**
			 * Select generico que se carga directamente desde el servicio de
			 * acuerdo al cambio de otro campo del formulario
			 */
			formlyConfigProvider
					.setType({
						name : 'selectReferencia',
						extends : 'select',
						wrapper : [ 'modalKitusabilidad' ],
						controller : function($scope, $http) {
							// $scope.referencia=$scope.model.pais;
							$scope.$watch(function() {
												return $scope.options.data.referencia;
											},
											function(newValue, oldValue) {
												if (newValue !== oldValue) {
													var datos = {};
													datos.codigo = newValue;
												}
											});

						}
					});
			
			
			formlyConfigProvider
			.setType({
				name : 'selectDia',
				extends : 'select',
				wrapper : [ 'calendarSelect' ],
				controller : 'diaController'
			});
			
			formlyConfigProvider
			.setType({
				name : 'selectMes',
				extends : 'select',
				wrapper : [ 'calendarSelect' ],
				controller : 'mesController'
			});

			formlyConfigProvider
			.setType({
				name : 'selectAno',
				extends : 'select',
				wrapper : [ 'calendarSelect' ],
				controller : 'anoController'
			});

			formlyConfigProvider.setType({
		        name: 'button1',
		        template: '<div><button type="{{::to.type}}" class="btn btn-{{::to.btnType}}" ng-click = "to.onClick($event)" >{{to.text}}</button></div>',
		        wrapper: ['bootstrapLabel'],
		        defaultOptions: {
		          templateOptions: {
		            btnType: 'success',
		            type: 'button'
//		            onClick : function(viewValue, modelValue, $scope, event) {
//		            	$scope.$parent.$parent.$parent.agregarMiembro();
//		            }
		          }
		        },
		        controller: function($scope) {
		        	$scope.to.onClick = $scope.$parent.$parent.$parent.$parent.agregarMiembro;
		        }
		    });
			
			

		});//fin provider





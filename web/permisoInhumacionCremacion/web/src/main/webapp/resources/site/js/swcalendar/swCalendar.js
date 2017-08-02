angular.module('sw.calendar', [])

.directive('swCalendar', function($parse) {
  return {

     scope:true,
	 require: 'ngModel',
     template:
    	 '<div class=" form-group" ng-class="{\'col-sm-8\': colwrap!=undefined,\'col-sm-12\': vertical!=undefined,\'col-sm-8\': horizontal!=undefined,\'has-default\': dia2== undefined || mes2== undefined || ano2== undefined, \'has-success\': dia2!= undefined && mes2!= undefined && ano2!= undefined}" >'
			
			         +'<input type="hidden" value="{{orientation}}"> ' 
					 +'<label class=" control-label"ng-class="{\'col-sm-5\': vertical==undefined}" >{{label}}{{ngModel}}</label>'     
			         +'<div class="input-group" >'
					 +'<span class="input-sm input-group-addon" >D\u00eda</span>'
					 +'<select class="input-sm form-control"  ng-model="dia2" ng-change="updateDia()" ng-options="dia for dia in dias " required >'
					 +' <option value="" selected="selected"></option>'
					 +'</select>'				
					 +'<span class="input-sm input-group-addon">Mes</span>'
					 +'<select class="input-sm form-control" ng-model="mes2" ng-change="updateMes()" ng-options="mes as mes.nombre for mes in meses track by mes.numero" required >'
					 +' <option value="" selected="selected"></option>'
					 +'</select>'
					 +'<span class="input-sm input-group-addon">A\u00f1o</span>'
					 +'<select class="input-sm form-control" ng-model="ano2" ng-change="updateAno()" ng-options="ano for ano in anos " required >'
					 +' <option value="" selected="selected"></option>'
					 +'</select>'
					 +'</div>'
					 +'</div>'
					,
					 
					   link: function(scope, element, attributes) {

						  scope.label=attributes.label;
						  var ho=attributes.horizontal;
						  var ve=attributes.vertical;
						 scope.colwrap=attributes.colwrap;
							  
							if(ve!=undefined&&ho!=undefined){
								
								if(sayTrue(ho)){
									
									 scope.horizontal=attributes.horizontal;
								}
							
							}
							else{
								
								
								if(ve!=undefined){
									
									if(sayTrue(ve)){
										scope.vertical=attributes.vertical;
									}
									
									
								}
								else{
									
									if(ho!=undefined){
										
										if(sayTrue(ho)){
										scope.horizontal=attributes.horizontal;
									}
									}
								}
							}
							
							
						  
						
						  
						 
						 
						 
						 function sayTrue(attr){
							 
							 return attr=="true";
						 }
						  function getMes(numeroMes){
							  
							   var nombreMes='';
							  switch(numeroMes)
							  {
								 
								  case 1:
								         nombreMes='01';
								  break;
								  case 2:
								         nombreMes='02';
								  break;
								  case 3:
								         nombreMes='03';
								  break;
								  case 4:
								         nombreMes='04';
								  break;
								  case 5:
								         nombreMes='05';
								  break;
								  case 6:
								         nombreMes='06';
								  break;
								  case 7:
								         nombreMes='07';
								  break;
								  case 8:
								         nombreMes='08';
								  break;
								  case 9:
								         nombreMes='09';
								  break;
								  case 10:
								         nombreMes='10';
								  break;
								  case 11:
								         nombreMes='11';
								  break;
								  case 12:
								         nombreMes='12';
								  break;
							  }
							 
							  return {numero:numeroMes,nombre:nombreMes};
						  }
						   
						    scope.dias = [];
						    var aqui=scope.dia+scope.id;
                            for(i=1;i<=31;i++){

                               scope.dias.push(i);
                            }
							 scope.meses = [];
                            for(i=1;i<=12;i++){

							
                               scope.meses.push(getMes(i));
                            }
							scope.anos = [];
                            for(i=1901;i<=2016;i++){

                               scope.anos.push(i);
                            }
							
							scope.updateAno=function updateAno()
							{
								
								var ano=scope.ano2;
								var mes=scope.mes2==undefined?scope.mes2:scope.mes2.numero;
								var dia=scope.dia2;
								if(ano!=undefined&&mes!=undefined&&dia!=undefined){
									
									 
									 var fecha=new Date(ano, mes-1, dia);
									 var model = $parse(attributes.ngModel);      
                                     model.assign(scope.$parent,fecha);
									
									 
									 
									
								    
									
									
								}
								
								var mesVacio=mes;
								var anoVacio=scope.ano2;
								//comprobamos la cantidad de elementos que tendria la lista de dias si el mes esta vacio
								if(mesVacio!=undefined){
									
									scope.dias.length=0;
									var lastDay = new Date(anoVacio, mesVacio, 0);
									var ultimoDia=lastDay.getDate();
									scope.dias.length=0;
									for(i=1;i<=ultimoDia;i++){
                                            scope.dias.push(i);
                                       }
									 
									 
									
									
								}
								
								
							}
							scope.updateDia=function updateDia()
							{
								var ano=scope.ano2;
								var mes=scope.mes2==undefined?scope.mes2:scope.mes2.numero;
								var dia=scope.dia2;
								if(ano!=undefined&&mes!=undefined&&dia!=undefined){
									
									 
									 var fecha=new Date(ano, mes-1, dia);
									 var model = $parse(attributes.ngModel);      
                                     model.assign(scope.$parent,fecha);
									
									
									 
									
								    
									
									
								}
								var mesVacio=scope.mes2;
								var anoVacio=scope.ano2;
								var diaValor=scope.dia2;
								if(mesVacio==undefined&&anoVacio!=undefined){
									
									
									isLeap = new Date(anoVacio, 1, 29).getMonth() == 1;
									//comprobar si el año es biciestro;
									if(isLeap){
										
										if(diaValor==31){
										
										scope.meses=[getMes(1),getMes(3),getMes(5),getMes(7),getMes(8),getMes(10),getMes(12)];
										
										
									}
									if(diaValor==30){
										
										scope.meses=[getMes(1),getMes(3),getMes(4),getMes(5),getMes(6),getMes(7),getMes(8),getMes(9),getMes(10),getMes(11),getMes(12)];
										
									}
									if(diaValor<30){
										if(diaValor)
										
										scope.meses=[getMes(1),getMes(2),getMes(3),getMes(4),getMes(5),getMes(6),getMes(7),getMes(8),getMes(9),getMes(10),getMes(11),getMes(12)];
									}
									
										
										
									}
									else{
										
										if(diaValor==31){
										
										scope.meses=[getMes(1),getMes(3),getMes(5),getMes(7),getMes(8),getMes(10),getMes(12)];
										
										
									}
									if(diaValor==30||diaValor==29){
										
										scope.meses=[getMes(1),getMes(3),getMes(4),getMes(5),getMes(6),getMes(7),getMes(8),getMes(9),getMes(10),getMes(11),getMes(12)];
										
									}
									
									if(diaValor<29){
									
										
										scope.meses=[getMes(1),getMes(2),getMes(3),getMes(4),getMes(5),getMes(6),getMes(7),getMes(8),getMes(9),getMes(10),getMes(11),getMes(12)];
									}
										
										
									}
									
									
									
									
								}
								if(mesVacio==undefined&&anoVacio==undefined){
									
									
									
									if(diaValor==31){
										
										scope.meses=[getMes(1),getMes(3),getMes(5),getMes(7),getMes(8),getMes(10),getMes(12)];
										
										
									}
									if(diaValor==30){
										
										scope.meses=[getMes(1),getMes(3),getMes(4),getMes(5),getMes(6),getMes(7),getMes(8),getMes(9),getMes(10),getMes(11),getMes(12)];
										
									}
									if(diaValor<30){
										
										
										scope.meses=[getMes(1),getMes(2),getMes(3),getMes(4),getMes(5),getMes(6),getMes(7),getMes(8),getMes(9),getMes(10),getMes(11),getMes(12)];
									}
									
									
									
								}
								
								if(mesVacio!=undefined){
									
									if(diaValor==31){
										
										scope.meses=[getMes(1),getMes(3),getMes(5),getMes(7),getMes(8),getMes(10),getMes(12)];
										
										
									}
									if(diaValor==30){
										
										scope.meses=[getMes(1),getMes(3),getMes(4),getMes(5),getMes(6),getMes(7),getMes(8),getMes(9),getMes(10),getMes(11),getMes(12)];
										
									}
									if(diaValor<30){
										
										
										scope.meses=[getMes(1),getMes(2),getMes(3),getMes(4),getMes(5),getMes(6),getMes(7),getMes(8),getMes(9),getMes(10),getMes(11),getMes(12)];
									}
									
									
								}
							}
							scope.updateMes=function updateMes()
							{
								var ano=scope.ano2;
								var mes=scope.mes2.numero;
								var dia=scope.dia2;
								if(ano!=undefined&&mes!=undefined&&dia!=undefined){
									
									 
									 var fecha=new Date(ano, mes-1, dia);
									 var model = $parse(attributes.ngModel);      
                                     model.assign(scope.$parent,fecha);									
									
									 
									
								    
									
									
								}
								var mesVacio=scope.mes2.numero;
								var anoVacio=scope.ano2;
								//comprobamos la cantidad de elementos que tendria la lista de dias si el mes esta vacio
								if(mesVacio!=undefined&&anoVacio==undefined){
									
									scope.dias.length=0;
									if(mesVacio==1||mesVacio==3||mesVacio==5||mesVacio==7||mesVacio==8||mesVacio==10||mesVacio==12){
										 for(i=1;i<=31;i++){
                                            scope.dias.push(i);
                                           }
										
									}
									if(mesVacio==2){
										
										for(i=1;i<=29;i++){
                                            scope.dias.push(i);
                                           }
									}
									if(mesVacio==4||mesVacio==6||mesVacio==9||mesVacio==11){
										
										for(i=1;i<=30;i++){
                                            scope.dias.push(i);
                                           }
										
									}
									
									
									
								}
								if(mesVacio!=undefined&&anoVacio!=undefined){
									
									var lastDay = new Date(anoVacio, mesVacio, 0);
									var ultimoDia=lastDay.getDate();
									scope.dias.length=0;
									for(i=1;i<=ultimoDia;i++){
                                            scope.dias.push(i);
                                           }
									
									
								}
								
							}
							
							
							
						   
						    
					   }
  };
})

.directive('swCalendarExtended', function($parse) {
	return {

		scope:true,
		require: 'ngModel',
		template:
			'	<div class="col-sm-12">'
			+'		<div class="col-sm-4">'
			+'			<div class=" form-group" ng-class="{\'has-default\': dia2== undefined || mes2== undefined || ano2== undefined || hora2==undefined || minuto2== undefined, \'has-success\': dia2!= undefined && mes2!= undefined && ano2!= undefined && hora2!= undefined && minuto2!=undefined}" >'
			+'				<input type="hidden" value="{{orientation}}"> ' 
			+'				<label class="control-label">{{label}}{{ngModel}}</label>'     
			+'			</div>'//cierro FORM-GROUP
			+'		</div>'//cierro col del label
			+'		<div class="col-sm-8">'
			+'			<div class="input-group" >'
			+'				<span class="input-sm input-group-addon" >D&iacute;a</span>'
			+'				<select class="input-sm form-control"  ng-model="dia2" ng-change="updateDia()" ng-options="dia for dia in dias " required >'
			+' 					<option value="" selected="selected"></option>'
			+'				</select>'				
			+'				<span class="input-sm input-group-addon">Mes</span>'
			+'				<select class="input-sm form-control" ng-model="mes2" ng-change="updateMes()" ng-options="mes as mes.nombre for mes in meses track by mes.numero" required >'
			+' 					<option value="" selected="selected"></option>'
			+'				</select>'
			+'				<span class="input-sm input-group-addon">A&ntilde;o</span>'
			+'				<select class="input-sm form-control" ng-model="ano2" ng-change="updateAno()" ng-options="ano for ano in anos " required >'
			+' 					<option value="" selected="selected"></option>'
			+'				</select>'		
			+'			</div>'		
			+'<br>'		
			+'			<div class=" input-group col-sm-5 pull-left" ng-class="{\'has-default\': dia2== undefined || mes2== undefined || ano2== undefined || hora2==undefined || minuto2== undefined, \'has-success\': dia2!= undefined && mes2!= undefined && ano2!= undefined && hora2!= undefined && minuto2!=undefined}" >'	
			+'				<span class="input-sm input-group-addon">Hora</span>'
			+'				<select class="input-sm form-control" ng-model="hora2" ng-change="updateHora()" ng-options="hora for hora in horas" required >'
			+' 					<option value="" selected="selected"></option>'
			+'				</select>'
			+'				<span class="input-sm input-group-addon">Min</span>'
			+'				<select class="input-sm form-control" ng-model="minuto2" ng-change="updateAno()" ng-options="min for min in minutos " required >'
			+' 					<option value="" selected="selected"></option>'
			+'				</select>'
			+'			</div>'
			+'		</div>'
			+'	</div>'//cierro row
			,

			link: function(scope, element, attributes) {

				scope.label=attributes.label;
				var ho=attributes.horizontal;
				var ve=attributes.vertical;
				var ex=attributes.extendida;
				scope.colwrap=attributes.colwrap;

				if(ve!=undefined&&ho!=undefined){
					if(sayTrue(ho)){
						scope.horizontal=attributes.horizontal;
					}
				}
				else{
					if(ve!=undefined){
						if(sayTrue(ve)){
							scope.vertical=attributes.vertical;
						}
					}
					else{
						if(ho!=undefined){
							if(sayTrue(ho)){
								scope.horizontal=attributes.horizontal;
							}
						}
					}
				}
				function sayTrue(attr){
					return attr=="true";
				}
				function getMes(numeroMes){
					var nombreMes='';
					switch(numeroMes)
					{
					case 1:
						nombreMes='01';
						break;
					case 2:
						nombreMes='02';
						break;
					case 3:
						nombreMes='03';
						break;
					case 4:
						nombreMes='04';
						break;
					case 5:
						nombreMes='05';
						break;
					case 6:
						nombreMes='06';
						break;
					case 7:
						nombreMes='07';
						break;
					case 8:
						nombreMes='08';
						break;
					case 9:
						nombreMes='09';
						break;
					case 10:
						nombreMes='10';
						break;
					case 11:
						nombreMes='11';
						break;
					case 12:
						nombreMes='12';
						break;
					}
					return {numero:numeroMes,nombre:nombreMes};
				}

				scope.minutos = [];
				for(i=0;i<60;i++){
					scope.minutos.push(i);
				}

				scope.horas = [];
				for(i=0;i<=23;i++){
					scope.horas.push(i);
				}

				scope.dias = [];
				var aqui=scope.dia+scope.id;

				for(i=1;i<=31;i++){
					scope.dias.push(i);
				}

				scope.meses = [];
				for(i=1;i<=12;i++){
					scope.meses.push(getMes(i));
				}

				scope.anos = [];
				for(i=1901;i<=2016;i++){
					scope.anos.push(i);
				}

				scope.updateAno=function updateAno()
				{
					var ano=scope.ano2;
					var mes=scope.mes2==undefined?scope.mes2:scope.mes2.numero;
					var dia=scope.dia2;
					var hora=scope.hora2;
					var minuto=scope.minuto2;
					if(ano!=undefined&&mes!=undefined&&dia!=undefined&&hora!=undefined&&minuto!=undefined){
						var fecha=new Date(ano, mes-1, dia, hora, minuto);
						var model = $parse(attributes.ngModel);      
						model.assign(scope.$parent,fecha);
					}

					var mesVacio=mes;
					var anoVacio=scope.ano2;
					//comprobamos la cantidad de elementos que tendria la lista de dias si el mes esta vacio
					if(mesVacio!=undefined){

						scope.dias.length=0;
						var lastDay = new Date(anoVacio, mesVacio, 0);
						var ultimoDia=lastDay.getDate();
						scope.dias.length=0;
						for(i=1;i<=ultimoDia;i++){
							scope.dias.push(i);
						}
					}
				}

				scope.updateHora=function updateHora()
				{
					var ano=scope.ano2;
					var mes=scope.mes2==undefined?scope.mes2:scope.mes2.numero;
					var dia=scope.dia2;
					var hora=scope.hora2;
					var minuto=scope.minuto2;
					if(ano!=undefined&&mes!=undefined&&dia!=undefined&&hora!=undefined&&minuto!=undefined){
						var fecha=new Date(ano, mes-1, dia, hora, minuto);
						var model = $parse(attributes.ngModel);      
						model.assign(scope.$parent,fecha);
					}

					var mesVacio=mes;
					var anoVacio=scope.ano2;
					//comprobamos la cantidad de elementos que tendria la lista de dias si el mes esta vacio
					if(mesVacio!=undefined){

						scope.dias.length=0;
						var lastDay = new Date(anoVacio, mesVacio, 0);
						var ultimoDia=lastDay.getDate();
						scope.dias.length=0;
						for(i=1;i<=ultimoDia;i++){
							scope.dias.push(i);
						}
					}
				}

				scope.updateDia=function updateDia()
				{
					var ano=scope.ano2;
					var mes=scope.mes2==undefined?scope.mes2:scope.mes2.numero;
					var dia=scope.dia2;
					var hora=scope.hora2;
					var minuto=scope.minuto2;
					if(ano!=undefined&&mes!=undefined&&dia!=undefined&&hora!=undefined&&minuto!=undefined){
						var fecha=new Date(ano, mes-1, dia, hora, minuto);
						var model = $parse(attributes.ngModel);      
						model.assign(scope.$parent,fecha);
					}
					var mesVacio=scope.mes2;
					var anoVacio=scope.ano2;
					var diaValor=scope.dia2;

					if(mesVacio==undefined&&anoVacio!=undefined){
						isLeap = new Date(anoVacio, 1, 29).getMonth() == 1;
						//comprobar si el año es biciestro;
						if(isLeap){
							if(diaValor==31){
								scope.meses=[getMes(1),getMes(3),getMes(5),getMes(7),getMes(8),getMes(10),getMes(12)];
							}
							if(diaValor==30){
								scope.meses=[getMes(1),getMes(3),getMes(4),getMes(5),getMes(6),getMes(7),getMes(8),getMes(9),getMes(10),getMes(11),getMes(12)];
							}
							if(diaValor<30){
								if(diaValor)
									scope.meses=[getMes(1),getMes(2),getMes(3),getMes(4),getMes(5),getMes(6),getMes(7),getMes(8),getMes(9),getMes(10),getMes(11),getMes(12)];
							}
						}
						else{
							if(diaValor==31){
								scope.meses=[getMes(1),getMes(3),getMes(5),getMes(7),getMes(8),getMes(10),getMes(12)];
							}
							if(diaValor==30||diaValor==29){
								scope.meses=[getMes(1),getMes(3),getMes(4),getMes(5),getMes(6),getMes(7),getMes(8),getMes(9),getMes(10),getMes(11),getMes(12)];
							}
							if(diaValor<29){
								scope.meses=[getMes(1),getMes(2),getMes(3),getMes(4),getMes(5),getMes(6),getMes(7),getMes(8),getMes(9),getMes(10),getMes(11),getMes(12)];
							}
						}
					}
					if(mesVacio==undefined&&anoVacio==undefined){
						if(diaValor==31){
							scope.meses=[getMes(1),getMes(3),getMes(5),getMes(7),getMes(8),getMes(10),getMes(12)];
						}
						if(diaValor==30){
							scope.meses=[getMes(1),getMes(3),getMes(4),getMes(5),getMes(6),getMes(7),getMes(8),getMes(9),getMes(10),getMes(11),getMes(12)];
						}
						if(diaValor<30){
							scope.meses=[getMes(1),getMes(2),getMes(3),getMes(4),getMes(5),getMes(6),getMes(7),getMes(8),getMes(9),getMes(10),getMes(11),getMes(12)];
						}
					}

					if(mesVacio!=undefined){
						if(diaValor==31){
							scope.meses=[getMes(1),getMes(3),getMes(5),getMes(7),getMes(8),getMes(10),getMes(12)];
						}
						if(diaValor==30){
							scope.meses=[getMes(1),getMes(3),getMes(4),getMes(5),getMes(6),getMes(7),getMes(8),getMes(9),getMes(10),getMes(11),getMes(12)];
						}
						if(diaValor<30){
							scope.meses=[getMes(1),getMes(2),getMes(3),getMes(4),getMes(5),getMes(6),getMes(7),getMes(8),getMes(9),getMes(10),getMes(11),getMes(12)];
						}
					}
				}

				scope.updateMes=function updateMes()
				{
					var ano=scope.ano2;
					var mes=scope.mes2.numero;
					var dia=scope.dia2;
					var hora=scope.hora2;
					var minuto=scope.minuto2;
					if(ano!=undefined&&mes!=undefined&&dia!=undefined&&hora!=undefined&&minuto!=undefined){
						var fecha=new Date(ano, mes-1, dia, hora, minuto);
						var model = $parse(attributes.ngModel);      
						model.assign(scope.$parent,fecha);
					}
					var mesVacio=scope.mes2.numero;
					var anoVacio=scope.ano2;
					//comprobamos la cantidad de elementos que tendria la lista de dias si el mes esta vacio
					if(mesVacio!=undefined&&anoVacio==undefined){

						scope.dias.length=0;
						if(mesVacio==1||mesVacio==3||mesVacio==5||mesVacio==7||mesVacio==8||mesVacio==10||mesVacio==12){
							for(i=1;i<=31;i++){
								scope.dias.push(i);
							}

						}
						if(mesVacio==2){

							for(i=1;i<=29;i++){
								scope.dias.push(i);
							}
						}
						if(mesVacio==4||mesVacio==6||mesVacio==9||mesVacio==11){

							for(i=1;i<=30;i++){
								scope.dias.push(i);
							}
						}
					}
					if(mesVacio!=undefined&&anoVacio!=undefined){

						var lastDay = new Date(anoVacio, mesVacio, 0);
						var ultimoDia=lastDay.getDate();
						scope.dias.length=0;
						for(i=1;i<=ultimoDia;i++){
							scope.dias.push(i);
						}
					}
				}
			}
	};
});










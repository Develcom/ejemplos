angular.module('sw.calendar', [])

.directive('swCalendar', function($parse) {
  return {
	  require: 'ngModel',
      scope: {
		modelo : '=ngModel'
      },
     template:
	 '<div class=" form-group" ng-class="{\'col-sm-4\': colwrap!=undefined,\'col-sm-12\': vertical!=undefined,\'col-sm-6\': horizontal!=undefined,\'has-default\': dia2== undefined || mes2== undefined || ano2== undefined, \'has-success\': dia2!= undefined && mes2!= undefined && ano2!= undefined}" >'
			
			         +'<div class="input-group" >'
					 +'<span class="input-sm input-group-addon" >Día</span>'
					 +'<select class="input-sm form-control"  ng-model="dia2" ng-change="updateDia()" ng-options="dia for dia in dias " required >'
					 +' <option value="" selected="selected"></option>'
					 +'</select>'				
					 +'<span class="input-sm input-group-addon">Mes</span>'
					 +'<select class="input-sm form-control" ng-model="mes2" ng-change="updateMes()" ng-options="mes as mes.nombre for mes in meses track by mes.numero" required >'
					 +' <option value="" selected="selected"></option>'
					 +'</select>'
					 +'<span class="input-sm input-group-addon">Año</span>'
					 +'<select class="input-sm form-control" ng-model="ano2" ng-change="updateAno()" ng-options="ano for ano in anos " required >'
					 +' <option value="" selected="selected"></option>'
					 +'</select>'
					 +'</div>'
					 +'</div>'
					,
					 
					   link: function(scope, element, attributes) {
						  var lfecha = new Date(scope.modelo);
                          scope.dia2 =  lfecha.getDate();
                          scope.mes2 = getMes(lfecha.getMonth() + 1);
                          scope.ano2 = lfecha.getFullYear();
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
                            for(i=1901;i<=2045;i++){

                               scope.anos.push(i);
                            }
							
							scope.updateAno=function updateAno()
							{
								
								var ano=scope.ano2;
								var mes=scope.mes2==undefined?scope.mes2:scope.mes2.numero;
								var dia=scope.dia2;
								if(ano!=undefined&&mes!=undefined&&dia!=undefined){
									
									 
									 var fecha=new Date(ano, mes-1, dia);
									
									scope.modelo = fecha;
									 
									 
									
								    
									
									
								}
								
								var mesVacio=mes;
								var anoVacio=scope.ano2;
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
									
										scope.modelo = fecha;
									
									
									 
									
								    
									
									
								}
								var mesVacio=scope.mes2;
								var anoVacio=scope.ano2;
								var diaValor=scope.dia2;
								if(mesVacio==undefined&&anoVacio!=undefined){
									
									
									isLeap = new Date(anoVacio, 1, 29).getMonth() == 1;
									
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
										scope.modelo = fecha;
									
									 
									
								    
									
									
								}
								var mesVacio=scope.mes2.numero;
								var anoVacio=scope.ano2;
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

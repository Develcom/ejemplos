/**
 * 
 */
(function(){
	'use-strict';
	var app=angular.module('validaciones',[]);

	App.constant('validaciones',{
		'cedula':{
			maximo : '9',
			minimo : '6',
			expReg : '^[0-9]',
			mensaje : 'Este campo admite solo n\u00fameros',
			placeholder : 'Ejemplo: 123456',
			excepciones : ['Backspace', 'Tab']			
		},
		'pasaporte':{
			maximo : '11', 
			minimo : '6',
			expReg : '^[0-9]',
			mensaje : 'Este campo admite solo n\u00fameros',
			placeholder : 'Ejemplo: 123456',
			excepciones : ['Backspace', 'Tab']			
		},
		'primerNombre' : {
			maximo : '50',
			minimo : '2',
			expReg : '^[A-Za-z\-\'\x7f-\xff\s]$',
			mensaje : 'Este campo solo permite letras, di\u00e9resis, ap\u00f3strofes, guiones y acentos',
			placeholder : 'Ingrese el nombre',
			excepciones : ['Backspace', ' ', 'Tab', 'Delete']						
		},
		'segundoNombre' : {
			maximo : '255',
			minimo : '2',
			expReg : '^[A-Za-z\-\'\x7f-\xff\s]$',
			mensaje : 'Este campo solo permite letras, di\u00e9resis, ap\u00f3strofes, guiones y acentos',
			placeholder : 'opcional',
			excepciones : ['Backspace', ' ', 'Tab', 'Delete']						
		},
		'primerApellido' : {
			maximo : '50',
			minimo : '2',
			expReg : '^[A-Za-z\-\'\x7f-\xff\s]$',
			mensaje : 'Este campo solo permite letras, di\u00e9resis, ap\u00f3strofes, guiones y acentos',
			placeholder : 'Ingrese el apellido',
			excepciones : ['Backspace', ' ', 'Tab', 'Delete']			
		},
		'segundoApellido' : {
			maximo : '50',
			minimo : '2',
			expReg : '^[A-Za-z\-\'\x7f-\xff\s]$',
			mensaje : 'Este campo solo permite letras, di\u00e9resis, ap\u00f3strofes, guiones y acentos',
			placeholder : 'opcional',
			excepciones : ['Backspace', ' ', 'Tab', 'Delete']			
		},
		'telefono' : {
			maximo : '12',
			minimo : '12',
			expReg : '^[0-9\-]+$',
			mensaje : 'Este campo admite solo n\u00fameros y guiones',
			placeholder : 'Ejemplo: 0212-11111111',
			excepciones : ['Backspace', ' ', 'Tab']						
		},
		'direccion' : {
			maximo : '300',
			minimo : '2',
			expReg : '^[A-Za-z0-9\-\'\x7f-\xff\s]$',
			mensaje : 'Este campo solo permite letras, di\u00e9resis, ap\u00f3strofes, guiones, acentos y numeros',
			placeholder : 'Ejemplo: Av. Venezuela',
			excepciones : ['Backspace', ' ', 'Tab', 'Delete']			
		},
		'tipoDocumento' : {
			maximo : '200',
			minimo : '2',
			expReg : '^[A-Za-z\-\'\x7f-\xff\s]$',
			mensaje : 'Este campo solo permite letras, di\u00e9resis, ap\u00f3strofes, guiones y acentos',
			placeholder : 'Ejemplo: Licencia deconducir',
			excepciones : ['Backspace', ' ', 'Tab']						
		},
		'cargoMiembroCC' : {
			maximo : '200',
			minimo : '2',
			expReg : '^[A-Za-z0-9\-\'\x7f-\xff\s]$',
			titulo : 'Formato cargo',
			mensaje : 'Este campo solo permite letras, di\u00e9resis, ap\u00f3strofes, guiones, acentos y n\u00fameros',
			placeholder : 'Ejemplo: Secretario',
			excepciones : ['Backspace', 'Tab', ' ']			
		},
		'observaciones' : {
			maximo : '200',
			minimo : '2',
			expReg : '^[A-Za-z0-9\-\'\x7f-\xff\s]$',
			mensaje : 'Este campo solo permite letras, di\u00e9resis, ap\u00f3strofes, guiones, acentos y n\u00fameros',
			titulo : 'Formato de observaciones',
			placeholder : 'Ingresar motivo de la inconformidad',
			excepciones : ['Backspace', 'Tab', ' ', 'Delete']
		},
		'numMPPS':{
			maximo : '6',
			minimo : '6',
			expReg : '^[0-9]',
			mensaje : 'Este campo admite solo n\u00fameros',
			placeholder : 'Ejemplo: 123456',
			excepciones : ['Backspace', 'Tab']
		},
		'acta':{
			maximo : '4',
			minimo : '20',
			expReg : '^[0-9]',
			mensaje : 'Este campo admite solo n\u00fameros',
			placeholder : 'Ejemplo: 123456',
			excepciones : ['Backspace', 'Tab']
		},
		'folio':{
			maximo : '5',
			minimo : '5',
			expReg : '^[0-9]',
			mensaje : 'Este campo admite solo n\u00fameros',
			placeholder : 'Ejemplo: 123456',
			excepciones : ['Backspace', 'Tab']
		},
		'tomo':{
			maximo : '2',
			minimo : '2',
			expReg : '^[0-9]',
			mensaje : 'Este campo admite solo n\u00fameros',
			placeholder : 'Ejemplo: 123456',
			excepciones : ['Backspace', 'Tab']
		},
		'numCert':{
			maximo : '8',
			minimo : '8',
			expReg : '^[0-9]',
			mensaje : 'Este campo admite solo n\u00fameros',
			placeholder : 'Ejemplo: 123456',
			excepciones : ['Backspace', 'Tab', 'Delete']
		},
		'edad':{
			maximo : '1',
			minimo : '3',
			expReg : '^[0-9]',
			mensaje : 'Este campo admite solo n\u00fameros',
			placeholder : 'Ejemplo: 100',
			excepciones : ['Backspace', 'Tab']
		},
		'sentencia':{
			maximo : '200',
			minimo : '2',
			expReg : '^[A-Za-z0-9\-\'\x7f-\xff\s]$',
			mensaje : 'Este campo solo permite letras, di\u00e9resis, ap\u00f3strofes, guiones, acentos y n\u00fameros',
			titulo : 'Formato de observaciones',
			placeholder : 'Ejemplo: Error',
			excepciones : ['Backspace', 'Tab', ' ']
		},
		'cementerio':{
			maximo : '200',
			minimo : '2',
			expReg : '^[A-Za-z0-9\-\'\x7f-\xff\s]$',
			mensaje : 'Este campo solo permite letras, di\u00e9resis, ap\u00f3strofes, guiones, acentos y n\u00fameros',
			placeholder : 'Ejemplo: Nombre del cementerio',
			excepciones : ['Backspace', 'Tab', 'Delete', ' ']
		}
		


	});
		
	
})();

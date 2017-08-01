#Servicio: Acta

##Requerimientos:

* Java 7 (jdk)

##Operaciones:

1. **consultarActa:** 
    * ***Descripcion:*** Busca un acta segun informacion suministrada.
    * ***Endpoint:*** /acta/consultarActa/
    * ***Método:*** GET
    * ***Entrada:*** Acta
    * ***Salida:*** Acta
    
2. **consultarActaLista:** 
    * ***Descripcion:*** Busca un acta segun informacion suministrada.
    * ***Endpoint:*** /acta/consultarActa/lista
    * ***Método:*** GET
    * ***Entrada:*** Acta
    * ***Salida:*** Acta

3. **actualizarActa:** 
    * ***Descripcion:*** Actualiza un Acta.
    * ***Endpoint:*** /acta/actualizarActa
    * ***Método:*** PUT
    * ***Entrada:*** Acta
    * ***Salida:*** boolean

4. **validarCertificadoMedico:** 
    * ***Descripcion:*** Consulta si está registrado número de certificado medico.
    * ***Endpoint:*** /acta/validarCertificadoMedico/
    * ***Método:*** GET
    * ***Entrada:*** codigoTramite, numeroCertificado
    * ***Salida:*** boolean

5. **existeActa:** 
    * ***Descripcion:*** Verifica si existe un acta.
    * ***Endpoint:*** /acta/existeActa/
    * ***Método:*** GET
    * ***Entrada:*** numeroActa
    * ***Salida:*** Map<String, String> 
	
6. **guardarORE:** 
    * ***Descripcion:*** Guardar Ore.
    * ***Endpoint:*** /acta/guardarORE/
    * ***Método:*** POST
    * ***Entrada:*** numSolicitud
    * ***Salida:*** Ore

7. **consultarOre:** 
    * ***Descripcion:*** Consulta un Ore.
    * ***Endpoint:*** /acta/consultarOre
    * ***Método:*** GET
    * ***Entrada:*** numSolicitud
    * ***Salida:*** Ore
	
8. **actualizarEstatusActa:**
    * ***Descripcion:*** Actualizar el estatus del Acta
    * ***Endpoint:*** /acta/actualizarEstatusActa
    * ***Método:*** POST
    * ***Entrada:*** codigoEstatusActa, acta
    * ***Salida:*** Acta
	
9. **guardarInsercion:**
    * ***Descripcion:*** Guarda la Insercion
    * ***Endpoint:*** /acta/guardarInsercion
    * ***Método:*** POST
    * ***Entrada:*** insercion
    * ***Salida:*** boolean
	
10. **consultarInsercion:**
    * ***Descripcion:*** Consulta la Insercion
    * ***Endpoint:*** /acta/consultarInsercion
    * ***Método:*** GET
    * ***Entrada:*** numSolicitud
    * ***Salida:*** Insercion
    
11. **guardarMedidaProteccion:**
    * ***Descripcion:*** Guarda Medida Proteccion
    * ***Endpoint:*** /acta/guardarMedidaProteccion
    * ***Método:*** POST
    * ***Entrada:*** medidaProteccion
    * ***Salida:*** boolean
	
12. **consultarMedidaProteccion**
    * ***Descripcion:*** Consulta la Medida de Proteccion
    * ***Endpoint:*** /acta/consultarMedidaProteccion
    * ***Método:*** GET
    * ***Entrada:*** numSolicitud
    * ***Salida:*** MedidaProteccion

13. **guardarExtemporanea:**
    * ***Descripcion:*** Guarda Extemporanea
    * ***Endpoint:*** /acta/guardarExtemporanea
    * ***Método:*** POST
    * ***Entrada:*** extemporanea
    * ***Salida:*** boolean
	
14. **consultarExtemporanea**
    * ***Descripcion:*** Consultar Extemporanea
    * ***Endpoint:*** /acta/consultarExtemporanea
    * ***Método:*** GET
    * ***Entrada:*** numSolicitud
    * ***Salida:*** Extemporanea
    
15. **guardarDecisionJudicial:**
    * ***Descripcion:*** Guarda la Decision Judicial
    * ***Endpoint:*** /acta/guardarDecisionJudicial
    * ***Método:*** POST
    * ***Entrada:*** decisionJudicial
    * ***Salida:*** boolean
	
16. **consultarDecisionJudicial**
    * ***Descripcion:*** Consulta la Decision Judicial
    * ***Endpoint:*** /acta/consultarDecisionJudicial
    * ***Método:*** GET
    * ***Entrada:*** numSolicitud
    * ***Salida:*** Decision Judicial 	

17. **guardarNacimiento:**
    * ***Descripcion:*** Guarda Nacimiento
    * ***Endpoint:*** /acta/guardarNacimiento
    * ***Método:*** POST
    * ***Entrada:*** nacimiento
    * ***Salida:*** boolean
    
18. **consultarNacimiento:**
    * ***Descripcion:*** Consultar Nacimiento
    * ***Endpoint:*** /acta/consultarNacimiento
    * ***Método:*** GET
    * ***Entrada:*** numSolicitud
    * ***Salida:*** Nacimiento

19. **guardarActaPrimigenia:**
    * ***Descripcion:*** Guarda datos del acta primigenia
    * ***Endpoint:*** /acta/guardarActaPrimigenia
    * ***Método:*** POST
    * ***Entrada:*** ActaPrimigenia
    * ***Salida:*** ActaPrimigenia
	
20. **consultaActaPrimigenia**
    * ***Descripcion:*** Consulta datos del acta primigenia
    * ***Endpoint:*** /acta/consultaActaPrimigenia
    * ***Método:*** GET
    * ***Entrada:*** numSolicitud
    * ***Salida:*** ActaPrimigenia

21. **buscarNumeroActaPorSolic**
    * ***Descripcion:*** Buscar el numero de acta dado el numero de la solicitud.
    * ***Endpoint:*** /acta/buscarNumeroActaPorSolic
    * ***Método:*** GET
    * ***Entrada:*** nroSolicitud
    * ***Salida:*** String
 		
##Artefactos Generados:
* servicios-acta.war

##Dependendencias
* sarc-comunes
* SARC II - Capa de Persistencia
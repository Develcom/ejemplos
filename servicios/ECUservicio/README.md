#Servicio: Ecu

##Requerimientos:

* Java 7 (jdk)

##Operaciones:

1. **validarEcu:** 
    * ***Descripcion:*** Valida que exista un ECU para el ciudadano
    * ***Endpoint:*** /ecu/validarEcu
    * ***Método:*** POST
    * ***Entrada:*** ciudadano
    * ***Salida:*** List<Participante>

2. **vincularActaEcuParticipante**
    * ***Descripcion:*** Vincula el ecu con su acta y participantes
    * ***Endpoint:*** /ecu/vincular/acta/ecu/participante/
    * ***Método:*** PUT
    * ***Entrada:*** numeroSolicitud
    * ***Salida:*** boolean

3. **actualizarEcu**
    * ***Descripcion:*** Actualiza el ecu 
    * ***Endpoint:*** /ecu/actualizarEcu
    * ***Método:*** POST
    * ***Entrada:*** codigoEstadoSolicitud, participante
    * ***Salida:*** Ecu
	
4. **buscarExistenciaECU:** 
    * ***Descripcion:*** Operación que permite buscar el ECU.
    * ***Endpoint:*** /ecu/buscarExistenciaECU/{numeroDocIdentidad}
    * ***Método:*** GET
    * ***Entrada:*** numeroDocIdentidad
    * ***Salida:*** boolean
	
5. **consultarECU:**
    * ***Descripcion:*** Operación que permite consultar el ECU.
    * ***Endpoint:*** /ecu/consultarECU/{numeroDocumento}/{tipoDocumento}
    * ***Método:*** GET
    * ***Entrada:*** numeroDocumento, tipoDocumento
    * ***Salida:*** Ecu	
	
##Artefactos Generados:
* servicios-ecu.war

##Dependendencias
* sarc-comunes
* SARC II - Capa de Persistencia
#Servicio: Participante

##Requerimientos:

* Java 7 (jdk)

##Operaciones:

1. **registrarParticipantes:** 
    * ***Descripcion:*** Registra una lista de participantes a una solicitud dada.
    * ***Endpoint:*** /participante/registrarParticipantes
    * ***Método:*** POST
    * ***Entrada:*** Solicitud
    * ***Salida:*** Solicitud

2. **buscarTipoParticipantePorTramite:** 
    * ***Descripcion:*** Busca los tipos participantes por el tramite dado.
    * ***Endpoint:*** /buscarTipoParticipantePorTramite/{codigoTramite}/{tipoDeclarante}
    * ***Método:*** GET
    * ***Entrada:*** codigoTramite, tipoDeclarante
    * ***Salida:*** List<TipoParticipante>

3. **actualizarParticipante:** 
    * ***Descripcion:*** Actualiza a un Participante en base a la información dada.
    * ***Endpoint:*** /participante/actualizarParticipante/{numeroSolicitud}
    * ***Método:*** POST
    * ***Entrada:*** numeroSolicitud, Participante
    * ***Salida:*** Participante    

4. **consultarParticPorTipo:** 
    * ***Descripcion:*** consultar un participante dado un numero de solicitud y un codigo tipo de participante. En la variable codigoTipo puede colocar varios 
						 codigos de tipo participante divididos por "," ejemplo: RMT,TA1,MAD
    * ***Endpoint:*** /participante/consultarParticPorTipo/{numeroSolicitud}/{codigoTipo}
    * ***Método:*** GET
    * ***Entrada:*** numeroSolicitud, codigoTipo
    * ***Salida:*** Participante 
	
5. **consultarParticPorSolicitud:** 
    * ***Descripcion:*** consultar una lista de Participantes dado un numero de Solicitud, para buscar todos los participantes de una solicitud esDeclarante:T, 
						 para obtener el participante declarante esDeclarante: D
    * ***Endpoint:*** /participante/consultarParticPorSolicitud/{numeroSolicitud}/{esDeclarante}
    * ***Método:*** GET
    * ***Entrada:*** numeroSolicitud,esDeclarante
    * ***Salida:*** List<Participante> 

6. **consultarParticPorActa:** 
    * ***Descripcion:*** consultar una lista de Participantes dado un numero de Acta.
    * ***Endpoint:*** /participante/consultarParticPorActa/{numeroActa}
    * ***Método:*** GET
    * ***Entrada:*** numeroActa
    * ***Salida:*** Participante 
	
7. **actualizarParticipante:** 
    * ***Descripcion:*** Registra una Declaracion Jurada en base a la información dada.
    * ***Endpoint:*** /participante/registrarDeclaracionJurada
    * ***Método:*** POST
    * ***Entrada:*** DeclaracionJurada
    * ***Salida:*** boolean 	

8. **registrarDeclaracionJurada:** 
    * ***Descripcion:*** Registra una Declaracion Jurada en base a la información dada.
    * ***Endpoint:*** /participante/registrarDeclaracionJurada
    * ***Método:*** POST
    * ***Entrada:*** DeclaracionJurada
    * ***Salida:*** boolean 	

9. **registrarConsejoComunal:** 
    * ***Descripcion:*** Registra una Carta Consejo Comunal en base a la información dada.
    * ***Endpoint:*** /participante/registrarConsejoComunal
    * ***Método:*** POST
    * ***Entrada:*** CartaConsejoComunal
    * ***Salida:*** boolean 

10. **consultarDeclaracionJurada:** 
    * ***Descripcion:*** Consulta una declaracion jurada en base a un numero de solicitud.
    * ***Endpoint:*** /participante/consultarDeclaracionJurada
    * ***Método:*** GET
    * ***Entrada:*** numSolicitud
    * ***Salida:*** List<DeclaracionJurada> 
11. **registrarParticPorSolicitud:** 
    * ***Descripcion:*** Registra un Participante en base a un numero de solicitud.
    * ***Endpoint:*** /participante/registrarParticPorSolicitud/{numSolicitud}
    * ***Método:*** POST
    * ***Entrada:*** numSolicitud, Participante
    * ***Salida:*** Participante 	
##Artefactos Generados:
* servicios-participante.war

##Dependendencias
* sarc-comunes
* SARC II - Capa de Persistencia
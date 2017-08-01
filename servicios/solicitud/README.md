#Servicio: Solicitud

##Requerimientos: 

* Java 7 (jdk)


##Operaciones:

1. **consultarDetalleSolicitud:** 
    * ***Descripcion:*** Operación que permite consultar una solicitud segun el número.
    * ***Endpoint:*** /solicitud/consultarDetalleSolicitud/{numero}
    * ***Método:*** GET
    * ***Entrada:*** numero
    * ***Salida:*** Solicitud
    
2. **consultarPorNumeroDocSolicitante:** 
    * ***Descripcion:*** Operación que permite consultar las solicitudes existentes asociadas a un documento del solicitante.
    * ***Endpoint:*** /solicitud/consultarPorNumeroDocSolicitante/{tipoSolicitante}/{tipoDocumento}/{numeroDocumento}
    * ***Método:*** GET
    * ***Entrada:*** tipoSolicitante, tipoDocumento, numeroDocumento
    * ***Salida:*** List<Solicitud>
    
3. **consultarTodos:** 
    * ***Descripcion:*** Operación que permite consultar todas las solicitudes existentes.
    * ***Endpoint:*** /solicitud/consultarTodos
    * ***Método:*** GET 
    * ***Salida:*** List<Solicitud>

4. **actualizarEstadoSolicitud:** 
    * ***Descripcion:*** Operación que permite actualizar el estado de una solicitud existente.
    * ***Endpoint:*** /solicitud/actualizarEstadoSolicitud
    * ***Método:*** POST
    * ***Entrada:*** Solicitud
    * ***Salida:*** Solicitud

5. **generarSolicitud:** 
    * ***Descripcion:*** Operación que permite generar una solicitud nueva.
    * ***Endpoint:*** /solicitud/generarSolicitud
    * ***Método:*** POST
    * ***Entrada:*** Solicitud
    * ***Salida:*** Solicitud

6. **consultarPorUsuario:**
    * ***Descripcion:*** Operación que permite consultar las solicitudes existentes asociadas a un usuario y rol.
    * ***Endpoint:*** /solicitud/consultarPorUsuario/{loginUsuario}/{codigoRol}
    * ***Método:*** GET
    * ***Entrada:*** loginUsuario,codigoRol
    * ***Salida:*** List<Solicitud>

7. **consultarSolicitudes:**
    * ***Descripcion:*** Operación que permite consultar las solicitudes existentes dado un rango de fechas.
    * ***Endpoint:*** /solicitud/consultarPorFecha/{fechaDesde}/{fechaHasta}
    * ***Método:*** GET
    * ***Entrada:*** fechaDesde,fechaHasta
    * ***Salida:*** List<Solicitud>
    
8. **consultarSolicitudes:**
    * ***Descripcion:*** Operación que permite consultar las solicitudes existentes dado una cadena de codigos de tramites o estatus de solicitud(separados por coma) y el tipo de codigo (T:Tramite o E:Estatus)
    * ***Endpoint:*** /solicitud/consultarPorTramiteOEstatus/{codigos}/{tipoCodigo}
    * ***Método:*** GET
    * ***Entrada:*** codigos,tipoCodigo
    * ***Salida:*** List<Solicitud>
    
9. **consultarSolicitudes:**
    * ***Descripcion:*** Operación que permite consultar las solicitudes existentes dado una cadena de codigos de tramites y una cadena de codigos  de estatus. 
    * ***Endpoint:*** /solicitud/consultarPorTramiteYEstatus/{codigosTramite}/{codigosEstatus}
    * ***Método:*** GET
    * ***Entrada:*** codigosTramite,codigosEstatus
    * ***Salida:*** List<Solicitud>
    
10. **consultarSolicitudes:**
    * ***Descripcion:*** Operación que permite consultar las solicitudes existentes dado una cadena de codigos de tramites o estatus de solicitud(separados por coma),el tipo de codigo (T:Tramite o E:Estatus) y un rango de fechas. 
    * ***Endpoint:*** /solicitud/consultarPorTramiteOEstatusYFecha/{codigos}/{tipoCodigo}/{fechaDesde}/{fechaHasta}
    * ***Método:*** GET
    * ***Entrada:*** codigos,tipoCodigo,fechaDesde,fechaHasta
    * ***Salida:*** List<Solicitud>

11. **consultarSolicitudes:**
    * ***Descripcion:*** Operación que permite consultar las solicitudes existentes dado una cadena de codigos de tramites, una cadena de estatus y una cadena de codigos de estatus y un rango de fechas.
    * ***Endpoint:*** /solicitud/consultarPorTramiteYEstatusYFecha/{codigosTramite}/{codigosEstatus}/{fechaDesde}/{fechaHasta}
    * ***Método:*** GET
    * ***Entrada:*** codigosTramite,codigosEstatus,fechaDesde,fechaHasta
    * ***Salida:*** List<Solicitud>
    


##Artefactos Generados:
* servicios-solicitud.war

##Dependendencias
* sarc-comunes
* SARC II - Capa de Persistencia
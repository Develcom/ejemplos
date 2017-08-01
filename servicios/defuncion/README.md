#Servicio: Defunción

##Requerimientos:

* Java 7 (jdk)

##Operaciones:

1. **validarCertificadoMedicoDefuncion:** 
    * ***Descripcion:*** Valida que un certificado de defuncion se encuentre registrado.
    * ***Endpoint:*** /validarCertificadoMedicoDefuncion/{numeroCertificado}
    * ***Método:*** GET
    * ***Entrada:*** numeroCertificado 
    * ***Salida:*** boolean

2. **guardarPermisoInhCre:** 
    * ***Descripcion:*** Guarda la informacion de Permiso Inhumacion y Cremacion.
    * ***Endpoint:*** /guardarPermisoInhCre/
    * ***Método:*** POST
    * ***Entrada:*** PermisoInhCre 
    * ***Salida:*** PermisoInhCre

3. **buscarProxNumPermiso:** 
    * ***Descripcion:*** Guarda la informacion de Permiso Inhumacion y Cremacion.
    * ***Endpoint:*** /buscarProxNumPermiso/{codOficina}
    * ***Método:*** GET
    * ***Entrada:*** String 
    * ***Salida:*** long

4. **consultaPermisoInhCre:** 
    * ***Descripcion:*** Consulta la informacion de Permiso Inhumacion y Cremacion.
    * ***Endpoint:*** /consultaPermisoInhCre/{numeroSolicitud}
    * ***Método:*** GET
    * ***Entrada:*** String 
    * ***Salida:*** PermisoInhCre		

5. **guardarDefuncion:**
    * ***Descripcion:*** Guarda Defuncion
    * ***Endpoint:*** /guardarDefuncion/
    * ***Método:*** POST
    * ***Entrada:*** defuncion
    * ***Salida:*** boolean

6. **consultarDefuncion:**
    * ***Descripcion:*** Consulta Defuncion
    * ***Endpoint:*** /consultarDefuncion/{numeroSolicitud}
    * ***Método:*** GET
    * ***Entrada:*** String
    * ***Salida:*** Defuncion		
##Artefactos Generados:
* servicios-defuncion.war

##Dependendencias
* sarc-comunes
* SARC II - Capa de Persistencia
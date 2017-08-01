#Servicio: Catalogo

##Requerimientos:
* Java 7 (jdk)

##Operaciones:

1. **EntePublico/consultarTodos:**
    * ***Descripcion:*** operación responsable de listar los Entes públicos.
    * ***Endpoint:*** /catalogo/EntePublico/consultarTodos
    * ***Método:*** GET
	* ***Salida:*** List<EntePublico>

2. **consultarModuloPorOficina:**
    * ***Descripcion:*** operación que es el responsable de listar los modulos de acuerdo a la oficina.
    * ***Endpoint:*** /catalogo/consultarModuloPorOficina/{codigo}
    * ***Método:*** GET
	* ***Entrada:*** codigo
	* ***Salida:*** List<Modulo>
    
3. **TipoDocumento/consultarTipoDocumento:**
    * ***Descripcion:*** operación responsable de listar los tipos de documentos de acuerdo al tipo de solicitante (E= ente publico) o (D = declarante).
    * ***Endpoint:*** /TipoDocumento/{tipo}
    * ***Método:*** GET
	* ***Entrada:*** tipo
    * ***Salida:*** List<TipoDocumento>

4. **TipoParticipante/buscarPorCodigo:**
    * ***Descripcion:*** operación responsable de buscar el tipo de participante dado su código.
    * ***Endpoint:*** /tipoParticipante/buscarPorCodigo/{codigo}/{isDeclarante}
    * ***Método:*** GET
	* ***Entrada:*** codigo, isDeclarante
    * ***Salida:*** List<TipoParticipante>

5. **TipoTramite/consultarTramitePorModulo:**
    * ***Descripcion:*** operación responsable de listar los tramites de acuerdo al código del modulo.
    * ***Endpoint:*** /TipoTramite/consultarTramitePorModulo/{codigoModulo}/{codTipoSolicitante}
    * ***Método:*** GET
	* ***Entrada:*** codigoModulo,codTipoSolicitante
    * ***Salida:*** List<Tramite>

6. **Ciudad/consultarTodos:**
    * ***Descripcion:*** operación responsable de listar las ciudades.
    * ***Endpoint:*** /Ciudad/consultarTodos
    * ***Método:*** GET
	* ***Salida:*** List<Ciudad>

7. **Municipio/consultarTodos:**
    * ***Descripcion:*** operación responsable de listar los municipios.
    * ***Endpoint:*** /Municipio/consultarTodos
    * ***Método:*** GET
	* ***Salida:*** List<Municipio>

8. **Parroquia/consultarTodos:**
    * ***Descripcion:*** operación responsable de listar las Parroquias.
    * ***Endpoint:*** /Parroquia/consultarTodos
    * ***Método:*** GET
	* ***Salida:*** List<Parroquia>
	
9. **Pais/consultarTodos:**
    * ***Descripcion:*** operación responsable de listar los Paises.
    * ***Endpoint:*** /Pais/consultarTodos
    * ***Método:*** GET
	* ***Salida:*** List<Pais>

10. **Estado/consultarTodos:**
    * ***Descripcion:*** operación responsable de listar los Estados.
    * ***Endpoint:*** /Estado/consultarTodos
    * ***Método:*** GET
	* ***Salida:*** List<Estado>

11. **Nacionalidad/consultarTodos:**
    * ***Descripcion:*** operación responsable de listar las Nacionalidades.
    * ***Endpoint:*** /Nacionalidad/consultarTodos
    * ***Método:*** GET
	* ***Salida:*** List<Nacionalidad>

12. **Ocupacion/consultarTodos:**
    * ***Descripcion:*** operación responsable de listar las Ocupaciones.
    * ***Endpoint:*** /Ocupacion/consultarTodos
    * ***Método:*** GET	
	* ***Salida:*** List<Ocupacion>

13. **Estado/consultarPorPais:**
    * ***Descripcion:*** operación responsable de listar los estados de acuerdo al código del Pais.
    * ***Endpoint:*** /Estado/consultarPorPais/{codigo}
    * ***Método:*** GET
	* ***Entrada:*** codigo
    * ***Salida:*** List<Estado>	
	
14. **Municipio/consultarPorEstado:**
    * ***Descripcion:*** operación responsable de listar los municipios de acuerdo al código del Estado.
    * ***Endpoint:*** /Municipio/consultarPorEstado/{codigo}
    * ***Método:*** GET
	* ***Entrada:*** codigo
    * ***Salida:*** List<Municipio>

15.	**Parroquia/consultarPorMunicipio/:**
    * ***Descripcion:*** operación responsable de listar las parroquias de acuerdo al código del Municipio.
    * ***Endpoint:*** /Parroquia/consultarPorMunicipio/{codigo}
    * ***Método:*** GET
	* ***Entrada:*** codigo
    * ***Salida:*** List<Parroquia>

16.	**ComunidadIndigena/consultarTodos:**
    * ***Descripcion:*** operación responsable de listar las comunidades indigenas.
    * ***Endpoint:*** /ComunidadIndigena/consultarTodos
    * ***Método:*** GET
	* ***Salida:*** List<ComunidadIndigena>
	
17.	**consultarDocumentosAutenticados:**
    * ***Descripcion:*** operación responsable de listar los documentos autenticados.
    * ***Endpoint:*** /consultarDocumentosAutenticados/
    * ***Método:*** GET
	* ***Salida:*** LinkedHashMap<String, String>
	
18. **Oficina/consultarTodos:**
    * ***Descripcion:*** operación responsable de listar las Oficinas.
    * ***Endpoint:*** /Oficina/consultarTodos
    * ***Método:*** GET
	* ***Salida:*** List<Oficina>

19. **Oficina/proximoNroConsecutivo:**
	* ***Descripcion:*** operación responsable de proximo nro consecutivo
	* ***Endpoint:*** /Oficina/proximoNroConsecutivo
	* ***Método:*** GET
	* ***Salida:*** long
	
##Artefactos Generados:
* servicios-catalogo.war

##Dependendencias
* sarc-comunes
* SARC II - Capa de Persistencia
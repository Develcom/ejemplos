#Servicio: Libro

##Requerimientos: 

* Java 7 (jdk)


##Operaciones:

1. **buscarLibrosEstatusPorTipoOficina:** 
    * ***Descripcion:*** Obtiene la colección de libros disponibles en base a los parametros dados.
    * ***Endpoint:*** /libro/buscarLibrosEstatusPorTipoOficina/{codigoOfic}/{estatus}/{anio}
    * ***Método:*** GET
	* ***Entrada:*** codigoOfic,estatus,anio
    * ***Salida:*** List<Libro>

2. **validarLibrosActivosPorOficina:** 
    * ***Descripcion:*** Valida la existencia de un libro activo para una oficina y un tipo de libro dato.
    * ***Endpoint:*** /libro/validarLibrosActivosPorOficina/{codigoOfic}/{estatus}/{anio}
    * ***Método:*** GET
    * ***Entrada:*** codigoOfic,estatus,anio
    * ***Salida:*** boolean

3. **crearLibro:** 
    * ***Descripcion:*** Crea un nuevo libro en base a la información dada.
    * ***Endpoint:*** /libro/crearLibro
    * ***Método:*** POST
    * ***Entrada:*** Libro
    * ***Salida:*** boolean

4. **actualizarLibro:** 
    * ***Descripcion:*** Actualiza la información de un libro en base a la información dada.
    * ***Endpoint:*** /libro/actualizarLibro
    * ***Método:*** PUT
    * ***Entrada:*** Libro
    * ***Salida:*** boolean

5. **aperturaCierreLibro:** 
    * ***Descripcion:*** Apertura o cierra un libro en base a la información dada.
    * ***Endpoint:*** /libro/aperturaCierreLibro
    * ***Método:*** PUT
    * ***Entrada:*** Libro
    * ***Salida:*** boolean

6. **consultarTodos:** 
    * ***Descripcion:*** consulta todos los libro en base a la información dada.
    * ***Endpoint:*** /libro/consultarTodos/{codigoOfic}
    * ***Método:*** GET
    * ***Entrada:*** codigoOfic
    * ***Salida:*** list<Libro>
	
7. **actualizarLibroCivil:** 
    * ***Descripcion:*** consulta todos los libro en base a la información dada.
    * ***Endpoint:*** /libro/actualizarLibroCivil/
    * ***Método:*** POST
    * ***Entrada:*** Participante
    * ***Salida:*** Participante

8. **validarTopeTomo:** 
    * ***Descripcion:*** valida el tope del numero del Tomo para un tipo libro activo en un año y oficina dada
    * ***Endpoint:*** /libro/validarTopeTomo/{codigoOfic}/{tipoLibro}/{anio}
    * ***Método:*** GET
    * ***Entrada:*** codigoOfic, tipoLibro, anio
    * ***Salida:*** boolean

9. **crearLibroDiario:**
    * ***Descripcion:*** crea el libro diario dado un número de solicitud
    * ***Endpoint:*** /libro/crearLibroDiario/{numeroSolicitud}
    * ***Método:*** POST
    * ***Entrada:*** numeroSolicitud
    * ***Salida:*** boolean

10. **actualizarLibroDiario:**
    * ***Descripcion:*** actualiza el libro diario dado un número de solicitud
    * ***Endpoint:*** /libro/actualizarLibroDiario/{numeroSolicitud}/{codigoEstatus}
    * ***Método:*** POST
    * ***Entrada:*** numeroSolicitud, codigoEstatus
    * ***Salida:*** boolean
	
11. **buscarTomoActual:**	
    * ***Descripcion:*** busca el tomo actual dado una oficina, año, tipo libro y estatus.
    * ***Endpoint:*** /libro/buscarTomoActual/{codigoOfic}/{estatus}/{anio}/{codTipoLibro}
    * ***Método:*** GET
    * ***Entrada:*** codigoOfic, estatus, anio, codTipoLibro
    * ***Salida:*** Tomo

12. **validarActasPorTomo:**
    * ***Descripcion:*** valida el tope de numeros de acta por tomo dado un codigo de oficina, un codigo de tipo libro, 
    un año y un tomo.
    * ***Endpoint:*** /libro/validarActasPorTomo/{codOficina}/{codTipoLibro}/{anio}/{numeroTomo}
    * ***Método:*** GET
    * ***Entrada:*** codOficina, codTipoLibro, anio, numeroTomo
    * ***Salida:*** boolean
    
13. **validarLibroDiarioActivoPorOficina:**
    * ***Descripcion*** Valida la existencia del libro diario con estatus abierto para una oficina y un dia determinado.
    *  ***Endpoint:*** /libro/validarLibroDiarioActivoPorOficina/{codigoOficina}/{fecha}/{estatus}
    * ***Metodo:*** GET
    *  ***Entrada:*** codigoOficina,fecha,estatus
    *  ***Salida:*** boolean

##Artefactos Generados:
* servicios-libro.war

##Dependendencias
* sarc-comunes
* SARC II - Capa de Persistencia

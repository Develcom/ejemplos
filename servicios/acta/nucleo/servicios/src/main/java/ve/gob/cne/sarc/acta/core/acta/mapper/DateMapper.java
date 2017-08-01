package ve.gob.cne.sarc.acta.core.acta.mapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.mapstruct.Mapper;
import ve.gob.cne.sarc.acta.core.acta.exception.ResourceNotFoundException;

/**
 * DateMapper.java
 *
 * @descripcion [Clase mapper para realizar de String a Date y viceversa]
 * @version 1.0 12/10/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring")
public class DateMapper {
    
    /**
     * Metodo que convierte Date a String
     * 
     * @param date fecha de tipo Date
     * @return String con la fecha
     */
    public String asString(Date date) {
        return date != null ? new SimpleDateFormat("dd/MM/yyyy").format(date) : null;          
    }
    
    /**
     * Metodo que convierte String a Date
     * 
     * @param date String con la fecha
     * @return Date con el valor de la fecha
     */
    public Date asDate(String date) {
        
        try {
            return date != null ? new SimpleDateFormat("dd/MM/yyyy").parse(date) : null;
        } catch (ParseException e) {
            throw new ResourceNotFoundException("Fecha inv\\u00e1lida");
        }
    }
    
}

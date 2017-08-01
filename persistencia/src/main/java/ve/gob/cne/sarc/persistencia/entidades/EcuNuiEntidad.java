package ve.gob.cne.sarc.persistencia.entidades;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * Se crea la clase ActaEntidad donde se Realizan los Query de consulta de cada metodo
 *
 *
 * @author Oscar Montilla
 */
@Entity
@Table(name = "X050T_ECU_NUI")

public class EcuNuiEntidad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CO_ECU_NUI", nullable = false, length = 22)
    @SequenceGenerator(name = "CO_ECU_NUI_SEQ", sequenceName = "X050S_CO_ECU_NUI", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CO_ECU_NUI_SEQ")
    private long id;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "IN_LETRA_NACIONALIDAD", nullable = false)
    private String letraNacionalidad;

    @JoinColumn(name = "CO_ECU", referencedColumnName = "CO_ECU", nullable = false)
    @ManyToOne(optional = false)
    private EcuEntidad ecu;

    @JoinColumn(name = "CO_NUI", referencedColumnName = "CO_NUI", nullable = false)
    @ManyToOne(optional = false)
    private NuiEntidad nui;

    public EcuNuiEntidad() {
        // Metodo Constructor
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLetraNacionalidad() {
        return letraNacionalidad;
    }

    public void setLetraNacionalidad(String letraNacionalidad) {
        this.letraNacionalidad = letraNacionalidad;
    }

    public EcuEntidad getEcu() {
        return ecu;
    }

    public void setEcu(EcuEntidad ecu) {
        this.ecu = ecu;
    }

    public NuiEntidad getNui() {
        return nui;
    }

    public void setNui(NuiEntidad nui) {
        this.nui = nui;
    }

}

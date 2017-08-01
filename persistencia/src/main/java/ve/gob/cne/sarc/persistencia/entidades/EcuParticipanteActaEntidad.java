package ve.gob.cne.sarc.persistencia.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import ve.gob.cne.sarc.persistencia.disparadores.EcuParticipanteActaDisparador;

/**
 * EcuParticipanteActaEntidad.java
 *
 * @metodo Se crea la clase EcuParticipanteActaEntidad donde se Realizan los Query de consulta de cada metodo
 * @author Oscar Montilla
 * @version 1.0 06/09/2016
 */
@Entity
@Table(name = "X051T_ECU_PARTICIPANTE_ACTA")
@EntityListeners({EcuParticipanteActaDisparador.class})
@XmlRootElement

public class EcuParticipanteActaEntidad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CO_ECU_PARTICIPANTE_ACTA", nullable = false, length = 22)
    @SequenceGenerator(name = "ECU_PARTICIPANTE_ACTA_SEQ", sequenceName = "X051S_CO_ECU_PARTICIPANTE_ACTA",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ECU_PARTICIPANTE_ACTA_SEQ")
    private long id;

    @JoinColumn(name = "CO_PARTICIPANTE", referencedColumnName = "CO_PARTICIPANTE")
    @ManyToOne(optional = false)
    private ParticipanteEntidad participante;

    @JoinColumn(name = "CO_ECU", referencedColumnName = "CO_ECU")
    @ManyToOne(optional = false)
    private EcuEntidad ecu;

    @JoinColumn(name = "CO_ACTA", referencedColumnName = "CO_ACTA")
    @ManyToOne(optional = false)
    private ActaEntidad acta;

    public EcuParticipanteActaEntidad() {
        // Metodo Constructor
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ParticipanteEntidad getParticipante() {
        return participante;
    }

    public void setParticipante(ParticipanteEntidad participante) {
        this.participante = participante;
    }

    public EcuEntidad getEcu() {
        return ecu;
    }

    public void setEcu(EcuEntidad ecu) {
        this.ecu = ecu;
    }

    public ActaEntidad getActa() {
        return acta;
    }

    public void setActa(ActaEntidad acta) {
        this.acta = acta;
    }

}

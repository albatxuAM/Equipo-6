package Modelo.UML;
import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

    /**
     * ENTIDAD ENTRENA
     * @author EQUIPO-6
     */
@Entity
@Table(name = "ENTRENA", schema = "EQDAW06", catalog = "")
public class EntrenaEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "COD_CONTRATO", nullable = false, precision = 0)
    private byte codContrato;
    @Basic
    @Column(name = "COD_ENTRENADOR", nullable = true, precision = 0,insertable =false, updatable = false)
    private Byte codEntrenador;
    @Basic
    @Column(name = "COD_EQUIPO", nullable = true, precision = 0, insertable =false, updatable = false)
    private Byte codEquipo;
    @Basic
    @Column(name = "FECHA_FIN", nullable = true)
    private Date fechaFin;
    @Basic
    @Column(name = "FECHA_INICIO", nullable = true)
    private Date fechaInicio;
    @ManyToOne
    @JoinColumn(name = "COD_ENTRENADOR", referencedColumnName = "COD_ENTRENADOR")
    private EntrenadoresEntity entrenadoresByCodEntrenador;
    @ManyToOne
    @JoinColumn(name = "COD_EQUIPO", referencedColumnName = "COD_EQUIPO")
    private EquiposEntity equiposByCodEquipo;

    /**
     *
     * GETTER COD CONTRATO
     *
     * @return codContrato
     *
     */
    public byte getCodContrato() {
        return codContrato;
    }

    /**
     *
     * SETTER COD CONTRATO
     *
     * @param codContrato
     *
     */
    public void setCodContrato(byte codContrato) {
        this.codContrato = codContrato;
    }

    /**
     *
     * GETTER COD ENTRENADOR
     *
     * @return codEntrenador
     *
     */
    public Byte getCodEntrenador() {
        return codEntrenador;
    }

    /**
     *
     * SETTER COD ENTRENADOR
     *
     * @param codEntrenador
     *
     */
    public void setCodEntrenador(Byte codEntrenador) {
        this.codEntrenador = codEntrenador;
    }

    /**
     *
     * GETTER COD EQUIPO
     *
     * @return codEquipo
     */
    public Byte getCodEquipo() {
        return codEquipo;
    }

    /**
     *
     * SETTER COD EQUIPO
     *
     * @param codEquipo
     *
     */
    public void setCodEquipo(Byte codEquipo) {
        this.codEquipo = codEquipo;
    }

    /**
     *
     * GETTER FECHA FIN
     *
     * @return fechaFin
     *
     */
    public Date getFechaFin() {
        return fechaFin;
    }

    /**
     *
     * SETTER FECHA FIN
     *
     * @param fechaFin
     *
     */
    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * GET FECHA INICIO
     *
     *
     * @return fechaInicio
     *
     */
    public Date getFechaInicio() {
        return fechaInicio;
    }

    /**
     *
     * SETTER FECHA INICIO
     *
     * @param fechaInicio
     *
     */
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

        /**
         *
         *COMPARA QUE SEAN OBJETOS DEL MISMO TIPO
         *
         * @param o
         *
         **/

        @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntrenaEntity that = (EntrenaEntity) o;
        return codContrato == that.codContrato && Objects.equals(codEntrenador, that.codEntrenador) && Objects.equals(codEquipo, that.codEquipo) && Objects.equals(fechaFin, that.fechaFin) && Objects.equals(fechaInicio, that.fechaInicio);
    }

    /**
     *
     * DEVUELVE EL HASHCODE DEL OBJETO
     *
     * @return hashCode
     *
     */
    @Override
    public int hashCode() {
        return Objects.hash(codContrato, codEntrenador, codEquipo, fechaFin, fechaInicio);
    }

    /**
     *
     * GETTER ENTRENADORES POR COD ENTRENADOR
     *
     * @return entrenadoresByCodEntrenador
     *
     */
    public EntrenadoresEntity getEntrenadoresByCodEntrenador() {
        return entrenadoresByCodEntrenador;
    }

    /**
     *
     * SETTER ENTRENADORES POR COD ENTRENADOR
     *
     * @param entrenadoresByCodEntrenador
     *
     */
    public void setEntrenadoresByCodEntrenador(EntrenadoresEntity entrenadoresByCodEntrenador) {
        this.entrenadoresByCodEntrenador = entrenadoresByCodEntrenador;
    }

    /**
     *
     * GETTER ENTRENADORES POR COD EQUIPO
     *
     * @return equiposByCodEquipo
     *
     */
    public EquiposEntity getEquiposByCodEquipo() {
        return equiposByCodEquipo;
    }

    /**
     *
     * SETTER EQUIPOS POR COD EQUIPO
     *
     * @param equiposByCodEquipo
     *
     */
    public void setEquiposByCodEquipo(EquiposEntity equiposByCodEquipo) {
        this.equiposByCodEquipo = equiposByCodEquipo;
    }
}

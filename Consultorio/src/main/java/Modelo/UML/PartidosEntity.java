package Modelo.UML;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "PARTIDOS", schema = "SYSTEM", catalog = "")
public class PartidosEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "COD_PARTIDO")
    private byte codPartido;
    @Basic
    @Column(name = "HORA_PARTIDO")
    private String horaPartido;
    @Basic
    @Column(name = "RESULTADO")
    private String resultado;
    @Basic
    @Column(name = "COD_JORNADA")
    private Byte codJornada;

    public byte getCodPartido() {
        return codPartido;
    }

    public void setCodPartido(byte codPartido) {
        this.codPartido = codPartido;
    }

    public String getHoraPartido() {
        return horaPartido;
    }

    public void setHoraPartido(String horaPartido) {
        this.horaPartido = horaPartido;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public Byte getCodJornada() {
        return codJornada;
    }

    public void setCodJornada(Byte codJornada) {
        this.codJornada = codJornada;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PartidosEntity that = (PartidosEntity) o;
        return codPartido == that.codPartido && Objects.equals(horaPartido, that.horaPartido) && Objects.equals(resultado, that.resultado) && Objects.equals(codJornada, that.codJornada);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codPartido, horaPartido, resultado, codJornada);
    }
}

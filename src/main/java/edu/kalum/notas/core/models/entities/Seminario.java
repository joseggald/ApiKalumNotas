package edu.kalum.notas.core.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "seminario")
@Entity
public class Seminario implements Serializable {
    @Id
    @Column(name = "seminario_id")
    private String seminarioId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "modulo_id", referencedColumnName = "modulo_id")
    private Modulo modulo;
    @Column(name = "nombre_seminario")
    private String nombreSeminario;
    @Column(name = "fecha_inicio")
    private Date fechaInicio;
    @Column(name = "fecha_fin")
    private Date fechaFin;
    @OneToMany(mappedBy = "seminario", fetch = FetchType.EAGER)
    @JsonIgnore
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private List<DetalleActividad> detalleActividad;
}
package edu.kalum.notas.core.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "detalle_actividad")
@Entity
public class DetalleActividad implements Serializable {
    @Id
    @Column(name = "detalle_actividad_id")
    private String detalleActividadId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "seminario_id", referencedColumnName = "seminario_id")
    private Seminario seminario;
    @Column(name = "nombre_actividad")
    private String nombreActividad;
    @Column(name = "nota_actividad")
    private Integer notaActividad;
    @Column(name = "fecha_creacion")
    private Date fechaCreacion;
    @Column(name = "fecha_entrega")
    private Date fechaEntrega;
    @Column(name = "fecha_postergacion")
    private Date fechaPostergacion;
    @Column(name = "estado")
    private String estado;
    @OneToMany(mappedBy = "detalleActividad", fetch = FetchType.EAGER)
    @JsonIgnore
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private List<DetalleNota> detalleNota;
}

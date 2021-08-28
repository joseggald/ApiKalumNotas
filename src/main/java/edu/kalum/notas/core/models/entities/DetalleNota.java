package edu.kalum.notas.core.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "detalle_nota")
@Entity
public class DetalleNota implements Serializable {
    @Id
    @Column(name = "detalle_nota_id")
    private String detalleNotaId;
    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "detalle_actividad_id", referencedColumnName = "detalle_actividad_id")
    private DetalleActividad detalleActividad;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "carne", referencedColumnName = "carne")
    private Alumno alumno;
    @Column(name = "valor_nota")
    private Integer valorNota;

}

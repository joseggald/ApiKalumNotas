package edu.kalum.notas.core.models.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "asignacion_alumno")
@Entity
public class AsignacionAlumno {

    @Id
    @Column(name = "asignacion_id")
    private String asignacionId;
    @Column(name = "fecha_asignacion")
    private Date fechaAsignacion;
    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "carne", referencedColumnName = "carne")
    private Alumno alumno;
    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "clase_id", referencedColumnName = "clase_id")
    private Clase clase;

}

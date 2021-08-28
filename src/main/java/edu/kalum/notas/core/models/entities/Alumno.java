package edu.kalum.notas.core.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table (name = "alumno")
@Entity
public class Alumno implements Serializable {
    @Id
    @Column(name = "carne")
    @NotEmpty(message = "Es necesario asignar un numero de carné.")
    private String carne;
    @Column(name = "no_expediente")
    @NotEmpty(message = "Es necesario asignar un numero de expediente.")
    private String noExpendiente;
    @Column(name = "apellidos")
    @NotEmpty(message = "El campo de apellidos debe ser llenado.")
    private String apellidos;
    @Column(name = "nombres")
    @NotEmpty(message = "El campo de nombres debe ser llenado.")
    private String nombres;
    @Column(name = "email")
    @Email (message = "Debe de ingresar un correo valido.")
    private String email;
    @OneToMany(mappedBy = "alumno", fetch = FetchType.EAGER)
    @JsonIgnore
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private List<AsignacionAlumno> asignacionAlumnos;

}

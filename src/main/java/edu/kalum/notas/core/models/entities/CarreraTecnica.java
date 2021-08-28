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
@Table(name = "carrera_tecnica")
@Entity
public class CarreraTecnica implements Serializable {

    @Id
    @Column(name = "codigo_carrera")
    private String codigoCarrera;
    @Column(name = "nombre")
    private String nombre;
    @OneToMany(mappedBy = "carreraTecnica", fetch = FetchType.EAGER)
    @JsonIgnore
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private List<Clase> clase;
    @OneToMany(mappedBy = "carreraTecnica", fetch = FetchType.LAZY)
    @JsonIgnore
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private List<Modulo> modulo;



}

package edu.kalum.notas.core.models.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "modulo")
@Entity
public class Modulo implements Serializable{
    @Id
    @Column(name = "modulo_id")
    private String modulo_id;

    @Column(name = "nombre_modulo")
    private String nombre_modulo;
    @Column(name = "numero_seminarios")
    private Integer numero_seminarios;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "codigo_carrera", referencedColumnName = "codigo_carrera")
    private CarreraTecnica carreraTecnica;

    @OneToMany(mappedBy = "modulo", fetch = FetchType.LAZY)
    @JsonIgnore
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private List<Seminario> seminario;

}
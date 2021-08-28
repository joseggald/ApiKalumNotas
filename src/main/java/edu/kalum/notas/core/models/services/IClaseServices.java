package edu.kalum.notas.core.models.services;

import edu.kalum.notas.core.models.entities.Clase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IClaseServices {
    public List<Clase>findAlll();
    public Page<Clase> findAll(Pageable pageable);
    public Clase save(Clase clase);
    public Clase findById(String id);
    public void delete(Clase clase);
    public void deleteById(String id);
}

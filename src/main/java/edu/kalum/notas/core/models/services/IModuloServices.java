package edu.kalum.notas.core.models.services;

import edu.kalum.notas.core.models.entities.Modulo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IModuloServices {
    public List<Modulo>findAll();
    public Page<Modulo>findAll(Pageable pageable);
    public Modulo save(Modulo modulo);
    public Modulo findById(String id);
    public void delete(Modulo modulo);
    public void deleteById(String id);
}

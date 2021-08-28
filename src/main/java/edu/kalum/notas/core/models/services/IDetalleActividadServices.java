package edu.kalum.notas.core.models.services;


import edu.kalum.notas.core.models.entities.DetalleActividad;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IDetalleActividadServices {
    public List<DetalleActividad> findAll();
    public Page<DetalleActividad> findAll(Pageable pageable);
    public DetalleActividad save(DetalleActividad detalleActividad);
    public DetalleActividad findById(String id);
    public void delete(DetalleActividad detalleActividad);
    public void deleteById(String id);
}

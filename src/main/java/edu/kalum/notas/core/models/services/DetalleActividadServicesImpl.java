package edu.kalum.notas.core.models.services;

import edu.kalum.notas.core.models.dao.IDetalleActividadDao;
import edu.kalum.notas.core.models.entities.DetalleActividad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetalleActividadServicesImpl implements IDetalleActividadServices {

    @Autowired
    private IDetalleActividadDao detalleActividadDao;

    @Override
    public List<DetalleActividad> findAll() {
        return this.detalleActividadDao.findAll();
    }

    @Override
    public Page<DetalleActividad> findAll(Pageable pageable) {
        return this.detalleActividadDao.findAll(pageable);
    }

    @Override
    public DetalleActividad save(DetalleActividad detalleActividad) {
        return this.detalleActividadDao.save(detalleActividad);
    }

    @Override
    public DetalleActividad findById(String id) {
        return this.detalleActividadDao.findById(id).orElse(null);
    }

    @Override
    public void delete(DetalleActividad detalleActividad) {
        this.detalleActividadDao.delete(detalleActividad);
    }

    @Override
    public void deleteById(String id) {
        this.detalleActividadDao.deleteById(id);
    }
}

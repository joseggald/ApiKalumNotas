package edu.kalum.notas.core.models.services;

import edu.kalum.notas.core.models.dao.IDetalleNotaDao;
import edu.kalum.notas.core.models.entities.DetalleNota;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetalleNotaServicesImpl implements IDetalleNotaServices {

    @Autowired
    private IDetalleNotaDao detalleNotaDao;
    @Override
    public List<DetalleNota> findAll() {
        return this.detalleNotaDao.findAll();
    }

    @Override
    public Page<DetalleNota> findAll(Pageable pageable) {
        return this.detalleNotaDao.findAll(pageable);
    }

    @Override
    public DetalleNota save(DetalleNota detalleNota) {
        return this.detalleNotaDao.save(detalleNota);
    }

    @Override
    public DetalleNota findById(String id) {
        return this.detalleNotaDao.findById(id).orElse(null);
    }

    @Override
    public void delete(DetalleNota detalleNota) {
        this.detalleNotaDao.delete(detalleNota);
    }

    @Override
    public void deleteById(String id) {
        this.detalleNotaDao.deleteById(id);
    }
}

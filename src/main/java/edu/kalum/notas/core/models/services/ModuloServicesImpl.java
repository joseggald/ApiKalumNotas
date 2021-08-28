package edu.kalum.notas.core.models.services;

import edu.kalum.notas.core.models.dao.IModuloDao;
import edu.kalum.notas.core.models.entities.Modulo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ModuloServicesImpl implements IModuloServices{

    @Autowired
    private IModuloDao moduloDao;

    @Override
    public List<Modulo> findAll() {
        return this.moduloDao.findAll();
    }

    @Override
    public Page<Modulo> findAll(Pageable pageable) {
        return this.moduloDao.findAll(pageable);
    }

    @Override
    public Modulo save(Modulo modulo) {
        return this.moduloDao.save(modulo);
    }

    @Override
    public Modulo findById(String id) {
        return this.moduloDao.findById(id).orElse(null);
    }

    @Override
    public void delete(Modulo modulo) {
        this.moduloDao.delete(modulo);
    }

    @Override
    public void deleteById(String id) {
        this.moduloDao.deleteById(id);
    }
}

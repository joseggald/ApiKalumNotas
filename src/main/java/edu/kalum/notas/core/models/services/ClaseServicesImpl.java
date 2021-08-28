package edu.kalum.notas.core.models.services;

import edu.kalum.notas.core.models.dao.IClaseDao;
import edu.kalum.notas.core.models.entities.Clase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ClaseServicesImpl implements IClaseServices{
    @Autowired
    private IClaseDao claseDao;
    @Override
    public List<Clase> findAlll() {
        return this.claseDao.findAll();
    }

    @Override
    public Page<Clase> findAll(Pageable pageable) {
        return this.claseDao.findAll(pageable);
    }

    @Override
    public Clase save(Clase clase) {
        return this.claseDao.save(clase);
    }

    @Override
    public Clase findById(String id) {
        return this.claseDao.findById(id).orElse(null);
    }

    @Override
    public void delete(Clase clase) {
        this.claseDao.delete(clase);
    }

    @Override
    public void deleteById(String id) {
        this.claseDao.deleteById(id);
    }
}

package edu.kalum.notas.core.models.services;

import edu.kalum.notas.core.models.dao.ISeminarioDao;
import edu.kalum.notas.core.models.entities.Seminario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SeminarioServicesImpl implements ISeminarioServices{
    @Autowired
    private ISeminarioDao seminarioDao;

    @Override
    public List<Seminario> findAll() {
        return this.seminarioDao.findAll();
    }

    @Override
    public Page<Seminario> findAll(Pageable pageable) {
        return this.seminarioDao.findAll(pageable);
    }

    @Override
    public Seminario save(Seminario seminario) {
        return this.seminarioDao.save(seminario);
    }

    @Override
    public Seminario findById(String id) {
        return this.seminarioDao.findById(id).orElse(null);
    }

    @Override
    public void delete(Seminario seminario) {
        this.seminarioDao.delete(seminario);
    }

    @Override
    public void deleteById(String id) {
        this.seminarioDao.deleteById(id);
    }
}

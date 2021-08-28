package edu.kalum.notas.core.models.services;

import edu.kalum.notas.core.models.dao.IAlumnosDao;
import edu.kalum.notas.core.models.entities.Alumno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AlumnoServicesImpl implements IAlumnoServices{
    @Autowired
    private IAlumnosDao alumnoDao;
    @Override
    public List<Alumno> findAll() {
        return alumnoDao.findAll();
    }

    @Override
    public Alumno findByCarne(String carne) {
        return alumnoDao.findByCarne(carne);
    }

    @Override
    public Alumno save(Alumno alumno) {
        return alumnoDao.save(alumno);
    }

    @Override
    public void delete(Alumno alumno) {
        alumnoDao.delete(alumno);
    }

    @Override
    public void deleteByCarne(String carne) {
        alumnoDao.deleteByCarne(carne);
    }
}
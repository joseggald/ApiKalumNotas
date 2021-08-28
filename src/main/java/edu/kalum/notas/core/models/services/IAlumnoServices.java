package edu.kalum.notas.core.models.services;

import edu.kalum.notas.core.models.entities.Alumno;

import java.util.List;

public interface IAlumnoServices {
    public List<Alumno> findAll();
    public Alumno findByCarne(String carne);
    public Alumno save(Alumno alumno);
    public void delete(Alumno alumno);
    public void deleteByCarne(String carne);
}

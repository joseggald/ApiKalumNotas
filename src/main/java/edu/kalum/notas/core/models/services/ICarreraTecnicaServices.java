package edu.kalum.notas.core.models.services;

import edu.kalum.notas.core.models.entities.CarreraTecnica;

import java.util.List;

public interface ICarreraTecnicaServices {
    public List<CarreraTecnica> findAll();
    public CarreraTecnica findByCodigoCarrera(String codigoCarrera);
    public CarreraTecnica save(CarreraTecnica carreraTecnica);
    public void delete(CarreraTecnica carreraTecnica);
    public void deleteByCodigoCarrera(String codigoCarrera);

}

package edu.kalum.notas.core.models.services;

import edu.kalum.notas.core.models.dao.ICarreraTecnicaDao;
import edu.kalum.notas.core.models.entities.CarreraTecnica;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CarreraTecnicaServicesImpl implements ICarreraTecnicaServices{
    @Autowired
    private ICarreraTecnicaDao carreraTecnicaDao;

    @Override
    public List<CarreraTecnica> findAll() {
        return carreraTecnicaDao.findAll();
    }

    @Override
    public CarreraTecnica findByCodigoCarrera(String codigoCarrera) {
        return carreraTecnicaDao.findByCodigoCarrera(codigoCarrera);
    }

    @Override
    public CarreraTecnica save(CarreraTecnica carreraTecnica) {
        return carreraTecnicaDao.save(carreraTecnica);
    }

    @Override
    public void delete(CarreraTecnica carreraTecnica) {
         carreraTecnicaDao.delete(carreraTecnica);

    }

    @Override
    public void deleteByCodigoCarrera(String codigoCarrera) {
        carreraTecnicaDao.findByCodigoCarrera(codigoCarrera);
    }

}

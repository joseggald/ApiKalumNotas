package edu.kalum.notas.core.models.dao;

import edu.kalum.notas.core.models.entities.CarreraTecnica;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICarreraTecnicaDao extends JpaRepository<CarreraTecnica, String> {
    public CarreraTecnica findByCodigoCarrera(String codigoCarrera);
   public void deleteByCodigoCarrera(String codigoCarrera);
}

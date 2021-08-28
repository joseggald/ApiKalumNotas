package edu.kalum.notas.core.models.dao;

import edu.kalum.notas.core.models.entities.DetalleNota;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDetalleNotaDao extends JpaRepository<DetalleNota, String> {
}

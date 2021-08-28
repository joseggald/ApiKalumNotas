package edu.kalum.notas.core.controllers;


import edu.kalum.notas.core.models.entities.Alumno;
import edu.kalum.notas.core.models.entities.CarreraTecnica;
import edu.kalum.notas.core.models.services.ICarreraTecnicaServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/kalum-notas/v1")
public class CarreraTecnicaController {

    private Logger logger= LoggerFactory.getLogger(CarreraTecnicaController.class);
    @Autowired
    private ICarreraTecnicaServices carreraTecnicaServices;
    @GetMapping("/carreras")
    public ResponseEntity<?>listarCarreras(){
        Map<String, Object> response = new HashMap<>();
        logger.debug("Iniaciando el proceso de la consulta de carreras en la base de datos.");
        try{
            logger.debug("Iniciando la consulta de base de datos.");
            List<CarreraTecnica> listaCarreras = carreraTecnicaServices.findAll();
            if (listaCarreras  == null || listaCarreras.size()==0 ){
                logger.warn("No existen registros en la tabla de carreras.");
                response.put("Mensaje", "No existen registros en la tabla carreras.");
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NO_CONTENT);
            }else{
                logger.info("Obteniendo listado de la informacion de carreras.");
                return new ResponseEntity<List<CarreraTecnica>>(listaCarreras,HttpStatus.OK);
            }

        }catch (CannotCreateTransactionException e){
            logger.error("Error al momento de conectarse a la base de datos.");
            response.put("Mensaje", "No error al momento de conectarse a la base de datos.");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);
        }catch(DataAccessException e ){
            logger.error("Error al momento de conectarse a la base de Datos.");
            response.put("Mensaje", "Error al momento de consultar la informacion a la base de datos.");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);

        }
    }
    @GetMapping("/carreras/{codigo_carrera}")
    public ResponseEntity<?>showCarreras(@PathVariable String codigo_carrera){
        Map<String, Object> response = new HashMap<>();
        logger.debug("Iniciando el proceso de la consulta de carreras en la base de datos.");
        try{
            logger.debug("Iniciando la consulta de base de datos por numero de carne: ".concat(codigo_carrera));
            CarreraTecnica carrera = carreraTecnicaServices.findByCodigoCarrera(codigo_carrera);
            if (carrera==null){
                logger.warn("No existe en la tabla de carreras con el codigo de carrera.: ".concat(codigo_carrera));
                response.put("Mensaje", "Error al conectarse a la base de datos.");
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
            }else{
                logger.info("Obteniendo información a la base de datos.".concat(codigo_carrera));
                return new ResponseEntity<CarreraTecnica>(carrera,HttpStatus.OK);
            }



        }catch (CannotCreateTransactionException e){
            logger.error("Error al momento de conectarse a la base de datos.");
            response.put("Mensaje", "No error al momento de conectarse a la base de datos.");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);
        }catch(DataAccessException e ){
            logger.error("Error al momento de conectarse a la base de Datos.");
            response.put("Mensaje", "Error al momento de consultar la informacion a la base de datos.");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);

        }
    }
}

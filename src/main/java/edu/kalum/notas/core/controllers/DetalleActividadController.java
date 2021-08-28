package edu.kalum.notas.core.controllers;

import edu.kalum.notas.core.models.entities.DetalleActividad;
import edu.kalum.notas.core.models.entities.DetalleNota;
import edu.kalum.notas.core.models.entities.Modulo;
import edu.kalum.notas.core.models.entities.Seminario;
import edu.kalum.notas.core.models.services.IDetalleActividadServices;
import edu.kalum.notas.core.models.services.IDetalleNotaServices;
import edu.kalum.notas.core.models.services.ISeminarioServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/kalum-notas/v1")
public class DetalleActividadController {
    @Value("2")
    private Integer registros;
    private Logger logger= LoggerFactory.getLogger(AsignacionAlumnosController.class);

    @Autowired
    private IDetalleActividadServices detalleActividadServices;
    @Autowired
    private ISeminarioServices seminarioServices;

    @GetMapping("/detalleActividades")
    public ResponseEntity<?> listaDetalleActividad(){
        Map<String, Object> response = new HashMap<>();
        logger.debug("Iniciando el proceso de la consulta de Detalle Actividad en la base de datos.");
        try {
            logger.debug("Iniciando el proceso de la consulta a la base de datos");
            List<DetalleActividad> listaDetalleActividad=detalleActividadServices.findAll();
            if(listaDetalleActividad == null || listaDetalleActividad.size()==0){
                logger.warn("No existen registros en la tabla Detalle Actividad.");
                response.put("Mensajes", "No existen registros en la tabla Detalle Actividad.");
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NO_CONTENT);
            } else{
                logger.info("Obteniendo lista de Detalle Actividad.");
                return new ResponseEntity<List<DetalleActividad>>(listaDetalleActividad, HttpStatus.OK);
            }

        }catch (CannotCreateTransactionException e){
            logger.error("Error al momento de conectarse a la base de datos.");
            response.put("Mensaje", "Error al momento de conectase a la base de datos.");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);
        }catch (DataAccessException e){
            logger.error("Error al momento de consultar a la base de datos.");
            response.put("Mensaje", "Error al momento de conectase a la base de datos.");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @GetMapping("/detalleActividades/page/{page}")
    public ResponseEntity<?> index(@PathVariable Integer page){
        Map<String, Object>response = new HashMap<>();
        Pageable pageable = PageRequest.of(page,5);
        try{
            Page<DetalleActividad> detalleActividades=detalleActividadServices.findAll(pageable);
            if(detalleActividades==null || detalleActividades.getSize()==0){
                return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NO_CONTENT);
            }else{
                return new ResponseEntity<Page<DetalleActividad>>(detalleActividades, HttpStatus.OK);
            }
        }catch (CannotCreateTransactionException e){
            logger.error("Error al momento de conectarse a la base de datos.");
            response.put("Mensaje", "Error al momento de conectase a la base de datos.");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);
        }catch (DataAccessException e){
            logger.error("Error al momento de consultar a la base de datos.");
            response.put("Mensaje", "Error al momento de conectase a la base de datos.");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);
        }

    }
    @GetMapping("/detalleActividades/{id}")
    public ResponseEntity<?> show(@PathVariable String id){
        Map<String, Object>response = new HashMap<>();
        try {
            DetalleActividad detalleActividad = detalleActividadServices.findById(id);
            if(detalleActividad==null){
                response.put("Mensaje","No existe el Detalle Actividad con el Id: ".concat(id));
                return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NO_CONTENT);
            }else{
                return new ResponseEntity<DetalleActividad>(detalleActividad, HttpStatus.OK);
            }

        }catch (CannotCreateTransactionException e){
            logger.error("Error al momento de conectarse a la base de datos.");
            response.put("Mensaje", "Error al momento de conectase a la base de datos.");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);
        }catch (DataAccessException e){
            logger.error("Error al momento de consultar a la base de datos.");
            response.put("Mensaje", "Error al momento de conectase a la base de datos.");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @PostMapping("/detalleActividades")
    public ResponseEntity<?> create(@Valid @RequestBody DetalleActividad registro, BindingResult result){
        DetalleActividad detalleActividad=null;
        Map<String, Object>response=new HashMap<>();
        if (result.hasErrors()){
            List<String> errores= result.getFieldErrors().stream().map(error -> error.getDefaultMessage()).
                    collect(Collectors.toList());
            response.put("Errores: ", errores);
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.BAD_REQUEST);
        }
        try{
            Seminario seminario= seminarioServices.findById(registro.getSeminario().getSeminarioId());
            if( seminario == null){
                response.put("Mensaje","No existe el seminario con el Id: ".concat(registro.getSeminario().getSeminarioId()));
                return new ResponseEntity<Map<String,Object>>(response, HttpStatus.BAD_REQUEST);
            }
            registro.setDetalleActividadId(UUID.randomUUID().toString());
            detalleActividad = this.detalleActividadServices.save(registro);
        }catch (CannotCreateTransactionException e){
            logger.error("Error al momento de conectarse a la base de datos.");
            response.put("Mensaje", "Error al momento de conectase a la base de datos.");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);
        }catch (DataAccessException e){
            logger.error("Error al momento de consultar a la base de datos.");
            response.put("Mensaje", "Error al momento de conectase a la base de datos.");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);
        }
        response.put("Mensaje", "El detalle actividad fue creado con exito.");
        response.put("Detalle Actividad: ", detalleActividad);
        return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);
    }

    @PutMapping("/detalleActividades/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody DetalleActividad update,BindingResult result, @PathVariable String id){
        Map<String,Object> response=new HashMap<>();
        if(result.hasErrors()){
            List<String> errores=result.getFieldErrors().stream().map(error -> error.getDefaultMessage()).
                    collect(Collectors.toList());
            response.put("Errores: ", errores);
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.BAD_REQUEST);
        }
        DetalleActividad detalleActividad = this.detalleActividadServices.findById(id);
        if(detalleActividad == null){
            response.put("Mensaje","No existe el detalle Actividad con el Id: ".concat(id));
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
        }
        try{
            Seminario seminario= seminarioServices.findById(update.getSeminario().getSeminarioId());
            if( seminario == null){
                response.put("Mensaje","No existe el seminario con el Id: ".concat(update.getSeminario().getSeminarioId()));
                return new ResponseEntity<Map<String,Object>>(response, HttpStatus.BAD_REQUEST);
            }
            detalleActividad.setEstado(update.getEstado());
            detalleActividad.setFechaCreacion(update.getFechaCreacion());
            detalleActividad.setFechaEntrega(update.getFechaEntrega());
            detalleActividad.setFechaPostergacion(update.getFechaPostergacion());
            detalleActividad.setNombreActividad(update.getNombreActividad());
            detalleActividad.setNotaActividad(update.getNotaActividad());
            this.detalleActividadServices.save(detalleActividad);

        }catch (CannotCreateTransactionException e){
            logger.error("Error al momento de conectarse a la base de datos.");
            response.put("Mensaje", "Error al momento de conectase a la base de datos.");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);
        }catch (DataAccessException e){
            logger.error("Error al momento de consultar a la base de datos.");
            response.put("Mensaje", "Error al momento de conectase a la base de datos.");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);
        }
        response.put("Mensaje","El Detalle Actividad ha sido modificado correctamente. ");
        response.put("Detalle Actividad: ",detalleActividad);
        return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
    }
    @DeleteMapping("/detalleActividades/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        Map<String, Object> response = new HashMap<>();
        DetalleActividad detalleActividad = null;
        try {
            detalleActividad= detalleActividadServices.findById(id);
            if (detalleActividad== null) {
                response.put("Mensaje", "No existe ningun detalle actividad con el id: ".concat(id));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
            } else {
                detalleActividadServices.delete(detalleActividad);
            }
        } catch (CannotCreateTransactionException e) {
            logger.error("Error al momento de conectarse a la base de datos.");
            response.put("Mensaje", "Error al momento de conectase a la base de datos.");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);
        } catch (DataAccessException e) {
            logger.error("Error al momento de consultar a la base de datos.");
            response.put("Mensaje", "Error al momento de conectase a la base de datos.");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);
        }
        response.put("Mensaje","El detalle actividad ha sido eliminada correctamente. ");
        response.put("Detalle Actividad: ", detalleActividad);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }
}

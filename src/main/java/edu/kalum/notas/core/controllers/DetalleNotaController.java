package edu.kalum.notas.core.controllers;

import edu.kalum.notas.core.models.entities.*;
import edu.kalum.notas.core.models.services.IAlumnoServices;
import edu.kalum.notas.core.models.services.IDetalleActividadServices;
import edu.kalum.notas.core.models.services.IDetalleNotaServices;
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
public class DetalleNotaController {
    @Value("2")
    private Integer registros;
    private Logger logger= LoggerFactory.getLogger(AsignacionAlumnosController.class);

    @Autowired
    private IDetalleNotaServices detalleNotaServices;
    @Autowired
    private IAlumnoServices alumnoServices;
    @Autowired
    private IDetalleActividadServices detalleActividadServices;

    @GetMapping("/detalleNotas")
    public ResponseEntity<?> listaDetalleNota(){
        Map<String, Object> response = new HashMap<>();
        logger.debug("Iniciando el proceso de la consulta de Detalle Nota en la base de datos.");
        try {
            logger.debug("Iniciando el proceso de la consulta a la base de datos");
            List<DetalleNota> listaDetalleNota=detalleNotaServices.findAll();
            if(listaDetalleNota == null || listaDetalleNota.size()==0){
                logger.warn("No existen registros en la tabla Detalle Nota.");
                response.put("Mensajes", "No existen registros en la tabla Detalle Nota.");
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NO_CONTENT);
            } else{
                logger.info("Obteniendo lista de Detalle Nota.");
                return new ResponseEntity<List<DetalleNota>>(listaDetalleNota, HttpStatus.OK);
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

    @GetMapping("/detalleNotas/page/{page}")
    public ResponseEntity<?> index(@PathVariable Integer page){
        Map<String, Object>response = new HashMap<>();
        Pageable pageable = PageRequest.of(page,5);
        try{
            Page<DetalleNota> detalleNotas=detalleNotaServices.findAll(pageable);
            if(detalleNotas==null || detalleNotas.getSize()==0){
                return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NO_CONTENT);
            }else{
                return new ResponseEntity<Page<DetalleNota>>(detalleNotas, HttpStatus.OK);
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
    @GetMapping("/detalleNotas/{id}")
    public ResponseEntity<?> show(@PathVariable String id){
        Map<String, Object>response = new HashMap<>();
        try {
            DetalleNota detalleNota = detalleNotaServices.findById(id);
            if(detalleNota==null){
                response.put("Mensaje","No existe el Detalle Nota con el Id: ".concat(id));
                return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NO_CONTENT);
            }else{
                return new ResponseEntity<DetalleNota>(detalleNota, HttpStatus.OK);
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

    @PostMapping("/detalleNotas")
    public ResponseEntity<?> create(@Valid @RequestBody DetalleNota registro, BindingResult result){
        DetalleNota detalleNota=null;
        Map<String, Object>response=new HashMap<>();
        if (result.hasErrors()){
            List<String> errores= result.getFieldErrors().stream().map(error -> error.getDefaultMessage()).
                    collect(Collectors.toList());
            response.put("Errores: ", errores);
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.BAD_REQUEST);
        }
        try{
            Alumno alumno = alumnoServices.findByCarne(registro.getAlumno().getCarne());
            if( alumno == null){
                response.put("Mensaje","No existe el alumno con el Id: ".concat(registro.getAlumno().getCarne()));
                return new ResponseEntity<Map<String,Object>>(response, HttpStatus.BAD_REQUEST);
            }
            DetalleActividad detalleActividad= detalleActividadServices.findById(registro.getDetalleActividad().getDetalleActividadId());
            if( detalleActividad == null){
                response.put("Mensaje","No existe el detalle actividad con el Id: ".concat(registro.getDetalleActividad().getDetalleActividadId()));
                return new ResponseEntity<Map<String,Object>>(response, HttpStatus.BAD_REQUEST);
            }
            registro.setDetalleNotaId(UUID.randomUUID().toString());
            detalleNota = this.detalleNotaServices.save(registro);
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
        response.put("Mensaje", "El detalle nota fue creado con exito.");
        response.put("Detalle Nota: ", detalleNota);
        return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);
    }

    @PutMapping("/detalleNotas/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody DetalleNota update,BindingResult result, @PathVariable String id){
        Map<String,Object> response=new HashMap<>();
        if(result.hasErrors()){
            List<String> errores=result.getFieldErrors().stream().map(error -> error.getDefaultMessage()).
                    collect(Collectors.toList());
            response.put("Errores: ", errores);
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.BAD_REQUEST);
        }
        DetalleNota detalleNota = this.detalleNotaServices.findById(id);
        if(detalleNota == null){
            response.put("Mensaje","No existe el detalle nota con el Id: ".concat(id));
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
        }
        try{
            Alumno alumno = alumnoServices.findByCarne(update.getAlumno().getCarne());
            if( alumno == null){
                response.put("Mensaje","No existe el alumno con el Id: ".concat(update.getAlumno().getCarne()));
                return new ResponseEntity<Map<String,Object>>(response, HttpStatus.BAD_REQUEST);
            }
            DetalleActividad detalleActividad= detalleActividadServices.findById(update.getDetalleActividad().getDetalleActividadId());
            if( detalleActividad == null){
                response.put("Mensaje","No existe el detalle actividad con el Id: ".concat(update.getDetalleActividad().getDetalleActividadId()));
                return new ResponseEntity<Map<String,Object>>(response, HttpStatus.BAD_REQUEST);
            }
            detalleNota.setValorNota(update.getValorNota());
            detalleNota.setAlumno(alumno);
            detalleNota.setDetalleActividad(detalleActividad);
            this.detalleNotaServices.save(detalleNota);

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
        response.put("Mensaje","El Detalle Nota ha sido modificado correctamente. ");
        response.put("Detalle Nota: ",detalleNota);
        return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
    }
    @DeleteMapping("/detalleNotas/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        Map<String, Object> response = new HashMap<>();
        DetalleNota detalleNota = null;
        try {
            detalleNota = detalleNotaServices.findById(id);
            if (detalleNota == null) {
                response.put("Mensaje", "No existe ningun detalle nota con el id: ".concat(id));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
            } else {
                detalleNotaServices.delete(detalleNota );
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
        response.put("Mensaje","El detalle nota ha sido eliminada correctamente. ");
        response.put("Detalle Nota: ", detalleNota);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }
}

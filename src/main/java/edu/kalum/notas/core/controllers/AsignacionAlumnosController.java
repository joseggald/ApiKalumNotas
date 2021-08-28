package edu.kalum.notas.core.controllers;

import edu.kalum.notas.core.models.entities.Alumno;
import edu.kalum.notas.core.models.entities.AsignacionAlumno;
import edu.kalum.notas.core.models.entities.Clase;
import edu.kalum.notas.core.models.services.IAlumnoServices;
import edu.kalum.notas.core.models.services.IAsignacionAlumnoServices;
import edu.kalum.notas.core.models.services.IClaseServices;
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
public class AsignacionAlumnosController {

    @Value("${edu.kalum.configuration.page.registros}")
    private Integer registros;

    private Logger logger= LoggerFactory.getLogger(AsignacionAlumnosController.class);
    @Autowired
    private IAsignacionAlumnoServices asignacionAlumnoServices;
    @Autowired
    private IAlumnoServices alumnoService;
    @Autowired
    private IClaseServices claseServices;

    @GetMapping("/asignaciones/page/{page}")
    public ResponseEntity<?> index(@PathVariable Integer page){
        Map<String, Object>response = new HashMap<>();
        Pageable pageable = PageRequest.of(page,5);
        try{
            Page<AsignacionAlumno> asignaciones=asignacionAlumnoServices.findAll(pageable);
            if(asignaciones==null || asignaciones.getSize()==0){
                return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NO_CONTENT);
            }else{
                return new ResponseEntity<Page<AsignacionAlumno>>(asignaciones, HttpStatus.OK);
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

    @GetMapping("/asignaciones/{id}")
    public ResponseEntity<?> show(@PathVariable String id){
        Map<String, Object>response = new HashMap<>();
        try {
            AsignacionAlumno asignacion = asignacionAlumnoServices.findById(id);
            if(asignacion==null){
                response.put("Mensaje","No existe la asiganacion con el Id: ".concat(id));
                return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NO_CONTENT);
            }else{
                return new ResponseEntity<AsignacionAlumno>(asignacion, HttpStatus.OK);
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

    @GetMapping("/asignaciones")
    public ResponseEntity<?>listaAsignaciones(){
        Map<String, Object>response = new HashMap<>();
        logger.debug("Iniciando el proceso de la consulta de las asignaciones en la base de datos.");
        try {
            logger.debug("Iniciando el proceso de la consulta a la base de datos");
            List<AsignacionAlumno> listaAsignaciones=asignacionAlumnoServices.findAll();
            if(listaAsignaciones == null || listaAsignaciones.size()==0){
                logger.warn("No existen registros en la tabla Asignaciones.");
                response.put("Mensajes", "No existen registros en la tabla Asignaciones.");
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NO_CONTENT);
            } else{
                logger.info("Obteniendo lista de Asignaciones.");
                return new ResponseEntity<List<AsignacionAlumno>>(listaAsignaciones, HttpStatus.OK);
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

    @PostMapping("/asignaciones")
    public ResponseEntity<?> create(@Valid @RequestBody AsignacionAlumno registro, BindingResult result){
        AsignacionAlumno asignacionAlumno=null;
        Map<String, Object>response=new HashMap<>();
        if (result.hasErrors()){
            List<String> errores= result.getFieldErrors().stream().map(error -> error.getDefaultMessage()).
                    collect(Collectors.toList());
            response.put("Errores: ", errores);
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.BAD_REQUEST);
        }
        try{
            Alumno alumno = alumnoService.findByCarne(registro.getAlumno().getCarne());
            if( alumno == null){
                response.put("Mensaje","No existe la asiganacion con el Id: ".concat(registro.getAlumno().getCarne()));
                return new ResponseEntity<Map<String,Object>>(response, HttpStatus.BAD_REQUEST);
            }
            Clase clase = claseServices.findById(registro.getClase().getClaseId());
            if( clase == null){
                response.put("Mensaje","No existe la asiganacion con el Id: ".concat(registro.getAlumno().getCarne()));
                return new ResponseEntity<Map<String,Object>>(response, HttpStatus.BAD_REQUEST);
            }
            registro.setAsignacionId(UUID.randomUUID().toString());
            asignacionAlumno = this.asignacionAlumnoServices.save(registro);
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
        response.put("Mensaje", "La asignación fue creada con exito.");
        response.put("Asiganación: ", asignacionAlumno);
        return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);
    }

    @PutMapping("/asignaciones/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody AsignacionAlumno update,BindingResult result, @PathVariable String id){
        Map<String,Object> response=new HashMap<>();
        if(result.hasErrors()){
            List<String> errores=result.getFieldErrors().stream().map(error -> error.getDefaultMessage()).
                    collect(Collectors.toList());
            response.put("Errores: ", errores);
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.BAD_REQUEST);
        }
        AsignacionAlumno asignacion = this.asignacionAlumnoServices.findById(id);
        if(asignacion == null){
            response.put("Mensaje","No existe la asiganacion con el Id: ".concat(id));
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
        }
        try{
            Alumno alumno = alumnoService.findByCarne(update.getAlumno().getCarne());
            if( alumno == null){
                response.put("Mensaje","No existe la asignacion con el Id: ".concat(update.getAlumno().getCarne()));
                return new ResponseEntity<Map<String,Object>>(response, HttpStatus.BAD_REQUEST);
            }
            Clase clase = claseServices.findById(update.getClase().getClaseId());
            if( clase == null){
                response.put("Mensaje","No existe la asignacion con el Id: ".concat(update.getAlumno().getCarne()));
                return new ResponseEntity<Map<String,Object>>(response, HttpStatus.BAD_REQUEST);
            }
            asignacion.setFechaAsignacion(update.getFechaAsignacion());
            asignacion.setAlumno(alumno);
            asignacion.setClase(clase);
            this.asignacionAlumnoServices.save(asignacion);

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
        response.put("Mensaje","La asignación ha sido modificada correctamente. ");
        response.put("Asignacion",asignacion);
        return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
    }

    @DeleteMapping("/asignaciones/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        Map<String, Object> response = new HashMap<>();
        AsignacionAlumno asignacion = null;
        try {
            asignacion = asignacionAlumnoServices.findById(id);
            if (asignacion == null) {
                response.put("Mensaje", "No existe ninguna asignacion con el id: ".concat(id));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
            } else {
                asignacionAlumnoServices.delete(asignacion);
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
        response.put("Mensaje","La asignación ha sido eliminada correctamente. ");
        response.put("Asignacion", asignacion);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }
}

package dh.backend.clinicamvc.controller;

import dh.backend.clinicamvc.entity.Odontologo;
import dh.backend.clinicamvc.exception.BadRequestException;
import dh.backend.clinicamvc.exception.ResourceNotFoundException;
import dh.backend.clinicamvc.service.impl.OdontologoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/odontologo")
public class OdontologoController {
    private static Logger LOGGER = LoggerFactory.getLogger(OdontologoController.class);
    private OdontologoService odontologoService;

    public OdontologoController(OdontologoService odontologoService) {
        this.odontologoService = odontologoService;
    }
    @PostMapping
    public ResponseEntity<Odontologo> registrarOdontologo(@RequestBody Odontologo odontologo){
        LOGGER.info("Nuevo odontologo resgistrado :)");
        return ResponseEntity.status(HttpStatus.CREATED).body(odontologoService.agregarOdontologo(odontologo));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Odontologo>  buscarOdontologoPorId(@PathVariable Integer id){
        Optional<Odontologo> odontologo = odontologoService.buscarUnOdontologo(id);
        if(odontologo.isPresent()){
            Odontologo odontologoARetornar = odontologo.get();
            LOGGER.info("Odontologo encontado con exito");
            return ResponseEntity.ok(odontologoARetornar);
        }
        else {
            LOGGER.info("El odontologo no fue encontrado");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping
    public ResponseEntity<String> modificarOdontologo(@org.jetbrains.annotations.NotNull @RequestBody Odontologo odontologo){
        Optional<Odontologo> odontologoOptional = odontologoService.buscarUnOdontologo(odontologo.getId());
        if(odontologoOptional.isPresent()){
            odontologoService.modificarOdontologo(odontologo);
            LOGGER.info("El odontologo fue exitosamente modificado");
            return ResponseEntity.ok("{\"message\": \"odontologo modificado\"}");
        } else {
            LOGGER.info("El odontologo no logro ser modificado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarOdontologo(@PathVariable Integer id) throws ResourceNotFoundException {
        Optional<Odontologo> odontologo = odontologoService.buscarUnOdontologo(id);
        if(odontologo.isPresent()){
            odontologoService.eliminarOdontologo(id);
            LOGGER.info("Odontologo eliminado con exito");
            return ResponseEntity.ok("{\"message\": \"odontologo eliminado\"}");
        } else {
            LOGGER.info("No fue posible eliminar ningun odontologo.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Odontologo>> buscarTodos(){
        LOGGER.info("Listado de odontologos exitoso");
        return ResponseEntity.ok(odontologoService.buscarTodosOdontologos());
    }

    @GetMapping("/apellido/{apellido}")
    public ResponseEntity<List<Odontologo>> buscarPorApellido(@PathVariable String apellido){
        List<Odontologo> listaOdontologos =odontologoService.buscarPorApellido(apellido);
        if(listaOdontologos.size()>0){
            LOGGER.info("La busqueda de odontologo por apellido fue exitosa");
            return ResponseEntity.ok(listaOdontologos);
        } else {
            LOGGER.info("La busqueda de odontologo por apellido no resulto exitosa");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<Odontologo>> buscarTodos(@PathVariable String nombre){
        return ResponseEntity.ok(odontologoService.buscarPorNombre(nombre));
    }
}

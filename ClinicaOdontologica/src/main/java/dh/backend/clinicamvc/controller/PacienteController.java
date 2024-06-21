package dh.backend.clinicamvc.controller;

import dh.backend.clinicamvc.entity.Paciente;
import dh.backend.clinicamvc.exception.ResourceNotFoundException;
import dh.backend.clinicamvc.service.IPacienteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/paciente")
public class PacienteController {
    private static Logger LOGGER = LoggerFactory.getLogger(PacienteController.class);
    public IPacienteService pacienteService;

    public PacienteController(IPacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @PostMapping
    public ResponseEntity<Paciente>  registrarPaciente(@RequestBody Paciente paciente){
        Paciente pacienteARetornar = pacienteService.registrarPaciente(paciente);
        if(pacienteARetornar==null){
            LOGGER.error("El paciente no pudo ser registrado");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            LOGGER.info("El paciente se registro con exito");
            return ResponseEntity.status(HttpStatus.CREATED).body(pacienteARetornar);
        }
    }

    @GetMapping
    public ResponseEntity<List<Paciente>>  buscarTodos(){
        LOGGER.info("Lista de pacientes exitoso");
        return ResponseEntity.ok(pacienteService.buscarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscarPacientePorId(@PathVariable Integer id){
        Optional<Paciente> paciente = pacienteService.buscarPorId(id);
        if(paciente.isPresent()){
            LOGGER.info("Busqueda de paciente por ID exitosa");
            return ResponseEntity.ok(paciente.get());
        }else{
            LOGGER.info("Busqueda de paciente por ID no resulto");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping
    public ResponseEntity<String>  actualizarPaciente(@RequestBody Paciente paciente){
        pacienteService.actualizarPaciente(paciente);
        LOGGER.info("El paciente fue actualizado exitosamente");
        return  ResponseEntity.ok("paciente actualizado");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String>  borrarPaciente(@PathVariable Integer id) throws ResourceNotFoundException {
        pacienteService.eliminarPaciente(id);
        LOGGER.info("El paciente se elimono exitosamente");
        return ResponseEntity.ok("{\"message\": \"paciente eliminado\"}");
    }

}

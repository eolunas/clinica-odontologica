package dh.backend.clinicamvc.service;

import dh.backend.clinicamvc.Dto.request.TurnoRequestDto;
import dh.backend.clinicamvc.Dto.response.TurnoResponseDto;
import dh.backend.clinicamvc.entity.Odontologo;
import dh.backend.clinicamvc.entity.Paciente;
import dh.backend.clinicamvc.entity.Turno;
import dh.backend.clinicamvc.exception.BadRequestException;
import dh.backend.clinicamvc.service.impl.TurnoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class TurnoServiceTest {
    private static Logger LOGGER = LoggerFactory.getLogger(TurnoServiceTest.class);
    @Autowired
    private TurnoService turnoService;
    private TurnoRequestDto turno;

    @BeforeEach
    void setUp(){
        //turno = new Turno();
        //Paciente paciente = new Paciente();
        //paciente.setNombre("Lisa");
        //paciente.setApellido("Simpson");
        //paciente.setDni("9876543");
        //paciente.setFechaIngreso(LocalDate.of(2024,3,24));

        //Odontologo  odontologo = new Odontologo();
        //odontologo.setNombre("Carlos");
        //odontologo.setApellido("Rodriguez");
        //odontologo.setNroMatricula("CD451687");

        //turno.setPaciente(paciente);
        //turno.setOdontologo(odontologo);

        turno = new TurnoRequestDto(1,1,"2024-06-20");

    }

    @Test
    @DisplayName("Testear que un turno fue registrado")
    void testTurnoRegistrado() throws BadRequestException {
        TurnoResponseDto turnoEnBD = turnoService.registrar(turno);
        LOGGER.info("Test de Turno registrado exitoso");
        assertNotNull(turnoEnBD);
    }
}
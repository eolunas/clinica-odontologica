package dh.backend.clinicamvc.service;

import dh.backend.clinicamvc.entity.Odontologo;
import dh.backend.clinicamvc.entity.Paciente;
import dh.backend.clinicamvc.service.impl.OdontologoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class OdontologoServiceTest {
    private static Logger LOGGER = LoggerFactory.getLogger(OdontologoServiceTest.class);
    @Autowired
    private OdontologoService odontologoService;
    private Odontologo odontologo;

    @BeforeEach
    void setUp(){
        odontologo = new Odontologo();
        odontologo.setNombre("Orlando");
        odontologo.setApellido("Dante");
        odontologo.setNroMatricula("CD3415");
    }

    @Test
    @DisplayName("Testear Odontologo por id")
    void testOdontologoGuardado () {
        Odontologo odontologoEnBD = odontologoService.agregarOdontologo(odontologo);
        LOGGER.info("Test realizado con exito");
        assertNotNull(odontologoEnBD);
    }

    @Test
    @DisplayName("Testear busqueda todos los odontologos")
    void testBusquedaTodos() {

        List<Odontologo> odontologos = odontologoService.buscarTodosOdontologos();
        assertTrue(odontologos.size()!=0);
        LOGGER.info("Test de lista de odontologos finalizado");

    }
}
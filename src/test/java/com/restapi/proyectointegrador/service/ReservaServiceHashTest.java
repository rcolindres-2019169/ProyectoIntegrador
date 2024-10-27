package com.restapi.proyectointegrador.service;

import com.restapi.proyectointegrador.model.Reserva;
import com.restapi.proyectointegrador.model.ReservaServiceI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ReservaServiceHashTest {

    private ReservaServiceI reservaService;

    @BeforeEach
    void setUp() {
        reservaService = new ReservaServiceHash();
    }

    @Test
    void createReserva_DeberiaAgregarReservaCorrectamente() {
        // Datos de prueba
        Reserva reserva = new Reserva("1", "usuario1", LocalDateTime.now());

        // Ejecución del método y verificación
        Reserva resultado = reservaService.createReserva(reserva);
        assertNotNull(resultado);
        assertEquals("1", resultado.getId());
        assertEquals("usuario1", resultado.getIdUsuario());

        // Verificar que la reserva fue almacenada
        Optional<Reserva> reservaGuardada = reservaService.getReservaById("1");
        assertTrue(reservaGuardada.isPresent());
        assertEquals("usuario1", reservaGuardada.get().getIdUsuario());
    }

    @Test
    void getReservaById_DeberiaRetornarReservaSiExiste() {
        // Datos de prueba
        Reserva reserva = new Reserva("1", "usuario1", LocalDateTime.now());
        reservaService.createReserva(reserva);

        // Ejecución del método y verificación
        Optional<Reserva> resultado = reservaService.getReservaById("1");
        assertTrue(resultado.isPresent());
        assertEquals("1", resultado.get().getId());
        assertEquals("usuario1", resultado.get().getIdUsuario());
    }

    @Test
    void getReservaById_DeberiaRetornarVacioSiNoExiste() {
        // Ejecución del método y verificación
        Optional<Reserva> resultado = reservaService.getReservaById("1");
        assertFalse(resultado.isPresent());
    }

    @Test
    void getAllReservas_DeberiaRetornarTodasLasReservas() {
        // Datos de prueba
        Reserva reserva1 = new Reserva("1", "usuario1", LocalDateTime.now());
        Reserva reserva2 = new Reserva("2", "usuario2", LocalDateTime.now());
        reservaService.createReserva(reserva1);
        reservaService.createReserva(reserva2);

        // Ejecución del método y verificación
        List<Reserva> resultado = reservaService.getAllReservas();
        assertEquals(2, resultado.size());
    }

    @Test
    void updateReserva_DeberiaActualizarReservaSiExiste() {
        // Datos de prueba
        Reserva reserva = new Reserva("1", "usuario1", LocalDateTime.now());
        reservaService.createReserva(reserva);

        // Nueva reserva con los datos actualizados
        Reserva reservaActualizada = new Reserva("1", "usuarioActualizado", LocalDateTime.now());
        Reserva resultado = reservaService.updateReserva("1", reservaActualizada);

        // Verificación
        assertNotNull(resultado);
        assertEquals("usuarioActualizado", resultado.getIdUsuario());

        // Confirmar que la reserva fue actualizada
        Optional<Reserva> reservaGuardada = reservaService.getReservaById("1");
        assertTrue(reservaGuardada.isPresent());
        assertEquals("usuarioActualizado", reservaGuardada.get().getIdUsuario());
    }

    @Test
    void deleteReserva_DeberiaEliminarReservaSiExiste() {
        // Datos de prueba
        Reserva reserva = new Reserva("1", "usuario1", LocalDateTime.now());
        reservaService.createReserva(reserva);

        // Ejecución del método y verificación
        boolean resultado = reservaService.deleteReserva("1");
        assertTrue(resultado);

        // Confirmar que la reserva fue eliminada
        Optional<Reserva> reservaEliminada = reservaService.getReservaById("1");
        assertFalse(reservaEliminada.isPresent());
    }

    @Test
    void deleteReserva_DeberiaRetornarFalseSiNoExiste() {
        // Ejecución del método y verificación
        boolean resultado = reservaService.deleteReserva("1");
        assertFalse(resultado);
    }
}


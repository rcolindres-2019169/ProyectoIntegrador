package com.restapi.proyectointegrador.service;

import com.restapi.proyectointegrador.model.Reserva;
import com.restapi.proyectointegrador.repository.ReservaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReservaServiceTest {

    @Mock
    private ReservaRepository reservaRepository;

    @InjectMocks
    private ReservaService reservaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void obtenerTodas_DeberiaRetornarTodasLasReservas() {
        // Datos de prueba
        Reserva reserva1 = new Reserva("1", "usuario1", LocalDateTime.now());
        Reserva reserva2 = new Reserva("2", "usuario2", LocalDateTime.now());
        List<Reserva> reservas = Arrays.asList(reserva1, reserva2);

        // Configurar comportamiento del mock
        when(reservaRepository.findAll()).thenReturn(reservas);

        // Ejecución del método y verificación
        List<Reserva> resultado = reservaService.obtenerTodas();
        assertEquals(2, resultado.size());
        verify(reservaRepository, times(1)).findAll();
    }

    @Test
    void obtenerPorId_DeberiaRetornarReservaSiExiste() {
        // Datos de prueba
        String id = "1";
        Reserva reserva = new Reserva(id, "usuario1", LocalDateTime.now());

        // Configurar comportamiento del mock
        when(reservaRepository.findById(id)).thenReturn(Optional.of(reserva));

        // Ejecución del método y verificación
        Optional<Reserva> resultado = reservaService.obtenerPorId(id);
        assertTrue(resultado.isPresent());
        assertEquals(id, resultado.get().getId());
        verify(reservaRepository, times(1)).findById(id);
    }

    @Test
    void obtenerPorId_DeberiaRetornarVacioSiNoExiste() {
        // Configurar comportamiento del mock
        String id = "1";
        when(reservaRepository.findById(id)).thenReturn(Optional.empty());

        // Ejecución del método y verificación
        Optional<Reserva> resultado = reservaService.obtenerPorId(id);
        assertFalse(resultado.isPresent());
        verify(reservaRepository, times(1)).findById(id);
    }

    @Test
    void crearReserva_DeberiaGuardarReserva() {
        // Datos de prueba
        Reserva reserva = new Reserva("usuario1");

        // Configurar comportamiento del mock
        when(reservaRepository.save(reserva)).thenReturn(reserva);

        // Ejecución del método y verificación
        Reserva resultado = reservaService.crearReserva(reserva);
        assertNotNull(resultado);
        assertEquals("usuario1", resultado.getIdUsuario());
        verify(reservaRepository, times(1)).save(reserva);
    }

    @Test
    void actualizarReserva_DeberiaActualizarReservaSiExiste() {
        // Datos de prueba
        String id = "1";
        Reserva reservaExistente = new Reserva(id, "usuario1", LocalDateTime.now());
        Reserva reservaNueva = new Reserva("usuario2");

        // Configurar comportamiento del mock
        when(reservaRepository.findById(id)).thenReturn(Optional.of(reservaExistente));
        when(reservaRepository.save(reservaNueva)).thenReturn(reservaNueva);

        // Ejecución del método y verificación
        Reserva resultado = reservaService.actualizarReserva(id, reservaNueva);
        assertNotNull(resultado);
        assertEquals("usuario2", resultado.getIdUsuario());
        verify(reservaRepository, times(1)).findById(id);
        verify(reservaRepository, times(1)).save(reservaNueva);
    }

    @Test
    void actualizarReserva_DeberiaRetornarNullSiNoExiste() {
        // Configurar comportamiento del mock
        String id = "1";
        Reserva reservaNueva = new Reserva("usuario2");
        when(reservaRepository.findById(id)).thenReturn(Optional.empty());

        // Ejecución del método y verificación
        Reserva resultado = reservaService.actualizarReserva(id, reservaNueva);
        assertNull(resultado);
        verify(reservaRepository, times(1)).findById(id);
        verify(reservaRepository, never()).save(reservaNueva);
    }

    @Test
    void eliminarReserva_DeberiaEliminarReserva() {
        // Datos de prueba
        String id = "1";

        // Ejecución del método y verificación
        reservaService.eliminarReserva(id);
        verify(reservaRepository, times(1)).deleteById(id);
    }
}

package com.restapi.proyectointegrador.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restapi.proyectointegrador.controller.reserva.ReservaController;
import com.restapi.proyectointegrador.model.Reserva;
import com.restapi.proyectointegrador.service.ReservaServiceHash;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReservaController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ReservaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservaServiceHash reservaService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules(); // Habilita el manejo de LocalDateTime en Jackson
    }

    @Test
    void createReserva_DeberiaRetornarReservaCreada() throws Exception {
        // Datos de prueba
        Reserva reserva = new Reserva("1", "usuario1", LocalDateTime.of(2024, 10, 10, 10, 0));

        Mockito.when(reservaService.createReserva(any(Reserva.class))).thenReturn(reserva);

        // Ejecución y Verificación
        mockMvc.perform(post("/api/reservas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reserva)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("1")))
                .andExpect(jsonPath("$.idUsuario", is("usuario1")))
                .andExpect(jsonPath("$.fecha", is("2024-10-10T10:00:00")));
    }

    @Test
    void getReserva_DeberiaRetornarReservaSiExiste() throws Exception {
        // Datos de prueba
        Reserva reserva = new Reserva("1", "usuario1", LocalDateTime.of(2024, 10, 10, 10, 0));

        Mockito.when(reservaService.getReservaById("1")).thenReturn(Optional.of(reserva));

        // Ejecución y Verificación
        mockMvc.perform(get("/api/reservas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("1")))
                .andExpect(jsonPath("$.idUsuario", is("usuario1")))
                .andExpect(jsonPath("$.fecha", is("2024-10-10T10:00:00")));
    }



    @Test
    void getAllReservas_DeberiaRetornarListaDeReservas() throws Exception {
        // Datos de prueba
        Reserva reserva1 = new Reserva("1", "usuario1", LocalDateTime.of(2024, 10, 10, 10, 0));
        Reserva reserva2 = new Reserva("2", "usuario2", LocalDateTime.of(2024, 10, 11, 11, 0));

        Mockito.when(reservaService.getAllReservas()).thenReturn(Arrays.asList(reserva1, reserva2));

        mockMvc.perform(get("/api/reservas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is("1")))
                .andExpect(jsonPath("$[0].idUsuario", is("usuario1")))
                .andExpect(jsonPath("$[1].id", is("2")))
                .andExpect(jsonPath("$[1].idUsuario", is("usuario2")));
    }

    @Test
    void updateReserva_DeberiaRetornarReservaActualizada() throws Exception {
        // Datos de prueba
        Reserva reservaActualizada = new Reserva("1", "usuarioActualizado", LocalDateTime.of(2024, 10, 12, 12, 0));

        Mockito.when(reservaService.updateReserva(eq("1"), any(Reserva.class))).thenReturn(reservaActualizada);

        mockMvc.perform(put("/api/reservas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reservaActualizada)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("1")))
                .andExpect(jsonPath("$.idUsuario", is("usuarioActualizado")))
                .andExpect(jsonPath("$.fecha", is("2024-10-12T12:00:00")));
    }

    @Test
    void deleteReserva_DeberiaRetornarTrueSiReservaEliminada() throws Exception {
        Mockito.when(reservaService.deleteReserva("1")).thenReturn(true);

        mockMvc.perform(delete("/api/reservas/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void deleteReserva_DeberiaRetornarFalseSiReservaNoExiste() throws Exception {
        Mockito.when(reservaService.deleteReserva("1")).thenReturn(false);

        mockMvc.perform(delete("/api/reservas/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }
}


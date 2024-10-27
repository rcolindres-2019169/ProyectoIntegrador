package com.restapi.proyectointegrador.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restapi.proyectointegrador.controller.usuario.UsuarioController;
import com.restapi.proyectointegrador.model.Usuario;
import com.restapi.proyectointegrador.service.UsuarioServiceHash;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UsuarioServiceHash usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(usuarioController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void createUsuario_DeberiaRetornarUsuarioCreado() throws Exception {
        Usuario usuario = new Usuario("1", "Juan", "juan@example.com");

        when(usuarioService.createUsuario(any(Usuario.class))).thenReturn(usuario);

        mockMvc.perform(post("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.nombre").value("Juan"))
                .andExpect(jsonPath("$.email").value("juan@example.com"));
    }

    @Test
    public void getUsuario_DeberiaRetornarUsuarioSiExiste() throws Exception {
        Usuario usuario = new Usuario("1", "Juan", "juan@example.com");

        when(usuarioService.getUsuarioById("1")).thenReturn(Optional.of(usuario));

        mockMvc.perform(get("/api/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.nombre").value("Juan"))
                .andExpect(jsonPath("$.email").value("juan@example.com"));
    }

    @Test
    public void getUsuario_DeberiaRetornarNotFoundSiNoExiste() throws Exception {
        when(usuarioService.getUsuarioById("nonexistent-id")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/usuarios/nonexistent-id"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getAllUsuarios_DeberiaRetornarListaDeUsuarios() throws Exception {
        List<Usuario> usuarios = new ArrayList<>();
        usuarios.add(new Usuario("1", "Juan", "juan@example.com"));
        usuarios.add(new Usuario("2", "Maria", "maria@example.com"));

        when(usuarioService.getAllUsuarios()).thenReturn(usuarios);

        mockMvc.perform(get("/api/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", org.hamcrest.Matchers.hasSize(2)));
    }

    @Test
    public void updateUsuario_DeberiaRetornarUsuarioActualizado() throws Exception {
        Usuario usuario = new Usuario("1", "Juan", "juan@example.com");
        Usuario updatedUsuario = new Usuario("1", "Juan Carlos", "juancarlos@example.com");

        when(usuarioService.updateUsuario(anyString(), any(Usuario.class))).thenReturn(updatedUsuario);

        mockMvc.perform(put("/api/usuarios/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedUsuario)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.nombre").value("Juan Carlos"))
                .andExpect(jsonPath("$.email").value("juancarlos@example.com"));
    }



    @Test
    public void deleteUsuario_DeberiaRetornarNotFoundSiNoSePuedeEliminar() throws Exception {
        when(usuarioService.deleteUsuario("nonexistent-id")).thenReturn(false);

        mockMvc.perform(delete("/api/usuarios/nonexistent-id"))
                .andExpect(status().isNotFound());
    }
}


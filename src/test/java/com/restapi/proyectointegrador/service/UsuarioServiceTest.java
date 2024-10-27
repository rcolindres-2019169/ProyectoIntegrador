package com.restapi.proyectointegrador.service;

import com.restapi.proyectointegrador.model.Usuario;
import com.restapi.proyectointegrador.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void obtenerTodos_DeberiaRetornarTodosLosUsuarios() {
        // Datos de prueba
        Usuario usuario1 = new Usuario("1", "Usuario Uno", "usuario1@example.com");
        Usuario usuario2 = new Usuario("2", "Usuario Dos", "usuario2@example.com");

        when(usuarioRepository.findAll()).thenReturn(Arrays.asList(usuario1, usuario2));

        // Ejecución y verificación
        List<Usuario> usuarios = usuarioService.obtenerTodos();
        assertEquals(2, usuarios.size());
        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    void obtenerPorId_DeberiaRetornarUsuarioSiExiste() {
        // Datos de prueba
        Usuario usuario = new Usuario("1", "Usuario Uno", "usuario1@example.com");

        when(usuarioRepository.findById("1")).thenReturn(Optional.of(usuario));

        // Ejecución y verificación
        Optional<Usuario> resultado = usuarioService.obtenerPorId("1");
        assertTrue(resultado.isPresent());
        assertEquals("Usuario Uno", resultado.get().getNombre());
        verify(usuarioRepository, times(1)).findById("1");
    }

    @Test
    void obtenerPorId_DeberiaRetornarVacioSiNoExiste() {
        when(usuarioRepository.findById("1")).thenReturn(Optional.empty());

        // Ejecución y verificación
        Optional<Usuario> resultado = usuarioService.obtenerPorId("1");
        assertFalse(resultado.isPresent());
        verify(usuarioRepository, times(1)).findById("1");
    }

    @Test
    void crearUsuario_DeberiaGuardarUsuarioCorrectamente() {
        // Datos de prueba
        Usuario usuario = new Usuario("1", "Usuario Uno", "usuario1@example.com");

        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        // Ejecución y verificación
        Usuario resultado = usuarioService.crearUsuario(usuario);
        assertNotNull(resultado);
        assertEquals("Usuario Uno", resultado.getNombre());
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    void actualizarUsuario_DeberiaActualizarUsuarioSiExiste() {
        // Datos de prueba
        Usuario usuarioExistente = new Usuario("1", "Usuario Uno", "usuario1@example.com");
        Usuario usuarioActualizado = new Usuario("1", "Usuario Actualizado", "actualizado@example.com");

        when(usuarioRepository.findById("1")).thenReturn(Optional.of(usuarioExistente));
        when(usuarioRepository.save(usuarioActualizado)).thenReturn(usuarioActualizado);

        // Ejecución y verificación
        Usuario resultado = usuarioService.actualizarUsuario("1", usuarioActualizado);
        assertNotNull(resultado);
        assertEquals("Usuario Actualizado", resultado.getNombre());
        verify(usuarioRepository, times(1)).findById("1");
        verify(usuarioRepository, times(1)).save(usuarioActualizado);
    }

    @Test
    void actualizarUsuario_DeberiaRetornarNullSiNoExiste() {
        Usuario usuarioActualizado = new Usuario("1", "Usuario Actualizado", "actualizado@example.com");

        when(usuarioRepository.findById("1")).thenReturn(Optional.empty());

        // Ejecución y verificación
        Usuario resultado = usuarioService.actualizarUsuario("1", usuarioActualizado);
        assertNull(resultado);
        verify(usuarioRepository, times(1)).findById("1");
        verify(usuarioRepository, times(0)).save(usuarioActualizado);
    }

    @Test
    void eliminarUsuario_DeberiaEliminarUsuarioSiExiste() {
        // Ejecución
        usuarioService.eliminarUsuario("1");

        // Verificación
        verify(usuarioRepository, times(1)).deleteById("1");
    }
}


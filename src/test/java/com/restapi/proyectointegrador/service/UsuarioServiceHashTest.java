package com.restapi.proyectointegrador.service;


import com.restapi.proyectointegrador.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioServiceHashTest {

    private UsuarioServiceHash usuarioServiceHash;

    @BeforeEach
    void setUp() {
        usuarioServiceHash = new UsuarioServiceHash();
    }

    @Test
    void createUsuario_DeberiaGuardarUsuarioCorrectamente() {
        // Datos de prueba
        Usuario usuario = new Usuario("1", "Usuario Uno", "usuario1@example.com");

        // Ejecución
        Usuario resultado = usuarioServiceHash.createUsuario(usuario);

        // Verificación
        assertNotNull(resultado);
        assertEquals("Usuario Uno", resultado.getNombre());
        assertEquals("usuario1@example.com", resultado.getEmail());
    }

    @Test
    void getUsuarioById_DeberiaRetornarUsuarioSiExiste() {
        // Datos de prueba
        Usuario usuario = new Usuario("1", "Usuario Uno", "usuario1@example.com");
        usuarioServiceHash.createUsuario(usuario);

        // Ejecución
        Optional<Usuario> resultado = usuarioServiceHash.getUsuarioById("1");

        // Verificación
        assertTrue(resultado.isPresent());
        assertEquals("Usuario Uno", resultado.get().getNombre());
    }

    @Test
    void getUsuarioById_DeberiaRetornarVacioSiNoExiste() {
        // Ejecución
        Optional<Usuario> resultado = usuarioServiceHash.getUsuarioById("1");

        // Verificación
        assertFalse(resultado.isPresent());
    }

    @Test
    void getAllUsuarios_DeberiaRetornarTodosLosUsuarios() {
        // Datos de prueba
        Usuario usuario1 = new Usuario("1", "Usuario Uno", "usuario1@example.com");
        Usuario usuario2 = new Usuario("2", "Usuario Dos", "usuario2@example.com");
        usuarioServiceHash.createUsuario(usuario1);
        usuarioServiceHash.createUsuario(usuario2);

        // Ejecución
        List<Usuario> usuarios = usuarioServiceHash.getAllUsuarios();

        // Verificación
        assertEquals(2, usuarios.size());
        assertTrue(usuarios.contains(usuario1));
        assertTrue(usuarios.contains(usuario2));
    }

    @Test
    void updateUsuario_DeberiaActualizarUsuarioSiExiste() {
        // Datos de prueba
        Usuario usuario = new Usuario("1", "Usuario Uno", "usuario1@example.com");
        usuarioServiceHash.createUsuario(usuario);

        Usuario usuarioActualizado = new Usuario("1", "Usuario Actualizado", "actualizado@example.com");

        // Ejecución
        Usuario resultado = usuarioServiceHash.updateUsuario("1", usuarioActualizado);

        // Verificación
        assertNotNull(resultado);
        assertEquals("Usuario Actualizado", resultado.getNombre());
        assertEquals("actualizado@example.com", resultado.getEmail());
    }

    @Test
    void deleteUsuario_DeberiaEliminarUsuarioSiExiste() {
        // Datos de prueba
        Usuario usuario = new Usuario("1", "Usuario Uno", "usuario1@example.com");
        usuarioServiceHash.createUsuario(usuario);

        // Ejecución
        boolean eliminado = usuarioServiceHash.deleteUsuario("1");

        // Verificación
        assertTrue(eliminado);
        assertFalse(usuarioServiceHash.getUsuarioById("1").isPresent());
    }

    @Test
    void deleteUsuario_DeberiaRetornarFalseSiNoExiste() {
        // Ejecución
        boolean eliminado = usuarioServiceHash.deleteUsuario("1");

        // Verificación
        assertFalse(eliminado);
    }
}

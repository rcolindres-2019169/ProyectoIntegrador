package com.restapi.proyectointegrador.model;
import java.util.List;
import java.util.Optional;

public interface UsuarioServiceI {
    Usuario createUsuario(Usuario usuario);
    Optional<Usuario> getUsuarioById(String id);
    List<Usuario> getAllUsuarios();
    Usuario updateUsuario(String id, Usuario usuario);
    boolean deleteUsuario(String id);
}
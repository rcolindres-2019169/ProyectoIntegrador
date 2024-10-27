package com.restapi.proyectointegrador.service;
import com.restapi.proyectointegrador.model.Usuario;
import com.restapi.proyectointegrador.model.UsuarioServiceI;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UsuarioServiceHash implements UsuarioServiceI {
    private Map<String, Usuario> usuarios = new HashMap<>();

    @Override
    public Usuario createUsuario(Usuario usuario) {
        usuarios.put(usuario.getId(), usuario);
        return usuario;
    }

    @Override
    public Optional<Usuario> getUsuarioById(String id) {
        return Optional.ofNullable(usuarios.get(id));
    }

    @Override
    public List<Usuario> getAllUsuarios() {
        return new ArrayList<>(usuarios.values());
    }

    @Override
    public Usuario updateUsuario(String id, Usuario usuario) {
        usuarios.put(id, usuario);
        return usuario;
    }

    @Override
    public boolean deleteUsuario(String id) {
        return usuarios.remove(id) != null;
    }

}

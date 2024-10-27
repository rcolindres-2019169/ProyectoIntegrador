package com.restapi.proyectointegrador.repository;

import com.restapi.proyectointegrador.model.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, String> {

}

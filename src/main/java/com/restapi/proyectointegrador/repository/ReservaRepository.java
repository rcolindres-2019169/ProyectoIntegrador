package com.restapi.proyectointegrador.repository;

import com.restapi.proyectointegrador.model.Reserva;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservaRepository extends MongoRepository<Reserva, String> {

}

package com.restapi.proyectointegrador.model;
import java.util.List;
import java.util.Optional;

public interface ReservaServiceI {
    Reserva createReserva(Reserva reserva);
    Optional<Reserva> getReservaById(String id);
    List<Reserva> getAllReservas();
    Reserva updateReserva(String id, Reserva reserva);
    boolean deleteReserva(String id);
}

package com.restapi.proyectointegrador.service;
import com.restapi.proyectointegrador.model.Reserva;
import com.restapi.proyectointegrador.model.ReservaServiceI;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class ReservaServiceHash implements ReservaServiceI {
    private Map<String, Reserva> reservas = new HashMap<>();

    @Override
    public Reserva createReserva(Reserva reserva) {
        reservas.put(reserva.getId(), reserva);
        return reserva;
    }

    @Override
    public Optional<Reserva> getReservaById(String id) {
        return Optional.ofNullable(reservas.get(id));
    }

    @Override
    public List<Reserva> getAllReservas() {
        return new ArrayList<>(reservas.values());
    }

    @Override
    public Reserva updateReserva(String id, Reserva reserva) {
        reservas.put(id, reserva);
        return reserva;
    }

    @Override
    public boolean deleteReserva(String id) {
        return reservas.remove(id) != null;
    }
}
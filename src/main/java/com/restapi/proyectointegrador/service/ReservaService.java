package com.restapi.proyectointegrador.service;

import com.restapi.proyectointegrador.model.Reserva;
import com.restapi.proyectointegrador.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    public List<Reserva> obtenerTodas() {
        return reservaRepository.findAll();
    }

    public Optional<Reserva> obtenerPorId(String id) {
        return reservaRepository.findById(id);
    }

    public Reserva crearReserva(Reserva reserva) {
        return reservaRepository.save(reserva);
    }


    public Reserva actualizarReserva(String id, Reserva reserva) {
        Optional<Reserva> reservaExistente = reservaRepository.findById(id);
        if (reservaExistente.isPresent()) {
            reserva.setId(id);
            return reservaRepository.save(reserva);
        }
        return null;  // Manejar excepciones personalizadas si es necesario
    }

    public void eliminarReserva(String id) {
        reservaRepository.deleteById(id);
    }
}

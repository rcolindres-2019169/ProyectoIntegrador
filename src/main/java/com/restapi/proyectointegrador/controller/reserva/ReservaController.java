package com.restapi.proyectointegrador.controller.reserva;
import com.restapi.proyectointegrador.model.Reserva;
import com.restapi.proyectointegrador.service.ReservaServiceHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    @Autowired
    private ReservaServiceHash reservaService;

    @PostMapping
    public Reserva createReserva(@RequestBody Reserva reserva) {
        return reservaService.createReserva(reserva);
    }

    @GetMapping("/{id}")
    public Optional<Reserva> getReserva(@PathVariable String id) {
        return reservaService.getReservaById(id);

    }

    @GetMapping
    public List<Reserva> getAllReservas() {
        return reservaService.getAllReservas();
    }

    @PutMapping("/{id}")
    public Reserva updateReserva(@PathVariable String id, @RequestBody Reserva reserva) {
        return reservaService.updateReserva(id, reserva);
    }

    @DeleteMapping("/{id}")
    public boolean deleteReserva(@PathVariable String id) {
        return reservaService.deleteReserva(id);
    }
}

package com.restapi.proyectointegrador.controller.mongo;

import com.restapi.proyectointegrador.model.Reserva;
import com.restapi.proyectointegrador.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/reservas")
public class MongoReservaController {

    @Autowired
    private ReservaService reservaService;

    @GetMapping
    public List<Reserva> obtenerTodas() {
        return reservaService.obtenerTodas();
    }

    @GetMapping("/{id}")
    public Optional<Reserva> obtenerPorId(@PathVariable String id) {
        return reservaService.obtenerPorId(id);
    }

    @PostMapping
    public Reserva crearReserva(@RequestBody Reserva reserva) {
        return reservaService.crearReserva(reserva);
    }

    @PutMapping("/{id}")
    public Reserva actualizarReserva(@PathVariable String id, @RequestBody Reserva reserva) {
        return reservaService.actualizarReserva(id, reserva);
    }

    @DeleteMapping("/{id}")
    public void eliminarReserva(@PathVariable String id) {
        reservaService.eliminarReserva(id);
    }
}

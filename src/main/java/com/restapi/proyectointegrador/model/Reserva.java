package com.restapi.proyectointegrador.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "reservas")
public class Reserva {
    @Id
    private String id; // MongoDB generará este id automáticamente
    private String idUsuario;
    private LocalDateTime fecha;

    // Constructor por defecto
    public Reserva() {
        this.fecha = LocalDateTime.now(); // Inicializa la fecha al momento de crear la instancia
    }

    // Constructor que solo recibe idUsuario
    public Reserva(String idUsuario) {
        this.idUsuario = idUsuario;
        this.fecha = LocalDateTime.now(); // Inicializa la fecha al momento de crear la instancia
    }

    public Reserva(String id, String idUsuario, LocalDateTime fecha) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.fecha = fecha;
    }

    public Reserva(String number, String usuario1) {

    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    // Se puede omitir el método setFecha() si no deseas modificar la fecha después de la creación
    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha; // O puedes mantener la lógica si deseas permitir que la fecha se cambie manualmente
    }
}

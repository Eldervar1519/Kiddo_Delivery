package com.kiddo.kiddodelivery;

import java.util.ArrayList;
import java.util.Objects;

public class Evento {

    private String titulo, direccion, fecha, inicio, fin, id;

    static ArrayList<Evento> listaEventos = new ArrayList<>();

    public Evento(){}

    public Evento(String titulo, String direccion, String fecha, String inicio, String fin, String id) {
        this.titulo = titulo;
        this.direccion = direccion;
        this.fecha = fecha;
        this.inicio = inicio;
        this.fin = fin;
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getFecha() {
        return fecha;
    }

    public String getInicio() {
        return inicio;
    }

    public String getFin() {
        return fin;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Evento evento = (Evento) o;
        return titulo.equals(evento.titulo) && direccion.equals(evento.direccion) && fecha.equals(evento.fecha) && inicio.equals(evento.inicio) && fin.equals(evento.fin) && id.equals(evento.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(titulo, direccion, fecha, inicio, fin, id);
    }

    @Override
    public String toString() {
        return "Evento{" +
                "titulo='" + titulo + '\'' +
                ", direccion='" + direccion + '\'' +
                ", fecha='" + fecha + '\'' +
                ", inicio='" + inicio + '\'' +
                ", fin='" + fin + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}

package com.kiddo.kiddodelivery;

import java.util.ArrayList;

public class Asistente {

    private String nombre, apellido, calle, población, id, idEvento, confirmacion;

    static ArrayList<Asistente> listaAsistentes = new ArrayList<>();

    public Asistente(String nombre, String apellido, String calle, String población,
                     String id, String idEvento, String confirmacion) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.calle = calle;
        this.población = población;
        this.id = id;
        this.idEvento = idEvento;
        this.confirmacion = confirmacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getPoblación() {
        return población;
    }

    public void setPoblación(String población) {
        this.población = población;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(String idEvento) {
        this.idEvento = idEvento;
    }

    public String getConfirmacion() {
        return confirmacion;
    }

    public void setConfirmacion(String confirmacion) {
        this.confirmacion = confirmacion;
    }

    @Override
    public String toString() {
        return "Asistente{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", calle='" + calle + '\'' +
                ", población='" + población + '\'' +
                ", id='" + id + '\'' +
                ", idEvento='" + idEvento + '\'' +
                ", confirmacion='" + confirmacion + '\'' +
                '}';
    }
}

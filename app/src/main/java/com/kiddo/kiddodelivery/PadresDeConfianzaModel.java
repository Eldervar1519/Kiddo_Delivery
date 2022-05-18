package com.kiddo.kiddodelivery;

public class PadresDeConfianzaModel {

    String nombre;
    String hijos;

    public PadresDeConfianzaModel() {
    }

    public PadresDeConfianzaModel(String nombre, String hijos) {
        this.nombre = nombre;
        this.hijos = hijos;
    }

    public PadresDeConfianzaModel(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public String getHijos() {
        return hijos;
    }
}

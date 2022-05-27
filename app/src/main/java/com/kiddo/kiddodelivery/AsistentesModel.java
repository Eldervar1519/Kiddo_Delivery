package com.kiddo.kiddodelivery;

public class AsistentesModel {

    String nombre, hijo, idPc, idEvento, confirmacion;
    int imagebtnConf, imagebtnMapa;

    public AsistentesModel(String nombre, String hijo, String idPc, String idEvento, String confirmacion,
                           int imagebtnConf, int imagebtnMapa) {
        this.nombre = nombre;
        this.hijo = hijo;
        this.idPc = idPc;
        this.idEvento = idEvento;
        this.confirmacion = confirmacion;
        this.imagebtnConf = imagebtnConf;
        this.imagebtnMapa = imagebtnMapa;
    }

    public String getNombre() {
        return nombre;
    }

    public String getHijo() {
        return hijo;
    }

    public String getIdPc() {
        return idPc;
    }

    public String getIdEvento() {
        return idEvento;
    }

    public String getConfirmacion() {
        return confirmacion;
    }

    public int getImagebtnConf() {
        return imagebtnConf;
    }

    public int getImagebtnMapa() {
        return imagebtnMapa;
    }

    @Override
    public String toString() {
        return "AsistentesModel{" +
                "nombre='" + nombre + '\'' +
                ", hijo='" + hijo + '\'' +
                ", idPc='" + idPc + '\'' +
                ", idEvento='" + idEvento + '\'' +
                ", confirmacion='" + confirmacion + '\'' +
                ", imagebtnConf=" + imagebtnConf +
                ", imagebtnMapa=" + imagebtnMapa +
                '}';
    }
}
package com.kiddo.kiddodelivery;

public class PadresDeConfianzaModel {

    String nombre, hijos, tlf;
    int image, imagebtnLlamar, imagebtnEliminar;




    public PadresDeConfianzaModel(String nombre, String hijos, String tlf, int image,
                                  int imagebtnLlamar, int imagebtnEliminar) {
        this.nombre = nombre;
        this.hijos = hijos;
        this.tlf = tlf;
        this.image = image;
        this.imagebtnLlamar = imagebtnLlamar;
        this.imagebtnEliminar = imagebtnEliminar;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTlf() {
        return tlf;
    }

    public String getHijos() {
        return hijos;
    }

    public int getImage() {
        return image;
    }

    public int getImagebtnLlamar() {
        return imagebtnLlamar;
    }

    public int getImagebtnEliminar() {
        return imagebtnEliminar;
    }
}

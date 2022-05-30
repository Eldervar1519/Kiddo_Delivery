package com.kiddo.kiddodelivery;

import java.util.Objects;

public class PadresDeConfianzaModel {

    private String nombre, hijos, tlf, uid, muid;
    int image, imagebtnLlamar, imagebtnEliminar;




    public PadresDeConfianzaModel(String nombre, String hijos, String tlf, String uid, String muid, int image,
                                  int imagebtnLlamar, int imagebtnEliminar) {
        this.nombre = nombre;
        this.hijos = hijos;
        this.tlf = tlf;
        this.uid = uid;
        this.muid = muid;
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

    public String getUid() {
        return uid;
    }

    public String getMuid() {
        return muid;
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

package com.kiddo.kiddodelivery;

public class EventosModel {

    String titulo, fechaHora, direccion, id;
    int imageIcono, imagebtnNotificacion, imagebtnEliminar, imagebtnMap;

    public EventosModel(String titulo, String fechaHora, String direccion, String id, int imageIcono,
                        int imagebtnNotificacion, int imagebtnEliminar, int imagebtnMap) {
        this.titulo = titulo;
        this.fechaHora = fechaHora;
        this.direccion = direccion;
        this.id = id;
        this.imageIcono = imageIcono;
        this.imagebtnNotificacion = imagebtnNotificacion;
        this.imagebtnEliminar = imagebtnEliminar;
        this.imagebtnMap = imagebtnMap;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getId() {
        return id;
    }

    public int getImageIcono() {
        return imageIcono;
    }

    public int getImagebtnNotificacion() {
        return imagebtnNotificacion;
    }

    public int getImagebtnEliminar() {
        return imagebtnEliminar;
    }

    public int getImagebtnMap() {
        return imagebtnMap;
    }
}

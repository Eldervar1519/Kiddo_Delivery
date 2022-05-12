package com.kiddo.kiddodelivery;

import java.util.ArrayList;
import java.util.Objects;

public class Usuario {

    private String nombre, apellido, dni, calle, poblacion, mail, tlf, id;

    static ArrayList<Usuario> listaUsuarios = new ArrayList<>();

    public Usuario() {
    }

    public Usuario(String nombre, String apellido, String dni, String calle, String poblacion, String mail, String tlf, String id) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.calle = calle;
        this.poblacion = poblacion;
        this.mail = mail;
        this.tlf = tlf;
        this.id = id;
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

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getTlf() {
        return tlf;
    }

    public void setTlf(String tlf) {
        this.tlf = tlf;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return nombre.equals(usuario.nombre) && apellido.equals(usuario.apellido) && dni.equals(usuario.dni) && calle.equals(usuario.calle) && poblacion.equals(usuario.poblacion) && mail.equals(usuario.mail) && tlf.equals(usuario.tlf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, apellido, dni, calle, poblacion, mail, tlf);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", dni='" + dni + '\'' +
                ", calle='" + calle + '\'' +
                ", poblacion='" + poblacion + '\'' +
                ", mail='" + mail + '\'' +
                ", tlf='" + tlf + '\'' +
                '}';
    }
}
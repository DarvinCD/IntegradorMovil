package com.example.daniel.integradormovil.model;

public class Persona {


    private String nombre;
    private String email;
    private String idFirebase;
    private String registrationToken;
    private String ahorro;

    public String getAhorro() {
        return ahorro;
    }

    public void setAhorro(String ahorro) {
        this.ahorro = ahorro;
    }

    public String getRegistrationToken() {
        return registrationToken;
    }

    public void setRegistrationToken(String setRegistrationToken) {
        this.registrationToken = setRegistrationToken;
    }



    public String getIdFirebase() {
        return idFirebase;
    }

    public void setIdFirebase(String idFirebase) {
        this.idFirebase = idFirebase;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

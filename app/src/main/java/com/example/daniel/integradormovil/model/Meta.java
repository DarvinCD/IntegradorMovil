package com.example.daniel.integradormovil.model;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Meta {


    private String nombre_meta;
    private String monto_meta;
    private String fecha_meta;


    public Meta() {

    }


    public Meta(String nombre_meta, String monto_meta, String fecha_meta) {
        this.nombre_meta = nombre_meta;
        this.monto_meta = monto_meta;
        this.fecha_meta = fecha_meta;

    }


    public String getNombre_meta() {
        return nombre_meta;
    }

    public void setNombre_meta(String nombre_meta) {
        this.nombre_meta = nombre_meta;
    }

    public String getMonto_meta() {
        return monto_meta;
    }

    public void setMonto_meta(String monto_meta) {
        this.monto_meta = monto_meta;
    }

    public String getFecha_meta() {
        return fecha_meta;
    }

    public void setFecha_meta(String fecha_meta) {
        this.fecha_meta = fecha_meta;
    }

}

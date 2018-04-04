package com.example.hose.ej1marzo18_listacompra.model;

import java.util.Date;

/**
 * Created by hose on 07/03/2018.
 */

public class Producto {
    private int idProducto;
    private String nombre;
    private String cantidad;
    private boolean comprado;
    private int imagen;

    public Producto() {
    }

    public Producto(String nombre, String cantidad, boolean comprado, int imagen) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.comprado = comprado;
        this.imagen = imagen;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public boolean isComprado() {
        return comprado;
    }

    public void setComprado(boolean comprado) {
        this.comprado = comprado;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }
}

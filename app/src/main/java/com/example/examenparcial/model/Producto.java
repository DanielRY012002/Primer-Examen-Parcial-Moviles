package com.example.examenparcial.model;

public class Producto {
    public String uid;
    public String nombre;
    public double precio;
    public int stock;
    public Producto(){
    }
    public Producto(String uid, String nombre, double precio, int stock) {
        this.uid = uid;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }
    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public double getPrecio() {
        return precio;
    }
    public void setPrecio(double precio) {
        this.precio = precio;
    }
    public int getStock() {
        return stock;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }
}

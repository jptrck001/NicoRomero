package com.adedo;

/**
 * Created by pc on 15/09/2015.
 */
public class Item_calif {
    protected String nombre;
    protected int viajes;
    protected float promedio;
    protected String auto;
    protected String mail;

    public Item_calif(String nombre, int viajes, float promedio, String auto, String mail) {
        super();
        this.nombre = nombre;
        this.viajes = viajes;
        this.promedio = promedio;
        this.auto = auto;
        this.mail = mail;
    }

    public String getMail() {
        return mail;
    }
    
    public int getViajes() {
        return viajes;
    }
    
    public float getPromedio() {
        return promedio;
    }
    
    public String getAuto() {
        return auto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}

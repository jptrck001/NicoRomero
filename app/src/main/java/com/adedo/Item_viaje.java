package com.adedo;

import android.graphics.drawable.Drawable;

public class Item_viaje {
    protected Drawable foto;
    protected String nombre;
    protected String mailc;
    protected int cantOcupadoa;
    protected int cantidad_asientos_disponibles = 0;
    protected String facebookc;
    protected String vehiculoc;
    protected String partida;
    protected String llegada;
    protected int hora;
    protected float promedio;
    protected int viajes;
    protected int idv;
    private String comentarios;
    private String perfil;

    public Item_viaje(Drawable foto, String nombre, int cantidad_asientos_disponibles, String mailc, int cantOcupadoa, String facebookc, String vehiculoc, String partida, String llegada, int hora,
            float promedio, int viajes, int idv, String comentarios, String perfil) {
        super();
        this.foto = foto;
        this.nombre = nombre;
        this.cantidad_asientos_disponibles = cantidad_asientos_disponibles;
        this.mailc = mailc;
        this.cantOcupadoa = cantOcupadoa;
        this.facebookc = facebookc;
        this.vehiculoc = vehiculoc;
        this.partida = partida;
        this.llegada = llegada;
        this.hora = hora;
        this.promedio = promedio;
        this.viajes = viajes;
        this.idv = idv;
        this.comentarios = comentarios;
        this.perfil = perfil;
    }
    
    public float getIdv() {
        return idv;
    }
    
    public float getViajes() {
        return viajes;
    }
    
    public float getPromedio() {
        return promedio;
    }

    public String getFacebookc() {
        return facebookc;
    }
    
    public String getVehiculoc() {
        return vehiculoc;
    }
    
    public String getPartida() {
        return partida;
    }
    
    public String getLlegada() {
        return llegada;
    }
    
    public int getHora() {
        return hora;
    }
    
    public String getMailc() {
        return mailc;
    }
    
    public int getCantOcupadoa() {
        return cantOcupadoa;
    }

    public Drawable getFoto() {
        return foto;
    }

    public void setFoto(Drawable foto) {
        this.foto = foto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad_asientos_disponibles() {
        return cantidad_asientos_disponibles;
    }

    public void setCantidad_asientos_disponibles(int cantidad_asientos_disponibles) {
        this.cantidad_asientos_disponibles = cantidad_asientos_disponibles;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public String getComentarios() {
        return comentarios;
    }
}

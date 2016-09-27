package com.adedo;

public class Item_viajes {
    
    protected String mailc;
    protected int diav;
    protected String mesv;
    protected int anov;
    protected int horav;
    protected int lugaresv;
    protected String partidav;
    protected String llegadav;

    public Item_viajes(String mailc, int diav, String mesv, int anov, int horav, int lugaresv, String partidav, String llegadav) {
        super();
        this.mailc = mailc;
        this.diav = diav;
        this.mesv = mesv;
        this.anov = anov;
        this.horav = horav;
        this.lugaresv = lugaresv;
        this.partidav = partidav;
        this.llegadav = llegadav;
    }
    
    public String getMailc() {
        return mailc;
    }
    
    public int getDiav() {
        return diav;
    }
    
    public String getMesv() {
        return mesv;
    }
    
    public int getAnov() {
        return anov;
    }
    
    public int getHorav() {
        return horav;
    }

    public int getLugaresv() {
        return lugaresv;
    }
    
    public String getPartidav() {
        return partidav;
    }
    
    public String getLlegadav() {
        return llegadav;
    }
}

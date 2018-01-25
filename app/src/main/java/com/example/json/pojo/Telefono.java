package com.example.json.pojo;

/**
 * Created by usuario on 25/01/18.
 */

public class Telefono {
    private String casa;
    private String movil;
    private String trabajo;

    public Telefono(String casa, String movil, String trabajo) {
        this.casa = casa;
        this.movil = movil;
        this.trabajo = trabajo;
    }

    public String getCasa() {
        return casa;
    }

    public void setCasa(String casa) {
        this.casa = casa;
    }

    public String getMovil() {
        return movil;
    }

    public void setMovil(String movil) {
        this.movil = movil;
    }

    public String getTrabajo() {
        return trabajo;
    }

    public void setTrabajo(String trabajo) {
        this.trabajo = trabajo;
    }
}

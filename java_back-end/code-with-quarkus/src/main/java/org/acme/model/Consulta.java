package org.acme.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Consulta {
    private int id;
    private String tipo;
    private int data;
    private int hora;


    public Consulta(int id,String tipo, int data, int hora) {
        this.id = id;
        this.tipo = tipo;
        this.data = data;
        this.hora = hora;
    }

    public Consulta() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }


}

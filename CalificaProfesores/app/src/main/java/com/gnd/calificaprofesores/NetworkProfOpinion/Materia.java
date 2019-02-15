package com.gnd.calificaprofesores.NetworkProfOpinion;

public class Materia {
    private String nombre;
    private String facultad;

    public Materia(String nombre, String facultad) {
        this.nombre = nombre;
        this.facultad = facultad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFacultad() {
        return facultad;
    }

    public void setFacultad(String facultad) {
        this.facultad = facultad;
    }
}

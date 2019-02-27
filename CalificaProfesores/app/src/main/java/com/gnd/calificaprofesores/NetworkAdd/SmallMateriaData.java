package com.gnd.calificaprofesores.NetworkAdd;

public class SmallMateriaData {
    String facultad, nombre;

    public SmallMateriaData(String facultad, String nombre) {
        this.facultad = facultad;
        this.nombre = nombre;
    }

    public String getFacultad() {
        return facultad;
    }

    public void setFacultad(String facultad) {
        this.facultad = facultad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}


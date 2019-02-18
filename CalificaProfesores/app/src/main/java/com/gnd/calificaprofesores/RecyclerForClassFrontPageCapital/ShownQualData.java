package com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital;

public class ShownQualData extends AdapterElement{
    private int conocimiento;
    private int amabilidad;
    private int clases;

    public ShownQualData(int conocimiento, int amabilidad, int clases) {
        super(8);
        this.conocimiento = conocimiento;
        this.amabilidad = amabilidad;
        this.clases = clases;
    }

    public int getConocimiento() {
        return conocimiento;
    }

    public void setConocimiento(int conocimiento) {
        this.conocimiento = conocimiento;
    }

    public int getAmabilidad() {
        return amabilidad;
    }

    public void setAmabilidad(int amabilidad) {
        this.amabilidad = amabilidad;
    }

    public int getClases() {
        return clases;
    }

    public void setClases(int clases) {
        this.clases = clases;
    }
}

package com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital;

public class ShownQualData extends AdapterElement{
    private float conocimiento;
    private float amabilidad;
    private float clases;

    public ShownQualData(float conocimiento, float clases, float amabilidad) {
        super(8);
        this.conocimiento = conocimiento;
        this.amabilidad = amabilidad;
        this.clases = clases;
    }

    public float getConocimiento() {
        return conocimiento;
    }

    public void setConocimiento(float conocimiento) {
        this.conocimiento = conocimiento;
    }

    public float getAmabilidad() {
        return amabilidad;
    }

    public void setAmabilidad(float amabilidad) {
        this.amabilidad = amabilidad;
    }

    public float getClases() {
        return clases;
    }

    public void setClases(float clases) {
        this.clases = clases;
    }
}

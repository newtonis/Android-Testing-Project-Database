package com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital;

/** layout asociado: layout_item_professor.xml **/

public class ProfessorData extends AdapterElement {
    private String name;
    private float conocimiento;
    private float clases;
    private float amabilidad;

    public ProfessorData(String _name, float _conocimiento, float _clases, float _amabilidad){
        super(1);
        name = _name;
        conocimiento = _conocimiento;
        clases = _clases;
        amabilidad = _amabilidad;
    }

    public String GetName() {
        return name;
    }

    public ProfessorData SetName(String name) {
        this.name = name;
        return this;
    }

    public float GetConocimiento() {
        return conocimiento;
    }

    public ProfessorData SetConocimiento(float conocimiento) {
        this.conocimiento = conocimiento;
        return this;
    }

    public float GetClases() {
        return clases;
    }

    public ProfessorData SetClases(float clases) {
        this.clases = clases;
        return this;
    }

    public float GetAmabilidad() {
        return amabilidad;
    }

    public ProfessorData SetAmabilidad(float amabilidad) {
        this.amabilidad = amabilidad;
        return this;
    }
}

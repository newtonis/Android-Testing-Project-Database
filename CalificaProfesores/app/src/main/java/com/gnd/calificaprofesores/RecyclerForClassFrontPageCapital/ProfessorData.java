package com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital;

import android.view.View;

/** layout asociado: layout_item_professor.xml **/

public class ProfessorData extends AdapterElement {
    private String name;
    private float conocimiento;
    private float clases;
    private float amabilidad;
    private View.OnClickListener clickListener;

    public ProfessorData(String _name, float _conocimiento, float _clases, float _amabilidad){
        super(1);
        name = _name;
        conocimiento = _conocimiento;
        clases = _clases;
        amabilidad = _amabilidad;
    }

    public View.OnClickListener getClickListener() {
        return clickListener;
    }

    public void setClickListener(View.OnClickListener clickListener) {
        this.clickListener = clickListener;
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

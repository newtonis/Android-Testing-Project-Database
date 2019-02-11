package com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital;

public class AdapterElement {
    private Integer type;
    public AdapterElement(Integer _type){
        type = _type;
    }
    public Integer GetType() {
        return type;
    }
    public void SetType(Integer type) {
        this.type = type;
    }
}

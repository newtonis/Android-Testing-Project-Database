package com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital;

public class AtributionData extends AdapterElement{
    String text;

    public AtributionData(String text) {
        super(20);
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

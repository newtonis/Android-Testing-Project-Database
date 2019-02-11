package com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital;

public class TitleData extends AdapterElement{
    private String text;
    public TitleData(String _text) {
        super(0);
        text = _text;
    }
    public String GetText() {
        return text;
    }
    public void SetText(String text) {
        this.text = text;
    }
}

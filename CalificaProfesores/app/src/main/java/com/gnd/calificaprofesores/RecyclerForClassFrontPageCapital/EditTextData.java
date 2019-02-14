package com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital;

import android.text.Editable;

public class EditTextData extends AdapterElement{
    String showText;
    Editable editable;
    public EditTextData(String _showText){
        super(4);
        showText = _showText;
    }
    public String getShowText(){
        return showText;
    }

    public void setShowText(String showText) {
        this.showText = showText;
    }

    public Editable getEditable() {
        return editable;
    }

    public void setEditable(Editable editable) {
        this.editable = editable;
    }
}

package com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital;

import android.text.Editable;

/**layout_expandable_text_view.xml **/

public class EditTextData extends AdapterElement{
    String showText, text;
    Editable editable;
    boolean anonimo, hasText;

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

    public boolean isAnonimo() {
        return anonimo;
    }

    public void setAnonimo(boolean anonimo) {
        this.anonimo = anonimo;
    }

    public boolean isHasText() {
        return hasText;
    }

    public void setHasText(boolean hasText) {
        this.hasText = hasText;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    public void updateText(){
        this.text = editable.toString();
    }
}


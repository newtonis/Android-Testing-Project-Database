package com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital;

import android.text.Editable;

/** layout_item_input_text_singleline.xml **/

public class InputLineTextData extends AdapterElement{
    String hintText, showText;
    Editable editable;

    public InputLineTextData(String hintText, String showText){
        super(16);
        this.hintText = hintText;
        this.showText = showText;
        editable = null;
    }

    public String getHintText() {
        return hintText;
    }

    public void setHintText(String hintText) {
        this.hintText = hintText;
    }

    public String getShowText() {
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
    public String getText(){
        return editable.toString();
    }
    public void clear(){
        editable.clear();
    }
}

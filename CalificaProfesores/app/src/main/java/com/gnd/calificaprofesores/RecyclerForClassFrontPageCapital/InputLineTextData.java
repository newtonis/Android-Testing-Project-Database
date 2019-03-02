package com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital;

import android.text.Editable;

/** layout_item_input_text_singleline.xml **/

public class InputLineTextData extends AdapterElement{
    String hintText, showText;
    Editable editable;
    String defaultText;
    boolean flagInitial;
    boolean singleLine;

    public InputLineTextData(String hintText, String showText){
        super(16);
        this.hintText = hintText;
        this.showText = showText;
        editable = null;
        flagInitial = true;
        singleLine = false;
    }

    public String getDefaultText() {
        return defaultText;
    }

    public void setDefaultText(String defaultText) {
        this.defaultText = defaultText;
    }

    public boolean isFlagInitial() {
        return flagInitial;
    }

    public void setFlagInitial(boolean flagInitial) {
        this.flagInitial = flagInitial;
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

    public boolean isSingleLine() {
        return singleLine;
    }

    public void setSingleLine(boolean singleLine) {
        this.singleLine = singleLine;
    }
}

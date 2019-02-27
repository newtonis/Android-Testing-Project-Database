package com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital;

import android.view.View;

/** layout_item_small_message_and_button.xml **/

public class ClickableData extends AdapterElement {
    String text;
    View.OnClickListener listener;

    public ClickableData(String text, View.OnClickListener listener) {
        super(19);
        this.text = text;
        this.listener = listener;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public View.OnClickListener getListener() {
        return listener;
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }
}

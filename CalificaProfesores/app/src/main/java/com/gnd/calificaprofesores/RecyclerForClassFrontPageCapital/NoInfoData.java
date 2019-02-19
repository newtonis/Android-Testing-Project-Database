package com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital;

import android.view.View;

/** layout_be_the_first.xml **/

public class NoInfoData extends AdapterElement{
    public String title;
    public String ButtonText;
    public View.OnClickListener clickListener;

    public NoInfoData(String title, String buttonText, View.OnClickListener clickListener) {
        super(10);
        this.title = title;
        ButtonText = buttonText;
        this.clickListener = clickListener;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getButtonText() {
        return ButtonText;
    }

    public void setButtonText(String buttonText) {
        ButtonText = buttonText;
    }

    public View.OnClickListener getClickListener() {
        return clickListener;
    }

    public void setClickListener(View.OnClickListener clickListener) {
        this.clickListener = clickListener;
    }
}

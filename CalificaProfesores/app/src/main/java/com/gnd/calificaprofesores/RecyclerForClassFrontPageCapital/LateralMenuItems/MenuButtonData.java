package com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.LateralMenuItems;

import android.view.View;

import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.AdapterElement;

public class MenuButtonData extends AdapterElement {
    private String value;
    private View.OnClickListener clickListener;
    private boolean bold;
    public MenuButtonData(String value, boolean bold) {
        super(21);
        if (!bold) {
            SetType(22);
        }
        this.value = value;
        this.clickListener = null;
        this.bold = bold;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public View.OnClickListener getClickListener() {
        return clickListener;
    }

    public void setClickListener(View.OnClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public boolean isBold() {
        return bold;
    }

    public void setBold(boolean bold) {
        this.bold = bold;
    }
}

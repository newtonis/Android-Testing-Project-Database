package com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital;

import android.view.View;

public class ButtonData extends AdapterElement {
    private String ButtonText;
    private View.OnClickListener onClickListener;

    public ButtonData(String _ButtonText){
        super(6);
        ButtonText = _ButtonText;
    }

    public String getButtonText() {
        return ButtonText;
    }

    public void setButtonText(String buttonText) {
        ButtonText = buttonText;
    }

    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}

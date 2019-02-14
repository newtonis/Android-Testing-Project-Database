package com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital;

public class ButtonData extends AdapterElement {
    String ButtonText;
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
}

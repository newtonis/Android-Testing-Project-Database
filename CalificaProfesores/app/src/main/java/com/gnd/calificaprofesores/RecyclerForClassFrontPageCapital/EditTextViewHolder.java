package com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.gnd.calificaprofesores.R;

public class EditTextViewHolder extends RecyclerView.ViewHolder {
    View mView;
    public EditTextViewHolder(View _view){
        super(_view);
        mView = _view;
    }
    public void SetDetails(final EditTextData model, String showText){
        final EditText text =  mView.findViewById(R.id.InputComment);
        text.setHint(showText);
        text.setText(model.getText());

        CheckBox NoOpina = mView.findViewById(R.id.CheckBoxNoOpinar);
        CheckBox EsAnonimo = mView.findViewById(R.id.CheckBoxAnonimo);

        NoOpina.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    text.setFocusable(false);
                    text.setEnabled(false);
                    text.setCursorVisible(false);
                }else{
                    text.setFocusable(true);
                    text.setEnabled(true);
                    text.setCursorVisible(true);
                    text.setFocusableInTouchMode(true);
                }
                model.setHasText(!isChecked);
            }
        });
        EsAnonimo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                model.setAnonimo(isChecked);
            }
        });
        //model.setHasText(true);
        //model.setAnonimo(false);
        model.setEditable(text.getText());
        NoOpina.setChecked(!model.isHasText());
        EsAnonimo.setChecked(model.isAnonimo());

    }
}

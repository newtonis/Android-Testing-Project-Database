package com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
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
        final EditText text = (EditText) mView.findViewById(R.id.InputComment);
        text.setHint(showText);

        model.setEditable(text.getText());
    }
}

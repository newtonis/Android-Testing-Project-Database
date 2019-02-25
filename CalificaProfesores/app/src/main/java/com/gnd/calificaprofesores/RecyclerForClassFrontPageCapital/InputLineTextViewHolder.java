package com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital;

import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.gnd.calificaprofesores.R;

/** layout_item_input_text_singleline.xml **/

public class InputLineTextViewHolder extends RecyclerView.ViewHolder  {
    View view;
    public InputLineTextViewHolder(View _view){
        super(_view);
        view = _view;
    }
    public void setDetails(InputLineTextData model, String showText, String title){
        EditText inputView = view.findViewById(R.id.textInput);
        TextView titleView = view.findViewById(R.id.titleContent);

        titleView.setText(title);
        inputView.setHint(showText);
        inputView.setText("");

        model.setEditable(inputView.getEditableText());

    }
}

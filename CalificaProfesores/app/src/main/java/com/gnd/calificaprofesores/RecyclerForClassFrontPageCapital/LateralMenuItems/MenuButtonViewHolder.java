package com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.LateralMenuItems;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.gnd.calificaprofesores.R;

public class MenuButtonViewHolder extends RecyclerView.ViewHolder {
    View view;
    public MenuButtonViewHolder(View _view){
        super(_view);
        view = _view;
    }
    public void setDetails(String buttonValue, View.OnClickListener clickListener, boolean bold){
        Button button = view.findViewById(R.id.Button);

        button.setText(buttonValue);

        button.setOnClickListener(clickListener);
    }
}

package com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.gnd.calificaprofesores.R;

public class ButtonItemViewHolder extends RecyclerView.ViewHolder {
    View mView;
    public ButtonItemViewHolder(View view){
        super(view);
        mView = view;
    }
    public void SetDetails(String buttonText){
        Button button = mView.findViewById(R.id.Button);
        button.setText(buttonText);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }
        });
    }

}

package com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.gnd.calificaprofesores.R;

public class DeletableSearchItemViewHolder extends RecyclerView.ViewHolder {
    View view;
    public DeletableSearchItemViewHolder(View _view){
        super(_view);
        view = _view;
    }
    public void setDetails(String title, String detail){
        TextView titleView = view.findViewById(R.id.Title);
        titleView.setText(title);

        TextView contentView = view.findViewById(R.id.Detail);
        contentView.setText(detail);
    }
}

package com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.gnd.calificaprofesores.R;

import org.w3c.dom.Text;

public class MiniSearchListItemViewHolder extends RecyclerView.ViewHolder {
    View view;
    public MiniSearchListItemViewHolder(View _view){
        super(_view);
        view = _view;
    }
    public void setDetails(String title, String details,View.OnClickListener listener){
        TextView titleView = view.findViewById(R.id.Title);
        TextView detailView = view.findViewById(R.id.Detail);

        titleView.setText(title);
        detailView.setText(details);

        ConstraintLayout content = view.findViewById(R.id.Content);
        content.setOnClickListener(listener);

    }
}

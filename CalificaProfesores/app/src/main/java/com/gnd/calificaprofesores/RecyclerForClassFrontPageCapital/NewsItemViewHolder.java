package com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.gnd.calificaprofesores.R;

import java.util.Date;

public class NewsItemViewHolder extends RecyclerView.ViewHolder{
    View view;
    public NewsItemViewHolder(View _view){
        super(_view);
        view = _view;
    }
    public void setDetails(String title, String content, String author, long timestamp){
        TextView titleView =  view.findViewById(R.id.TitleNews);
        titleView.setText(title);

        TextView contentView = view.findViewById(R.id.TextContent);
        contentView.setText(content);

        Date date = new Date(timestamp);

        TextView smallInfoView = view.findViewById(R.id.TextDate);
        smallInfoView.setText(date.toString());

    }
}

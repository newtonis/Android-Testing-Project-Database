package com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gnd.calificaprofesores.R;

import java.util.Date;

public class VerifyInfoViewHolder extends RecyclerView.ViewHolder {
    View view;
    public VerifyInfoViewHolder(View _view){
        super(_view);
        view = _view;
    }

    public void setDetails(
            String title,
            String content,
            long timestamp,
            View.OnClickListener acceptAction,
            View.OnClickListener rejectAction
    ){
        TextView titleView =  view.findViewById(R.id.TitleNews);
        titleView.setText(title);

        TextView contentView = view.findViewById(R.id.TextContent);
        contentView.setText(Html.fromHtml(content));

        Date date = new Date(timestamp);

        TextView smallInfoView = view.findViewById(R.id.TextDate);
        smallInfoView.setText(date.toString());

        Button acceptButton = view.findViewById(R.id.AceeptButton);
        Button rejectButton = view.findViewById(R.id.RejectButton);

        acceptButton.setOnClickListener(acceptAction);
        rejectButton.setOnClickListener(rejectAction);
    }

}

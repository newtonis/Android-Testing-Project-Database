package com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.gnd.calificaprofesores.R;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Map;


/** layout_item_opinion_prof.xml **/

public class OpinionProfViewHolder extends RecyclerView.ViewHolder {
    View mView;
    public OpinionProfViewHolder(View view){
        super(view);
        mView = view;
    }
    public void setDetails(
            String title,
            String detail,
            String textContent,
            long conocimiento,
            long clases,
            long amabilidad,
            long timestamp,
            Float score){

        TextView titleView = mView.findViewById(R.id.TitleName);
        titleView.setText(title);

        TextView detailView = mView.findViewById(R.id.TitleMaterias);
        detailView.setText(detail);

        TextView contentView = mView.findViewById(R.id.CommentText);
        contentView.setText(textContent);

        RatingBar stars = mView.findViewById(R.id.RatingBar);
        stars.setRating(score);

        Date stamp = new Date((Long) timestamp);

        TextView TextDate = mView.findViewById(R.id.TextDate);
        TextDate.setText(stamp.toString());

        RoundCornerProgressBar conocimientoView = mView.findViewById(R.id.progress_2);
        RoundCornerProgressBar clasesView = mView.findViewById(R.id.progress_1);
        RoundCornerProgressBar amabilidadView = mView.findViewById(R.id.progress_3);

        conocimientoView.setMax(5);
        clasesView.setMax(5);
        amabilidadView.setMax(5);

        conocimientoView.setProgress((int)conocimiento);
        clasesView.setProgress((int)clases);
        amabilidadView.setProgress((int)amabilidad);

    }
}

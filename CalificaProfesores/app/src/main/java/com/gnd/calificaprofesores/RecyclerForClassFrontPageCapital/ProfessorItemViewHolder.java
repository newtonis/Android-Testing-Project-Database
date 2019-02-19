package com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital;

import android.media.Rating;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;


import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.gnd.calificaprofesores.R;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.rey.material.widget.ProgressView;


/** layout asociado: layout_item_professor.xml **/

public class ProfessorItemViewHolder extends RecyclerView.ViewHolder {
    private View mView;
    public ProfessorItemViewHolder(View _mView){
        super(_mView);
        mView = _mView;
    }
    public void SetDetails(String ProfessorName, Float Conocimiento, Float Clases, Float Amabilidad){
        RoundCornerProgressBar conocimiento = mView.findViewById(R.id.progress_2);
        RoundCornerProgressBar clases = mView.findViewById(R.id.progress_1);
        RoundCornerProgressBar amabilidad = mView.findViewById(R.id.progress_3);

        conocimiento.setMax(100);
        conocimiento.setProgress(Math.round(Conocimiento*100f));

        clases.setMax(100);
        clases.setProgress(Math.round(Clases*100f));

        amabilidad.setMax(100);
        amabilidad.setProgress(Math.round(Amabilidad*100f));

        float average = (Conocimiento+Clases+Amabilidad)/3;

        RatingBar score = mView.findViewById(R.id.RatingBar);
        score.setRating(average * 5f);

        TextView titulo = mView.findViewById(R.id.ProfessorName);
        titulo.setText(ProfessorName);
    }



}

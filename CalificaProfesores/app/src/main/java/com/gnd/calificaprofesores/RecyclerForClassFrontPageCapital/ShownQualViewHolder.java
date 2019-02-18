package com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.gnd.calificaprofesores.R;

public class ShownQualViewHolder extends RecyclerView.ViewHolder {
    View mView;
    public ShownQualViewHolder(View view){
        super(view);
        mView = view;
    }
    public void setDetails(int conocimiento, int amabilidad, int clases){
        ProgressBar conocimientoView = mView.findViewById(R.id.progress_2);
        ProgressBar clasesView = mView.findViewById(R.id.progress_1);
        ProgressBar amabilidadView = mView.findViewById(R.id.progress_3);

        conocimientoView.setMax(5);
        clasesView.setMax(5);
        amabilidadView.setMax(5);

        conocimientoView.setProgress((int)conocimiento);
        clasesView.setProgress((int)clases);
        amabilidadView.setProgress((int)amabilidad);
    }
}

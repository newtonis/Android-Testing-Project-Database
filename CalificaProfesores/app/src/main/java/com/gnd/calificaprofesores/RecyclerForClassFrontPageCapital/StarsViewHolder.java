package com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.gnd.calificaprofesores.R;

public class StarsViewHolder extends RecyclerView.ViewHolder  {
    View mView;
    public StarsViewHolder(View _mView){
        super(_mView);
        mView = _mView;
    }
    public void SetDetails(float Rating){
        RatingBar ratingBar = (RatingBar)mView.findViewById(R.id.Rating);
        ratingBar.setRating(Rating);

    }
}

package com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RatingBar;

import com.gnd.calificaprofesores.R;

public class SimpleScoreSelectorViewHolder extends RecyclerView.ViewHolder {
    View view;
    public SimpleScoreSelectorViewHolder(View _view){
        super(_view);
        view = _view;
    }

    public void setDetails(final SimpleScoreSelectorData model){
        // no details lol!
        RatingBar ratingBar = view.findViewById(R.id.RatingBar);
        ratingBar.setRating(model.getStars());

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                model.setStars(rating);
            }
        });
    }
}

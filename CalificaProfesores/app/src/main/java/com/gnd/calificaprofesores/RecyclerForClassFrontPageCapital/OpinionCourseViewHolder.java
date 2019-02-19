package com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.gnd.calificaprofesores.R;

import java.sql.Date;

/** layout_opinion.xml **/

public class OpinionCourseViewHolder extends RecyclerView.ViewHolder {
    View view;
    public OpinionCourseViewHolder(View _view){
        super(_view);
        view = _view;
    }
    public void setDetails(String author, String text, Long score, long timestamp){
        TextView mAuthorText = view.findViewById(R.id.AuthorText);
        TextView mCommentText = view.findViewById(R.id.CommentText);
        RatingBar mScore = view.findViewById(R.id.ScoreData);
        TextView dateText = view.findViewById(R.id.DateText);

        if (author != null) {
            mAuthorText.setText(author);
        }else{
            mAuthorText.setText("Anónimo");
        }
        mCommentText.setText(text);
        mScore.setRating( score.floatValue() / 2f);

        Date date = new Date(timestamp);

        dateText.setText(date.toString());
    }
}

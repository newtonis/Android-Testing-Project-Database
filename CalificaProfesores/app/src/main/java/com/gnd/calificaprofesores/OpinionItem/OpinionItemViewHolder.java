package com.gnd.calificaprofesores.OpinionItem;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.gnd.calificaprofesores.R;

/** Aqui administramos la parte gráfica de un comentario **/

public class OpinionItemViewHolder extends RecyclerView.ViewHolder{
    View mView;

    public OpinionItemViewHolder(View _mView){
        super(_mView);
        mView = _mView;
    }

    public void setDetails(String author, String text, Long score){
        TextView mAuthorText = mView.findViewById(R.id.AuthorText);
        TextView mCommentText = mView.findViewById(R.id.CommentText);
        RatingBar mScore = mView.findViewById(R.id.ScoreData);
        if (author != null) {
            mAuthorText.setText(author);
        }else{
            mAuthorText.setText("Anónimo");
        }
        mCommentText.setText(text);
        mScore.setRating( score.floatValue() / 2f);
    }

}

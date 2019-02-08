package com.gnd.calificaprofesores.OpinionItem;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gnd.calificaprofesores.R;

import java.util.List;

public class AdapterCourseComments extends RecyclerView.Adapter<OpinionItemViewHolder> {

    private List<CourseComment> Comments;

    public AdapterCourseComments( List<CourseComment> _Comments){

        this.Comments = _Comments;
        //notifyDataSetChanged();
     }

    @NonNull
    @Override
    public OpinionItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_opinion, viewGroup, false);
        return new OpinionItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OpinionItemViewHolder opinionItemViewHolder, int i) {
        CourseComment dataItem = Comments.get(i);
        opinionItemViewHolder.setDetails(dataItem.getAuthor(), dataItem.getContent(), dataItem.getValoracion());
    }

    @Override
    public int getItemCount() {
        return Comments.size();
    }

}

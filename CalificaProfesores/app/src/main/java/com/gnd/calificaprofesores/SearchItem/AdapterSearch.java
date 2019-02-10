package com.gnd.calificaprofesores.SearchItem;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gnd.calificaprofesores.OpinionItem.CourseComment;
import com.gnd.calificaprofesores.R;

import java.util.List;

/** Aqui creamos el adapter para administrar los buscadores de materias, profesores y universidades **/

public class AdapterSearch extends RecyclerView.Adapter<SearchItemViewHolder> {

    private List<CourseComment> Comments;

    public AdapterSearch( List<CourseComment> _Comments){
        this.Comments = _Comments;
    }

    @NonNull
    @Override
    public SearchItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_list_element, viewGroup, false);
        return new SearchItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchItemViewHolder opinionItemViewHolder, int i) {
        CourseComment dataItem = Comments.get(i);
        opinionItemViewHolder.setDetails(dataItem.getAuthor(), dataItem.getContent(), dataItem.getValoracion());
    }

    @Override
    public int getItemCount() {
        return Comments.size();
    }

}
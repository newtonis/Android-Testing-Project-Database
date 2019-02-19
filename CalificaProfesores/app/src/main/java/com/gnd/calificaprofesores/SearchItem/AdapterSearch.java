package com.gnd.calificaprofesores.SearchItem;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gnd.calificaprofesores.NetworkSearchQueriesHandler.UniData;
import com.gnd.calificaprofesores.OpinionItem.CourseComment;
import com.gnd.calificaprofesores.R;

import java.util.List;
import java.util.Set;

/** Aqui creamos el adapter para administrar los buscadores de materias, profesores y universidades **/

public class AdapterSearch extends RecyclerView.Adapter<SearchItemViewHolder> {

    private List<UniData> SearchItems;

    public AdapterSearch( List<UniData> _SearchItems){
        this.SearchItems = _SearchItems;
    }

    @NonNull
    @Override
    public SearchItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_list_element, viewGroup, false);
        return new SearchItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchItemViewHolder opinionItemViewHolder, int i) {
        UniData dataItem = SearchItems.get(i);

        opinionItemViewHolder.setDetails(dataItem.GetClickListener(),dataItem.GetUniShortName(), dataItem.GetUniShownName(), 0L);

    }

    @Override
    public int getItemCount() {
        return SearchItems.size();
    }

}
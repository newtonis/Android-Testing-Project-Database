package com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gnd.calificaprofesores.NetworkSearchQueriesHandler.UniData;
import com.gnd.calificaprofesores.R;
import com.gnd.calificaprofesores.SearchItem.SearchItemViewHolder;

import java.util.ArrayList;
import java.util.List;


public class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<AdapterElement> Items;

    public Adapter(){
        Items = new ArrayList<>();
    }
    @Override
    public int getItemCount() {
        return Items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return Items.get(position).GetType();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView;
        switch (viewType){
            case 0:
                itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item_title, viewGroup, false);
                return new TitleViewHolder(itemView);
            case 1:
                itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item_professor, viewGroup, false);
                return new ProfessorItemViewHolder(itemView);
            case 2:
                itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item_rating_bar, viewGroup, false);
                return new StarsViewHolder(itemView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        AdapterElement dataItem = Items.get(i);

        if (dataItem.GetType() == 0) {
            TitleData element = (TitleData) dataItem;
            TitleViewHolder holder = (TitleViewHolder) viewHolder;
            holder.SetDetails(element.GetText());

        }else if (dataItem.GetType() == 1) {
            ProfessorData element = (ProfessorData) dataItem;
            ProfessorItemViewHolder holder = (ProfessorItemViewHolder) viewHolder;
            holder.SetDetails(element.GetName(),element.GetConocimiento(),element.GetClases(),element.GetAmabilidad());
        }else if(dataItem.GetType() == 2){
            StarsData element = (StarsData) dataItem;
            StarsViewHolder holder = (StarsViewHolder) viewHolder;
            holder.SetDetails(element.GetRating());
        }

        /*opinionItemViewHolder.setDetails(dataItem.GetUniShortName(), dataItem.GetUniShownName(), 0L);
        opinionItemViewHolder.mView.setOnClickListener(dataItem.GetClickListener());*/
    }
    public void AddElement(AdapterElement element){
        Items.add(element);
    }
}

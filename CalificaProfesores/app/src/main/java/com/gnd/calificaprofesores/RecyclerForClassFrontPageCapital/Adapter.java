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
            case 3:
                itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_list_element, viewGroup, false);
                return new SelectableItemViewHolder(itemView);
            case 4:
                itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_expandable_text_view, viewGroup, false);
                return new EditTextViewHolder(itemView);
            case 5:
                itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_score_selector, viewGroup, false);
                return new ScoreSelectorViewHolder(itemView);
            case 6:
                itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_button, viewGroup, false);
                return new ButtonItemViewHolder(itemView);
            case 7:
                itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item_opinion_prof, viewGroup, false);
                return new OpinionProfViewHolder(itemView);
            case 8:
                itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_scores, viewGroup, false);
                return new ShownQualViewHolder(itemView);
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

        }else if(dataItem.GetType() == 3){
            SelectableItem element = (SelectableItem) dataItem;
            SelectableItemViewHolder holder = (SelectableItemViewHolder) viewHolder;
            holder.SetDetails(element, element.getTitle(),element.getDetail());

        }else if(dataItem.GetType() == 4){
            EditTextData element = (EditTextData) dataItem;
            EditTextViewHolder holder = (EditTextViewHolder) viewHolder;
            holder.SetDetails(element, element.getShowText());

        }else if(dataItem.GetType() == 5){
            ScoreSelectorData element = (ScoreSelectorData) dataItem;
            ScoreSelectorViewHolder holder = (ScoreSelectorViewHolder) viewHolder;
            holder.SetDetails(element,   element.GetValue(0), element.GetValue(1), element.GetValue(2));

        }else if(dataItem.GetType() == 6){
            ButtonData element = (ButtonData) dataItem;
            ButtonItemViewHolder holder = (ButtonItemViewHolder) viewHolder;
            holder.SetDetails(element, element.getButtonText());

        }else if(dataItem.GetType() == 7){
            OpinionProfData element = (OpinionProfData) dataItem;
            OpinionProfViewHolder holder = (OpinionProfViewHolder) viewHolder;
            holder.setDetails(
                    element.getTitle(),
                    element.getDetails(),
                    element.getTextContent(),
                    element.getConocimiento(),
                    element.getClases(),
                    element.getAmabilidad(),
                    element.getTimestamp(),
                    element.getStars()
            );

        }else if(dataItem.GetType() == 8){
            ShownQualData element = (ShownQualData) dataItem;
            ShownQualViewHolder holder = (ShownQualViewHolder) viewHolder;
            holder.setDetails(
                    element.getConocimiento(),
                    element.getAmabilidad(),
                    element.getClases()
            );
        }

        /*opinionItemViewHolder.setDetails(dataItem.GetUniShortName(), dataItem.GetUniShownName(), 0L);
        opinionItemViewHolder.mView.setOnClickListener(dataItem.GetClickListener());*/
    }
    public void AddElement(AdapterElement element){
        Items.add(element);
    }
}

package com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gnd.calificaprofesores.NetworkSearchQueriesHandler.UniData;
import com.gnd.calificaprofesores.OpinionItem.OpinionItemViewHolder;
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
            case 9:
                itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_news_item, viewGroup, false);
                return new NewsItemViewHolder(itemView);
            case 10:
                itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_be_the_first, viewGroup, false);
                return new NoInfoViewHolder(itemView);
            case 11:
                itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_opinion, viewGroup, false);
                return new OpinionCourseViewHolder(itemView);
            case 12:
                itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_stars_selector, viewGroup, false);
                return new SimpleScoreSelectorViewHolder(itemView);
            case 13:
                itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item_deletable, viewGroup, false);
                return new DeletableSearchItemViewHolder(itemView);
            case 14:
                itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item_mini_search, viewGroup, false);
                return new MiniSearchViewHolder(itemView);
            case 15:
                itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_list_element_mini, viewGroup, false);
                return new MiniSearchListItemViewHolder(itemView);
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
            holder.SetDetails(
                    element.GetName(),
                    element.GetConocimiento(),
                    element.GetClases(),
                    element.GetAmabilidad(),
                    element.getClickListener()
            );

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
            holder.SetDetails(
                    element,
                    element.GetValue(0),
                    element.GetValue(1),
                    element.GetValue(2)
            );

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
        }else if(dataItem.GetType() == 9){
            NewsItemData element = (NewsItemData) dataItem;
            NewsItemViewHolder holder = (NewsItemViewHolder) viewHolder;
            holder.setDetails(
                    element.getTitle(),
                    element.getContent(),
                    element.getAuthor(),
                    element.getTimestamp()
            );
        }else if(dataItem.GetType() == 10){
            NoInfoData element = (NoInfoData) dataItem;
            NoInfoViewHolder holder = (NoInfoViewHolder) viewHolder;
            holder.setDetails(
                    element.getTitle(),
                    element.getButtonText(),
                    element.getClickListener()
            );
        }else if(dataItem.GetType() == 11){
            OpinionCourseData element = (OpinionCourseData) dataItem;
            OpinionCourseViewHolder holder = (OpinionCourseViewHolder) viewHolder;
            holder.setDetails(
                    element.getAuthor(),
                    element.getContent(),
                    element.getScore(),
                    element.getTimestamp()
            );
        }else if(dataItem.GetType() == 12){
            SimpleScoreSelectorData element = (SimpleScoreSelectorData) dataItem;
            SimpleScoreSelectorViewHolder holder = (SimpleScoreSelectorViewHolder) viewHolder;
            holder.setDetails(element); // nothing to do, lol!
        }else if(dataItem.GetType() == 13){
            UniData element = (UniData) dataItem;
            DeletableSearchItemViewHolder holder = (DeletableSearchItemViewHolder) viewHolder;
            holder.setDetails(
                    element.GetUniShortName(),
                    element.GetUniShownName()
            );
        }else if(dataItem.GetType() == 14){
            MiniSearchData element = (MiniSearchData) dataItem;
            MiniSearchViewHolder holder = (MiniSearchViewHolder) viewHolder;
            holder.setDetails(
                    element,
                    element.getShownText(),
                    element.getSwitchText()
            );
        }else if(dataItem.GetType() == 15){
            MiniSearchListItemData element = (MiniSearchListItemData) dataItem;
            MiniSearchListItemViewHolder holder = (MiniSearchListItemViewHolder) viewHolder;
            holder.setDetails(
                    element.getTitle(),
                    element.getDetail(),
                    element.getListener()
            );
        }
        /*opinionItemViewHolder.setDetails(dataItem.GetUniShortName(), dataItem.GetUniShownName(), 0L);
        opinionItemViewHolder.mView.setOnClickListener(dataItem.GetClickListener());*/
    }
    public void AddElement(AdapterElement element){
        Items.add(element);
    }
    public void clear(){
        Items.clear();
    }
}

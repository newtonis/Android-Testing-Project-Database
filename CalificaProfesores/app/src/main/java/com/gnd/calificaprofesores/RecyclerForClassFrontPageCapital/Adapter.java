package com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gnd.calificaprofesores.NetworkSearchQueriesHandler.UniData;
import com.gnd.calificaprofesores.OpinionItem.OpinionItemViewHolder;
import com.gnd.calificaprofesores.R;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.LateralMenuItems.MenuButtonData;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.LateralMenuItems.MenuButtonViewHolder;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.LateralMenuItems.MenuSeparatorData;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.LateralMenuItems.MenuSeparatorViewHolder;
import com.gnd.calificaprofesores.SearchItem.SearchItemViewHolder;
import com.gnd.calificaprofesores.SmallLoadingViewHolder;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

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
            case 3: //search_list_element_mini
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
            case 15: // search_list_element_mini
                itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_list_element_mini, viewGroup, false);
                return new MiniSearchListItemViewHolder(itemView);
            case 16:
                itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_input_text_singleline, viewGroup, false);
                return new InputLineTextViewHolder(itemView);
            case 17:
                itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item_loading, viewGroup, false);
                return new SmallLoadingViewHolder(itemView);
            case 18:
                itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_list_element, viewGroup, false);
                return new SearchItemViewHolder(itemView);
            case 19:
                itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item_small_message_and_button, viewGroup, false);
                return new ClickableViewHolder(itemView);
            case 20:
                itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_atribution, viewGroup, false);
                return new AtributionViewHolder(itemView);
            case 21:
                itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_button, viewGroup, false);
                return new MenuButtonViewHolder(itemView);
            case 22:
                itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item_menu_button_no_bold, viewGroup, false);
                return new MenuButtonViewHolder(itemView);
            case 23:
                itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item_divider, viewGroup, false);
                return new MenuSeparatorViewHolder(itemView);
            case 24:
                itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item_verify_change, viewGroup, false);
                return new MenuSeparatorViewHolder(itemView);
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
                    element.getTimestampLong()
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
                    element.GetUniShownName(),
                    element.GetClickListener()
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
        }else if(dataItem.GetType() == 16){
            InputLineTextData element = (InputLineTextData) dataItem;
            InputLineTextViewHolder holder = (InputLineTextViewHolder) viewHolder;

            holder.setDetails(
                    element,
                    element.getHintText(),
                    element.getShowText()
            );
        }else if(dataItem.GetType() == 17){
            // nothing to do!
        }else if(dataItem.GetType() == 18){
            UniData element = (UniData) dataItem;
            SearchItemViewHolder holder = (SearchItemViewHolder) viewHolder;

            holder.setDetails(
                    element.GetClickListener(),
                    element.GetUniShortName(),
                    element.GetUniShownName(),
                    0L
            );
        }else if(dataItem.GetType() == 19){
            ClickableData element = (ClickableData) dataItem;
            ClickableViewHolder holder = (ClickableViewHolder) viewHolder;

            holder.setDetails(
                    element.getText(),
                    element.getListener()
            );

        }else if(dataItem.GetType() == 20){
            AtributionData element = (AtributionData) dataItem;
            AtributionViewHolder holder = (AtributionViewHolder) viewHolder;

            holder.setDetails(element.getText());
        }else if(dataItem.GetType() == 21 || dataItem.GetType() == 22) {
            MenuButtonData element = (MenuButtonData) dataItem;
            MenuButtonViewHolder holder = (MenuButtonViewHolder) viewHolder;

            holder.setDetails(
                    element.getValue(),
                    element.getClickListener(),
                    element.isBold()
            );
        }else if(dataItem.GetType() == 23){
            MenuSeparatorData element = (MenuSeparatorData) dataItem;
            MenuSeparatorViewHolder holder = (MenuSeparatorViewHolder) viewHolder;

            holder.setDetails(
            );
        }else if(dataItem.GetType() == 24){
            VerifyInfoData element = (VerifyInfoData) dataItem;
            VerifyInfoViewHolder holder = (VerifyInfoViewHolder) viewHolder;

            holder.setDetails(
                    element.getTitle(),
                    element.getContent(),
                    element.getTimestamp(),
                    element.getAcceptAction(),
                    element.getRejectAction()
            );

        }
        /*opinionItemViewHolder.setDetails(dataItem.GetUniShortName(), dataItem.GetUniShownName(), 0L);
        opinionItemViewHolder.mView.setOnClickListener(dataItem.GetClickListener());*/
    }
    public void AddElement(AdapterElement element){
        Items.add(element);
    }
    public void removeElement(AdapterElement element){
        Items.remove(element);
    }
    public void clear(){
        Items.clear();
    }
}

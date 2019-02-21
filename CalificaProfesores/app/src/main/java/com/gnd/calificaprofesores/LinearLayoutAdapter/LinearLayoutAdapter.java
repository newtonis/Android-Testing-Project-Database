package com.gnd.calificaprofesores.LinearLayoutAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.gnd.calificaprofesores.R;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.DeletableItemData;
import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.DeletableSearchItemViewHolder;

import java.util.ArrayList;

public class LinearLayoutAdapter{
    ArrayList<DeletableSearchItemViewHolder> viewHolders;
    ArrayList<DeletableItemData> elements;

    LinearLayout view;
    public LinearLayoutAdapter(LinearLayout view){
        this.view = view;
        viewHolders = new ArrayList<>();
        elements = new ArrayList<>();
    }
    public void addElement(DeletableItemData element){
        View itemView;
        itemView = LayoutInflater.from(view.getContext()).inflate(R.layout.layout_item_deletable, view, false);

        view.addView(itemView);

        elements.add(element);
        DeletableSearchItemViewHolder holder = new DeletableSearchItemViewHolder(itemView);
        viewHolders.add(holder);


    }
};
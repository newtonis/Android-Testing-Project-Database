package com.gnd.calificaprofesores.ListItems;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.gnd.calificaprofesores.R;

import org.w3c.dom.Text;

/**
 * Created by newtonis on 23/02/18.
 * Aqui configuramos como se ve un profesor
 * en el buscador de profesores
 *
 * */

public class ListItemViewHolder extends RecyclerView.ViewHolder{
    View mView;

    public ListItemViewHolder(View _mView){
        super(_mView);
        mView = _mView;
    }

    public void setDetails(Context ctx,String title,String detail){
        TextView mTitle = mView.findViewById(R.id.Title);
        TextView mDetail = mView.findViewById(R.id.Detail);

        mTitle.setText(title);
        mDetail.setText(detail);

    }
}

package com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.gnd.calificaprofesores.R;

/** View asociado: layout_item_title.xml **/

public class TitleViewHolder extends RecyclerView.ViewHolder {
    private View mView;
    public TitleViewHolder(View _mView){
        super(_mView);
        mView = _mView;
    }
    public void SetDetails(String TitleContent){
        TextView title = (TextView)mView.findViewById(R.id.Title);
        title.setText(TitleContent);

    }
}

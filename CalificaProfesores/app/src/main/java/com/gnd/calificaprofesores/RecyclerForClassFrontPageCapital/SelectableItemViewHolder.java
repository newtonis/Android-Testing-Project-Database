package com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital;

import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gnd.calificaprofesores.R;

import org.w3c.dom.Text;

import java.io.Console;

/** search_list_element.xml **/

public class SelectableItemViewHolder extends RecyclerView.ViewHolder {
    private View mView;
    public SelectableItemViewHolder(View _mView){
        super(_mView);
        mView = _mView;
    }
    public void SetDetails(final SelectableItem model, String titleText,String detailsText){
        TextView title = mView.findViewById(R.id.Title);
        TextView detail = mView.findViewById(R.id.Detail);

        title.setText(titleText);
        detail.setText(detailsText);

        mView.findViewById(R.id.Content).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                model.setClicked();
                if (model.getClicked()){
                    mView.findViewById(R.id.Content).setBackgroundResource(R.drawable.item_color_selected);
                }else{
                    mView.findViewById(R.id.Content).setBackgroundResource(R.drawable.layout_bg_no_corner);
                }
            }
        }); //setBackgroundResource
        /*if (model.getClicked()){
            mView.findViewById(R.id.Content).setBackgroundColor(R.drawable.item_color_selected);
        }else{
            mView.findViewById(R.id.Content).setBackgroundColor(R.drawable.item_color);
        }*/
}

}

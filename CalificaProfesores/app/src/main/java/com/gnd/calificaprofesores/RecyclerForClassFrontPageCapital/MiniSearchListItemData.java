package com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital;

import android.view.View;


/** layout_list_element_mini.xml **/

public class MiniSearchListItemData extends AdapterElement{
    String title, detail;
    View.OnClickListener listener;

    public MiniSearchListItemData(String title, String detail) {
        super(15);
        this.title = title;
        this.detail = detail;
        listener = null;
    }

    public View.OnClickListener getListener() {
        return listener;
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}

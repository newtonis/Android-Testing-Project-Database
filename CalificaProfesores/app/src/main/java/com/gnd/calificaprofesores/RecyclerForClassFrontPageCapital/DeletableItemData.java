package com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital;

import android.widget.TextView;

/** layout_item_deletable.xml **/

public class DeletableItemData extends AdapterElement {
    String title, detail;
    public DeletableItemData(String _title, String _detail){
        super(13);
        title = _title;
        detail = _detail;
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

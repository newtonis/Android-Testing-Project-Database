package com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital;

import android.view.View;

public class VerifyInfoData extends AdapterElement {
    private String title, content;
    private long timestamp;
    private View.OnClickListener rejectAction, acceptAction;

    public VerifyInfoData(String _title, String _content,long _timestamp){
         super(24);
         title = _title;
         content = _content;
         timestamp = _timestamp;
    }

    public View.OnClickListener getRejectAction() {
        return rejectAction;
    }

    public void setRejectAction(View.OnClickListener rejectAction) {
        this.rejectAction = rejectAction;
    }

    public View.OnClickListener getAcceptAction() {
        return acceptAction;
    }

    public void setAcceptAction(View.OnClickListener acceptAction) {
        this.acceptAction = acceptAction;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}

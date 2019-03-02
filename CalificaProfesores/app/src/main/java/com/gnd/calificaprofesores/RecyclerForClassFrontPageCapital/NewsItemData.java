package com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital;

import java.util.Map;

public class NewsItemData extends AdapterElement{
    String title, content, author;
    Map timestamp = null;
    long timestamp_long;

    public NewsItemData(String title, String content, String author, long timestamp) {
        super(9);
        this.title = title;
        this.content = content;
        this.author = author;
        this.timestamp_long = timestamp;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Map getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Map timestamp) {
        this.timestamp = timestamp;
    }

    public long getTimestampLong(){
        return timestamp_long;
    }

    public void setTimestampLong(long timestamp){
        timestamp_long = timestamp;
    }
}

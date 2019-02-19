package com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital;

public class NewsItemData extends AdapterElement{
    String title, content, author;
    long timestamp;

    public NewsItemData(String title, String content, String author, long timestamp) {
        super(9);
        this.title = title;
        this.content = content;
        this.author = author;
        this.timestamp = timestamp;
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

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}

package com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital;

import android.widget.RatingBar;
import android.widget.TextView;

import com.gnd.calificaprofesores.R;

/** layout_opinion.xml **/

public class OpinionCourseData extends AdapterElement {
    private String author, content;
    private Long score, timestamp;

    public OpinionCourseData(String author, String content, Long score, Long timestamp) {
        super(11);
        this.author = author;
        this.content = content;
        this.score = score;
        this.timestamp = timestamp;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}

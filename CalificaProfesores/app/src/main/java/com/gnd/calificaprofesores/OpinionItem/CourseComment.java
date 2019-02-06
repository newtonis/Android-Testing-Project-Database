package com.gnd.calificaprofesores.OpinionItem;

import java.util.Map;

/*** Aqui administramos los datos de una opinión para que sea mostrada **/

public class CourseComment {
    private String author, content;
    private Long popularidad, score;
    Map timestamp;
    public CourseComment(String author, String content, Long popularidad, Long score){
        this.score = score;
        this.author = author;
        this.content = content;
        this.popularidad = popularidad;
        this.timestamp = null;
    }
    public void SetTimestamp(Map timestamp){
        this.timestamp = timestamp;
    }
    public Map GetTimestamp(){
        return this.timestamp;
    }
    public String GetAuthor(){
        return this.author;
    }
    public String GetContent(){
        return this.content;
    }
    public Long GetPopularidad(){
        return this.popularidad;
    }
    public Long GetScore(){
        return this.score;
    }
}

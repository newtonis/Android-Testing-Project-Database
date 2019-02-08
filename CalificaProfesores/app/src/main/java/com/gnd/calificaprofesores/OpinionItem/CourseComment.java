package com.gnd.calificaprofesores.OpinionItem;

import java.util.Map;

/*** Aqui administramos los datos de una opinión para que sea mostrada **/

public class CourseComment {
    private String author, content;
    private Long valoracion, likes;
    private Map timestamp;
    public CourseComment(String author, String content, Long valoracion, Long likes){
        this.likes = likes;
        this.author = author;
        this.content = content;
        this.valoracion = valoracion;
        this.timestamp = null;
    }
    public void SetTimestamp(Map timestamp){
        this.timestamp = timestamp;
    }
    public Map getTimestamp(){
        return this.timestamp;
    }
    public String getAuthor(){
        return this.author;
    }
    public String getContent(){
        return this.content;
    }
    public Long getLikes(){
        return this.likes;
    }
    public Long getValoracion(){
        return this.valoracion;
    }
}

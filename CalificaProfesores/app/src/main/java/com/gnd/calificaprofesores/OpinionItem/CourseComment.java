package com.gnd.calificaprofesores.OpinionItem;

import java.util.Map;

/*** Aqui administramos los datos de una opinión para que sea mostrada **/

public class CourseComment {
    private String author, content;
    private Long valoracion, likes;
    private Map timestamp;
    private Long timestampLong;
    private boolean anonimo;
    private boolean conTexto;
    public CourseComment(String author, String content, Long valoracion, Long likes){
        this.likes = likes;
        this.author = author;
        this.content = content;
        this.valoracion = valoracion;
        this.timestamp = null;
        conTexto = true;
    }

    public boolean isAnonimo() {
        return anonimo;
    }

    public void setAnonimo(boolean anonimo) {
        this.anonimo = anonimo;
    }

    public boolean isConTexto() {
        return conTexto;
    }

    public void setConTexto(boolean conTexto) {
        this.conTexto = conTexto;
    }

    public Long getTimestampLong() {
        return timestampLong;
    }

    public void setTimestampLong(Long timestampLong) {
        this.timestampLong = timestampLong;
    }

    public void SetTimestamp(Map timestamp){
        this.timestamp = timestamp;
    }
    public Map getTimestamp(){
        return this.timestamp;
    }
    public String getAuthor(){
        if (this.anonimo) {
            return "Anónimo";
        }else{
            return this.author;
        }
    }
    public String getContent(){
        if (this.conTexto) {
            return this.content;
        }else{
            return "";
        }
    }
    public Long getLikes(){
        return this.likes;
    }
    public Long getValoracion(){
        return this.valoracion;
    }
}

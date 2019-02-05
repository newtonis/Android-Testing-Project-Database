package com.gnd.calificaprofesores.OpinionItem;

/*** Aqui administramos los datos de una opinión para que sea mostrada **/

public class CourseComment {
    private String author, content;
    private Long popularidad, score;
    public CourseComment(String author, String content, Long popularidad, Long score){
        this.score = score;
        this.author = author;
        this.content = content;
        this.popularidad = popularidad;
    }
    String GetAuthor(){
        return this.author;
    }
    String GetContent(){
        return this.content;
    }
    Long GetPopularidad(){
        return this.popularidad;
    }
    Long GetScore(){
        return this.score;
    }
}

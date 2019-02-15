package com.gnd.calificaprofesores.NetworkProfOpinion;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class UserProfComment {
    String author, content;
    Map timestamp;
    Long amabilidad, conocimiento, clases, likes;
    Map<String, String> materias;
    public UserProfComment(){
        materias = new TreeMap<>();
    }
    public UserProfComment(
            String _author,
            String _content,
            Map<String, String> _materias,
            Map _timestamp,
            Long _amabilidad,
            Long _conocimiento,
            Long _clases){

        author = _author;
        content = _content;
        materias = _materias;
        timestamp = _timestamp;
        amabilidad = _amabilidad;
        conocimiento = _conocimiento;
        clases = _clases;
        likes = 0L;
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

    public Map getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Map timestamp) {
        this.timestamp = timestamp;
    }

    public Long getLikes() {
        return likes;
    }

    public void setLikes(Long likes) {
        this.likes = likes;
    }

    public Long getAmabilidad() {
        return amabilidad;
    }

    public void setAmabilidad(Long amabilidad) {
        this.amabilidad = amabilidad;
    }

    public Long getConocimiento() {
        return conocimiento;
    }

    public void setConocimiento(Long conocimiento) {
        this.conocimiento = conocimiento;
    }

    public Long getClases() {
        return clases;
    }

    public void setClases(Long clases) {
        this.clases = clases;
    }

    public Map<String, String> getMaterias() {
        return materias;
    }

    public void setMaterias(Map<String, String> materias) {
        this.materias = materias;
    }
}

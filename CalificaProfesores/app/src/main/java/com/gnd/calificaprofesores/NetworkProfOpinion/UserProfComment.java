package com.gnd.calificaprofesores.NetworkProfOpinion;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class UserProfComment {
    String author, content;

    Long amabilidad, conocimiento, clases, likes;
    Map<String, String> materias;
    Map<String, String> timestamp;
    long timestamp_long;
    boolean anonimo;
    boolean conTexto;
    public UserProfComment(){
        materias = new TreeMap<>();
    }
    public UserProfComment(
            String _author,
            String _content,
            Map<String, String> _materias,
            Map<String, String> _timestamp,
            Long _amabilidad,
            Long _conocimiento,
            Long _clases,
            boolean _anonimo,
            boolean _conTexto){

        author = _author;
        content = _content;
        materias = _materias;
        timestamp = _timestamp;
        amabilidad = _amabilidad;
        conocimiento = _conocimiento;
        clases = _clases;
        likes = 0L;
        anonimo = _anonimo;
        conTexto = _conTexto;
    }

    public long getTimestamp_long() {
        return timestamp_long;
    }

    public void setTimestamp_long(long timestamp_long) {
        this.timestamp_long = timestamp_long;
    }

    public String getAuthor(){
        if (anonimo) {
            return "Anónimo";
        }else {
            return author;
        }
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        if (this.isConTexto()) {
            return content;
        }else{
            return "";
        }
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Map<String, String> getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Map<String, String> timestamp) {
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

    public boolean getAnonimo() {
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
}

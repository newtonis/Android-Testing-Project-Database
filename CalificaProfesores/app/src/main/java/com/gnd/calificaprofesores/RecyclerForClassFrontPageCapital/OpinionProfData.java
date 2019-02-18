package com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital;

import com.gnd.calificaprofesores.NetworkProfOpinion.UserProfComment;

import java.util.Map;

public class OpinionProfData extends AdapterElement {
    private String title, details, textContent;
    private Long conocimiento, clases, amabilidad;
    private Float stars;
    private Long timestamp;

    public OpinionProfData(
            String title,
            String details,
            String textContent,
            Long conocimiento,
            Long clases,
            Long amabilidad,
            Long timestamp,
            Float stars) {
        super(7);
        this.title = title;
        this.details = details;
        this.textContent = textContent;
        this.conocimiento = conocimiento;
        this.clases = clases;
        this.amabilidad = amabilidad;
        this.timestamp = timestamp;
        this.stars = stars;
    }

    public OpinionProfData(UserProfComment comment){
        super(7);
        String materiasText = "";
        Float stars = (comment.getConocimiento() + comment.getClases() + comment.getAmabilidad()) / 3f;

        Map<String, String> materias = comment.getMaterias();

        boolean start = true;
        for (String materia_key : materias.keySet()){
            if (start){
                start = false;
            }else{
                materiasText += ", ";
            }
            materiasText += materias.get(materia_key);
        }
        this.title = comment.getAuthor();
        this.details = materiasText;
        this.textContent = comment.getContent();
        this.conocimiento = comment.getConocimiento();
        this.clases = comment.getClases();
        this.amabilidad = comment.getAmabilidad();
        this.timestamp = comment.getTimestamp_long();
        this.stars = stars;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
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

    public Long getAmabilidad() {
        return amabilidad;
    }

    public void setAmabilidad(Long amabilidad) {
        this.amabilidad = amabilidad;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Float getStars() {
        return stars;
    }

    public void setStars(Float stars) {
        this.stars = stars;
    }

}

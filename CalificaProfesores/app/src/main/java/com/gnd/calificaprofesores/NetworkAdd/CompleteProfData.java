package com.gnd.calificaprofesores.NetworkAdd;

import java.util.Map;

public class CompleteProfData {
    private String ProfName;
    private Long ProfId; // 0 means new professor

    private Map<String, String> Facultades;
    private Map<String, String> Materias;

    private boolean erase;
    private boolean ConMaterias;


    public CompleteProfData(String profName, Long profId, Map<String, String> facultades, Map<String, String> materias, boolean erase, boolean ConMaterias) {
        ProfName = profName;
        ProfId = profId;
        Facultades = facultades;
        Materias = materias;
        this.erase = erase;
        this.ConMaterias = ConMaterias;
    }

    public String getProfName() {
        return ProfName;
    }

    public Long getProfId() {
        return ProfId;
    }

    public Map<String, String> getFacultades() {
        return Facultades;
    }

    public Map<String, String> getMaterias() {
        return Materias;
    }

    public boolean isErase() {
        return erase;
    }

    public boolean isConMaterias() {
        return ConMaterias;
    }

    public void setProfName(String profName) {
        ProfName = profName;
    }

    public void setProfId(Long profId) {
        ProfId = profId;
    }

    public void setFacultades(Map<String, String> facultades) {
        Facultades = facultades;
    }

    public void setMaterias(Map<String, String> materias) {
        Materias = materias;
    }

    public void setErase(boolean erase) {
        this.erase = erase;
    }

    public void setConMaterias(boolean conMaterias) {
        ConMaterias = conMaterias;
    }
}

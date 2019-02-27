package com.gnd.calificaprofesores.NetworkAdd;

import java.util.Map;

public class CompleteProfData {
    private String ProfName;
    private String ProfId; // 0 means new professor

    private Map<String, String> Facultades;
    private Map<String, SmallMateriaData> Materias;

    private boolean erase;
    private boolean ConMaterias;


    public CompleteProfData(
            String profName,
            String profId,
            Map<String, String> facultades,
            Map<String, SmallMateriaData> materias,
            boolean erase,
            boolean ConMaterias
    ) {
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

    public String getProfId() {
        return ProfId;
    }

    public Map<String, String> getFacultades() {
        return Facultades;
    }

    public Map<String, SmallMateriaData> getMaterias() {
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

    public void setProfId(String profId) {
        ProfId = profId;
    }

    public void setFacultades(Map<String, String> facultades) {
        Facultades = facultades;
    }

    public void setMaterias(Map<String, SmallMateriaData> materias) {
        Materias = materias;
    }

    public void setErase(boolean erase) {
        this.erase = erase;
    }

    public void setConMaterias(boolean conMaterias) {
        ConMaterias = conMaterias;
    }
}

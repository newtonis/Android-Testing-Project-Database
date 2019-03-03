package com.gnd.calificaprofesores.NetworkAdd;

import com.google.firebase.database.ServerValue;

import java.util.Map;

/** representa el campo de la base de datos para una solicitud para agregar un profesor **/

public class CompleteProfData {
    private String ProfName, profId;
    private boolean create; // 0 means new professor

    private Map<String, String> facultades;
    private Map<String, SmallMateriaData> materias;

    private boolean erase;
    private Map timestamp;

    public CompleteProfData(
            String profName,
            String profId,
            boolean create,
            Map<String, String> facultades,
            Map<String, SmallMateriaData> materias,
            boolean erase
    ) {
        ProfName = profName;
        this.profId = profId;
        this.create = create;
        this.facultades = facultades;
        this.materias = materias;
        this.erase = erase;
        timestamp = ServerValue.TIMESTAMP;
    }

    public String getProfName() {
        return ProfName;
    }

    public boolean getCreate() {
        return create;
    }

    public Map<String, String> getFacultades() {
        return facultades;
    }

    public Map<String, SmallMateriaData> getMaterias() {
        return materias;
    }

    public boolean isErase() {
        return erase;
    }

    public void setProfName(String profName) {
        ProfName = profName;
    }

    public void setCreate(boolean profId) {
        this.create = create;
    }

    public void setFacultades(Map<String, String> facultades) {
        this.facultades = facultades;
    }

    public void setMaterias(Map<String, SmallMateriaData> materias) {
        this.materias = materias;
    }

    public void setErase(boolean erase) {
        this.erase = erase;
    }

    public Map getTimestamp() {
        return timestamp;
    }

    public String getProfId() {
        return profId;
    }

    public void setProfId(String profId) {
        this.profId = profId;
    }
}

package com.gnd.calificaprofesores.NetworkAdd;

import com.google.firebase.database.ServerValue;

import java.util.Map;

public class CompleteUniData {
    String uniShortName;
    String uniCompleteName;
    Map timestamp;
    Long longTimestamp;
    boolean erase;
    public CompleteUniData(String uniShortName, String uniCompleteName) {
        this.uniShortName = uniShortName;
        this.uniCompleteName = uniCompleteName;
        erase = false;
        timestamp = ServerValue.TIMESTAMP;
    }

    public String getUniShortName() {
        return uniShortName;
    }

    public void setUniShortName(String uniShortName) {
        this.uniShortName = uniShortName;
    }

    public String getUniCompleteName() {
        return uniCompleteName;
    }

    public void setUniCompleteName(String uniCompleteName) {
        this.uniCompleteName = uniCompleteName;
    }

    public boolean isErase() {
        return erase;
    }

    public void setErase(boolean erase) {
        this.erase = erase;
    }

    public Map getTimestamp() {
        return timestamp;
    }

}

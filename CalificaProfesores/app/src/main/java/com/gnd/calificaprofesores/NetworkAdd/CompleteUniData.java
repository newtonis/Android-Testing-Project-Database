package com.gnd.calificaprofesores.NetworkAdd;

public class CompleteUniData {
    String uniShortName;
    String uniCompleteName;

    public CompleteUniData(String uniShortName, String uniCompleteName) {
        this.uniShortName = uniShortName;
        this.uniCompleteName = uniCompleteName;
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
}

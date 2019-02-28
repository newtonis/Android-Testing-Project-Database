package com.gnd.calificaprofesores.NetworkHandler;

public class UserExtraData {
    private String ShownName;
    private String UniId;
    private String UniName;

    public UserExtraData(String _ShownName){
        this.ShownName = _ShownName;
        UniId = "";
        UniName = "";
    }
    public String GetShownName(){
        return this.ShownName;
    }

    public String getShowName() {
        return ShownName;
    }

    public void setShowName(String showName) {
        ShownName = showName;
    }

    public String getUniId() {
        return UniId;
    }

    public void setUniId(String uniId) {
        UniId = uniId;
    }

    public String getUniName() {
        return UniName;
    }

    public void setUniName(String uniName) {
        UniName = uniName;
    }
}

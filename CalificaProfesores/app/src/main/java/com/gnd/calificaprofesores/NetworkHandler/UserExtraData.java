package com.gnd.calificaprofesores.NetworkHandler;

import com.firebase.ui.auth.data.model.User;

import javax.inject.Singleton;

public class UserExtraData{
    private String ShownName;
    private String UniId;
    private String UniName;
    private boolean isAdmin;
    private boolean gotDataAdmin;

    public UserExtraData(){
        ShownName = "";
        UniId = "";
        UniName = "";
        isAdmin = false;
        gotDataAdmin = false;
    }

    public UserExtraData(String _ShownName){
        this.ShownName = _ShownName;
        UniId = "";
        UniName = "";
        isAdmin = false;
        gotDataAdmin = false;
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

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean isGotDataAdmin() {
        return gotDataAdmin;
    }

    public void setGotDataAdmin(boolean gotDataAdmin) {
        this.gotDataAdmin = gotDataAdmin;
    }

    public void logOut(){
        isAdmin = false;
        gotDataAdmin = false;
        UniId = "";
        UniName = "";
        ShownName = "";
    }
}

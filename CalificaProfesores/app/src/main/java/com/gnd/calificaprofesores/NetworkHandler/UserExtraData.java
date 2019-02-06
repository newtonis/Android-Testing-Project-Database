package com.gnd.calificaprofesores.NetworkHandler;

public class UserExtraData {
    private String ShowName;
    private String uid;

    UserExtraData(String _uid, String _ShownName){
        this.uid = uid;
        this.ShowName = _ShownName;
    }
    String GetShownName(){
        return this.ShowName;
    }

}

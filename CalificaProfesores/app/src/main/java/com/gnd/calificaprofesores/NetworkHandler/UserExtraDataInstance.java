package com.gnd.calificaprofesores.NetworkHandler;


public class UserExtraDataInstance {
    private static UserExtraData userExtraData = null;

    public UserExtraDataInstance(){

    }

    public static UserExtraData getInstance(){
        if (userExtraData == null) {
            userExtraData = new UserExtraData();
        }
        return userExtraData;
    }
}

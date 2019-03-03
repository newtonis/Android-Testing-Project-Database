package com.gnd.calificaprofesores.NetworkHandler;

public class UserDataManagerInstance {
    private static UserDataManager userDataManager = null;

    public static UserDataManager getInstance(){
        if (userDataManager == null){
            userDataManager = new UserDataManager("");
        }
        return userDataManager;
    }
}

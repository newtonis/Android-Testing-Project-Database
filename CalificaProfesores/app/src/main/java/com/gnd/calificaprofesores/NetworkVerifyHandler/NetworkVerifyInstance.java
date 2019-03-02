package com.gnd.calificaprofesores.NetworkVerifyHandler;

public class NetworkVerifyInstance {
    public static NetworkVerifyHandler networkVerifyHandler = null;

    public static NetworkVerifyHandler getInstance(){
        if (networkVerifyHandler == null){
            networkVerifyHandler = new NetworkVerifyHandler();
        }
        return networkVerifyHandler;
    }
}

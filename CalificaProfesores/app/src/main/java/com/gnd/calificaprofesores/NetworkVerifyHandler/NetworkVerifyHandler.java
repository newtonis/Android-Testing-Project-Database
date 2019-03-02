package com.gnd.calificaprofesores.NetworkVerifyHandler;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NetworkVerifyHandler {
    private DatabaseReference database;
    public NetworkVerifyHandler(){
        database = FirebaseDatabase.getInstance().getReference();

    }

    public void listenForUniRequests(){

    }
    public void listenForProfAddRequests(){

    }
    public void listenForCourseAddRequests(){

    }

}

package com.gnd.calificaprofesores.NetworkHandler;

import com.google.firebase.database.ServerValue;

import java.util.Map;

/**
 * Created by newtonis on 24/02/18.
 */

public class Vote {
    private String CA , CE , A;

    private Map<String,String> time;

    Vote(Long CA, Long CE, Long a) {
        this.CA = Long.toString(CA);
        this.CE = Long.toString(CE);
        A = Long.toString(a);
        time = ServerValue.TIMESTAMP;
    }

}

package com.gnd.calificaprofesores.AdapterClassFrontPage;

import java.util.Map;

public class FirebMessage {
    public String message;
    public String senderPhoneNumber;
    public String receiverPhoneNumber;
    public Map time;

    public FirebMessage() {
    }

    public FirebMessage(String message, String senderPhoneNumber, String receiverPhoneNumber, Map time) {
        this.message = message;
        this.senderPhoneNumber = senderPhoneNumber;
        this.receiverPhoneNumber = receiverPhoneNumber;
        this.time = time;
    }
}
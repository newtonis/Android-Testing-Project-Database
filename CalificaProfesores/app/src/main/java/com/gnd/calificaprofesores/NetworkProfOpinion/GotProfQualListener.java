package com.gnd.calificaprofesores.NetworkProfOpinion;

public interface GotProfQualListener {
    void onGotProfQualListener(float conocimiento, float clases,float amabilidad);
    void onError();
}

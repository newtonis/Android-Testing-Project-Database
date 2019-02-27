package com.gnd.calificaprofesores.IntentsManager;

import android.content.Context;
import android.content.Intent;

import com.gnd.calificaprofesores.NetworkProfOpinion.UserProfComment;

public class IntentProfManager {
    String ProfName;
    String ProfId;
    Intent myIntent;
    Context ctx;
    Class cls;

    public IntentProfManager(Intent intent){
        myIntent = intent;
        ProfName = intent.getStringExtra("ProfName");
        ProfId = intent.getStringExtra("ProfId");
    }

    public IntentProfManager(Intent intent, String _ProfName, String _ProfId){
        myIntent = intent;
        this.ProfName = _ProfName;
        this.ProfId = _ProfId;
        myIntent.putExtra("ProfName",_ProfName);
        myIntent.putExtra("ProfId",_ProfId);
    }
    public IntentProfManager(){
        ProfName = "Issac Newton";
        ProfId = "";
    }
    public Intent GetIntent(){
        return myIntent;
    }
    public String GetProfName(){
        return ProfName;
    }
    public String GetProfId(){
        return ProfId;
    }
    public IntentProfManager ConvertIntent(Context _ctx, Class _cls){
        return new IntentProfManager(
                new Intent(_ctx, _cls), ProfName, ProfId
        );
    }



}

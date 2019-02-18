package com.gnd.calificaprofesores.IntentsManager;

import android.content.Context;
import android.content.Intent;

import com.gnd.calificaprofesores.NetworkProfOpinion.UserProfComment;

public class IntentProfManager {
    String ProfName;
    Long ProfId;
    Intent myIntent;
    Context ctx;
    Class cls;

    public IntentProfManager(Intent intent){
        myIntent = intent;
        ProfName = intent.getStringExtra("ProfName");
        ProfId = intent.getLongExtra("ProfId", 1L);
    }

    public IntentProfManager(Intent intent, String _ProfName, Long _ProfId){
        myIntent = intent;
        this.ProfName = _ProfName;
        this.ProfId = _ProfId;
        myIntent.putExtra("ProfName",_ProfName);
        myIntent.putExtra("ProfId",_ProfId);
    }
    public IntentProfManager(){
        ProfName = "Issac Newton";
        ProfId = 1L;
    }
    public Intent GetIntent(){
        return myIntent;
    }
    public String GetProfName(){
        return ProfName;
    }
    public Long GetProfId(){
        return ProfId;
    }
    public IntentProfManager ConvertIntent(Context _ctx, Class _cls){
        return new IntentProfManager(
                new Intent(_ctx, _cls), ProfName, ProfId
        );
    }



}

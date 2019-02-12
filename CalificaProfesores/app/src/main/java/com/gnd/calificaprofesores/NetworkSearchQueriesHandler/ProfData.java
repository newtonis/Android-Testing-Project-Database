package com.gnd.calificaprofesores.NetworkSearchQueriesHandler;

import android.view.View;

public class ProfData {
    private String Name;
    private Long Id;
    private String Details;
    private View.OnClickListener clickListener;

    public ProfData(String _Name,Long _Id, String _Details){
        Name = _Name;
        Id = _Id;
        Details = _Details;
    }
    public void SetClickListener(View.OnClickListener listener){
        this.clickListener = listener;
    }
    public View.OnClickListener GetClickListener(){
        return this.clickListener;
    }
    public Long GetId(){
        return this.Id;
    }

    public String GetName() {
        return Name;
    }

    public void SetName(String name) {
        Name = name;
    }

    public String GetDetails() {
        return Details;
    }

    public void SetDetails(String details) {
        Details = details;
    }

}

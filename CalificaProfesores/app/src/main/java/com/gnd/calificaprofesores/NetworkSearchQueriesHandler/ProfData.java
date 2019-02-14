package com.gnd.calificaprofesores.NetworkSearchQueriesHandler;

import android.view.View;

/** Se usa para almacenar la informacion de los profesores en el buscador **/
/** Como se usa en un tree_set necesitamos definirle comparador **/

public class ProfData implements Comparable<ProfData> {
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

    @Override
    public int compareTo(ProfData o) {
        if (this.Id > o.Id){
            return 1;
        }else if (this.Id < o.Id){
            return -1;
        }else{
            return 0;
        }
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (getClass() != o.getClass()) return false;
        ProfData oUni = (ProfData)o;
        return oUni.Id == this.Id;
    }
}

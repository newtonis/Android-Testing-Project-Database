package com.gnd.calificaprofesores.NetworkSearchQueriesHandler;

import android.view.View;

import java.util.Comparator;

public class UniData implements Comparable<UniData> {
    private Long id;
    private String UniShortName;
    private String UniShownName;
    private View.OnClickListener clickListener;
    public UniData(Long _id, String _UniShortName, String _UniShownName){
        id = _id;
        UniShortName = _UniShortName;
        UniShownName = _UniShownName;
    }
    public void SetClickListener(View.OnClickListener listener){
        this.clickListener = listener;
    }
    public View.OnClickListener GetClickListener(){
        return this.clickListener;
    }
    public Long GetId(){
        return this.id;
    }
    public String GetUniShortName(){
        return UniShortName;
    }
    public String GetUniShownName(){
        return UniShownName;
    }

    @Override
    public int compareTo(UniData o) {
        if (this.id > o.id){
            return 1;
        }else if (this.id < o.id){
            return -1;
        }else{
            return 0;
        }
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (getClass() != o.getClass()) return false;
        UniData oUni = (UniData)o;
        return oUni.id == this.id;
    }
}

package com.gnd.calificaprofesores.NetworkSearchQueriesHandler;

import java.util.Comparator;

public class UniData implements Comparator<UniData> {
    private Long id;
    private String UniShortName;
    private String UniShownName;

    public UniData(Long _id, String _UniShortName, String _UniShownName){
        id = _id;
        UniShortName = _UniShortName;
        UniShownName = _UniShownName;
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
    public int compare(UniData o1, UniData o2) {
        if (o1.id > o2.id){
            return 1;
        }else if (o1.id < o2.id){
            return -1;
        }else{
            return 0;
        }
    }
}

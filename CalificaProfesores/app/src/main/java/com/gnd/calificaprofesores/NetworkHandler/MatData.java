package com.gnd.calificaprofesores.NetworkHandler;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by newtonis on 22/02/18.
 */
class CorruptDataException extends Exception {
}

public class MatData {
    Map<String , Long > data;
    String facultad , name;
    Long facultadId;

    MatData(){
        data = new TreeMap<>();
        facultad = "GND";
    }
    MatData( Map<String,Long> _data){
        data = _data;
    }
    void AddProperty(String prop,Long val){
        data.put(prop,val);
    }
    void setFacultad(String _facultad){
        facultad = _facultad;
    }
    void setFacultadId(Long _id){
        facultadId = _id;
    }
    Long getFacultadId(){
        return facultadId;
    }

    void setName(String _name){
        name = _name;
    }
    public String getFacultad(){
        return facultad;
    }
    public String getName(){
        return  name;
    }

    public Float getPropAvg(String prop){
        if (prop != "CA" && prop != "CE" && prop != "A"){
            return 0f;
        }
        if ( data.get("CNT")== 0){
            return 0f;
        }
        return data.get(prop).floatValue() / data.get("CNT").floatValue();
    }
    public Float getProp(String prop){
        return data.get(prop).floatValue();
    }
}

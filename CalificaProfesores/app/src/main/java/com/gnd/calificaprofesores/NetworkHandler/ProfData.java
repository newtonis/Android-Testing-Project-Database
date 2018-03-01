package com.gnd.calificaprofesores.NetworkHandler;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by newtonis on 28/02/18.
 */

public class ProfData {
    private Long Id;
    private String Name;
    Map<String , Long > info; /// informacion adicional del profesor

    public ProfData(Long id,String _Name) {
        info = new TreeMap<>();
        Id = id;
        Name = _Name;
    }
    public String getName(){
        return Name;
    }
    public Long getId() {
        return Id;
    }
    public void setId(Long id) {
        Id = id;
    }
    public void addProperty(String prop,Long data){
        info.put(prop,data);
    }
    public Long getProperty(String prop){
        return info.get(prop).longValue();
    }
}

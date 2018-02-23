package com.gnd.calificaprofesores.ListItems;

/**
 * Created by newtonis on 23/02/18.
 *
 * Representa el controlador de un item de la lista de de profesores que aparece
 * En la ventana para buscar profesores or nombre
 *
 * */

public class BasicListItem {
    public String getName() {
        return Name;
    }
    public String getDetail(){
        return Detail;
    }
    public Long getId(){ return Id; }

    private String Name;
    private String Detail;
    private Long Id;

    public void setName(String name) {
        Name = name;
    }
    public void setDetail(String detail){
        Detail = detail;
    }
    public void setId(Long id){
        this.Id = id;
    }

    public BasicListItem(String Name,String detail,Long id) {
        this.Name = Name;
        this.Detail = detail;
        this.Id = id;
    }
    public BasicListItem(){
        this.Name = "undefined";
    }

}

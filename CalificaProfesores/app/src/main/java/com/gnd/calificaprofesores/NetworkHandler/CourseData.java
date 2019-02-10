package com.gnd.calificaprofesores.NetworkHandler;

public class CourseData {
    private Long Id;
    private String ShownName;
    private String detail;
    public CourseData(Long _Id, String _ShownName, String _detail){
        this.Id = _Id;
        this.ShownName = _ShownName;
        this.detail = _detail;
    };
    public Long GetId(){
        return this.Id;
    }
    public String GetShownName(){
        return ShownName;
    }
    public String GetDetail(){
        return detail;
    }
}

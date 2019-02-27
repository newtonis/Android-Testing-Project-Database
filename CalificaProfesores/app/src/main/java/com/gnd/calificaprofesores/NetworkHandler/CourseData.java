package com.gnd.calificaprofesores.NetworkHandler;

import android.view.View;

import com.gnd.calificaprofesores.NetworkProfOpinion.ProfExtendedData;
import com.gnd.calificaprofesores.NetworkSearchQueriesHandler.UniData;

import java.util.List;
import java.util.Map;

/** se usa para el buscador **/

public class CourseData implements Comparable<CourseData>{
    private String Id;
    private String ShownName;
    private String detail;
    private View.OnClickListener clickListener;
    private List<ProfExtendedData> professors;
    private Float score;

    public CourseData(String _Id, String _ShownName, String _detail){
        this.Id = _Id;
        this.ShownName = _ShownName;
        this.detail = _detail;
        professors = null;
    };

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public List<ProfExtendedData> getProfessors() {
        return professors;
    }

    public void setProfessors(List<ProfExtendedData> professors) {
        this.professors = professors;
    }

    public void SetClickListener(View.OnClickListener clickListener){
        this.clickListener = clickListener;
    }
    public View.OnClickListener GetClickListener(){
        return this.clickListener;
    }
    public String GetId(){
        return this.Id;
    }
    public String GetShownName(){
        return ShownName;
    }
    public String GetDetail(){
        return detail;
    }

    @Override
    public int compareTo(CourseData o) {
        return this.Id.compareTo(o.Id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (getClass() != o.getClass()) return false;
        CourseData oUni = (CourseData)o;
        return oUni.Id == this.Id;
    }
}

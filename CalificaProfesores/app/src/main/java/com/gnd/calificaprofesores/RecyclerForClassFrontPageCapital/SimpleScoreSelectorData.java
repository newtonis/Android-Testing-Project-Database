package com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital;

public class SimpleScoreSelectorData extends AdapterElement{
    private Float stars;

    public SimpleScoreSelectorData(){
        super(12);
        stars = 0f;
    }

    public Float getStars() {
        return stars;
    }

    public void setStars(Float stars) {
        this.stars = stars;
    }
}

package com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital;

public class StarsData extends AdapterElement{
    private Float rating;
    public StarsData(Float _rating){
        super(2);
        rating = _rating;
    }
    public Float GetRating() {
        return rating;
    }
    public void SetRating(Float rating) {
        this.rating = rating;
    }
}

package com.gnd.calificaprofesores.OpinionItem;

/*** Aqui administramos los datos de una opinión para que sea mostrada **/

public class OpinionItem {
    private Long Score;
    private String Author;
    private String Text;

    public String getAuthor() {
        return Author;
    }
    public String getText(){
        return Text;
    }
    public Long getScore(){ return Score; }

    public OpinionItem(String Author, String Text, Long Score){
        this.Author = Author;
        this.Text = Text;
        this.Score = Score;
    }

}

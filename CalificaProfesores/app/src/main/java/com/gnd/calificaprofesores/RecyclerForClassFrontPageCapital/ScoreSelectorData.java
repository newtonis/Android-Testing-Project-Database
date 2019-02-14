package com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital;

/** layout_score_selector.xml **/

public class ScoreSelectorData extends AdapterElement {
    Integer []itemsValue;
    public ScoreSelectorData(Integer v1, Integer v2, Integer v3){
        super(5);
        itemsValue = new Integer[3];

        itemsValue[0] = v1;
        itemsValue[1] = v2;
        itemsValue[2] = v3;
    }
    public Integer GetValue(Integer item){
        return itemsValue[item];
    }
}

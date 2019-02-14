package com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital;

/** Boton seleccionable con un titulo **/

/** search_list_element.xml **/

public class SelectableItem extends AdapterElement  {
    String title, detail;
    boolean clicked;
    public SelectableItem(String _title, String _detail){
        super(3);
        title = _title;
        detail = _detail;
        clicked = false;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setClicked(){
        if (clicked){
            clicked = false;
        }else{
            clicked = true;
        }
    }
    public boolean getClicked(){
        return clicked;
    }
}

package com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital;

/** Boton seleccionable con un titulo **/

/** search_list_element_mini.xml **/

public class SelectableItem extends AdapterElement  {
    String title, detail;
    String id;
    boolean clicked;
    public SelectableItem(String id, String _title, String _detail){
        super(3);
        this.id = id;
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

    public boolean isClicked() {
        return clicked;
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

package com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital;


import android.text.Editable;
import android.view.View;

import com.gnd.calificaprofesores.NetworkSearchQueriesHandler.UniData;
import com.gnd.calificaprofesores.SearchItem.AdapterSearch;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/** layout_item_mini_search.xml **/

public class MiniSearchData extends AdapterElement{
    private String shownText, switchText;
    private Set<UniData> elementSet;

    private boolean enabled;
    private boolean allowSwitch;
    private Adapter adapter;
    private Adapter adapter2;
    private Editable editable;
    private SearchCalledListener searchCalledListener;

    public MiniSearchData(
            String shownText,
            String switchText,
            boolean allowSwitch,
            SearchCalledListener listener) {
        super(14);
        this.shownText = shownText;
        this.switchText = switchText;
        this.allowSwitch = allowSwitch;
        elementSet = new TreeSet<>();
        searchCalledListener = listener;
        enabled = true;

        adapter = new Adapter();
        adapter2 = new Adapter();
    }

    public String getShownText() {
        return shownText;
    }

    public void setShownText(String shownText) {
        this.shownText = shownText;
    }

    public String getSwitchText() {
        return switchText;
    }

    public void setSwitchText(String switchText) {
        this.switchText = switchText;
    }

    public Set<UniData> getElementSet() {
        return elementSet;
    }

    public void setElementSet(Set<UniData> elementSet) {
        this.elementSet = elementSet;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isAllowSwitch() {
        return allowSwitch;
    }

    public void setAllowSwitch(boolean allowSwitch) {
        this.allowSwitch = allowSwitch;
    }

    public void Search(String text){
        searchCalledListener.onSearchCalled(text);
    }

    public SearchCalledListener getSearchCalledListener() {
        return searchCalledListener;
    }

    public void setSearchCalledListener(SearchCalledListener searchCalledListener) {
        this.searchCalledListener = searchCalledListener;
    }

    public Adapter getAdapter() {
        return adapter;
    }

    public void setAdapter(Adapter adapter) {
        this.adapter = adapter;
    }

    public Adapter getAdapter2() {
        return adapter2;
    }

    public void setAdapter2(Adapter adapter2) {
        this.adapter2 = adapter2;
    }

    public void SearchResults(List<UniData> data){
       adapter.clear();
        for (final UniData element : data) {
            MiniSearchListItemData miniSearchListItemData = new MiniSearchListItemData(
                    element.GetUniShortName(),
                    element.GetUniShownName()
            );
            miniSearchListItemData.setListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    element.SetType(13); // deletable item type
                    if (!elementSet.contains(element)){
                        element.SetClickListener(new View.OnClickListener(){
                            @Override
                            public void onClick(View v) {
                                elementSet.remove(element);
                                adapter2.removeElement(element);
                                adapter2.notifyDataSetChanged();
                            }
                        });
                        adapter2.AddElement(element);
                        adapter2.notifyDataSetChanged();
                    }
                    elementSet.add(element);
                    eraseText();
                }
            });

            adapter.AddElement(miniSearchListItemData);
        }
        adapter.notifyDataSetChanged();
    }
    public void eraseText(){
        editable.clear();
    }

    public Editable getEditable() {
        return editable;
    }

    public void setEditable(Editable editable) {
        this.editable = editable;
    }

}

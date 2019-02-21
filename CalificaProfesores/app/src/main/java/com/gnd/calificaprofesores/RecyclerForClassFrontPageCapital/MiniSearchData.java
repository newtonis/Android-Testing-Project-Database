package com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital;


import com.gnd.calificaprofesores.NetworkSearchQueriesHandler.UniData;
import com.gnd.calificaprofesores.SearchItem.AdapterSearch;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

/** layout_item_mini_search.xml **/

public class MiniSearchData extends AdapterElement{
    private String shownText, switchText;
    private Set<UniData> elementSet;
    private boolean enabled;
    private boolean allowSwitch;
    private AdapterSearch adapter;
    private SearchCalledListener searchCalledListener;
    private UniData SelectedItem;

    public MiniSearchData(
            String shownText,
            String switchText,
            boolean allowSwitch,
            SearchCalledListener listener) {
        super(12);
        this.shownText = shownText;
        this.switchText = switchText;
        this.allowSwitch = allowSwitch;
        elementSet = new TreeSet<>();
        searchCalledListener = listener;
        enabled = true;
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

    public AdapterSearch getAdapter() {
        return adapter;
    }

    public void setAdapter(AdapterSearch adapter) {
        this.adapter = adapter;
    }

    public void SearchResults(ArrayList<UniData> data){
        for (UniData element : data) {
            adapter.addElement(element);
        }
        adapter.notifyDataSetChanged();
    }

    public UniData clickedAddButton(){
        elementSet.add(SelectedItem);
        return SelectedItem;
    }
}

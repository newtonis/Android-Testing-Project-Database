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
    private Long maxElements;
    private OnButtonSelectedListener buttonSelectedListener;

    public MiniSearchData(
            String shownText,
            String switchText,
            boolean allowSwitch,
            Long maxElements,
            SearchCalledListener listener) {
        super(14);
        this.shownText = shownText;
        this.switchText = switchText;
        this.allowSwitch = allowSwitch;
        elementSet = new TreeSet<>();
        searchCalledListener = listener;
        enabled = true;
        this.maxElements = maxElements; // -1 means infinity
        adapter = new Adapter();
        adapter2 = new Adapter();
        buttonSelectedListener = null;
    }

    public Long getMaxElements() {
        return maxElements;
    }

    public void setMaxElements(Long maxElements) {
        this.maxElements = maxElements;
    }

    public OnButtonSelectedListener getButtonSelectedListener() {
        return buttonSelectedListener;
    }

    public void setButtonSelectedListener(OnButtonSelectedListener buttonSelectedListener) {
        this.buttonSelectedListener = buttonSelectedListener;
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

    public void SearchResults(List<UniData> originalData){
        List<UniData> data = new ArrayList<>();
        /// only first four items
        int count = 0;
        for (UniData item : originalData){
            if (count < 4){
                data.add(item);
                count ++;
            }
        }
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
                        if (
                                !elementSet.contains(element) &&
                                (maxElements == -1L || elementSet.size() < maxElements)
                        ) {
                            element.SetClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    elementSet.remove(element);
                                    adapter2.removeElement(element);
                                    if (buttonSelectedListener != null){
                                        buttonSelectedListener.onButtonSelected(elementSet.size() == maxElements);
                                    }
                                    adapter2.notifyDataSetChanged();
                                }
                            });

                            adapter2.AddElement(element);
                            adapter2.notifyDataSetChanged();
                        }
                        elementSet.add(element);
                        if (buttonSelectedListener != null){
                            buttonSelectedListener.onButtonSelected(elementSet.size() == maxElements);
                        }
                        eraseText();

                        adapter.clear();
                        adapter.notifyDataSetChanged();
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

    public void AddElement(AdapterElement element){
        adapter.AddElement(element);
    }
    public void notifyDataSetChanged(){
        adapter.notifyDataSetChanged();
    }
}

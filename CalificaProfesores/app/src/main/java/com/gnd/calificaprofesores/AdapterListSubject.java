package com.gnd.calificaprofesores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by newtonis on 21/02/18.
 */

public abstract class AdapterListSubject extends BaseAdapter{
    private final ArrayList mData;
    Map <String , CheckBox> checkBoxes;

    public AdapterListSubject(Map<String, String> map) {
        mData = new ArrayList();
        checkBoxes = new TreeMap<>();
        mData.addAll(map.entrySet());
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Map.Entry<String, String> getItem(int position) {
        return (Map.Entry) mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO implement you own logic with ID
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View result;

        if (convertView == null) {
            result = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_adapter_subject, parent, false);
        } else {
            result = convertView;
        }

        Map.Entry<String, String> item = getItem(position);

        // TODO replace findViewById by ViewHolder
        ((CheckBox) result.findViewById(R.id.CheckBox)).setText(item.getKey());
        ((TextView) result.findViewById(R.id.SmallText)).setText(item.getValue());

        checkBoxes.put(item.getKey(),(CheckBox) result.findViewById(R.id.CheckBox));

        ((CheckBox) result.findViewById(R.id.CheckBox)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                OnCheckBoxPress();
            }
        });
        return result;
    }
    public String getItemName(int position){
        Map.Entry<String,String> item = getItem(position);
        return item.getKey();
    }
    public boolean isChecked(String itemName){
       if (checkBoxes.containsKey(itemName)) {
           return checkBoxes.get(itemName).isChecked();
       }
       return true;
    }
    public void checkAll(){
        for (Map.Entry<String,CheckBox> entry : checkBoxes.entrySet()){
            entry.getValue().setChecked(true);
        }
    }
    public abstract void OnCheckBoxPress();
}
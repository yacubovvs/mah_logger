package ru.cubos.data;

import java.util.ArrayList;
import java.util.List;

public class Data {
    public List<DataElement> dataList = new ArrayList();
    public float v_max;
    public float v_min;
    public float ma_max;
    public float ma_min;

    public int length(){
        return dataList.size();
    }

    public float getDelta_ma(){
        return ma_max - ma_min;
    }

    public float getDelta_v(){
        return v_max - v_min;
    }

    public void addData(float v, float ma){
        if(v>v_max) v_max = v;
        if(ma>ma_max) ma_max = ma;
        if(v<v_min) v_min = v;
        if(ma<ma_min) ma_min = ma;

        dataList.add(new DataElement(v, ma));
    }
}

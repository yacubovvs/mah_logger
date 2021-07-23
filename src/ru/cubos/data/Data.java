package ru.cubos.data;

import java.util.ArrayList;
import java.util.List;

public class Data {
    public List<DataElement> dataList = new ArrayList();
    public Float v_max = null;
    public Float v_min = null;
    public Float ma_max = null;
    public Float ma_min = null;

    public float get_v_max(){
        if(v_max==null) return 0;
        return v_max;
    }
    public float get_v_min(){
        if(v_min==null) return 0;
      return v_min;
    }
    public float get_ma_max(){
        if(ma_max==null) return 0;
      return ma_max;
    } ;
    public float get_ma_min(){
        if(ma_min==null) return 0;
      return ma_min;
    }

    public int length(){
        return dataList.size();
    }

    public float getDelta_ma(){
        if(ma_max==null ||ma_min==null) return 0;
        return ma_max - ma_min;
    }

    public float getDelta_v(){
        if(v_max==null ||v_min==null) return 0;
        return v_max - v_min;
    }

    public void addData(float v, float ma){
        if(v_max==null || v>v_max) v_max = v;
        if(ma_max==null || ma>ma_max) ma_max = ma;
        if(v_min==null || v<v_min) v_min = v;
        if(ma_min==null || ma<ma_min) ma_min = ma;

        dataList.add(new DataElement(v, ma));
    }
}

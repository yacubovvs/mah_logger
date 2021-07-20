package ru.cubos.data;

import java.util.ArrayList;
import java.util.List;

public class DataSimple extends Data {
    List<VmAhData_value> dataValue= new ArrayList();

    class VmAhData_value{
        float V = 0;
        float mah = 0;
    }

    public DataSimple(){

    }

    @Override
    public long get_size() {
        return 0;
    }

    @Override
    public float get_value(int line, double position) {
        return 0;
    }
}

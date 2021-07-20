package ru.cubos.data;

import java.util.List;

abstract public class Data {
    List<DataLine> dataLines;

    public abstract long get_size();
    public abstract float get_value(int line, double position);
}

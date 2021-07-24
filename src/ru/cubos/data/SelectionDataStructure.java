package ru.cubos.data;

public class SelectionDataStructure {
    public float duration_value_ms = 5;
    public int selectionDuration = 0;
    public float average_v = (float) 0.0;
    public float average_ma = (float) 0.0;

    public float getSelectionDuration_ms(){
        return (float)selectionDuration*duration_value_ms;
    }

    public float getSelection_mah(){
        return average_ma*getSelectionDuration_ms()/1000/60/60;
    }
}

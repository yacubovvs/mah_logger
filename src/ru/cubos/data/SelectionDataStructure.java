package ru.cubos.data;

import static ru.cubos.settings.commonSettings.DELAY_BETWEEN_DATA_MS;

public class SelectionDataStructure {
    public float duration_value_ms = DELAY_BETWEEN_DATA_MS;
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

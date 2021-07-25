package ru.cubos.settings;

public class commonSettings {
    public final static int LIVE_DATA_LENGTH = 5000;
    public static float DELAY_BETWEEN_DATA_MS = (float) 5.15;

    private static int counter = 0;
    private static final int COUNT_MAX = 100;
    static long start_counting;

    public static void newGotMessage_clearCounter(){
        counter=0;
    }
    public static void newGotMessage(){
        if(counter==0){
            start_counting = System.currentTimeMillis();
            counter++;
        }else if(counter>=COUNT_MAX){
            DELAY_BETWEEN_DATA_MS = ((float)(System.currentTimeMillis() - start_counting))/(float)COUNT_MAX;
            //System.out.println(DELAY_BETWEEN_DATA_MS);
            newGotMessage_clearCounter();
        }else{
            counter++;
        }


    }
}

package ru.cubos;

import ru.cubos.data.Data;
import ru.cubos.data.DataElement;
import ru.cubos.forms.MainForm;
import ru.cubos.forms.elements.views.GraphPannel;

import java.io.*;

import static ru.cubos.settings.commonSettings.LIVE_DATA_LENGTH;

public class MainModel {

    public MainForm mainForm;
    File dataFile;
    public Data liveData;
    boolean dataUpdated = false;

    public MainModel(){
        liveData = new Data();
        dataFile = new File("logs/data.txt");
        dataFile.delete();

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(dataFile);
            FileInputStream fis = new FileInputStream(dataFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        FileOutputStream finalFos = fos;
        mainForm = new MainForm(){
            @Override
            public void clearLiveStream(){
                MainModel.this.clearLiveStream();
            }

            @Override
            public void onGetDataString(String string){
                String[] dataString = string.trim().split(";");

                float ma = 0;
                float v = 0;

                for(int i=0; i<dataString.length; i++){
                    String[] valueSplit = dataString[i].trim().split(" ");

                    float value = 0;
                    try {
                        value = Float.parseFloat(valueSplit[0]);
                    }catch(Exception e){}

                    if(valueSplit.length>1){
                        if(valueSplit[1].trim().equals("ma")){
                            ma = value;
                        }else if(valueSplit[1].trim().equals("v")){
                            v = value;
                        }
                    }

                }

                //System.out.println("Add data " + ma + " " + v);

                if(mainForm.liveViewEnable) {
                    liveData.addData(v, ma);

                    if (liveData.length() >= LIVE_DATA_LENGTH) {
                        liveData.dataList.remove(0);
                    }
                }

                dataUpdated = true;


                /*
                try {
                    finalFos.write((string+"\n").getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                */
            }
        };

        dataUpdateThread.start();

        /* * /
        float k = 1;
        liveData.addData((float) (4.18 - 4.18*0.5/(19.0/1)), (float) (0.0*k));
        liveData.addData((float) (4.18 - 4.18*0.5/(19.0/2)), (float) (0.0*k));
        liveData.addData((float) (4.18 - 4.18*0.5/(19.0/3)), (float) (5.0*k));
        liveData.addData((float) (4.18 - 4.18*0.5/(19.0/4)), (float) (5.0*k));
        liveData.addData((float) (4.18 - 4.18*0.5/(19.0/5)), (float) (10.0*k));
        liveData.addData((float) (4.18 - 4.18*0.5/(19.0/6)), (float) (10.0*k));
        liveData.addData((float) (4.18 - 4.18*0.5/(19.0/7)), (float) (5.0*k));
        liveData.addData((float) (4.18 - 4.18*0.5/(19.0/8)), (float) (5.0*k));
        liveData.addData((float) (4.18 - 4.18*0.5/(19.0/9)), (float) (0.0*k));
        liveData.addData((float) (4.18 - 4.18*0.5/(19.0/10)), (float) (0.0*k));
        liveData.addData((float) (4.18 - 4.18*0.5/(19.0/11)), (float) (-5.0*k));
        liveData.addData((float) (4.18 - 4.18*0.5/(19.0/12)), (float) (-5.0*k));
        liveData.addData((float) (4.18 - 4.18*0.5/(19.0/13)), (float) (-10.0*k));
        liveData.addData((float) (4.18 - 4.18*0.5/(19.0/14)), (float) (-10.0*k));
        liveData.addData((float) (4.18 - 4.18*0.5/(19.0/15)), (float) (-5.0*k));
        liveData.addData((float) (4.18 - 4.18*0.5/(19.0/16)), (float) (-5.0*k));
        liveData.addData((float) (4.18 - 4.18*0.5/(19.0/17)), (float) (0.0*k));
        liveData.addData((float) (4.18 - 4.18*0.5/(19.0/18)), (float) (0.0*k));
        liveData.addData((float) (4.18 - 4.18*0.5/(19.0/19)), (float) (0.0*k));

        dataUpdated = true;
        // */

    }

    Thread dataUpdateThread = new Thread(new Runnable() {
        @Override
        public void run() {
            while (true){
                if(dataUpdated){
                    try {
                        // Update GraphPannel
                        //if(mainForm.liveViewEnable) {
                            ((GraphPannel) mainForm.graphPanel).updateGraph(MainModel.this);
                            if (liveData != null && liveData.length() > 0 && ((GraphPannel) mainForm.graphPanel).isDrawing == false) {

                                DataElement lastElement = liveData.dataList.get(liveData.dataList.size() - 2);
                                mainForm.setCurrent_v(lastElement.v);
                                mainForm.setCurrent_ma(lastElement.ma);
                                mainForm.setMax_ma(liveData.ma_max);
                                mainForm.setMin_ma(liveData.ma_min);
                                mainForm.setMax_v(liveData.v_max);
                                mainForm.setMin_v(liveData.v_min);
                                mainForm.setDelta_ma(liveData.getDelta_ma());
                                mainForm.setDelta_v(liveData.getDelta_v());
                            }
                        //}
                    }catch(Exception e){}
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }


    });

    public void show() {
        mainForm.setVisible(true);
    }

    synchronized public void clearLiveStream(){
        dataUpdated = false;
        liveData.reset();
        ((GraphPannel) mainForm.graphPanel).updateGraph(MainModel.this);
        dataUpdated = true;
    }

}

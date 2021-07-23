package ru.cubos;

import ru.cubos.data.Data;
import ru.cubos.data.DataElement;
import ru.cubos.forms.MainForm;
import ru.cubos.forms.elements.views.GraphPannel;

import java.io.*;
import java.util.ArrayList;

import static ru.cubos.settings.commonSettings.LIVE_DATA_LENGTH;

public class MainModel {
    public MainForm mainForm;
    File dataFile;
    public Data data;
    boolean dataUpdated = false;

    public MainModel(){
        data = new Data();
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

                    data.addData(v, ma);

                if(data.length()>=LIVE_DATA_LENGTH){
                    data.dataList.remove(0);
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
    }

    Thread dataUpdateThread = new Thread(new Runnable() {
        @Override
        public void run() {
            while (true){
                if(dataUpdated){
                    // Update GraphPannel
                    ((GraphPannel)mainForm.graphPanel).updateGraph(MainModel.this);
                    if(data!=null && data.length()>0 && ((GraphPannel)mainForm.graphPanel).isDrawing==false){

                        DataElement lastElement = data.dataList.get(data.dataList.size()-2);
                        mainForm.setCurrent_v(lastElement.v);
                        mainForm.setCurrent_ma(lastElement.ma);
                        mainForm.setMax_ma(data.ma_max);
                        mainForm.setMin_ma(data.ma_min);
                        mainForm.setMax_v(data.v_max);
                        mainForm.setMin_v(data.v_min);
                        mainForm.setDelta_ma(data.getDelta_ma());
                        mainForm.setDelta_v(data.getDelta_v());
                    }
                    //System.out.println(data.dataList.size());
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



}

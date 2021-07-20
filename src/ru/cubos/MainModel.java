package ru.cubos;

import ru.cubos.forms.MainForm;

import java.io.*;

public class MainModel {
    MainForm mainForm;
    File dataFile;

    public MainModel(){

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
                try {
                    finalFos.write((string+"\n").getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public void show() {
        mainForm.setVisible(true);
    }


}

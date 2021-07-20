package ru.cubos;

import ru.cubos.forms.MainForm;

public class MainModel {
    MainForm mainForm;
    public MainModel(){
        mainForm = new MainForm();
    }

    public void show() {
        mainForm.setVisible(true);
    }
}

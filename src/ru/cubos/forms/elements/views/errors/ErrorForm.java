package ru.cubos.forms.elements.images.views.errors;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ErrorForm extends JFrame{
    private     JPanel mainpanel;
    private JTextArea textArea1;
    private JButton closeBtn;
    static ErrorForm errorForm;

    public static void showError(String errorText){
        if(errorForm==null){
            errorForm = new ErrorForm(errorText);
        }else{
            errorForm.addErrorString(errorText);
            errorForm.setVisible(true);
        }
    }

    public ErrorForm(String errorText){

        setContentPane(mainpanel);
        setTitle("Ошибка");
        //setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        ////////////////////////////////////////////////////////////////////
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int sizeWidth = 800;
        int sizeHeight = 900;
        setSize(new Dimension(sizeWidth,sizeHeight));
        int locationX = (screenSize.width - sizeWidth) / 2;
        int locationY = (screenSize.height - sizeHeight) / 2;
        this.setBounds(locationX, locationY, sizeWidth, sizeHeight);
        ////////////////////////////////////////////////////////////////////

        //setSize(new Dimension(800,900));
        setVisible(true);
        setFormListeners();

        textArea1.setText(errorText);

    }

    protected void setFormListeners(){
        closeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ErrorForm.this.setVisible(false);
                errorForm = null;
            }
        });
    }

    public void addErrorString(String errorText){
        textArea1.setText(textArea1.getText() + "\n" + errorText);
    }


}

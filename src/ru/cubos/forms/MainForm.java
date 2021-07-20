package ru.cubos.forms;

import jssc.SerialPortException;
import jssc.SerialPortList;
import ru.cubos.connectors.SerialConnector;
import ru.cubos.forms.elements.views.GraphPannel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;


public class MainForm extends JFrame {
    private JPanel mainpanel;
    private JButton stopButton;
    private JButton pauseButton;
    private JButton startButton;
    private JButton resumeButton;
    private JButton resetButton;
    private JComboBox comboBoxComPorts;
    private JButton connectButton;
    private JButton updateButton;
    public JPanel graphPanel;
    private boolean isConnected = false;
    SerialConnector serialConnector;
    public MainForm() {

        serialConnector = new SerialConnector(){
            @Override
            public void onStringRead(String string){
                System.out.println("New string " + string);
            }
        };

        setContentPane(mainpanel);
        setTitle("RLS CV");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setSize(600,400);
        setExtendedState(MAXIMIZED_BOTH);
        setTitle("Новый замер");

        setButtonSize(stopButton);
        setButtonSize(pauseButton);
        setButtonSize(startButton);
        setButtonSize(resumeButton);
        setButtonSize(resetButton);

        addSerialConnectActions();
    }

    public void setButtonSize(JButton button){
        Dimension touchBtnSize = new Dimension(300, 40);
        button.setSize(touchBtnSize);
        button.setMinimumSize(touchBtnSize);
        button.setMaximumSize(touchBtnSize);
    }

    void addSerialConnectActions(){
        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                if(isConnected){
                    try {
                        serialConnector.disconnect();
                    } catch (SerialPortException e) {
                        e.printStackTrace();
                    }

                    onSerialPortDisonnect();
                }else{

                    serialConnector.setPort(comboBoxComPorts.getSelectedItem().toString());
                    try {
                        serialConnector.connect();
                        onSerialPortConnect();
                    } catch (SerialPortException e) {
                        e.printStackTrace();
                        onSerialPortDisonnect();
                    }

                }

                isConnected =! isConnected;
                updateConnectStatus();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                updateComboBoxCom_Ports();
            }
        });
        updateComboBoxCom_Ports();
    }

    void updateComboBoxCom_Ports(){
        String[] portNames = SerialPortList.getPortNames();
        comboBoxComPorts.removeAllItems();
        for(String portName: portNames){
            comboBoxComPorts.addItem(portName);
        }
    }

    void updateConnectStatus(){
        if(isConnected) connectButton.setText("Disconnect");
        else connectButton.setText("Connect");
    }

    void onSerialPortConnect(){
    }

    void onSerialPortDisonnect(){
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        graphPanel = new GraphPannel();
    }
}


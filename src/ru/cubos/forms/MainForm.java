package ru.cubos.forms;

import jssc.SerialPortException;
import jssc.SerialPortList;
import ru.cubos.connectors.SerialConnector;
import ru.cubos.forms.elements.views.GraphPannel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainForm extends JFrame {
    private JLabel labelSelectionAverageV;
    private JPanel mainpanel;
    private JButton stopButton;
    private JButton startButton;
    private JButton resetButton;
    private JComboBox comboBoxComPorts;
    private JButton connectButton;
    private JButton updateButton;
    public JPanel graphPanel;
    private JButton liveButton;
    private JLabel labelCurrentMa;
    private JLabel labelCurrentV;
    private JLabel labelMaxV;
    private JLabel labelMinV;
    private JLabel labelMinma;
    private JLabel labelMaxma;
    private JLabel labelDeltaMa;
    private JLabel labelDeltaV;
    private JLabel labelSelectionDuration;
    private JLabel labelSelectionMah;
    private JLabel labelSelectionAveragemA;
    private boolean isConnected = false;
    SerialConnector serialConnector;

    public void onGetDataString(String string){

    }

    public MainForm() {

        serialConnector = new SerialConnector(){
            @Override
            public void onStringRead(String string){
                onGetDataString(string);
            }
        };

        setContentPane(mainpanel);
        setTitle("RLS CV");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setSize(800,600);
        //setExtendedState(MAXIMIZED_BOTH);
        setTitle("Новый замер");

        setButtonSize(stopButton);
        setButtonSize(startButton);
        setButtonSize(resetButton);
        setButtonSize(liveButton);

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

    public void setCurrent_ma(float ma){
        labelCurrentMa.setText("" + ma);
    }

    public void setCurrent_v(float v){
        labelCurrentV.setText("" + v);
    }

    public void setMax_ma(float ma){
        labelMaxma.setText("" + ma);
    }

    public void setMin_ma(float ma){
        labelMinma.setText("" + ma);
    }

    public void setMax_v(float v){
        labelMaxV.setText("" + v);
    }

    public void setMin_v(float v){
        labelMinV.setText("" + v);
    }

    public void setDelta_ma(float ma){
        labelDeltaMa.setText("" + ma);
    }

    public void setDelta_v(float v){
        labelDeltaV.setText("" + v);
    }

    public void setSelectionDuration(float t){
        labelSelectionDuration.setText("" + t);
    }

    public void selectionMah(float mah){
        labelSelectionMah.setText("" + mah);
    }

    public void setSelectionAverageV(float v){
        labelSelectionAverageV.setText("" + v);
    }

    public void setSelectionAverageMa(float ma){
        labelSelectionAveragemA.setText("" + ma);
    }
}


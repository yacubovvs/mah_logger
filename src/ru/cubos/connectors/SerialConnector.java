package ru.cubos.connectors;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

import java.util.ArrayList;
import java.util.List;

public class SerialConnector {
    protected List<Byte> receivedByteList = new ArrayList();

    static SerialPort serialPort;

    public void setPort(String serialPortName){
        serialPort = new SerialPort(serialPortName);
    }

    public void connect() throws SerialPortException {
        try{
            serialPort.closePort();
        }catch (Exception e){

        }
        serialPort.openPort();
        serialPort.setParams(SerialPort.BAUDRATE_115200, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
        //serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN | SerialPort.FLOWCONTROL_RTSCTS_OUT);
        //SerialPort.MASK_RXCHAR
        serialPort.addEventListener(new PortReader(), SerialPort.MASK_RXCHAR);
    }

    private class PortReader implements SerialPortEventListener {

        synchronized public void serialEvent(SerialPortEvent event) {
            if(event.isRXCHAR() && event.getEventValue() > 0){
                try {
                    byte data[] = serialPort.readBytes(event.getEventValue());
                    for(int i=0; i<data.length; i++){
                        //System.out.print(data[i]);
                        receivedByteList.add(data[i]);
                    }
                }
                catch (SerialPortException ex) {
                    System.out.println(ex);
                }
            }
        }
    }

    public SerialConnector() {
        Thread decoder = new Thread(() -> {
            while(true) {
                String s = this.readString();
                //System.out.println("Read string " + s);
                onStringRead(s);
            }
        });
        decoder.start();
    }

    public void onStringRead(String string){
    }

    public int readInt() {
        try {
            int value = Integer.parseInt(this.readString());
            return value;
        } catch (Exception var2) {
            return -1;
        }
    }

    public long readLong() {
        try {
            String parseString = this.readString();
            long value = Long.parseLong(parseString);
            return value;
        } catch (Exception var4) {
            return -1L;
        }
    }

    public void disconnect() throws SerialPortException {
        serialPort.closePort();
    }

    public String readString() {
        String s = "";

        while(true) {
            while(true) {
                while(this.receivedByteList.size() <= 0) {
                    try {
                        Thread.sleep(10L);
                    } catch (InterruptedException var4) {
                        var4.printStackTrace();
                    }
                }

                Byte b = (Byte)this.receivedByteList.get(0);
                if (b == null) {
                    this.receivedByteList.remove(0);
                } else {
                    char c = (char)b.intValue();
                    this.receivedByteList.remove(0);
                    //if (c != ' ' && c != '\n') {
                    if (c != '\n') {
                        s = s + c;
                    } else {
                        s = s.trim();
                        if (s.length() > 0) {
                            return s;
                        }
                    }
                }
            }
        }
    }


}

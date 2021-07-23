package ru.cubos.forms.elements.views;

import ru.cubos.MainModel;
import ru.cubos.data.Data;
import ru.cubos.data.DataElement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;

public class GraphPannel extends JPanel {

    /*
    int maxValueA =  100;
    int minValueA =  50;
    int coordinate_steps_value =  10;
     */

    float selectionStart = (float) 0.13;
    float selectionEnd = (float) 0.43;
    float mousePosition = 0;

    public GraphPannel(){
        super();

        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                selectionEnd = (float)e.getX()/getWidth();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                mousePosition = (float)e.getX()/getWidth();
            }
        });

        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {

            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                selectionStart = (float)mouseEvent.getX()/getWidth();
                selectionEnd = selectionStart;
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                //selectionEnd = (float)mouseEvent.getX()/getWidth();
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {

            }
        });
    }

    private int getLineVerticalLinesinGraph(){
        return 10;
    }

    private int getCoordinatesSteps_inPx(){
        return getHeight()/getLineVerticalLinesinGraph();
    }

    Data data = new Data();

    public boolean isDrawing = false;
    @Override
    protected void paintComponent(Graphics g) {
        if(isDrawing) return;
        isDrawing = true;
        super.paintComponent(g);
        this.setBackground(new Color(255,255,255));



        // Selection
        g.setColor(new Color(133, 133, 133));

        int x1;
        int x2;
        if(selectionStart<selectionEnd){
            x1 = (int)(getWidth()*selectionStart);
            x2 = (int)(getWidth()*(selectionEnd-selectionStart));
        }else{
            x1 = (int)(getWidth()*selectionEnd);
            x2 = (int)(getWidth()*(selectionStart - selectionEnd));
        }
        g.fillRect(x1, 0, x2, getHeight());


        // Drawing coordinates net
        int step = getCoordinatesSteps_inPx();

        g.setColor(new Color(176, 176, 176));

        for(int i=1; i<=getLineVerticalLinesinGraph(); i++){
            g.drawLine(0,getHeight()*i/(getLineVerticalLinesinGraph()),getWidth(),getHeight()*i/(getLineVerticalLinesinGraph()));
        }

        // Draw data
        float resolution_X = 0;
        float resolution_MA_Y = 0;
        float resolution_V_Y = 0;

        if(data.length()!=0) resolution_X = (float)getWidth() / (float)(data.length()-2);
        if(data.getDelta_ma()!=0) resolution_MA_Y = getHeight()/data.getDelta_ma();
        if(data.getDelta_v()!=0) resolution_V_Y = getHeight()/data.getDelta_v();

        if(data.dataList.size()!=0) {
            DataElement dataElement = data.dataList.get(0);
            Point lastPoint_v = new Point((int) 0, (int) ( ((dataElement.v - data.get_v_min()) * resolution_V_Y) * 0.8 + 0.1*getHeight()) +1);
            Point lastPoint_ma = new Point((int) 0, (int) ( ((dataElement.ma - data.get_ma_min()) * resolution_MA_Y) * 0.8 + 0.1*getHeight()) +1);
            for (int i = 1; i < data.dataList.size(); i++) {
                dataElement = data.dataList.get(i);

                Point currentPoint_v = new Point((int) (i * resolution_X), (int) ( ((dataElement.v - data.get_v_min()) * resolution_V_Y) * 0.8 + 0.1*getHeight()) +1);
                Point currentPoint_ma = new Point((int) (i * resolution_X), (int) ( ((dataElement.ma - data.get_ma_min()) * resolution_MA_Y) * 0.8 + 0.1*getHeight()) +1);


                //setLiveWidth(g, 2);
                g.setColor(new Color(176, 21, 0));
                g.drawLine(currentPoint_ma.x, getHeight() - currentPoint_ma.y, lastPoint_ma.x, getHeight() - lastPoint_ma.y);
                g.setColor(new Color(0, 16, 176));
                g.drawLine(currentPoint_v.x, getHeight() - currentPoint_v.y, lastPoint_v.x, getHeight() - lastPoint_v.y);
                //g.drawLine(currentPoint_v.x, getHeight() - currentPoint_v.y, lastPoint_v.x, getHeight() - lastPoint_v.y);
                //setLiveWidth(g, 1);


                lastPoint_v = currentPoint_v;
                lastPoint_ma = currentPoint_ma;
            }
        }

        //setLineWidth(g, 3);

        for(int i=1; i<=getLineVerticalLinesinGraph()-1; i++){
            int height = i*step;
            g.setColor(new Color(255, 0, 0));
            float value_ma = (data.getDelta_ma() - ((float)data.getDelta_ma()*(i-1))/(getLineVerticalLinesinGraph()-2)) - data.getDelta_ma()/2;
            //g.drawString("" + ((int)value_ma*100)/100, 10, height - 2);
            g.drawString("" + ( (float)((int)(value_ma*100)) )/100, 10, height - 2);

            g.setColor(new Color(0, 0, 255));
            float value_v = (data.getDelta_v() - ((float)data.getDelta_v()*(i-1))/(getLineVerticalLinesinGraph()-2)) - data.getDelta_v()/2;
            g.drawString("" + ( (float)((int)(value_v*100)) )/100, 10, height + 18);
        }

        isDrawing = false;
        // drawing graph
    }

    void setLineWidth(Graphics g, int lineWidth){
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(lineWidth));
    }

    public void updateGraph(MainModel mainModel){
        data = mainModel.liveData;
        //this.setVisible(true);
        //mainModel.mainForm.validate();
        //mainModel.mainForm.repaint();
        this.validate();
        this.repaint();
    }

    class Point{
        public int x, y;
        public Point(int x, int y){ this.x = x; this.y = y;}
    }
}

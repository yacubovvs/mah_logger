package ru.cubos.forms.elements.views;

import ru.cubos.Main;
import ru.cubos.MainModel;
import ru.cubos.data.Data;
import ru.cubos.data.DataElement;

import javax.swing.*;
import java.awt.*;

public class GraphPannel extends JPanel {

    /*
    int maxValueA =  100;
    int minValueA =  50;
    int coordinate_steps_value =  10;
     */

    private int getCoordinatesSteps_inPx(){
        return getHeight()/10;
    }

    Data data = new Data();

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(new Color(255,255,255));


        //g.drawString("Contours: ", 20, 20);

        // Drawing coordinates net

        int step = getCoordinatesSteps_inPx();

        g.setColor(new Color(176, 176, 176));
        for(int i=0; i<getHeight(); i+=step){
            g.drawLine(0,getHeight()-i-1,getWidth(),getHeight()-i-1);
        }

        // Draw data
        float resolution_X = 0;
        float resolution_MA_Y = 0;
        float resolution_V_Y = 0;

        if(data.length()!=0) resolution_X = getWidth() / data.length();
        if(data.getDelta_ma()!=0) resolution_MA_Y = getHeight()/data.getDelta_ma();
        if(data.getDelta_v()!=0) resolution_V_Y = getHeight()/data.getDelta_v();

        Point lastPoint_v = new Point(0,0);
        Point lastPoint_ma = new Point(0,0);
        int position = 0;
        for(DataElement dataElement: data.dataList){

            Point currentPoint_v = new Point((int) (position*resolution_X), (int) ((dataElement.v - data.v_min)*resolution_V_Y));
            Point currentPoint_ma = new Point((int) (position*resolution_X), (int) ((dataElement.ma - data.ma_min)*resolution_MA_Y));

            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(new BasicStroke(2));
            g.setColor(new Color(176, 21, 0));
            g.drawLine(currentPoint_ma.x, getHeight() - currentPoint_ma.y, lastPoint_ma.x, getHeight() - lastPoint_ma.y);
            g.setColor(new Color(0, 16, 176));
            g.drawLine(currentPoint_v.x, getHeight() - currentPoint_v.y, lastPoint_v.x, getHeight() - lastPoint_v.y);
            g2d.setStroke(new BasicStroke(1));

            lastPoint_v = currentPoint_v;
            lastPoint_ma = currentPoint_ma;
            position++;
        }

        // drawing graph
    }

    public void updateGraph(MainModel mainModel){
        data = mainModel.data;
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

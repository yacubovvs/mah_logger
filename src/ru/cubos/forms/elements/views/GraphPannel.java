package ru.cubos.forms.elements.views;

import javax.swing.*;
import java.awt.*;

public class GraphPannel extends JPanel {

    int maxValueA =  100;
    int minValueA =  50;
    int coordinate_steps_value =  10;

    private int getCoordinatesSteps_inValue(){
        return (maxValueA - minValueA)/(coordinate_steps_value-1);
    }

    private int getCoordinatesSteps_inPx(){
        return getHeight()/(coordinate_steps_value-1);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(new Color(255,255,255));


        //g.drawString("Contours: ", 20, 20);
        //g.drawLine(0,0,100,100);
        //g.drawLine(100,0,0,100);

        // drawing coordinate net
        int step = getCoordinatesSteps_inPx();

        for(int i=0; i<getHeight(); i+=step){
            g.drawLine(0,getHeight()-i-1,getWidth(),getHeight()-i-1);
        }
    }
}

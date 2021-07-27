package ru.cubos.forms.elements.views;

import ru.cubos.MainModel;
import ru.cubos.data.Data;
import ru.cubos.data.DataElement;
import ru.cubos.data.SelectionDataStructure;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;

public class ScrollPannel extends JPanel {

    float scrollPosition = (float) 0.5;
    float scrollerSize = (float) 0.06;

    public ScrollPannel(){
        super();


        Dimension touchBtnSize = new Dimension(-1, 17);
        this.setSize(touchBtnSize);
        this.setMinimumSize(touchBtnSize);
        this.setMaximumSize(touchBtnSize);

        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
            }

            @Override
            public void mouseMoved(MouseEvent e) {
            }
        });

        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {

            }
        });
    }

    Boolean isDrawing = false;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(isDrawing) return;
        isDrawing = true;

        this.setBackground(new Color(195, 195, 195));

        g.setColor(new Color(128,128,128));

        g.fillRect((int)((float)getWidth()*scrollPosition - (float)getWidth()*scrollerSize/2.0), 0, (int)((float)getWidth()*scrollerSize/2.0), getHeight());

        isDrawing = false;
        /*
        if(isDrawing) return;
        isDrawing = true;
        super.paintComponent(g);
        this.setBackground(new Color(255,255,255));

        // Selection
        g.setColor(new Color(135, 227, 196));

        if(selectionStart!=selectionEnd && data.length()!=0) {
            int x1;
            int x2;
            int x1_element;
            int x2_element;
            if (selectionStart < selectionEnd) {
                x1 = (int) (getWidth() * selectionStart);
                x2 = (int) (getWidth() * (selectionEnd - selectionStart));
            } else {
                x1 = (int) (getWidth() * selectionEnd);
                x2 = (int) (getWidth() * (selectionStart - selectionEnd));
            }
            g.fillRect(x1, 0, x2, getHeight());

            x1_element = (int) (data.dataList.size()*selectionStart);
            x2_element = (int) (data.dataList.size()*selectionEnd);

            SelectionDataStructure selectionDataStructure = new SelectionDataStructure();
            float v_summ = 0;
            float ma_summ = 0;
            for(int i=x1_element; i<=x2_element; i++){
                if(i>data.length()-1) continue;
                selectionDataStructure.selectionDuration++;
                //System.out.println(i);
                DataElement selectionElement = data.dataList.get(i);
                v_summ += selectionElement.v;
                ma_summ += selectionElement.ma;
            }

            selectionDataStructure.average_ma = ma_summ/selectionDataStructure.selectionDuration;
            selectionDataStructure.average_v = v_summ/selectionDataStructure.selectionDuration;
            onSelectionDataGot(selectionDataStructure);
        }else{
            SelectionDataStructure selectionDataStructure = new SelectionDataStructure();
            onSelectionDataGot(selectionDataStructure);
        }

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

        DataElement mouseSelected = null;
        int mouseSelected_i=0;
        if(mouseIsOnPannel && data.dataList.size()>1){
            mouseSelected = data.dataList.get((int) ((float)data.length()* mousePosition_X));
        }

        if(data.dataList.size()!=0) {
            DataElement dataElement = data.dataList.get(0);
            Point lastPoint_v = new Point((int) 0, (int) ( ((dataElement.v - data.get_v_min()) * resolution_V_Y) * 0.8 + 0.1*getHeight()) +1);
            Point lastPoint_ma = new Point((int) 0, (int) ( ((dataElement.ma - data.get_ma_min()) * resolution_MA_Y) * 0.8 + 0.1*getHeight()) +1);
            for (int i = 1; i < data.dataList.size(); i++) {
                dataElement = data.dataList.get(i);

                Point currentPoint_v = new Point((int) (i * resolution_X), (int) ( ((dataElement.v - data.get_v_min()) * resolution_V_Y) * 0.8 + 0.1*getHeight()) +1);
                Point currentPoint_ma = new Point((int) (i * resolution_X), (int) ( ((dataElement.ma - data.get_ma_min()) * resolution_MA_Y) * 0.8 + 0.1*getHeight()) +1);

                //setLiveWidth(g, 2);
                if(mA_isVisible) {
                    g.setColor(new Color(176, 21, 0));
                    g.drawLine(currentPoint_ma.x, getHeight() - currentPoint_ma.y, lastPoint_ma.x, getHeight() - lastPoint_ma.y);
                }
                if(V_isVisible) {
                    g.setColor(new Color(0, 16, 176));
                    g.drawLine(currentPoint_v.x, getHeight() - currentPoint_v.y, lastPoint_v.x, getHeight() - lastPoint_v.y);
                }
                //g.drawLine(currentPoint_v.x, getHeight() - currentPoint_v.y, lastPoint_v.x, getHeight() - lastPoint_v.y);
                //setLiveWidth(g, 1);


                lastPoint_v = currentPoint_v;
                lastPoint_ma = currentPoint_ma;

                if(mouseIsOnPannel) {
                    if (dataElement == mouseSelected){
                        mouseSelected_i = i;
                    }
                }
            }
        }

        // Drawing mouse selection point
        if(mouseIsOnPannel && mouseSelected_i!=0) {
            //mouseSelected
            //mouseSelected_i;
            //System.out.println("drawing mouse selection point " + mouseSelected_i);

            g.setColor(new Color(70, 70, 70));
            final int rect_width = 87;
            final int rect_height = 18;
            final int rect_margin = 5;
            final int rects_margins = 5;
            g.fillRect(getWidth()-rect_width - rect_margin, rect_margin, rect_width - rect_margin, rect_margin + rect_height);
            g.fillRect(getWidth()-rect_width - rect_margin - rects_margins - rect_width, rect_margin, rect_width - rect_margin, rect_margin + rect_height);

            g.setColor(new Color(255, 255, 255));
            g.drawString("" + mouseSelected.ma + " mA", getWidth()-rect_width - rect_margin+5, rect_margin + rect_height/2 + 7);
            g.drawString("" + mouseSelected.v + " V", getWidth()-rect_width - rect_margin - rects_margins - rect_width+5, rect_margin + rect_height/2 + 7);
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
            g.drawString("" + ( (float)((int)(value_v*100)) )/100, 10, height + 16);
        }

        isDrawing = false;
        // drawing graph*/
    }


}

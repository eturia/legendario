package Parkeersimulator.view;

import Parkeersimulator.controller.*;
import Parkeersimulator.model.Calculate;

import javax.swing.*;
import java.awt.*;

public class PieView extends AbstractView {

    CarController carController;
    private Calculate amountAdHoc;
    private Calculate amountPassHolder;
    private int openSpots;

    public PieView() {
        carController = (CarController) super.registerController.getObjectInstance("CarController");
        setLayout(new BorderLayout());

        setSize(250,250);
        setPreferredSize(new Dimension(250,250));


    }

    public void paintComponent(Graphics g) {
        Calculate adhoc = carController.getAdhoc();
        Calculate passHolder = carController.getPass();
        int amountAdHoc = adhoc.getCount();
        int amountPassHolder = passHolder.getCount();
        openSpots = carController.getNumberOfOpenSpots();


        g.setColor(Color.green);
        g.fillArc(10, 10, 180, 180, 0, 360);
        g.setColor(Color.blue);
        g.fillArc(10, 10, 180, 180, amountAdHoc, amountPassHolder);
        g.setColor(Color.red);
        g.fillArc(10, 10, 180, 180, 0, amountAdHoc);
    }

    public void updateView()
    {
        repaint();
    }

    public void setVisibility(boolean visibility)
    {
        setVisible(visibility);
    }
}

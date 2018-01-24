package Parkeersimulator.view;

import Parkeersimulator.controller.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;

public class ControllerView extends AbstractView{

    SimulatorController simulatorController;
    private Timer simulateTimer;
    private JButton start, stop;


    public ControllerView(){
        this.simulatorController = (SimulatorController) super.registerController.getObjectInstance("SimulatorController");

        startButton();
        stopButton();
        add(start);
        add(stop);
        setLayout(new GridLayout(1,2));
        setSize(60, 20);
        setVisible(true);
    }

    private void startButton() {
        start = new JButton("Start");


        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                simulatorController.startRunning();
                if(simulateTimer != null) {
                    simulateTimer.restart();
                }
                else{
                    simulateTimer = new Timer(15, new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            if(simulatorController.isActive())
                                simulatorController.tick();
                        }
                    });
                    simulateTimer.setRepeats(true);
                    simulateTimer.start();}
            }
        });
    }

    private void stopButton(){
        stop = new JButton("Stop");


        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(simulatorController.isActive()) {
                    simulatorController.stoprunning();
                }
            }
        });
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


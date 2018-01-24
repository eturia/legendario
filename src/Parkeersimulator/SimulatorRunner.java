package Parkeersimulator;


import Parkeersimulator.controller.*;
import Parkeersimulator.view.*;

import javax.swing.*;
import java.awt.*;

public class SimulatorRunner extends JFrame{
    private SimulatorController simulatorController;
    private SimulatorView simulatorView;

    public SimulatorRunner() {
        RegisterController reg = RegisterController.getInstance();
        reg.addObjectReference(new CarController(3,6,30));
        reg.addObjectReference(new QueueController());
        simulatorController = new SimulatorController();
        reg.addObjectReference(simulatorController);

        simulatorView = new SimulatorView();

        setTitle("Parkeergarage El Legendarios");
        setLayout(new BorderLayout());
        getContentPane().add(simulatorView, BorderLayout.CENTER);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        AbstractView.notifyViews();
    }

    public static void main(String[] args){
        new SimulatorRunner();
    }
}



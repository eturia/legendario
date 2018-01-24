package Parkeersimulator;

import Parkeersimulator.controller.*;
import Parkeersimulator.view.*;

import javax.swing.*;
import java.awt.*;

public class SimulatorRunner extends JFrame {

    private SimulatorController simulatorController;
    private SimulatorView simulatorView;
    private PieView pieView;
    private ButtonView button;

    public SimulatorRunner() {

        ReferanceController reg = ReferanceController.getInstance();
        reg.addObjectReference(new CarController(3, 6, 30));
        reg.addObjectReference(new TimeController());
        reg.addObjectReference(new QueueController());

        simulatorController = new SimulatorController();
        reg.addObjectReference(simulatorController);
        simulatorView = new SimulatorView();
        button = new ButtonView();
        pieView = new PieView();

        setTitle("Parkeergarage El Legendarios");
        setLayout(new BorderLayout());

        getContentPane().add(simulatorView, BorderLayout.CENTER);
        getContentPane().add(pieView, BorderLayout.EAST);
        getContentPane().add(button, BorderLayout.SOUTH);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);
        setResizable(false);
        AbstractView.notifyViews();
    }

    public static void main(String[] args) {
        new SimulatorRunner();
    }
}

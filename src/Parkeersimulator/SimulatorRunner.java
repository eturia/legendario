package Parkeersimulator;

import Parkeersimulator.controller.SimulatorController;
import Parkeersimulator.model.Model;
import Parkeersimulator.controller.Controller;
import Parkeersimulator.model.ParkingModel;
import Parkeersimulator.view.ParkGarageView;
import Parkeersimulator.view.View;

import javax.swing.*;
import java.awt.*;

public class SimulatorRunner extends JFrame {

    public SimulatorRunner(){
        setTitle("Parkeergarage El Legendarios");

        ParkingModel model=new ParkingModel();
        Controller controller= new SimulatorController(model);
        View view=new ParkGarageView(controller);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(view);
        pack();
        setVisible(true);

        model.registerView(view);
        model.updateViews();


    }
    public static void main(String[] args) {
        new SimulatorRunner();
    }
}



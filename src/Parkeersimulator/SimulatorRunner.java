package Parkeersimulator;

import Parkeersimulator.controller.SimulatorController;
import Parkeersimulator.model.Model;
import Parkeersimulator.controller.Controller;
import Parkeersimulator.model.ParkingModel;
import Parkeersimulator.view.*;

import javax.swing.*;
import java.awt.*;

/**
 * Main class in the application.
 */

public class SimulatorRunner extends JFrame {

    public SimulatorRunner(){
        setTitle("Parkeergarage El Legendarios");
        setLayout(new BorderLayout());

        //Creates a new model and controller
        ParkingModel model=new ParkingModel();
        Controller controller= new SimulatorController(model);

        //Creating new views and adding them
        View view=new ParkGarageView(controller);
        View view2=new StatisticView();
        View view3=new TimeView();
        View view4 = new PieChartView();

        getContentPane().add(view, BorderLayout.CENTER);
        getContentPane().add(view2, BorderLayout.SOUTH);
        getContentPane().add(view3, BorderLayout.NORTH);
        getContentPane().add(view4, BorderLayout.EAST);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);

        //Registering views to the model and update them
        model.registerView(view);
        model.registerView(view2);
        model.registerView(view3);
        model.registerView(view4);

        model.updateViews();

    }
    public static void main(String[] args) {
        new SimulatorRunner();
    }
}



package Parkeersimulator;

import Parkeersimulator.controller.SimulatorController;
import Parkeersimulator.model.Model;
import Parkeersimulator.controller.Controller;
import Parkeersimulator.model.ParkingModel;
import Parkeersimulator.view.ParkGarageView;
import Parkeersimulator.view.StatisticView;
import Parkeersimulator.view.View;

import javax.swing.*;
import java.awt.*;

public class SimulatorRunner extends JFrame {

    public SimulatorRunner(){
        setTitle("Parkeergarage El Legendarios");

        JPanel root=new JPanel();
        root.setLayout(new GridBagLayout());

        ParkingModel model=new ParkingModel();
        Controller controller= new SimulatorController(model);
        View view=new ParkGarageView(controller);
        View view2=new StatisticView();


        root.add(view);
        root.add(view2);
        add(root);
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        model.registerView(view);
        model.registerView(view2);
        model.updateViews();

    }
    public static void main(String[] args) {
        new SimulatorRunner();
    }
}



package Parkeersimulator.main;

import Parkeersimulator.model.SimulatorModel;
import Parkeersimulator.controller.SimulatorController;
import Parkeersimulator.view.SimulatorView;


public class Simulator {
    private SimulatorModel model;
    private SimulatorController controller;


    public Simulator(){
        model = new SimulatorModel(controller);
        controller = new SimulatorController();



    }
}

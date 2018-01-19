package Parkeersimulator.main;

import Parkeersimulator.model.SimulatorModel;
import Parkeersimulator.controller.SimulatorController;

public class Simulator {
    private SimulatorModel model;
    private SimulatorController controller;

    public Simulator(){
        model = new SimulatorModel();
    }
}

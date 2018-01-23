package Parkeersimulator.controller;

import Parkeersimulator.model.SimulatorModel;
import Parkeersimulator.view.SimulatorView;
import Parkeersimulator.model.Car;
import Parkeersimulator.model.Location;




public class SimulatorController {
    private SimulatorModel model;
    private SimulatorView view;



    public void setView(SimulatorView view){
        this.view = view;
    }

    public void setModel(SimulatorModel model){
        this.model = model;
    }

    public void startSimulation(int duration){
        model.setSimulationLength(duration);
        model.run();
    }
}

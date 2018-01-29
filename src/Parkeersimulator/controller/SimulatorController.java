package Parkeersimulator.controller;

import Parkeersimulator.model.ParkingModel;

import java.awt.event.ActionEvent;

public class SimulatorController extends Controller<ParkingModel> {

    public SimulatorController(ParkingModel model){
        super(model);
    }

    @Override
    protected boolean eventHandler(String naam, ActionEvent e) {
        switch(naam) {
            case "knop_start":
                model.run();
                return true;

        }

        return false;
    }
}

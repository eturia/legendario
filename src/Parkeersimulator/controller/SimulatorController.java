package Parkeersimulator.controller;

import Parkeersimulator.model.ParkingModel;
import Parkeersimulator.view.ParkGarageView;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class SimulatorController extends Controller<ParkingModel> {

    public SimulatorController(ParkingModel model){
        super(model);
    }

    @Override
    protected boolean eventHandler(String naam, ActionEvent e) {
        switch(naam) {
            case "knop_start":
                model.start();
                return true;
            case "knop_stop":
                model.setRunning(false);
                return true;
            case "knop_reset":
                model.reset();
                return true;
        }

        return false;
    }
}

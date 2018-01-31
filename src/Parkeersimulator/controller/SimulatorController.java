package Parkeersimulator.controller;

import Parkeersimulator.model.ParkingModel;
import Parkeersimulator.view.ParkGarageView;
import Parkeersimulator.view.*;
import Parkeersimulator.model.*;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;

public class SimulatorController extends Controller<ParkingModel> {

    private Garage garage;

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



        }

        return false;
    }


}

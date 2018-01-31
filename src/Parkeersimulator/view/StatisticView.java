package Parkeersimulator.view;

import Parkeersimulator.model.Model;
import Parkeersimulator.model.ParkingModel;

import javax.swing.*;
import java.awt.*;

/**
 * This class creates view objects for certain statistics and updates them
 */

public class StatisticView extends View {

    private JLabel freeSpotsLabel, totalCarsLabel, adHocLabel, parkingPassLabel, carQueueLabel;
    private JTextField freeSpots, totalCars, adHoc, parkingPass, carQueue;

    /**
     * Create labels and text fields
     */

    public StatisticView (){
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());


        // Free spots label
        freeSpotsLabel = new JLabel("Vrije plekken");
        add(freeSpotsLabel);

        // Free spots textfield
        freeSpots = new JTextField(3);
        freeSpots.setText("0");
        freeSpots.setEditable(false);
        freeSpots.setBackground(Color.WHITE);
        add(freeSpots);

        //Total label
        totalCarsLabel = new JLabel("Totaal auto's");
        add(totalCarsLabel);

        //Total textfield
        totalCars = new JTextField(3);
        totalCars.setText("0");
        totalCars.setEditable(false);
        totalCars.setBackground(Color.WHITE);
        add(totalCars);

        //AdHoc label
        adHocLabel = new JLabel("AdHoc auto's");
        add(adHocLabel);

        //AdHoc textfield
        adHoc = new JTextField(3);
        adHoc.setText("0");
        adHoc.setEditable(false);
        adHoc.setBackground(Color.WHITE);
        add(adHoc);

        //ParkingPass label
        parkingPassLabel = new JLabel("ParkingPass");
        add(parkingPassLabel);

        //ParkingPass textfield
        parkingPass = new JTextField(3);
        parkingPass.setText("0");
        parkingPass.setEditable(false);
        parkingPass.setBackground(Color.WHITE);
        add(parkingPass);

    }

    /**
     * Updates the view
     * @param model
     */

    public void update(Model model) {
        ParkingModel parkingModel = (ParkingModel)model;

        freeSpots.setText("" + ((ParkingModel) model).getGarage().getNumberOfOpenSpots());
        totalCars.setText("" + ((ParkingModel) model).getGarage().getTotalCars());
        adHoc.setText("" + ((ParkingModel) model).getGarage().getAdhoc().getCount());
        parkingPass.setText("" + ((ParkingModel) model).getGarage().getPass().getCount());

    }


}
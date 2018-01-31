package Parkeersimulator.view;

import Parkeersimulator.model.*;

import javax.swing.*;
import java.awt.*;


public class StatisticView extends View {

    private JLabel freeSpotsLabel, totalCarsLabel, adHocLabel, parkingPassLabel, carQueueLabel;
    private JTextField freeSpots, totalCars, adHoc, parkingPass, carQueue;


    public StatisticView (){
        setLayout(new GridLayout(0, 2, 0, 0));

        JPanel statisticPanel = new JPanel();

        // Vrije plekken label
        freeSpotsLabel = new JLabel("Vrije plekken");
        add(freeSpotsLabel);

        //Totaal label
        totalCarsLabel = new JLabel("Totaal auto's");
        add(totalCarsLabel);


        //Adhoc label
        adHocLabel = new JLabel("AdHoc auto's");
        add(adHocLabel);


        //ParkingPass label
        parkingPassLabel = new JLabel("ParkingPass");
        add(parkingPassLabel);


        //Carqueue label
        carQueueLabel = new JLabel("Auto's in queue");
        add(carQueueLabel);

    }

    public void update(Model model) {
        ParkingModel parkingModel = (ParkingModel)model;

        int freeSpots = parkingModel.getGarage().getNumberOfOpenSpots();
        int totalCars = parkingModel.getGarage().getTotalCars();
        int adHoc = parkingModel.getGarage().getAdhoc().getCount();
        int parkingPass = parkingModel.getGarage().getPass().getCount();

        freeSpotsLabel.setText("Vrije plekken: "+ freeSpots);
        totalCarsLabel.setText("Totaal auto's: "+totalCars);
        adHocLabel.setText("Totaal auto's die betaalt hebben: "+adHoc);
        parkingPassLabel.setText("Totaal auto's met een abbonement: " +parkingPass);
    }
}
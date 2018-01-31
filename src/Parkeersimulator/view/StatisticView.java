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

        // Vrije plekken text field
        freeSpots = new JTextField();
        freeSpots.setText("0");
        freeSpots.setEditable(false);
        freeSpots.setBackground(Color.WHITE);
        add(freeSpots);

        //Totaal label
        totalCarsLabel = new JLabel("Totaal auto's");
        add(totalCarsLabel);

        //Totaal textfield
        totalCars = new JTextField();
        totalCars.setText("0");
        totalCars.setEditable(false);
        totalCars.setBackground(Color.WHITE);
        add(totalCars);

        //Adhoc label
        adHocLabel = new JLabel("AdHoc auto's");
        add(adHocLabel);

        //Adhoc textfield
        adHoc = new JTextField();
        adHoc.setText("0");
        adHoc.setEditable(false);
        adHoc.setBackground(Color.WHITE);
        add(adHoc);

        //ParkingPass label
        parkingPassLabel = new JLabel("ParkingPass");
        add(parkingPassLabel);

        //ParkingPass textfield
        parkingPass = new JTextField();
        parkingPass.setText("0");
        parkingPass.setEditable(false);
        parkingPass.setBackground(Color.WHITE);
        add(parkingPass);

        //Carqueue label
        carQueueLabel = new JLabel("Auto's in queue");
        add(carQueueLabel);

        //Carqueue textfield
        carQueue = new JTextField();
        carQueue.setText("0");
        carQueue.setEditable(false);
        carQueue.setBackground(Color.WHITE);
        add(carQueue);
    }

    public void update(Model model) {
        ParkingModel parkingModel = (ParkingModel)model;

        freeSpots.setText("" + parkingModel.getGarage().getNumberOfOpenSpots());
        totalCars.setText("" + parkingModel.getGarage().getTotalCars());
        adHoc.setText("" + parkingModel.getGarage().getAdhoc().getCount());
        parkingPass.setText("" + parkingModel.getGarage().getPass().getCount());

    }
}
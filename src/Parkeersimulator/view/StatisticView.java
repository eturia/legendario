package Parkeersimulator.view;

import Parkeersimulator.model.Model;
import Parkeersimulator.model.ParkingModel;

import javax.swing.*;

import java.awt.*;


public class StatisticView extends View {

    private JLabel freeSpotsLabel, totalCarsLabel, adHocLabel, parkingPassLabel, carQueueLabel;
    private JTextField freeSpots, totalCars, adHoc, parkingPass, carQueue;


    public StatisticView (){
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());


        // Vrije plekken label
        freeSpotsLabel = new JLabel("Vrije plekken");
        add(freeSpotsLabel);

        // Vrije plekken text field
        freeSpots = new JTextField(3);
        freeSpots.setText("0");
        freeSpots.setEditable(false);
        freeSpots.setBackground(Color.WHITE);
        add(freeSpots);

        //Totaal label
        totalCarsLabel = new JLabel("Totaal auto's");
        add(totalCarsLabel);

        //Totaal textfield
        totalCars = new JTextField(3);
        totalCars.setText("0");
        totalCars.setEditable(false);
        totalCars.setBackground(Color.WHITE);
        add(totalCars);

        //Adhoc label
        adHocLabel = new JLabel("AdHoc auto's");
        add(adHocLabel);

        //Adhoc textfield
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

        //Carqueue label
        carQueueLabel = new JLabel("Auto's in queue");
        add(carQueueLabel);

        //Carqueue textfield
        carQueue = new JTextField(3);
        carQueue.setText("0");
        carQueue.setEditable(false);
        carQueue.setBackground(Color.WHITE);
        add(carQueue);

    }

    public void update(Model model) {
        ParkingModel parkingModel = (ParkingModel)model;

        freeSpots.setText("" + ((ParkingModel) model).getGarage().getNumberOfOpenSpots());
        totalCars.setText("" + ((ParkingModel) model).getGarage().getTotalCars());
        adHoc.setText("" + ((ParkingModel) model).getGarage().getAdhoc().getCount());
        parkingPass.setText("" + ((ParkingModel) model).getGarage().getPass().getCount());

    }


}
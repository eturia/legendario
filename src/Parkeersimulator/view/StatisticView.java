package Parkeersimulator.view;
import Parkeersimulator.controller.Controller;
import Parkeersimulator.controller.SimulatorController;
import Parkeersimulator.model.CarQueue;
import Parkeersimulator.model.Model;
import Parkeersimulator.model.ParkingModel;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.util.HashMap;

public class StatisticView extends View {
    private SimulatorController simulatorController;

    private JLabel freeSpotsLabel, totalCarsLabel, adHocLabel, parkingPassLabel, carQueueLabel;
    private JTextField freeSpots, totalCars, adHoc, parkingPass, carQueue;


    public StatisticView (){
        setLayout(new GridLayout(0, 2, 0, 0));

        JPanel statisticPanel = new JPanel();
        add(statisticPanel);
        statisticPanel.setLayout(new BorderLayout(0, 0));

        // Vrije plekken label
        freeSpotsLabel = new JLabel("Vrije plekken");
        add(freeSpotsLabel);

        // Vrije plekken text field
        freeSpots = new JTextField();
        freeSpots.setText("0");
        freeSpots.setEditable(false);
        freeSpots.setColumns(10);
        freeSpots.setBackground(Color.WHITE);
        add(freeSpots);

        //Totaal label
        totalCarsLabel = new JLabel("Totaal auto's");
        add(totalCarsLabel);

        //Totaal textfield
        totalCars = new JTextField();
        totalCars.setText("0");
        totalCars.setEditable(false);
        totalCars.setColumns(10);
        totalCars.setBackground(Color.WHITE);
        add(totalCars);

        //Adhoc label
        adHocLabel = new JLabel("AdHoc auto's");
        add(adHocLabel);

        //Adhoc textfield
        adHoc = new JTextField();
        adHoc.setText("0");
        adHoc.setEditable(false);
        adHoc.setColumns(10);
        adHoc.setBackground(Color.WHITE);
        add(adHoc);

        //ParkingPass label
        parkingPassLabel = new JLabel("ParkingPass");
        add(parkingPassLabel);

        //ParkingPass textfield
        parkingPass = new JTextField();
        parkingPass.setText("0");
        parkingPass.setEditable(false);
        parkingPass.setColumns(10);
        parkingPass.setBackground(Color.WHITE);
        add(parkingPass);

        //Carqueue label
        carQueueLabel = new JLabel("Auto's in queue");
        add(carQueueLabel);

        //Carqueue textfield
        carQueue = new JTextField();
        carQueue.setText("0");
        carQueue.setEditable(false);
        carQueue.setColumns(10);
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

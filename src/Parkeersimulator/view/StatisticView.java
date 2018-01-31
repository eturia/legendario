package Parkeersimulator.view;
import Parkeersimulator.controller.Controller;
import Parkeersimulator.controller.SimulatorController;
import Parkeersimulator.model.Model;
import Parkeersimulator.model.ParkingModel;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.util.HashMap;

public class StatisticView extends View {
    private SimulatorController simulatorController;

    private JLabel freeSpotsLabel, totalCarsLabel;
    private JTextField freeSpots, totalCars;


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

        //Totaal text field
        totalCars = new JTextField();
        totalCars.setText("0");
        totalCars.setEditable(false);
        totalCars.setColumns(10);
        totalCars.setBackground(Color.WHITE);
        add(totalCars);





    }

    public void update(Model model) {
        ParkingModel parkingModel = (ParkingModel)model;

        freeSpots.setText("" + ((ParkingModel) model).getGarage().getNumberOfOpenSpots());




    }


}

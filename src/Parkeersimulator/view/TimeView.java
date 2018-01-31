package Parkeersimulator.view;

import Parkeersimulator.model.*;

import javax.swing.*;
import java.awt.*;

/**
 * Shows the current time on the car park
 */

public class TimeView extends View {

    private JLabel month,week,day,time;

    public TimeView(){
        //Creating the date/time JLabels
        month = new JLabel();
        week = new JLabel();
        day = new JLabel();
        time = new JLabel();

        //Adding the date/time labels to the Time panel
        add(month);
        add(week);
        add(day);
        add(time);
    }

    /**
     * Updates the view
     * @param model
     */

    public void update(Model model) {
        ParkingModel parkingModel = (ParkingModel)model;

        //Updating the date/time with evert tick()
        day.setText("Day: " + parkingModel.getTime().getDay());
        week.setText("Week: " + parkingModel.getTime().getWeek());
        month.setText("Month: " + parkingModel.getTime().getMonth());
        time.setText("Hour: " + parkingModel.getTime().getTimeTable());
    }
}

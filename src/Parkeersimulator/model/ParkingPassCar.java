package Parkeersimulator.model;


import java.awt.*;

public class ParkingPassCar extends Car {
    private static final Color COLOR=Color.blue;

    public ParkingPassCar() {
        this.setMinutesLeft(super.generateParkingTime());
        this.setHasToPay(false);
    }

    public Color getColor(){
        return COLOR;
    }
}
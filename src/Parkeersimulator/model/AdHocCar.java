package Parkeersimulator.model;

import java.awt.*;

public class AdHocCar extends Car {

	private static final Color COLOR=Color.red;
	private int stayMinutes;

    public AdHocCar() {
        this.stayMinutes = super.generateParkingTime();
        this.setMinutesLeft(stayMinutes);
        this.setHasToPay(true);
    }

    public Color getColor(){
        return COLOR;
    }

    public boolean getIsPaying(){
        return super.getIsPaying();
    }
}
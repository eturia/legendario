package Parkeersimulator.model;

import Parkeersimulator.view.ParkGarageView;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class ParkingModel extends Model implements Runnable{

    public final Garage garage;
    public final CarQueue carQueue;

    private CarQueue entranceCarQueue;
    private CarQueue entrancePassQueue;
    private CarQueue paymentCarQueue;
    private CarQueue exitCarQueue;

    private static final int AD_HOC = 1;
    private static final int PASS = 2;


    private int tickPause = 100;

    private int day = 0;
    private int hour = 0;
    private int minute = 0;


    int weekDayArrivals= 100; // average number of arriving cars per hour
    int weekendArrivals = 200; // average number of arriving cars per hour
    int weekDayPassArrivals= 50; // average number of arriving cars per hour
    int weekendPassArrivals = 5; // average number of arriving cars per hour

    int enterSpeed = 3; // number of cars that can enter per minute
    int paymentSpeed = 7; // number of cars that can pay per minute
    int exitSpeed = 5; // number of cars that can leave per minute

    //Multithreading info
    private Thread thread;
    private String threadName = "model";
    private boolean running = true;


    public ParkingModel(){
        entranceCarQueue = new CarQueue();
        entrancePassQueue = new CarQueue();
        paymentCarQueue = new CarQueue();
        exitCarQueue = new CarQueue();
        garage = new Garage(3,6,30);
        carQueue = new CarQueue();
    }

    public Garage getGarage() {
        return garage;
    }

    public CarQueue getCarQueue() {
        return carQueue;
    }

    public void run() {
        int i = 0;

        while (i < 10000 && running) {
            tick();
            i++;
        }
    }

    public void start () {
        if (thread == null) {
            thread = new Thread (this, threadName);
            running = true;
            thread.start ();
        }
        else
        {
            thread = null;	// Delete the thread.
            start();	// Remake a new thread and start the simulation.
        }
    }

    private void tick() {
        advanceTime();
        handleExit();
        updateViews();
        // Pause.
        try {
            Thread.sleep(tickPause);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        handleEntrance();
        garage.tick();
    }

    private void advanceTime(){
        // Advance the time by one minute.
        minute++;
        while (minute > 59) {
            minute -= 60;
            hour++;
        }
        while (hour > 23) {
            hour -= 24;
            day++;
        }
        while (day > 6) {
            day -= 7;
        }

    }

    private void handleEntrance(){
        carsArriving();
        carsEntering(entrancePassQueue);
        carsEntering(entranceCarQueue);
    }

    private void handleExit(){
        carsReadyToLeave();
        carsPaying();
        carsLeaving();
    }

    private void carsArriving(){
        int numberOfCars=getNumberOfCars(weekDayArrivals, weekendArrivals);
        addArrivingCars(numberOfCars, 1);
        numberOfCars=getNumberOfCars(weekDayPassArrivals, weekendPassArrivals);
        addArrivingCars(numberOfCars, 2);
    }

    private void carsEntering(CarQueue queue) {
        int i = 0;
        while (queue.carsInQueue() > 0 &&
                garage.getNumberOfOpenSpots() > 0 &&
                i < enterSpeed) {
            Car car = queue.removeCar();
            if (car.getColor() == Color.blue) {
                Location freeLocation = garage.getFirstPassLocation();
                garage.setCarAt(freeLocation, car);
                garage.getPass().increment();
            } else {
                Location freeLocation = garage.getFirstPaidLocation();
                garage.setCarAt(freeLocation, car);
                garage.getAdhoc().increment();
            }
            i++;
        }
    }

    private void carsReadyToLeave(){
        // Add leaving cars to the payment queue.
        Car car = getGarage().getFirstLeavingCar();
        while (car!=null) {
            if (car.getHasToPay()){
                car.setIsPaying(true);
                paymentCarQueue.addCar(car);
            }
            else {
                carLeavesSpot(car);
            }
            car = garage.getFirstLeavingCar();
        }
    }

    private void carsPaying(){
        // Let cars pay.
        int i=0;
        while (paymentCarQueue.carsInQueue()>0 && i < paymentSpeed){
            Car car = paymentCarQueue.removeCar();
            // TODO Handle payment.
            carLeavesSpot(car);
            i++;
        }
    }

    private void carsLeaving(){
        // Let cars leave.
        int i=0;
        while (exitCarQueue.carsInQueue()>0 && i < exitSpeed){
            exitCarQueue.removeCar();
            i++;
        }
    }

    private int getNumberOfCars(int weekDay, int weekend){
        Random random = new Random();

        // Get the average number of cars that arrive per hour.
        int averageNumberOfCarsPerHour = day < 5
                ? weekDay
                : weekend;

        // Calculate the number of cars that arrive this minute.
        double standardDeviation = averageNumberOfCarsPerHour * 0.3;
        double numberOfCarsPerHour = averageNumberOfCarsPerHour + random.nextGaussian() * standardDeviation;
        return (int)Math.round(numberOfCarsPerHour / 60);
    }

    private void addArrivingCars(int numberOfCars, int type) {
        // Add the cars to the back of the queue.
        switch (type) {
            case AD_HOC:
                for (int i = 0; i < numberOfCars; i++) {
                    entranceCarQueue.addCar(new AdHocCar());
                }
                break;
            case PASS:
                for (int i = 0; i < numberOfCars; i++) {
                    if (garage.getPassHolder() < garage.getPassSpots()) {
                        entrancePassQueue.addCar(new ParkingPassCar());
                    }
                }
                break;
        }
    }

    private void carLeavesSpot(Car car){
        garage.removeCarAt(car.getLocation());
        exitCarQueue.addCar(car);
    }

    public void setRunning( boolean flag)
    {
        running = flag;
    }



}
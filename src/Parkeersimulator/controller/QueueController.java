package Parkeersimulator.controller;

import Parkeersimulator.model.*;

import java.awt.*;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class QueueController extends AbstractController {

    private SimulatorController simulatorController;
    private TimeController timeController;
    private CarController carController;
    private CarQueue entranceCarQueue;
    private CarQueue entrancePassQueue;
    private CarQueue paymentCarQueue;
    private CarQueue exitCarQueue;
    private int carsLeft;
    private int numberOfCarsInQueue;
    private int carsToday;
    private int leftCarsToday;

    int weekDayArrivals; // average number of arriving cars per hour
    int weekendArrivals; // average number of arriving cars per hour
    int weekDayPassArrivals; // average number of arriving cars per hour
    int weekendPassArrivals; // average number of arriving cars per hour

    int enterSpeed = 3; // number of cars that can enter per minute
    int paymentSpeed = 7; // number of cars that can pay per minute
    int exitSpeed = 5; // number of cars that can leave per minute

    private static final String AD_HOC = "1";
    private static final String PASS = "2";

    public QueueController() {

        this.simulatorController = (SimulatorController) super.registerController.getObjectInstance("SimulatorController");
        this.carController = (CarController) super.registerController.getObjectInstance("CarController");
        this.timeController = (TimeController) super.registerController.getObjectInstance("TimeController");

        entranceCarQueue = new CarQueue();
        entrancePassQueue = new CarQueue();
        paymentCarQueue = new CarQueue();
        exitCarQueue = new CarQueue();

        setArrivals();

        carsLeft = 0;
    }

    protected void handleEntrance() {
        carsArriving();
        setArrivals();
        carsLeavingQueue(entranceCarQueue);
        carsEntering(entrancePassQueue);
        carsEntering(entranceCarQueue);
    }

    protected void handleExit() {
        carsReadyToLeave();
        carsPaying();
        carsLeaving();
    }

    private void carsReadyToLeave() {
        // Add leaving cars to the payment queue.
        Car car = carController.getFirstLeavingCar();
        while (car != null) {
            if (car.getHasToPay()) {
                car.setIsPaying(true);
                paymentCarQueue.addCar(car);
            } else {
                carLeavesSpot(car);
            }
            car = carController.getFirstLeavingCar();
        }
    }

    private int getNumberOfCars(int weekDay, int weekend) {
        Random random = new Random();

        // Get the average number of cars that arrive per hour.
        int averageNumberOfCarsPerHour = timeController.getDay() < 5
                ? weekDay
                : weekend;

        // Calculate the number of cars that arrive this minute.
        double standardDeviation = averageNumberOfCarsPerHour * 0.3;
        double numberOfCarsPerHour = averageNumberOfCarsPerHour + random.nextGaussian() * standardDeviation;
        return (int) Math.round(numberOfCarsPerHour / 60);
    }

    private void carsPaying() {
        int i = 0;
        while (paymentCarQueue.carsInQueue() > 0 && i < paymentSpeed) {
            Car car = paymentCarQueue.removeCar();
            // TODO Handle payment.
            carLeavesSpot(car);
            i++;
        }
    }


    private void carsLeaving() {
        int i = 0;
        while (exitCarQueue.carsInQueue() > 0 && i < exitSpeed) {
            exitCarQueue.removeCar();
            i++;
            leftCarsToday++;
        }
    }

    private void carLeavesSpot(Car car) {
        carController.removeCarAt(car.getLocation());
        exitCarQueue.addCar(car);
    }

    private void addArrivingCars(int numberOfCars, String type) {
        // Add the cars to the back of the queue.
        switch (type) {
            case AD_HOC:
                for (int i = 0; i < numberOfCars; i++) {
                    entranceCarQueue.addCar(new AdHocCar());
                }
                break;
            case PASS:
                for (int i = 0; i < numberOfCars; i++) {
                    if (carController.getPassHolder() < carController.getPassSpots()) {
                        entrancePassQueue.addCar(new ParkingPassCar());
                    }
                }
                break;
        }
    }

    private void carsArriving() {
        int numberOfCars = getNumberOfCars(weekDayArrivals, weekendArrivals);
        addArrivingCars(numberOfCars, AD_HOC);

        numberOfCars = getNumberOfCars(weekDayPassArrivals, weekendPassArrivals);
        addArrivingCars(numberOfCars, PASS);
    }

    private void carsEntering(CarQueue queue) {
        int i = 0;
        while (queue.carsInQueue() > 0 &&
                carController.getNumberOfOpenSpots() > 0 &&
                i < enterSpeed) {
            Car car = queue.removeCar();
            if (car.getColor() == Color.blue) {
                Location freeLocation = carController.getFirstPassLocation();
                carController.setCarAt(freeLocation, car);
                carController.getPass().increment();
            } else {
                Location freeLocation = carController.getFirstPaidLocation();
                carController.setCarAt(freeLocation, car);
                carController.getAdhoc().increment();
            }
            i++;
            carsToday++;
        }
    }

    private void carsLeavingQueue(CarQueue queue) {
        numberOfCarsInQueue = queue.carsInQueue();
        int randomNum = ThreadLocalRandom.current().nextInt(1, 10 + 1);
        if (queue.carsInQueue() >= 20) {
            if (randomNum > 1) {
                carsLeft++;
                carLeavingQueue();
            }
        } else if (queue.carsInQueue() >= 15) {
            if (randomNum > 3) {
                carsLeft++;
                carLeavingQueue();
            }
        } else if (queue.carsInQueue() >= 10) {
            if (randomNum > 5) {
                carsLeft++;
                carLeavingQueue();
            }
        } else if (queue.carsInQueue() >= 5) {
            if (randomNum > 8) {
                carsLeft++;
                carLeavingQueue();
            }
        } else if (queue.carsInQueue() >= 3) {
            if (randomNum > 9) {
                carsLeft++;
                carLeavingQueue();
            }
        }
    }

    protected void setArrivals() {
        if (timeController.getHour() >= 22 && timeController.getHour() >= 5) {
            weekDayArrivals = 10; // average number of arriving cars per hour
            weekDayPassArrivals = 1; // average number of arriving cars per hour
            weekendArrivals = 15; // average number of arriving cars per hour
            weekendPassArrivals = 1; // average number of arriving cars per hour
        } else if (timeController.getHour() < 7 ||
                timeController.getHour() > 9 && timeController.getHour() < 12 ||
                timeController.getHour() > 14 && timeController.getHour() < 17 ||
                timeController.getHour() > 19 && timeController.getHour() < 22) {
            weekDayArrivals = 25; // average number of arriving cars per hour
            weekDayPassArrivals = 10; // average number of arriving cars per hour
            weekendArrivals = 100; // average number of arriving cars per hour
            weekendPassArrivals = 25; // average number of arriving cars per hour
        } else {
            weekDayArrivals = 100; // average number of arriving cars per hour
            weekDayPassArrivals = 50; // average number of arriving cars per hour
            weekendArrivals = 200; // average number of arriving cars per hour
            weekendPassArrivals = 30; // average number of arriving cars per hour
        }
    }

    private void carLeavingQueue() {
        entranceCarQueue.removeCar();
    }

}

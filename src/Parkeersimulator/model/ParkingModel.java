package Parkeersimulator.model;

import java.awt.*;
import java.util.Random;

public class ParkingModel extends Model implements Runnable{

    public Garage garage;
    private Time time;
    private CarQueue entranceCarQueue;
    private CarQueue entrancePassQueue;
    private CarQueue paymentCarQueue;
    private CarQueue exitCarQueue;

    private static final int AD_HOC = 1;
    private static final int PASS = 2;

    private int tickPause = 100;
    private int currentTick = 1;

    int weekDayArrivals;
    int weekendArrivals;
    int weekDayPassArrivals;
    int weekendPassArrivals;

    int enterSpeed = 3; // number of cars that can enter per minute
    int paymentSpeed = 7; // number of cars that can pay per minute
    int exitSpeed = 5; // number of cars that can leave per minute

    //Multithreading
    private Thread thread;
    private String threadName = "model";
    private boolean running = true;


    public ParkingModel(){
        entranceCarQueue = new CarQueue();
        entrancePassQueue = new CarQueue();
        paymentCarQueue = new CarQueue();
        exitCarQueue = new CarQueue();
        garage = new Garage(3,6,30);
        time = new Time();
    }

    public Garage getGarage() {
        return garage;
    }

    public int getCurrentTick()
    {
        return currentTick;
    }

    public Time getTime() {
        return time;
    }

    public void reset(){
        entranceCarQueue = new CarQueue();
        entrancePassQueue = new CarQueue();
        paymentCarQueue = new CarQueue();
        exitCarQueue = new CarQueue();
        garage = new Garage(3,6,30);
        time = new Time();

        currentTick = 1;

        getGarage().resetStats();
    }

    public void run() {
        int i = 0;

        while (i < 10000 && running) {
            tick();
            i++;
            currentTick++;
        }
    }

    public void start () {
        if (thread == null) {
            thread = new Thread (this, threadName);        //Creates a new thread
            running = true;                                       //Sets running to true
            thread.start ();                                      //Start the thread
        }
        else
        {
            thread = null;	                                      // Delete the thread.
            start();	                                          // Remake a new thread and start the simulation.
        }
    }

    /**
     * Generates the flow in time periods
     */
    protected void setArrivals() {
        if (time.getHour() >= 22 && time.getHour() >= 5) {
            weekDayArrivals = 10; // average number of arriving cars per hour
            weekDayPassArrivals = 1; // average number of arriving cars per hour
            weekendArrivals = 15; // average number of arriving cars per hour
            weekendPassArrivals = 1; // average number of arriving cars per hour
        } else if (time.getHour() < 7 ||
                time.getHour() > 9 && time.getHour() < 12 ||
                time.getHour() > 14 && time.getHour() < 17 ||
                time.getHour() > 19 && time.getHour() < 22) {
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

    private void tick() {
        time.advanceTime();
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

    private void handleEntrance(){
        carsArriving();
        setArrivals();
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
        int averageNumberOfCarsPerHour = time.getDay() < 5
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

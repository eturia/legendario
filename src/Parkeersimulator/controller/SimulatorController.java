package Parkeersimulator.controller;

import Parkeersimulator.view.AbstractView;
import Parkeersimulator.view.SimulatorView;

public class SimulatorController extends AbstractController{

    private SimulatorView simulatorView;
    private QueueController queueController;
    private CarController carController;


    private int day = 0;
    private int hour = 0;
    private int minute = 0;

    private int tickPause = 100;

    public SimulatorController() {
        this.queueController = (QueueController) super.registerController.getObjectInstance("QueueController");
        this.carController = (CarController) super.registerController.getObjectInstance("CarController");

        run();
    }

    public void run() {
        for (int i = 0; i < 10000; i++) {
            tick();
            AbstractView.notifyViews();
        }
    }

    private void tick() {
    	advanceTime();
        queueController.handleExit();
    	updateViews();
    	// Pause.
        try {
            Thread.sleep(tickPause);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        queueController.handleEntrance();
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
    
    private void updateViews(){
    	carController.tick();
        AbstractView.notifyViews();
    }

}

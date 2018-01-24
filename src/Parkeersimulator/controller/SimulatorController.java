package Parkeersimulator.controller;

import Parkeersimulator.view.AbstractView;


public class SimulatorController extends AbstractController {

    private TimeController timeController;
    private QueueController QueueController;
    private CarController carController;

    private boolean active;
    private int tickPause = 100;

    public SimulatorController( ) {
        super();
        carController = (CarController) super.registerController.getObjectInstance("CarController");
        timeController = (TimeController) super.registerController.getObjectInstance("TimeController");
        QueueController = (QueueController) super.registerController.getObjectInstance("QueueController");
    }

    public boolean isActive(){
        return active;
    }

    public void run() {
        active = true;
        while(active){
            tick();
            AbstractView.notifyViews();
        }
    }

    public void stoprunning() {
        this.active = false;
    }

    public void startRunning()
    {
        this.active = true;
    }

    public void tick() {
        try {
        timeController.advanceTime();
        QueueController.handleExit();
        updateViews();
            Thread.sleep(tickPause);
        } catch (Exception e) {
            e.printStackTrace();
        }
        QueueController.handleEntrance();
    }


    public void updateViews(){
        carController.tick();
        AbstractView.notifyViews();
    }
}
package Parkeersimulator.controller;

public class TimeController extends AbstractController{

    private int minute = 30;
    private int hour = 6;
    private int day = 1;


    public TimeController() {
    }

    public void advanceTime() {
        // Advance the time by 1 minutes.
        minute+=1;
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


    public int getDay() {
        return this.day;
    }

    protected int getHour() {
        return hour;
    }

    protected int getMinute(){
        return minute;
    }

}
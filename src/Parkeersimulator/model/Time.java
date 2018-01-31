package Parkeersimulator.model;

public class Time {

    //Day 1 08:30
    private int minute;
    private int hour;
    private int day;
    private int week;
    private int month;
    private int year;

    public Time() {
        minute = 30;
        hour = 8;
        day = 1;
        week = 1;
        month = 1;
        year = 2018;
    }

    /**
     *
     * @return return the value of minute
     */
    protected int getMinute(){
        return minute;
    }

    /**
     *
     * @return return the value of hour
     */
    protected int getHour() {
        return hour;
    }

    /**
     *
     * @return return the value of day
     */
    public int getDay() {
        return day;
    }

    /**
     *
     * @return return the value of week
     */
    public int getWeek() {
        return week;
    }

    /**
     *
     * @return return the value of month
     */
    public int getMonth() {
        return month;
    }

    /**
     *
     * @return return the value of year
     */
    public int getYear(){
        return year;
    }


    // Advances the time in the simulator
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
        while (day > 7) {
            day -= 7;
            week++;
        }
        while (week > 4){
            week -= 4;
            month++;
        }
        while (month > 12){
            month -= 12;
            year++;
        }
    }

    /**
     *
     * @return time, returns the time in a digital form.
     */
    public String getTimeTable() {
        String time;
        if (hour < 10 && minute < 10) {
            time = "0" + hour + ":0" + minute;
        } else if (hour < 10) {
            time = "0" + hour + ":" + minute;
        } else if (minute < 10) {
            time = hour + ":0" + minute;
        } else {
            time = hour + ":" + minute;
        }
        return time;
    }
}

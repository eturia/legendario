package Parkeersimulator.model;

public class Garage {

    private int numberOfFloors;
    private int numberOfRows;
    private int numberOfPlaces;
    private int numberOfOpenSpots;
    private Car[][][] cars;
    private int passSpots;
    private int passHolder;
    private Calculate adhoc;
    private Calculate pass;

    public Garage(int numberOfFloors, int numberOfRows, int numberOfPlaces){
        this.numberOfFloors = numberOfFloors;
        this.numberOfRows = numberOfRows;
        this.numberOfPlaces = numberOfPlaces;
        this.numberOfOpenSpots =numberOfFloors*numberOfRows*numberOfPlaces;
        cars = new Car[numberOfFloors][numberOfRows][numberOfPlaces];

        adhoc = new Calculate("adhoc");
        pass = new Calculate("pass");
        passSpots = 180;
        passHolder = 0;
    }

    public int getNumberOfFloors() {
        return numberOfFloors;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public int getNumberOfPlaces() {
        return numberOfPlaces;
    }

    public int getNumberOfOpenSpots(){
        return numberOfOpenSpots;
    }

    public Calculate getAdhoc(){
        return adhoc;
    }

    public Calculate getPass(){
        return pass;
    }

    protected int getPassHolder(){
        return passHolder;
    }

    public int getPassSpots() {return passSpots; }

    public Car[][][] getAllCars() { return cars; }

    public int getTotalCars(){
        return getPass().getCount() + getAdhoc().getCount();
    }

    public Car getCarAt(Location location) {
        if (!locationIsValid(location)) {
            return null;
        }
        return cars[location.getFloor()][location.getRow()][location.getPlace()];
    }

    private boolean locationIsValid(Location location) {
        int floor = location.getFloor();
        int row = location.getRow();
        int place = location.getPlace();
        if (floor < 0 || floor >= numberOfFloors || row < 0 || row > numberOfRows || place < 0 || place > numberOfPlaces) {
            return false;
        }
        return true;
    }

    public boolean setCarAt(Location location, Car car) {
        if (!locationIsValid(location)) {
            return false;
        }
        Car oldCar = getCarAt(location);
        if (oldCar == null) {
            cars[location.getFloor()][location.getRow()][location.getPlace()] = car;
            car.setLocation(location);
            numberOfOpenSpots--;
            return true;
        }
        return false;
    }

    protected Car removeCarAt(Location location) {
        if (!locationIsValid(location)) {
            return null;
        }
        Car car = getCarAt(location);
        if (car == null) {
            return null;
        }
        if (car instanceof ParkingPassCar) {
            cars[location.getFloor()][location.getRow()][location.getPlace()] = null;
            car.setLocation(null);
            numberOfOpenSpots++;
            pass.decrement();
            passHolder--;
            return car;
        } else{
            cars[location.getFloor()][location.getRow()][location.getPlace()] = null;
            car.setLocation(null);
            numberOfOpenSpots++;
            adhoc.decrement();
            return car;
        }
    }

    public Car getFirstLeavingCar() {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    Car car = getCarAt(location);
                    if (car != null && car.getMinutesLeft() <= 0 && !car.getIsPaying()) {
                        return car;
                    }
                }
            }
        }
        return null;
    }

    public void tick() {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    Car car = getCarAt(location);
                    if (car != null) {
                        car.tick();
                    }
                }
            }
        }
    }

    protected Location getFirstPassLocation() {
        if (passHolder < getPassSpots()) {
            for (int floor = 0; floor < getNumberOfFloors(); floor++) {
                for (int row = 0; row < getNumberOfRows(); row++) {
                    for (int place = 0; place < getNumberOfPlaces(); place++) {
                        Location location = new Location(floor, row, place);
                        if (getCarAt(location) == null) {
                            passHolder++;
                            return location;
                        }
                    }
                }
            }
        }
        return null;
    }

    public Location getFirstPaidLocation() {
        int paid = 0;
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    paid++;
                    Location location = new Location(floor, row, place);
                    if (getCarAt(location) == null) {
                        if (paid <= getPassSpots()) {
                            //Do Nothing
                        } else {
                            return location;
                        }
                    }
                }
            }
        }
        return null;
    }

}


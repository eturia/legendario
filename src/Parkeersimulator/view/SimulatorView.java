package Parkeersimulator.view;

import Parkeersimulator.controller.*;
import Parkeersimulator.model.*;

import javax.swing.*;
import java.awt.*;

public class SimulatorView extends AbstractView{
    private CarController carController;
    private CarParkView carParkGarageView;

    public SimulatorView() {
        carController = (CarController) super.registerController.getObjectInstance("CarController");

        carParkGarageView = new CarParkView();
        add(carParkGarageView, BorderLayout.CENTER);
        setVisible(true);
    }

    public void updateView() {
        carParkGarageView.updateView();
        showStatus();
    }

    public void showStatus() {
        if(!isVisible()) {
            setVisible(true);
        }
    }

    private class CarParkView extends JPanel {
        
        private Dimension size;
        private Image carParkImage;    
    
        /**
         * Constructor for objects of class CarPark
         */
        public CarParkView() {
            size = new Dimension(0, 0);
        }
    
        /**
         * Overridden. Tell the GUI manager how big we would like to be.
         */
        public Dimension getPreferredSize() {
            return new Dimension(800, 500);
        }
    
        /**
         * Overriden. The car park view component needs to be redisplayed. Copy the
         * internal image to screen.
         */
        public void paintComponent(Graphics g) {
            if (carParkImage == null) {
                return;
            }
    
            Dimension currentSize = getSize();
            if (size.equals(currentSize)) {
                g.drawImage(carParkImage, 0, 0, null);
            }
            else {
                // Rescale the previous image.
                g.drawImage(carParkImage, 0, 0, currentSize.width, currentSize.height, null);
            }
        }

        public void updateView() {
            // Create a new car park image if the size has changed.

            if (!size.equals(getSize())) {
                size = getSize();
                carParkImage = createImage(size.width, size.height);
            }
            Graphics graphics = carParkImage.getGraphics();
            int passCounter = 0;
            for(int floor = 0; floor < carController.getNumberOfFloors(); floor++) {
                for(int row = 0; row < carController.getNumberOfRows(); row++) {
                    for(int place = 0; place < carController.getNumberOfPlaces(); place++) {
                        Location location = new Location(floor, row, place);
                        Car car = carController.getCarAt(location);
                        passCounter++;
                        if (passCounter <= carController.getPassSpots()) {
                            Color color = car == null ? Color.lightGray : car.getColor();
                            drawPlace(graphics, location, color);
                        } else {
                            Color color = car == null ? Color.white : car.getColor();
                            drawPlace(graphics, location, color);
                        }
                    }
                }
            }
            repaint();

        }
    
        /**
         * Paint a place on this car park view in a given color.
         */
        private void drawPlace(Graphics graphics, Location location, Color color) {
            graphics.setColor(color);
            graphics.fillRect(
                    location.getFloor() * 260 + (1 + (int)Math.floor(location.getRow() * 0.5)) * 75 + (location.getRow() % 2) * 20,
                    60 + location.getPlace() * 10,
                    20 - 1,
                    10 - 1); // TODO use dynamic size or constants
        }
    }
    public void setVisibility(boolean visibility) {
        setVisible(visibility);
    }

}

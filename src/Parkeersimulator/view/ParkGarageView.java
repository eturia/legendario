package Parkeersimulator.view;

import Parkeersimulator.controller.Controller;
import Parkeersimulator.model.*;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class ParkGarageView extends View {

        private Dimension size;
        private Image carParkImage;

        private JButton knop_start, knop_stop, knop_reset;
        private JLabel lengthLabel;
        private JTextField durationField;

        static final Color FRAME_BG_COLOR = new Color(221, 221, 221);

        /**
         * Constructor for objects of class CarPark
         */
    public ParkGarageView(Controller controller) {
        super(controller);
        size = new Dimension(0, 0);

        //test
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        inputPanel.setBackground(FRAME_BG_COLOR);
        GridBagConstraints c = new GridBagConstraints();

        // Bediening
        knop_start = new JButton("Start");
        knop_start.setName("knop_start");
        knop_start.addActionListener(controller);

        knop_stop = new JButton("Stop");
        knop_stop.setName("knop_stop");
        knop_stop.addActionListener(controller);

        //Start knop
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth=1;
        c.gridheight=1;
        add(knop_start, c);

        //Stop knop
        c.gridy++;
        add(knop_stop, c);

        //Lengte Label
        lengthLabel = new JLabel("Aantal minuten");
        add(lengthLabel,c);

        //Lengte Veld
        durationField = new JTextField(3);
        durationField.setName("durationField");
        durationField.addActionListener(controller);

        add(durationField, c);


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

    public void update(Model model) {
        ParkingModel parkingModel = (ParkingModel)model;
        // Create a new car park image if the size has changed.
        if (!size.equals(getSize())) {
            size = getSize();
            carParkImage = createImage(1000, 1000);
        }
        Graphics graphics = carParkImage.getGraphics();
        for(int floor = 0; floor < parkingModel.getGarage().getNumberOfFloors(); floor++) {
            for(int row = 0; row < parkingModel.getGarage().getNumberOfRows(); row++) {
                for(int place = 0; place < parkingModel.getGarage().getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    Car car = parkingModel.getGarage().getCarAt(location);
                    Color color = car == null ? Color.white : car.getColor();
                    drawPlace(graphics, location, color);
                }
            }
        }
        repaint();
    }

}
package Parkeersimulator.view;

import Parkeersimulator.controller.Controller;
import Parkeersimulator.model.*;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.image.BufferedImage;


public class ParkGarageView extends View {

        private Dimension size;
    private BufferedImage carParkImage;

        private JButton knop_start, knop_stop, knop_reset;
        static final Color FRAME_BG_COLOR = new Color(221, 221, 221);

    private String[] legendaName;
    private Color[] legendaColor;

    /**
     * Constructor for objects of class CarPark
     */
    public ParkGarageView(Controller controller) {
        super(controller);
        size = new Dimension(0, 0);

        legendaName = new String[]{"AdHoc","ParkingPass","Abonnee plekken","Leeg Plekken"};
        legendaColor = new Color[]{Color.red, Color.blue,Color.lightGray, Color.WHITE};

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
        int width = carParkImage.getWidth();
        int height = carParkImage.getHeight();

        graphics.setColor(color);
        graphics.fillRect(
                location.getFloor() * 260 + (1 + (int)Math.floor(location.getRow() * 0.5)) * 75 + (location.getRow() % 2) * 20,
                60 + location.getPlace() * 10,
                20 - 1,
                10 - 1);

        for(int i = 0; i < legendaName.length; i++) {
            graphics.setColor(legendaColor[i]);
            graphics.fillRect(width - (width/6), (height/20) + (i * (height/20)), (width/80), (height/40));
            graphics.setColor(Color.BLACK);
            graphics.setFont(new Font("Arial", Font.PLAIN, (width+height)/100));
            graphics.drawString(legendaName[i], width - (width/8), (height/20) + (i * (height/20)) + ((height/160) + (graphics.getFont().getSize() / 2)));
        }
    }

    public void update(Model model) {
        ParkingModel parkingModel = (ParkingModel)model;
        // Create a new car park image if the size has changed.
        if (!size.equals(getSize())) {
            size = getSize();
            carParkImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
        }
        Graphics graphics = carParkImage.getGraphics();
        int passCounter = 0;
        for(int floor = 0; floor < parkingModel.getGarage().getNumberOfFloors(); floor++) {
            for(int row = 0; row < parkingModel.getGarage().getNumberOfRows(); row++) {
                for(int place = 0; place < parkingModel.getGarage().getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    Car car = parkingModel.getGarage().getCarAt(location);
                    passCounter++;
                    if (passCounter <= parkingModel.getGarage().getPassSpots()) {
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

}
package Parkeersimulator.view;

import java.awt.Color;
import java.awt.Dimension;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import Parkeersimulator.model.*;
import Parkeersimulator.model.ParkingPassCar;


public class PieChartView extends View {

    private Dimension size;
    private BufferedImage pieChartImage;


    private String[] cars;
    private Color[] colors;
    private String[] legendaNames;

    public PieChartView() {
        size = new Dimension(0, 0);


        cars = new String[]{"AdHoc","ParkingPass","Abonnee plekken","Leeg"};
        colors = new Color[] {AdHocCar.COLOR, ParkingPassCar.COLOR,Color.lightGray, Color.WHITE};
        legendaNames = new String[] {"AdHoc","ParkingPass","Abonnee plekken","Leeg"};
    }

    /**
     * The starting method for the Graphics
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (pieChartImage == null) {
            return;
        }

        Dimension currentSize = getSize();
        if (size.equals(currentSize)) {
            g.drawImage(pieChartImage, 0, 0, null);
        }
        else {
            // Rescale the previous image.
            g.drawImage(pieChartImage, 0, 0, currentSize.width, currentSize.height, null);
        }
    }

    @Override
    public void update(Model model) {
        ParkingModel parkingModel = (ParkingModel)model;

        // Create a new car park image if the size has changed.
        if (!size.equals(getSize())) {
            size = getSize();
            pieChartImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
        }
        // Get the graphics from the image to draw on
        Graphics g = pieChartImage.getGraphics();

        int count = 2;
        int[] array = new int[3];
        int oldDegrees=0;
        String[] klasse = new String[3];
        double percentage = 0;


        Calculate pass = parkingModel.getGarage().getPass();
        Calculate adhoc = parkingModel.getGarage().getAdhoc();

        count = count + pass.getCount() + adhoc.getCount() + parkingModel.getGarage().getNumberOfOpenSpots();
        array[0] = pass.getCount(); // number of actors
        klasse[0] = pass.getName();
        array[1] = adhoc.getCount(); // number of actors
        klasse[1] = adhoc.getName();// names of the classes to set the colour later
        array[2] = parkingModel.getGarage().getNumberOfOpenSpots();
        klasse[2] = "open";

        // loop through the array and do action
        for(int i=0;i<array.length;i++)
        {
            double x = array[i];
            percentage = (x / count * 100);



            //als percentage / 2 een restwaarde heeft, percentage +1, zodat de PieView goed uitlijnd
            if((percentage % 2) != 0 && (percentage % 2) <= 0.5)
            {
                percentage +=1;
            }

            double graden = (percentage*3.65);
            // set the colour corresponding to the class name
            if(klasse[i].equals("pass"))
            {
                g.setColor(Color.BLUE);
            }
            if(klasse[i].equals("adhoc"))
            {
                g.setColor(Color.RED);
            }
            if(klasse[i].equals("open"))
            {
                g.setColor(Color.GREEN);
            }

            // if the degrees is lower than 1, then make it 1 or else it won't show
            if(graden < 1 && graden > 0) graden=1;
            g.fillArc(10,10,200,200,oldDegrees,(int)graden);
            oldDegrees += graden; // keep track of the current position


        }

        // Repaint
        repaint();
    }


    /**
     * Set the default size for this JPanel.
     */
    public Dimension getPreferredSize() {
        return new Dimension(300, 200);
    }
}
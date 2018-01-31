package Parkeersimulator.view;

import java.awt.Color;
import java.awt.Dimension;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Parkeersimulator.model.*;


public class PieChartView extends View {

    private Dimension size;
    private BufferedImage pieChartImage;

    private String[] cars;
    private Color[] colors;
    private String[] legendaNames;

    public PieChartView() {
        size = new Dimension(0, 0);

        cars = new String[]{"parkingpass","adhoc","leeg"};
        colors = new Color[] {ParkingPassCar.COLOR,AdHocCar.COLOR,Color.WHITE};
        legendaNames = new String[] {"ParkingPass","Adhoc","Lege plekken"};
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

        int width = pieChartImage.getWidth();
        int height = pieChartImage.getHeight();

        Calculate pass = parkingModel.getGarage().getPass();
        Calculate adhoc = parkingModel.getGarage().getAdhoc();

        count = count + pass.getCount() + adhoc.getCount() + parkingModel.getGarage().getNumberOfOpenSpots();
        array[0] = pass.getCount();
        klasse[0] = pass.getName();
        array[1] = adhoc.getCount();
        klasse[1] = adhoc.getName();
        array[2] = parkingModel.getGarage().getNumberOfOpenSpots();
        klasse[2] = "open";

        // Draw legenda item

        for(int i=0;i<array.length;i++)
        {
            double x = array[i];
            percentage = (x / count * 100);

            //Voor uitlijning bij restwaarde
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
                g.setColor(Color.WHITE);
            }
            // Als een graden onder de nul komt maak er 1 van zo niet, toon niet
            if(graden < 1 && graden > 0) graden=1;
            g.fillArc(10,10,200,200,oldDegrees,(int)graden);
            oldDegrees += graden; // huidige positie meten


            g.fillRect(width - 160, height - (cars.length*20) + (i*20), 10, 10);
            g.setColor(Color.BLACK);
            g.drawString(legendaNames[i] + "", width - 140, height - cars.length*20 + i*20 + (5 + (g.getFont().getSize() / 2)));

        }

        repaint();
    }


    /**
     * Set the default size for this JPanel.
     */
    public Dimension getPreferredSize() {
        return new Dimension(300, 200);
    }
}
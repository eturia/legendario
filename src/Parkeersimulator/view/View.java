package Parkeersimulator.view;

import Parkeersimulator.model.Model;
import Parkeersimulator.controller.Controller;

import javax.swing.*;

/**
 * This is the parent class for all views inside the simulation
 */

abstract public class View extends JPanel {

    protected final Controller controller;

    public View(Controller controller) {
        this.controller=controller;
    }

    public View() {
        this(null);
    }

    /**
     * Abstract method for all view sub-classes
     * @param model
     */

    abstract public void update(Model model);

}

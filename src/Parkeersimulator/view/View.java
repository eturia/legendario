package Parkeersimulator.view;

import Parkeersimulator.model.Model;
import Parkeersimulator.controller.Controller;

import javax.swing.*;

abstract public class View extends JPanel {

    protected final Controller controller;

    public View(Controller controller) {
        this.controller=controller;
    }

    public View() {
        this(null);
    }

    abstract public void update(Model model);

}

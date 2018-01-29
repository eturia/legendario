package Parkeersimulator.controller;

import Parkeersimulator.model.Model;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

abstract public class Controller<M extends Model> implements ActionListener {

    protected final M model;

    public Controller(M model) {
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String naam=((Component)e.getSource()).getName();
        if ((naam==null) || (naam.isEmpty())) {
            throw new IllegalStateException("View-element heeft geen naam");
        }
        if (!eventHandler(naam, e)) {
            throw new IllegalStateException("Event van element "+naam+" niet afgehandeld.");
        }
    }

    abstract protected boolean eventHandler(String naam, ActionEvent e);

}
package Parkeersimulator.model;

import Parkeersimulator.view.View;

import java.util.ArrayList;

abstract public class Model {

    ArrayList<View> views = new ArrayList<>();

    public void registerView(View view) {
        views.add(view);
    }

    public void updateViews() {
        for(View view : views) {
            view.update(this);
        }
    }

}

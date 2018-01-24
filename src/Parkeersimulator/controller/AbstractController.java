package Parkeersimulator.controller;



public abstract class AbstractController
{
    protected ReferanceController registerController;

    public AbstractController()
    {
        this.registerController = ReferanceController.getInstance();
    }
}
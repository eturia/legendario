package Parkeersimulator.controller;


public abstract class AbstractController
{

    protected RegisterController registerController;

    public AbstractController()
    {
        this.registerController = RegisterController.getInstance();
    }
}
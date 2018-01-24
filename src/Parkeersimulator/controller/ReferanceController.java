package Parkeersimulator.controller;

import java.util.HashMap;


public class ReferanceController {

    private static ReferanceController Instance = null;
    private HashMap<String, AbstractController> ControllerObjects;

    private ReferanceController(){
        this.Instance = this;
        this.ControllerObjects = new HashMap<>();
    }

    public static ReferanceController getInstance()
    {
        if(Instance == null)
            new ReferanceController();

        return Instance;
    }

    public void addObjectReference(AbstractController o)
    {
        String[] name = o.getClass().getName().split("\\.");
        ControllerObjects.put(name[2],o);
    }

    public Object getObjectInstance(String ObjectName)
    {
        if(ControllerObjects.get(ObjectName) != null)
            return ControllerObjects.get(ObjectName);

        return null;
    }


}

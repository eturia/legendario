package Parkeersimulator.controller;

import java.util.HashMap;


public class RegisterController {

    private static RegisterController Instance = null;
    private HashMap<String, AbstractController> ControllerObjects;

    private RegisterController(){
        this.Instance = this;
        this.ControllerObjects = new HashMap<>();
    }

    public static RegisterController getInstance()
    {
        if(Instance == null) {
            new RegisterController();
        }
        return Instance;
    }

    public void addObjectReference(AbstractController o)
    {
        String[] name = o.getClass().getName().split("\\.");
        ControllerObjects.put(name[1],o);
    }

    public Object getObjectInstance(String ObjectName)
    {
        if(ControllerObjects.get(ObjectName) != null) {
            return ControllerObjects.get(ObjectName);
        }
        return null;
    }


}

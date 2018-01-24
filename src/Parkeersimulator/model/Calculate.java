package Parkeersimulator.model;

public class Calculate {
    private String name;
    private int count;

    public Calculate(String name)
    {
        this.name = name;
        count = 0;
    }

    public String getName()
    {
        return name;
    }

    public int getCount()
    {
        return count;
    }

    public void reset()
    {
        count = 0;
    }

    public void increment()
    {
        count++;
    }

    public void decrement(){
        count--;
    }
}
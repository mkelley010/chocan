package com.company;

public class Service
{
    private String ID;
    private String name;
    private double fee;

    Service()
    {
        this.ID = "No ID";
        this.name = "No name";
        this.fee = 0;
    }

    Service(String newID, String newName, double newFee)
    {
        this.ID = newID;
        this.name = newName;
        this.fee = newFee;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setFee(double fee)
    {
        if (fee < 0)
        {
            this.fee = 0;
        }
        else
        {
            this.fee = fee;
        }
    }

    public void setID(String ID)
    {
        if (ID.length() > 6)
        {
            this.ID = ID.substring(0,6);
        }
        else
        {
            this.ID = ID;
        }
    }

    public void print()
    {
        System.out.println("Name: " + name);
        System.out.println("ID: " + ID);
        System.out.println("Fee: " + fee);
    }

    public String getID(){return this.ID;}

    public double getFee() {
        return fee;
    }

    public String getName()
    {
        return name;
    }
}

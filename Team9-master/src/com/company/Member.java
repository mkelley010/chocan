package com.company;

import java.util.Iterator;
import java.util.LinkedList;

public class Member extends User
{

    private boolean status; //True = VALIDATED
    private LinkedList servicesReceived;

    Member()
    {
        servicesReceived = new LinkedList();
        this.status = true;
    }

    Member(String newName, String newID, String newAddress, String newCity, String newState, String newZip, String newStatus)
    {
       setName(newName);
       setID(newID);
       setAddress(newAddress);
       setCity(newCity);
       setState(newState);
       setZip(newZip);
       servicesReceived = new LinkedList();
       if(newStatus.equals("SUSPENDED"))
       {
           this.status = false;
       }
       else
       {
           this.status = true;
       }
    }

    public LinkedList getServicesReceived()
    {
        return servicesReceived;
    }

    public void display()
    {
        super.display();
        if(this.status)
        {
            System.out.println("VALIDATED");
        }
        else
        {
            System.out.println("SUSPENDED");
        }
    }

    public boolean getStatus()
    {
        return this.status;
    }

    public void setStatus(boolean status)
    {
        this.status = status;
    }

    public void setStatus(String status)
    {
        if (status.equals("SUSPENDED"))
        {
            this.status = false;
        }
        else
        {
            this.status = true;
        }
    }
}

package com.company;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class Provider extends User
{
    private double fee; //Up to 99,999.99
    private int numServices; //Up to 3 digits
    private LinkedList servicesProvided;

    Provider()
    {
        this.fee = 0;
        this.numServices = 0;
        this.servicesProvided = new LinkedList();
    }

    Provider (String newName, String newID, String newAddress, String newCity, String newState, String newZip)
    {
       setName(newName);
       setID(newID);
       setAddress(newAddress);
       setCity(newCity);
       setState(newState);
       setZip(newZip);
       servicesProvided = new LinkedList();
    }

    public double getFee()
    {
        return this.fee;
    }

    public int getNumServices()
    {
        return this.numServices;
    }

    public void setFee(double f)
    {
        if(f >= 99999.99)
        {
            this.fee = 99999.99;
        }
        else
        {
            this.fee = f;
        }
    }

    public void setNumServices(int numServices)
    {
        if (numServices > 999)
        {
            this.numServices = 999;
        }
        else if (numServices < 0)
        {
            this.numServices = 0;
        }
        else
        {
            this.numServices = numServices;
        }
    }

    public LinkedList getServicesProvided()
    {
        return servicesProvided;
    }

    // Creates a service for a provider
    public void newProvidedService(LinkedList memberList, LinkedList providerDir)
    {
        Scanner in = new Scanner(System.in);
        Utility util = new Utility();
        boolean noMember = true;
        Member aMember = null;
        // Searches for member. If member exists and not suspended continues with service
        do {
            try {
                System.out.println("Enter the member's ID number: ");
                String memberID = in.nextLine();
                aMember = util.findMember(memberList, memberID);
                if (!aMember.getStatus()) {
                    System.out.println("SUSPENDED");
                    return;
                } else {
                    System.out.println("VALIDATED");
                    noMember = false;
                }

            } catch (NullPointerException n) {
                System.out.println("Member not found.");

            }
        } while (noMember);

        boolean noService = true;

        Service aService = null;
        // Search for a service ID and adds service to service object
        do {
            try {
                util.displayProviderDirectory(providerDir);
                System.out.println("Enter the desired service ID: ");
                String serviceCode = in.nextLine();
                aService = util.findService(providerDir, serviceCode);
                aService.print();
                System.out.println("Is this the correct service? (Y/N) ");
                if (in.nextLine().equalsIgnoreCase("Y")) {
                    noService = false;
                }
            } catch (NullPointerException n) {
                System.out.println("Service code not found.");
            }
        } while (noService);

        String comments;
        System.out.println("Enter any comments: ");
        comments = in.nextLine();

        String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        ServiceReport serviceProvided = new ServiceReport(aMember.getID(), this.ID, timeStamp, aService.getID(), comments, aService.getFee(),
                aService.getName(), aMember.getName());

        // Add service to provider AND member
        this.servicesProvided.add(serviceProvided);
        LinkedList memberServiceList = aMember.getServicesReceived();
        memberServiceList.add(serviceProvided);
        this.fee += aService.getFee();
        ++numServices;
        return;
    }

    public void displayProvidedServices()
    {
        Iterator<ServiceReport> iterator = servicesProvided.listIterator(0);
        if(!iterator.hasNext())
        {
            System.out.println("There are no provided services to display.");
            return;
        }
        for(int i = 0; i < servicesProvided.size(); i++)
        {
            iterator.next().display();
        }
    }

}

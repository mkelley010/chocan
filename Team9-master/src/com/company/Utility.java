package com.company;

import java.io.*;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Scanner;

public class Utility
{
    private Scanner input;

    Utility()
    {
        this.input = new Scanner(System.in);
    }

    public void providerMenu(Provider currentProvider, LinkedList memberList, LinkedList providerDir)
    {
        String response = "1";
        Report reportGenerator = new Report();

        while(!response.equals("0"))
        {
            System.out.println("1: New Member Service Report\n2: Generate weekly summary report\n3: View the provider directory\n" +
                    "4: View your total fees accrued this week\n5: View your services provided this week\n0: Exit");
            response = input.nextLine();
            switch(response)
            {
                case "1": currentProvider.newProvidedService(memberList, providerDir); break;

                case "2":
                    try
                    {
                        String fileName = currentProvider.getName();
                        String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss"));
                        PrintWriter writer = new PrintWriter(System.getProperty("user.dir") + "/src/com/company/reports/providerreports/" + fileName + timeStamp + ".txt", "UTF-8");
                        reportGenerator.writeProvider(writer, currentProvider);
                        writer.close();
                    }
                    catch (FileNotFoundException e)
                    {
                        System.out.println("File not found!");
                        break;
                    }
                    catch (UnsupportedEncodingException format)
                    {
                        System.out.println("Unsupported format.");
                        break;
                    }
                    System.out.println("Report written to file.");
                    break;

                case "3": displayProviderDirectory(providerDir); break;

                case "4":
                    System.out.println("Total fees: $" + currentProvider.getFee()); break;

                case "5": currentProvider.displayProvidedServices(); break;

                case "0": break;

                default:
                    System.out.println("Invalid choice, please try again."); break;
            }
        }


    }

    // Creates a provider directory linked list by reading in from directory.txt
    public boolean readProviderDirectory(LinkedList providerDirectory) throws IOException
    {
        Service newService = new Service();
        String id, name;
        double fee;
        URL url = getClass().getResource("directory.txt");
        File file = new File(url.getPath());
        try
        {
            Scanner reader = new Scanner(new BufferedReader(new FileReader(file)));
            while (reader.hasNext())
            {
                // Reads service ID->name->fee then stores object in linked list
                id = reader.nextLine();
                name = reader.nextLine();
                fee = reader.nextDouble();
                addService(providerDirectory, id, name, fee);
                reader.nextLine();
            }
        }
        catch (FileNotFoundException ex)
        {
            System.out.println("File not found!");
            return false;
        }
        return true;
    }

    // Adds a specific service to directory
    public void addService(LinkedList providerDirectory, String newID, String newName, double newFee)
    {
        Service newService = new Service(newID, newName, newFee);
        providerDirectory.add(newService);
    }

    // Reads in the list of ChocAn members from member.txt and stores in linked list of members
    public boolean readMembers(LinkedList memberList)
    {
        Member newMember = new Member();
        String name, id, address, city, state, zip, status;
        URL url = getClass().getResource("member.txt");
        File file = new File(url.getPath());
        try
        {
            Scanner reader = new Scanner(new BufferedReader(new FileReader(file)));
            while (reader.hasNext())
            {
                // Reads name->ID->address->city->state->zip->status into object then adds object to linked list
                name = reader.nextLine();
                id = reader.nextLine();
                address = reader.nextLine();
                city = reader.nextLine();
                state = reader.nextLine();
                zip = reader.nextLine();
                status = reader.nextLine();
                addMember(memberList, name, id, address, city, state, zip, status);
            }
        }
        catch (FileNotFoundException ex)
        {
            System.out.println("File not found!");
            return false;
        }
        return true;
    }

    // Adds a specific member to member list
    public void addMember(LinkedList memberList, String newName, String newID, String newAddress, String newCity, String newState, String newZip, String newStatus)
    {
        Member newMember = new Member(newName, newID, newAddress, newCity, newState, newZip, newStatus);
        memberList.add(newMember);
    }

    // Reads and writes providers to provider list
    public boolean readProviders(LinkedList providerList)
    {
        Provider newProvider = new Provider();
        String name, id, address, city, state, zip;
        URL url = getClass().getResource("providers.txt");
        File file = new File(url.getPath());
        try
        {
            Scanner reader = new Scanner(new BufferedReader(new FileReader(file)));
            while (reader.hasNext())
            {
                name = reader.nextLine();
                id = reader.nextLine();
                address = reader.nextLine();
                city = reader.nextLine();
                state = reader.nextLine();
                zip = reader.nextLine();
                addProvider(providerList, name, id, address, city, state, zip);
            }
        }
        catch (FileNotFoundException ex)
        {
            System.out.println("File not found!");
            return false;
        }
        return true;
    }

    // Adds a specific provider to list
    public void addProvider(LinkedList providerList, String newName, String newID, String newAddress, String newCity, String newState, String newZip)
    {
        Provider newProvider = new Provider(newName, newID, newAddress, newCity, newState, newZip);
        providerList.add(newProvider);
    }

    // Find a member in member list
    public Member findMember(LinkedList memberList, String ID)
    {
        Iterator<Member> memberIterator = memberList.listIterator(0);
        for(int i = 0; i < memberList.size(); i++)
        {
            if(ID.equals(memberIterator.next().getID()))
            {
                return ((ListIterator<Member>) memberIterator).previous();
            }
        }
        System.out.println("ID not found.");
        return null;
    }

    // Find a provider in provider list
    public Provider findProvider(LinkedList providerList, String ID)
    {
        Iterator<Provider> providerIterator = providerList.listIterator(0);
        for (int i = 0; i < providerList.size(); i++)
        {
            if (ID.equals(providerIterator.next().getID()))
            {
                return ((ListIterator<Provider>) providerIterator).previous();
            }
        }
        System.out.println("ID not found.");
        return null;
    }

    // Find a service in provider directory
    public Service findService(LinkedList providerDirectory, String service)
    {
        Iterator<Service> serviceIterator = providerDirectory.listIterator(0);
        for (int i = 0; i < providerDirectory.size(); i++)
        {
            if (service.equals(serviceIterator.next().getID()))
            {
                return ((ListIterator<Service>) serviceIterator).previous();
            }
        }
        System.out.println("Service not found.");
        return null;

    }

    // Print the entire provider directory
    public void displayProviderDirectory(LinkedList providerDirectory)
    {
        Iterator<Service> serviceIterator = providerDirectory.listIterator(0);
        for (int i = 0; i < providerDirectory.size(); i++)
        {
            serviceIterator.next().print();
        }
    }

    public Provider providerLogin(LinkedList providerList)
    {
        Scanner in = new Scanner(System.in);

        Provider providerAcct = new Provider();
        System.out.println("Enter your ID number: ");
        String ID = in.nextLine();
        try
        {
            providerAcct = findProvider(providerList, ID);
            System.out.println("Welcome " + providerAcct.getName());
        } catch(NullPointerException e)
        {
            System.out.println("No match for provider ID " + ID + " found.");
            return null;
        }
        return providerAcct;
    }

    // Manager menu controls and functionality
    public void managerMenu(LinkedList providerList, LinkedList memberList)
    {
        Report reportGenerator = new Report();
        Manager manager = new Manager();
        int option = 0;
        printManagerMenu();
        option = input.nextInt();
        while (option != 0)
        {
            switch (option)
            {
                case 1:
                    manager.addMember(memberList);
                    break;
                case 2:
                    manager.addProvider(providerList);
                    break;
                case 3:
                    manager.editMember(memberList);
                    break;
                case 4:
                    manager.editProvider(providerList);
                    break;
                case 5:
                    manager.deleteMember(memberList);
                    break;
                case 6:
                    manager.deleteProvider(providerList);
                    break;
                case 7:
                    reportGenerator.writeMemberReport(memberList);
                    break;
                case 8:
                    reportGenerator.writeProviderReport(providerList);
                    break;
                case 9:
                    reportGenerator.writeEFTReport(providerList);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");break;
            }
            printManagerMenu();
            option = input.nextInt();
        }

    }

    // Simply prints the manager menu display
    public void printManagerMenu()
    {
        System.out.println("1) Add member");
        System.out.println("2) Add provider");
        System.out.println("3) Edit member");
        System.out.println("4) Edit provider");
        System.out.println("5) Delete member");
        System.out.println("6) Delete provider");
        System.out.println("7) Generate member reports");
        System.out.println("8) Generate provider reports");
        System.out.println("9) Generate EFT report");
        System.out.println("0) Quit");
        System.out.println("Select an option: ");
    }

}

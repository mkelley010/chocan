package com.company;

import java.io.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Random;


public class Manager extends Utility
{
    private Scanner in;
    private Random generate;
    private String pathName;

    public Manager()
    {
        this.in = new Scanner(System.in);
        this.generate = new Random(System.currentTimeMillis());
        this.pathName = (System.getProperty("user.dir") + "/src/com/company/");
    }

    public void editMember(LinkedList memberList)
    {
        boolean noMember = true;
        Member aMember = null;
        do {
            try
            {
                System.out.println("Enter the member's ID number");
                String memberID = in.nextLine();
                aMember = findMember(memberList, memberID);
                if(aMember != null)
                    noMember = false;

                aMember.display();
            } catch (NullPointerException n) {
                System.out.println("Member not found.");

            }
        } while (noMember);


        System.out.println("What would you like to manage for member " + aMember.getID());
        boolean cont = true;
        do
        {

            System.out.println("1: Account Status\n2: Address\n3: Name\n4: Exit");
            String response = in.nextLine();
            switch (response)
            {
                case "1":
                    String ans;
                    if(aMember.getStatus())
                    {
                        System.out.println("Member is currently valid, set to suspended? (Y/N)");
                        ans = in.nextLine();
                        if(ans.equalsIgnoreCase("Y"))
                        {
                            aMember.setStatus(false);
                        }
                    }
                    else
                    {
                        System.out.println("Member is currently suspended, set to valid? (Y/N)");
                        ans = in.nextLine();
                        if(ans.equalsIgnoreCase("Y"))
                        {
                            aMember.setStatus(true);
                        }
                    }
                    break;

                case "2": updateUserAddress(aMember); break;

                case "3": updateUserName(aMember); break;

                case "4": cont = false; break;

                default: break;
            }
            System.out.println("Member update completed.");
            aMember.display();
        }while(cont);

        try{
            Iterator<Member> memberIterator = memberList.listIterator(0);
            PrintWriter write = new PrintWriter(pathName + "member.txt", "UTF-8");
            for(int i = 0; i < memberList.size(); i++)
            {
                writeMember(write, memberIterator.next());
            }
            write.close();
        }
        catch(FileNotFoundException e)
        {
            System.out.println("File not found!");
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        System.out.println("Edited!");
    }

    public void editProvider(LinkedList providerList)
    {
        boolean noProvider = true;
        Provider aProvider = null;
        do {
            try
            {
                System.out.println("Enter the provider's ID number");
                String providerID = in.nextLine();
                aProvider = findProvider(providerList, providerID);
                if(aProvider != null)
                    noProvider = false;

                aProvider.display();
            } catch (NullPointerException n) {
                System.out.println("Provider not found.");

            }

        }while(noProvider);

        System.out.println("What would you like to manage for provider " + aProvider.getID());
        boolean cont = true;
        do{
            System.out.println("1: Address\n2: Name\n3: Fee Total\n4: Exit");
            String response = in.nextLine();
            switch (response)
            {
                case "1": updateUserAddress(aProvider); break;


                case "2": updateUserName(aProvider); break;

                case "3":
                    System.out.println("The current provider's total fees accrued is " + aProvider.getFee());
                    System.out.printf("Enter their new fee amount: ");
                    double fee = in.nextDouble();
                    aProvider.setFee(fee);
                    break;

                case "4": cont = false; break;

                default: cont = false; break;
            }
            System.out.println("Provider update completed.");
            aProvider.display();
        }while(cont);
        try{
            Iterator<Provider> providerIterator = providerList.listIterator(0);
            PrintWriter write = new PrintWriter(pathName + "providers.txt", "UTF-8");
            for(int i = 0; i < providerList.size(); i++)
            {
                writeProvider(write, providerIterator.next());
            }
            write.close();
        }
        catch(FileNotFoundException e)
        {
            System.out.println("File not found!");
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        System.out.println("Edited!");
    }
    private void updateUserName(User user)
    {
        System.out.println("Enter the new name: ");
        String name = in.nextLine();
        user.setName(name);
    }

    private void updateUserAddress(User user)
    {
        String address;
        String city;
        String zip;
        String state;
        System.out.println("Enter the new street address: ");
        address = in.nextLine();
        user.setAddress(address);
        System.out.println("Enter the new city: ");
        city = in.nextLine();
        user.setCity(city);
        System.out.println("Enter the new state: ");
        state = in.nextLine();
        user.setState(state);
        System.out.println("Enter the new ZIP code: ");
        zip = in.nextLine();
        user.setZip(zip);
    }

    public boolean addMember(LinkedList memberList)
    {
        String name;
        String address;
        String ID;
        String state;
        String city;
        String ZIP;

        ID =  String.format("%09d", generate.nextInt(100000000));
        Iterator<Member> memberIterator = memberList.listIterator(0);
        boolean noMatch = true;
        do {
            for (int i = 0; i < memberList.size(); i++)
            {
                if (ID.equals(memberIterator.next().getID()))
                {
                    ID = String.format("%09d", generate.nextInt(100000000));
                    noMatch = true;
                    break;
                }
            }
            if(!memberIterator.hasNext())
                noMatch = false;
        }while(noMatch);

        System.out.println("Enter the new member's name: ");
        name = in.nextLine();
        System.out.println("Enter the new member's street address: ");
        address = in.nextLine();
        System.out.println("Enter the new member's city: ");
        city = in.nextLine();
        System.out.println("Enter the new member's state of residence: ");
        state = in.nextLine();
        System.out.println("Enter the new member's ZIP code: ");
        ZIP = in.nextLine();

        Member newMember = new Member(name, ID, address, city, state, ZIP, "ACTIVE");
        memberList.add(newMember);
        try
        {
            PrintWriter write = new PrintWriter(new FileWriter(pathName + "member.txt", true));
            writeMember(write, newMember);
            write.close();
        }catch (IOException e) {
            System.out.println("member.txt not found.");
            System.err.println(e);
            return false;
        }
        System.out.println("Added!");
        return true;
    }

    public boolean addProvider(LinkedList providerList)
    {
        String name;
        String address;
        String ID;
        String state;
        String city;
        String ZIP;

        ID =  String.format("%09d", generate.nextInt(10000000));
        ID = "9" + ID;
        Iterator<Provider> providerIterator = providerList.listIterator(0);
        boolean noMatch = true;
        do {
            for (int i = 0; i < providerList.size(); i++)
            {
                if (ID.equals(providerIterator.next().getID()))
                {
                    ID = String.format("%09d", generate.nextInt(10000000));
                    ID = "9" + ID;
                    noMatch = true;
                    break;
                }
            }
            if(!providerIterator.hasNext())
                noMatch = false;
        }while(noMatch);

        System.out.println("Enter the new provider's name: ");
        name = in.nextLine();
        System.out.println("Enter the new provider's street address: ");
        address = in.nextLine();
        System.out.println("Enter the new provider's city: ");
        city = in.nextLine();
        System.out.println("Enter the new provider's state of residence: ");
        state = in.nextLine();
        System.out.println("Enter the new provider's ZIP code: ");
        ZIP = in.nextLine();

        Provider newProvider = new Provider(name, ID, address, city, state, ZIP);
        providerList.add(newProvider);
        try
        {
            PrintWriter write = new PrintWriter(new FileWriter(pathName + "providers.txt", true));
            writeProvider(write, newProvider);
            write.close();
        }catch (IOException e) {
            System.out.println("member.txt not found.");
            System.err.println(e);
            return false;
        }
        System.out.println("Added!");
        return true;
    }

    private void writeMember(PrintWriter write, Member aMember)
    {
        write.println(aMember.getName());
        write.println(aMember.getID());
        write.println(aMember.getAddress());
        write.println(aMember.getCity());
        write.println(aMember.getState());
        write.println(aMember.getZip());
        if(aMember.getStatus())
        {
            write.println("ACTIVE");
        }
        else
        {
            write.println("SUSPENDED");
        }
    }

    private void writeProvider(PrintWriter write, Provider aProvider)
    {
        write.println(aProvider.getName());
        write.println(aProvider.getID());
        write.println(aProvider.getAddress());
        write.println(aProvider.getCity());
        write.println(aProvider.getState());
        write.println(aProvider.getZip());
    }

    public boolean deleteMember(LinkedList memberList)
    {
        System.out.println("Enter the member ID to remove: ");
        String IDToRemove = in.nextLine();
        Member toRemove;
        try {
            toRemove = findMember(memberList, IDToRemove);
            toRemove.display();
        } catch (NullPointerException e) {
            System.out.println("Member not found.");
            return false;
        }
        System.out.println(toRemove.getName() + " will be removed.");

        memberList.remove(toRemove);
        Iterator<Member> memberIterator = memberList.listIterator(0);
        try{
            PrintWriter write = new PrintWriter(pathName + "member.txt", "UTF-8");
            for(int i = 0; i < memberList.size(); i++)
            {
                writeMember(write, memberIterator.next());
            }
            write.close();
        }
        catch(FileNotFoundException e)
        {
            System.out.println("File not found!");
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        System.out.println("Removed!");
        return true;
    }

    public boolean deleteProvider(LinkedList providerList)
    {
        System.out.println("Enter the provider ID to remove: ");
        String IDToRemove = in.nextLine();
        Provider toRemove;
        try {
            toRemove = findProvider(providerList, IDToRemove);
            toRemove.display();
        } catch (NullPointerException e) {
            System.out.println("Member not found.");
            return false;
        }
        System.out.println(toRemove.getName() + " will be removed...");
        providerList.remove(toRemove);
        Iterator<Provider> providerIterator = providerList.listIterator(0);
        try{
            PrintWriter write = new PrintWriter(pathName + "providers.txt", "UTF-8");
            for(int i = 0; i < providerList.size(); i++)
            {
                writeProvider(write, providerIterator.next());
            }
            write.close();
        }
        catch(FileNotFoundException e)
        {
            System.out.println("File not found!");
            providerList.add(toRemove);
            return false;
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
            providerList.add(toRemove);
            return false;

        }
        System.out.println("Removed!");
        return true;
    }
}

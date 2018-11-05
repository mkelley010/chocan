package com.company;
/* Team 9 is Peter Chung, Michael Kelley, Sean Lesch
 * Program for CS300 Group Project, Spring 2018
 * ChocAn Data Processing Software
 */

import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        Scanner input = new Scanner(System.in);
        LinkedList providerDirectory = new LinkedList(), memberList = new LinkedList(), providerList = new LinkedList();
        Utility utility = new Utility();
        utility.readProviderDirectory(providerDirectory);
        utility.readMembers(memberList);
        utility.readProviders(providerList);
        Provider tempProvider;
        Report generateReports = new Report();
        System.out.println("Welcome to the program, please select the account features to access:");
        String response = null;

        while(response == null || !response.equals("0"))
        {
            System.out.println("0: Exit\n1: Manager\n2: Provider");
            response = input.nextLine();
            if (response.equals("1"))
            {
                utility.managerMenu(providerList, memberList);
            } else if (response.equals("2"))
            {
                tempProvider = utility.providerLogin(providerList);
                if(tempProvider != null)
                {
                    utility.providerMenu(tempProvider, memberList, providerDirectory);
                }
            } else if(response.equals("0"))
            {

            }
            else
            {
                System.out.println("Invalid input, please try again.");
            }
        }
        generateReports.writeProviderReport(providerList);
        generateReports.writeEFTReport(providerList);
        generateReports.writeMemberReport(memberList);
        generateReports.writeServiceReports(providerList);
    }


}

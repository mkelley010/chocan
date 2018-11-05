package com.company;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.Iterator;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.File;


public class Report
{
    Report()
    {
        // Creates reports directories. Don't delete the Report object in Main so that this constructor is called to avoid errors.
        try
        {
           Files.createDirectories(Paths.get(System.getProperty("user.dir") + "/src/com/company/reports"));
           Files.createDirectories(Paths.get(System.getProperty("user.dir") + "/src/com/company/reports/memberreports"));
           Files.createDirectories(Paths.get(System.getProperty("user.dir") + "/src/com/company/reports/EFTreports"));
           Files.createDirectories(Paths.get(System.getProperty("user.dir") + "/src/com/company/reports/servicereports"));
           Files.createDirectories(Paths.get(System.getProperty("user.dir") + "/src/com/company/reports/providerreports"));
        }
        catch (IOException e)
        {
            System.out.println("IO Error");
        }
    }

    // Deletes all files in a directory with a path name as an argument
    public void cleanDirectory(String pathName)
    {
        File directory = new File(pathName);
        for(File files: directory.listFiles())
        {
            if (!files.isDirectory())
            {
                files.delete();
            }
        }
    }

    // Creates service report file and writes all service reports to it
    public boolean writeServiceReports(LinkedList providerList)
    {
        ServiceReport aServiceReport;
        Iterator<Provider> providerIterator = providerList.listIterator(0);
        String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss"));
        String fileName;
        String pathName = (System.getProperty("user.dir") + "/src/com/company/reports/servicereports/");
        cleanDirectory(pathName);
        for(int i = 0; i < providerList.size(); i++)
        {
            LinkedList serviceList = providerIterator.next().getServicesProvided();
            Iterator<ServiceReport> serviceReportIterator = serviceList.listIterator(0);

            try
            {
                for(int j = 0; j < serviceList.size(); j++)
                {
                    aServiceReport = serviceReportIterator.next();
                    fileName = aServiceReport.getProviderID() + "_" + aServiceReport.getMemberID();
                    PrintWriter writer = new PrintWriter(System.getProperty("user.dir") + "/src/com/company/reports/servicereports/" + fileName + timeStamp + ".txt", "UTF-8");
                    writeService(writer, aServiceReport);
                    writer.close();
                }
            }
            catch(FileNotFoundException e)
            {
                System.out.println("File not found!");
                return false;
            }
            catch (UnsupportedEncodingException format)
            {
                System.out.println("Unsupported format.");
                return false;
            }
        }
        System.out.println("Report generated!");
        return true;
    }

    // Writes a single service to file
    public void writeService(PrintWriter writer, ServiceReport service)
    {
        writer.println("CURRENT TIME: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        writer.println("TIME OF SERVICE: " + service.getServiceTime());
        writer.println("PROVIDER ID: " + service.getProviderID());
        writer.println("MEMBER ID: " + service.getMemberID());
        writer.println("SERVICE CODE:" + service.getCode());
        writer.println("COMMENTS: " + service.getComments());
    }

    // Writes all providers to their own separate report files
    public boolean writeProviderReport(LinkedList providerList)
    {
        Provider tempProvider;
        String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss"));
        String fileName;
        Iterator<Provider> providerIterator = providerList.listIterator(0);
        String pathName = (System.getProperty("user.dir") + "/src/com/company/reports/providerreports/");
        cleanDirectory(pathName);
        for (int i = 0; i < providerList.size(); i++)
        {
            try
            {
                // Creates separate files for each provider and writes data to them
                tempProvider = providerIterator.next();
                fileName = tempProvider.getName();
                PrintWriter writer = new PrintWriter(System.getProperty("user.dir") + "/src/com/company/reports/providerreports/" + fileName + timeStamp + ".txt", "UTF-8");
                writeProvider(writer, tempProvider);
                writer.close();
            }
            catch (FileNotFoundException e)
            {
                System.out.println("File not found!");
                return false;
            }
            catch (UnsupportedEncodingException format)
            {
                System.out.println("Unsupported format.");
                return false;
            }
        }
        System.out.println("Report generated!");
        return true;
    }

    // Writes a single provider to file
    public void writeProvider(PrintWriter writer, Provider provider)
    {
        String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String timeStampSystem = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LinkedList servicesProvided = provider.getServicesProvided();
        Iterator<ServiceReport> servicesProvidedIterator = servicesProvided.listIterator(0);
        ServiceReport tempReport;
        DecimalFormat doubleFormatter = new DecimalFormat("#.00");
        writer.println("PROVIDER");
        writer.println("--------");
        writer.println("Name: " + provider.getName());
        writer.println("ID: " + provider.getID());
        writer.println("Address: " + provider.getAddress());
        writer.println("City & State: " + provider.getCity() + ", " + provider.getState());
        writer.println("Zip: " + provider.getZip());
        writer.println("Total consultations: " + provider.getNumServices());
        if (provider.getFee() == 0)
        {
            writer.println("Fee: 0.00");
        }
        else
        {
            writer.println("Fee: " + doubleFormatter.format(provider.getFee()));
        }
        writer.println('\n');
        writer.println("SERVICES PROVIDED");
        writer.println("-----------------");
        if (servicesProvided.size() == 0)
        {
            writer.println("No services provided.");
        }
        else
        {
            for (int i = 0; i < servicesProvided.size(); i++)
            {
                tempReport = servicesProvidedIterator.next();
                writer.println("Date of Service: " + tempReport.getServiceTime());
                writer.println("Time System Received Service: " + timeStampSystem);
                writer.println("Member Name: " + tempReport.getMemberName());
                writer.println("Member ID: " + tempReport.getMemberID());
                writer.println("Service Code: " + tempReport.getCode());
                writer.println("Fee: " + doubleFormatter.format(tempReport.getFee()));
                writer.println('\n');
            }
        }

    }

    // Write all members to their own report file
    public boolean writeMemberReport(LinkedList memberList)
    {
        String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss"));
        Member tempMember;
        Iterator<Member> memberIterator = memberList.listIterator(0);
        String pathName = (System.getProperty("user.dir") + "/src/com/company/reports/memberreports/");
        String fileName;
        cleanDirectory(pathName);
        for (int i = 0; i < memberList.size(); i++)
        {
            try
            {
                // Creates separate file for each member and writes data to it
                tempMember = memberIterator.next();
                fileName = tempMember.getName();
                PrintWriter writer = new PrintWriter(System.getProperty("user.dir") + "/src/com/company/reports/memberreports/" + fileName + timeStamp+ ".txt", "UTF-8");
                writeMember(writer, tempMember);
                writer.close();
            }
            catch (FileNotFoundException e)
            {
                System.out.println("File not found!");
                return false;
            }
            catch (UnsupportedEncodingException format)
            {
                System.out.println("Unsupported format.");
                return false;
            }
        }
        System.out.println("Report generated!");
        return true;
    }

    // Write a single member to file
    public void writeMember(PrintWriter writer, Member member)
    {
        LinkedList servicesReceived = member.getServicesReceived();
        Iterator<ServiceReport> servicesReceivedIterator = servicesReceived.listIterator(0);
        ServiceReport tempReport;
        writer.println("MEMBER");
        writer.println("------");
        writer.println("Name: " + member.getName());
        writer.println("ID: " + member.getID());
        writer.println("Address: " + member.getAddress());
        writer.println("City & State: " + member.getCity() + ", " + member.getState());
        writer.println("Zip: " + member.getZip());
        writer.println('\n');
        writer.println("SERVICES RECEIVED");
        writer.println("-----------------");
        if (servicesReceived.size() == 0)
        {
            writer.println("No services received.");
        }
        else
        {
            for (int i = 0; i < servicesReceived.size(); i++)
            {
                tempReport = servicesReceivedIterator.next();
                writer.println("Date: " + tempReport.getServiceTime());
                writer.println("Provider ID: " + tempReport.getProviderID());
                writer.println("Service Name: " + tempReport.getName());
                writer.println('\n');
            }
        }

    }

    // Creates an EFT report file and writes EFT data to it
    public boolean writeEFTReport(LinkedList providerList)
    {
        String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss"));
        Provider tempProvider;
        String fileName;
        int totalConsultations = 0, totalProvidersWithServices = 0;
        double totalFee = 0;
        DecimalFormat doubleFormatter = new DecimalFormat("#.00");
        Iterator<Provider> providerIterator = providerList.listIterator(0);
        String pathName = (System.getProperty("user.dir") + "/src/com/company/reports/EFTreports/");
        cleanDirectory(pathName);
        try
        {
            fileName = "EFTreport";
            PrintWriter writer = new PrintWriter(System.getProperty("user.dir") + "/src/com/company/reports/EFTreports/" + fileName + timeStamp + ".txt", "UTF-8");
            writer.println("EFT Report");
            writer.println("----------");
            for (int i = 0; i < providerList.size(); i++)
            {
                tempProvider = providerIterator.next();
                if (tempProvider.getNumServices() != 0)
                {
                    totalProvidersWithServices++;
                    writeEFT(writer, tempProvider);
                    totalFee += tempProvider.getFee();
                    totalConsultations += tempProvider.getNumServices();
                    writer.println('\n');
                }
            }
            writer.println("Total Providers w/ Consultations: " + totalProvidersWithServices);
            writer.println("Total Consultations For All Providers: " + totalConsultations);
            if (totalFee != 0)
            {
                writer.println("Total Fees For All Providers: " + doubleFormatter.format(totalFee));
            }
            else
            {
                writer.println("Total Fees For All Providers: 0.00");
            }
            writer.close();
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Directory not found");
            return false;
        }
        catch (UnsupportedEncodingException format)
        {
            System.out.println("Unsupported format");
            return false;
        }
        System.out.println("Report generated!");
        return true;
    }

    // Write a single provider for the EFT report
    public void writeEFT(PrintWriter writer, Provider provider)
    {
        DecimalFormat doubleFormatter = new DecimalFormat("#.00");
        LinkedList servicesProvided = provider.getServicesProvided();
        Iterator<ServiceReport> servicesProvidedIterator = servicesProvided.listIterator(0);
        writer.println("Name: " + provider.getName());
        writer.println("Number of Consultations: " + provider.getNumServices());
        writer.println("Fee: " + doubleFormatter.format(provider.getFee()));
    }




}

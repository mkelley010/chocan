package com.company;

public class ServiceReport {
    private String memberID;
    private String providerID;
    private String serviceTime;
    private String code;
    private String comments;
    private String name;
    private String memberName;
    private double fee;

    public ServiceReport(String mID, String pID, String sTime, String code, String comments, double f, String name, String memberName)
    {
        this.memberID = mID;
        this.providerID = pID;
        this.serviceTime = sTime;
        this.code = code;
        if (comments.length() > 100) {
            this.comments = comments.substring(0, 100);
        }
        else
        {
            this.comments = comments;
        }
        this.fee = f;
        this.name = name;
        this.memberName = memberName;
    }

    public void display()
    {
        System.out.println("Member: " + this.memberID);
        System.out.println("Provider: " + this.providerID);
        System.out.println("Time of service: " + this.serviceTime);
        System.out.println("Comments: " + this.comments);
    }

    public double getFee()
    {
        return fee;
    }

    public String getCode()
    {
        return code;
    }

    public String getComments()
    {
        return comments;
    }

    public String getMemberID()
    {
        return memberID;
    }

    public String getProviderID()
    {
        return providerID;
    }

    public String getServiceTime()
    {
        return serviceTime;
    }

    public String getMemberName()
    {
        return memberName;
    }

    public String getName()
    {
        return name;
    }
}

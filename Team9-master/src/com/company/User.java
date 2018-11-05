package com.company;



/*The User class is the base object for all user objects, both
 * providers and members. The provider child class adds little
 * functionality, so the creation of member objects will be done
 * using the User class and methods.
 */
public class User
{

    protected String name; //25 character max
    protected String address; //25 character max
    protected String city; //14 character max
    protected String zip; //5 character max
    protected String state; //2 character max
    protected String ID; //9 character max

    public User()
    {
        this.name = null;
        this.address = null;
        this.ID = null;
        this.zip = null;
        this.state = null;
        this.city = null;

    }

    public void display()
    {
        System.out.println(this.name);
        System.out.println(this.ID);
        System.out.println(this.address);
        System.out.println(this.city + ", " + this.state);
        System.out.println(this.zip);
        System.out.println();
    }

    public String getID()
    {
        return this.ID;
    }

    public void setName(String n)
    {
        if(n.length() > 25)
        {
            this.name = n.substring(0, 25);
        }
        else
        {
            this.name = n;
        }
    }

    public void setID(String i)
    {
        if(i.length() > 9)
        {
            this.ID = i.substring(0, 9);
        }
        else if (i.length() < 9)
        {
            this.ID = "999999999";
        }
        else
        {
            this.ID = i;
        }
    }

    public void setAddress(String a)
    {
        if(a.length() > 25)
        {
            this.address = a.substring(0, 25);
        }
        else
        {
            this.address = a;
        }
    }

    public void setZip(String z)
    {
        if(z.length() > 5)
        {
            this.zip = z.substring(0, 5);
        }
        else
        {
            this.zip = z;
        }
    }

    public void setState(String s)
    {
        if(s.length() > 2)
        {
            this.state = s.substring(0, 2);
        }
        else
        {
            this.state = s;
        }
    }

    public void setCity(String c)
    {
        if(c.length() > 14)
        {
            this.city = c.substring(0, 14);
        }
        else
        {
            this.city = c;
        }
    }

    public String getName()
    {
        return name;
    }

    public String getAddress()
    {
        return address;
    }

    public String getCity()
    {
        return city;
    }

    public String getState()
    {
        return state;
    }

    public String getZip()
    {
        return zip;
    }
}



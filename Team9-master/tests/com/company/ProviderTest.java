package com.company;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProviderTest
{

    @Test
    void setFee()
    {
        Provider tester = new Provider();
        tester.setFee(99999999999.99);
        assertEquals(99999.99, tester.getFee());
        tester.setFee(0);
        assertEquals(0, tester.getFee());
        tester.setFee(99999.99);
        assertEquals(99999.99, tester.getFee());
    }

    @Test
    void setNumServices()
    {
        Provider tester = new Provider();
        tester.setNumServices(1000);
        assertEquals(999, tester.getNumServices());
        tester.setNumServices(-1);
        assertEquals(0, tester.getNumServices());
        tester.setNumServices(500);
        assertEquals(500, tester.getNumServices());
    }

    @Test
    void testProviderCreation()
    {
        Provider testProvider = new Provider();
        assertEquals(0, testProvider.getFee());
        assertEquals(0, testProvider.getNumServices());
        assertEquals(0, testProvider.getServicesProvided().size());
    }

}
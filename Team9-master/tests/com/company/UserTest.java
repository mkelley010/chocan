package com.company;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest
{
    private User testUser = new User();

    @Test
    void setName()
    {
        testUser.setName("jsdfmasmfsdmfsmfsmgnaorigniong");
        assertEquals(25, testUser.getName().length());
    }

    @Test
    void setID()
    {
        testUser.setID("9999999999");
        assertEquals(9, testUser.getID().length());
        testUser.setID("1");
        assertEquals("999999999", testUser.getID());
    }

    @Test
    void setAddress()
    {
        testUser.setAddress("akfkksdfjsidfjosjdfosjginginerignreiginifnbinsfdfsfsfsdb");
        assertEquals(25, testUser.getAddress().length());
    }

    @Test
    void setZip()
    {
        testUser.setZip("123456");
        assertEquals(5, testUser.getZip().length());
        testUser.setZip("12345");
        assertEquals("12345", testUser.getZip());
    }

    @Test
    void setState()
    {
        testUser.setState("TEXAS");
        assertEquals(2, testUser.getState().length());
        assertEquals("TE", testUser.getState().substring(0,2));
    }

    @Test
    void setCity()
    {
        testUser.setCity("111111111111111111111");
        assertEquals(14, testUser.getCity().length());
    }

    @Test
    void testUserCreation()
    {
        assertNull(testUser.getAddress());
        assertNull(testUser.getCity());
        assertNull(testUser.getState());
        assertNull(testUser.getZip());
        assertNull(testUser.getID());
        assertNull(testUser.getName());
    }
}
package com.company;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class MemberTest
{
    private Member testMember = new Member();
    @Test
    void testMemberCreation()
    {
        assertEquals(0, testMember.getServicesReceived().size());
        assertTrue(testMember.getStatus());
    }

    @Test
    void setStatus()
    {
        testMember.setStatus("SUSPENDED");
        assertFalse(testMember.getStatus());
        testMember.setStatus("VALIDATED");
        assertTrue(testMember.getStatus());
    }


}
package com.example;

import com.example.entity.Ticket;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TicketTest {

    @Test
    public void testDefaultConstructor() {
        Ticket ticket = new Ticket();
        assertNull(ticket.getTicketId());
        assertNull(ticket.getPostedBy());
        assertNull(ticket.getMessageText());
        assertEquals("pending", ticket.getResState());
        assertEquals(0, ticket.getAmount());
    }

    @Test
    public void testConstructorWithParameters() {
        Ticket ticket = new Ticket(123, 456, "Test Message", "resolved", 100);
        assertEquals(123, ticket.getTicketId());
        assertEquals(456, ticket.getPostedBy());
        assertEquals("Test Message", ticket.getMessageText());
        assertEquals("resolved", ticket.getResState());
        assertEquals(100, ticket.getAmount());
    }

    @Test
    public void testSettersAndGetters() {
        Ticket ticket = new Ticket();

        ticket.setTicketId(789);
        assertEquals(789, ticket.getTicketId());

        ticket.setPostedBy(100);
        assertEquals(100, ticket.getPostedBy());

        ticket.setMessageText("Updated Message");
        assertEquals("Updated Message", ticket.getMessageText());

        ticket.setResState("closed");
        assertEquals("closed", ticket.getResState());

        ticket.setAmount(200);
        assertEquals(200, ticket.getAmount());
    }

    @Test
    public void testEquals() {
        Ticket ticket1 = new Ticket(1, 1, "Message1", "pending", 50);
        Ticket ticket2 = new Ticket(1, 1, "Message1", "pending", 50);
        Ticket ticket3 = new Ticket(2, 1, "Message1", "pending", 50);
        Ticket ticket4 = new Ticket(1, 2, "Message1", "pending", 50);
        Ticket ticket5 = new Ticket(1, 1, "Message2", "pending", 50);
        Ticket ticket6 = new Ticket(1, 1, "Message1", "resolved", 50);
        Ticket ticket7 = new Ticket(1, 1, "Message1", "pending", 100);

        assertTrue(ticket1.equals(ticket2));
        assertFalse(ticket1.equals(ticket3));
        assertFalse(ticket1.equals(ticket4));
        assertFalse(ticket1.equals(ticket5));
        assertFalse(ticket1.equals(ticket6));
        assertFalse(ticket1.equals(ticket7));

        // Test with null values
        assertFalse(ticket1.equals(null));
        assertFalse(ticket1.equals("someString"));
    }

    @Test
    public void testToString() {
        Ticket ticket = new Ticket(1, 2, "Sample Message", "pending", 75);
        String expected = "Message{" +
                "messageId=" + 1 +
                ", postedBy=" + 2 +
                ", messageText='" + "Sample Message" + '\'' +
                ", timePostedEpoch=" + "pending" +
                '}';
        assertEquals(expected, ticket.toString());
    }

    // Edge Case Tests
    @Test
    public void testSetterWithNullValues() {
        Ticket ticket = new Ticket();
        ticket.setPostedBy(null);
        ticket.setMessageText(null); 
        // No assertions for null values as the class allows them

        ticket.setResState(null); // Test with null for resState
        assertNull(ticket.getResState()); 
    }

    @Test
    public void testSetterWithEmptyMessageText() {
        Ticket ticket = new Ticket();
        ticket.setMessageText("");
        assertEquals("", ticket.getMessageText());
    }

    // Additional Tests (consider adding more depending on specific requirements)
    // - Test with invalid input data (e.g., negative amount)
    // - Test with very long messageText strings (if there are length constraints)
    // - Test with different resState values
}
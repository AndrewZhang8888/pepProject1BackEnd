package com.example;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.example.controller.SocialMediaController;
import com.example.entity.Account;
import com.example.entity.Ticket;
import com.example.exception.DuplicateUsernameException;
import com.example.exception.InvalidRegistrationDataException;
import com.example.service.AccountService;
import com.example.service.MessageService;

@SpringBootTest
public class SocialMediaControllerTests {

    @Mock
    private MessageService mockMsgServ;

    @Mock
    private AccountService mockAccServ;

    @InjectMocks
    private SocialMediaController controller;

    // Test getData()
    @Test
    public void testGetData() {
        Map<String, String> expectedData = new HashMap<>();
        expectedData.put("message", "Hello from the API!");

        when(mockMsgServ.getAllMessages()).thenReturn(new ArrayList<>()); // Stub for empty messages

        Map<String, String> response = controller.getData();

        assertEquals(200, 200);
        assertEquals(expectedData, response);
    }

    // Test registerAccount() with successful registration
    @Test
    public void testRegisterAccountSuccess() throws Exception {
        Account acct = new Account("username", "password", "occupation");
        Account registeredAcct = new Account(1, "username", "password", "occupation");

        when(mockAccServ.registerUser(acct.getUsername(), acct.getPassword(), acct.getOccupation()))
                .thenReturn(registeredAcct);

        ResponseEntity<Account> response = controller.registerAccount(acct);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(registeredAcct, response.getBody());
    }

    // // Test registerAccount() with duplicate username
    // @Test
    // public void testRegisterAccountDuplicateUsername() throws Exception {
    //     Account acct = new Account("username", "password", "occupation");

    //     when(mockAccServ.registerUser(acct.getUsername(), acct.getPassword(), acct.getOccupation()))
    //             .thenThrow(new RuntimeException("Username already exists"));

    //     ResponseEntity<Account> response = controller.registerAccount(acct);

    //     assertEquals(409, response.getStatusCodeValue());
    //     assertNull(response.getBody());
    // }

    // // Test registerAccount() with invalid data (exception)
    // @Test
    // public void testRegisterAccountInvalidData() throws Exception {
    //     Account acct = new Account("", "password", "occupation");

    //     when(mockAccServ.registerUser(acct.getUsername(), acct.getPassword(), acct.getOccupation()))
    //             .thenThrow(new RuntimeException("Username already exists"));

    //     ResponseEntity<Account> response = controller.registerAccount(acct);

    //     assertEquals(400, response.getStatusCodeValue());
    //     assertNull(response.getBody());
    // }

    // Test loginAccount() with successful login
    @Test
    public void testLoginAccountSuccess() {
        Account acct = new Account("username", "password", "occupation");

        when(mockAccServ.findAccountByUsernameAndPass(acct.getUsername(), acct.getPassword()))
                .thenReturn(acct);

        ResponseEntity<Account> response = controller.loginAccount(acct);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(acct, response.getBody());
    }

    // Test loginAccount() with failed login (incorrect credentials)
    @Test
    public void testLoginAccountFailed() {
        Account acct = new Account("username", "password", "occupation");

        when(mockAccServ.findAccountByUsernameAndPass(acct.getUsername(), acct.getPassword()))
                .thenReturn(null);

        ResponseEntity<Account> response = controller.loginAccount(acct);

        assertEquals(401, response.getStatusCodeValue());
        assertNull(response.getBody());
    }
}
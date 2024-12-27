package com.example.controller;

import com.example.entity.*;
import com.example.service.*;
import com.example.exception.*;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
// import org.springframework.beans.factory.annotation.*;
// import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

@RestController
@CrossOrigin
public class SocialMediaController {
    MessageService msgServ;
    AccountService accServ;

    @Autowired
    public SocialMediaController(MessageService msgServ, AccountService accServ){
        this.msgServ = msgServ;
        this.accServ = accServ;
    }

    @GetMapping("/api/data")
    public Map<String, String> getData() {
        Map<String, String> data = new HashMap<>();
        data.put("message", "Hello from the API!");
        return data;
    }

    //User Story 1 POST;localhost_8080/register
    //Request Body
    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<Account> registerAccount(@RequestBody Account acct){
        try {
            Account registeredAccount = accServ.registerUser(acct.getUsername(), acct.getPassword(), acct.getOccupation());
            return ResponseEntity.status(200).body(registeredAccount);
        } catch (DuplicateUsernameException e) {
            return ResponseEntity.status(409).build();
        } catch (InvalidRegistrationDataException e) {
            return ResponseEntity.status(400).build();
        }
    }

    //User Story 2 POST;localhost:8080/login
    //Request Body 
    @PostMapping("/login")
    @ResponseBody

    public ResponseEntity<Account> loginAccount(@RequestBody Account acct){
        Account acc = accServ.findAccountByUsernameAndPass(acct.getUsername(), acct.getPassword());
        if(acc!=null){
            return ResponseEntity.status(200).body(acc);
        } else{
            return ResponseEntity.status(401).build();      
        }
    } 
    
    //User Story 3 POST;localhost:8080/messages 
    //Request Body
    @PostMapping("/messages")
    @ResponseBody
    public ResponseEntity<Ticket> createMessage(@RequestBody Ticket msg){
        Ticket retMsg = msgServ.createMessage(msg);
        if (retMsg!=null){
            return ResponseEntity.status(200).body(retMsg);
        } else{
            return ResponseEntity.status(400).build();
        }
    }

    //User Story 4 GET;localhost:8080/messages
    @GetMapping("/messages")
    @ResponseBody
    public ResponseEntity<List<Ticket>> getAllMessages(){
        List<Ticket> msg_list = new ArrayList<Ticket>();
        msg_list = msgServ.getAllMessages();
        return ResponseEntity.status(200).body(msg_list);
    }

    //User Story 5 GET;localhost:8080/messages/{messageId}
    //PathVariable
    @GetMapping("/messages/{messageId}")
    @ResponseBody
    public ResponseEntity<Ticket> getMessageById(@PathVariable int messageId){
        Ticket msg = msgServ.getMessageById(messageId);
        if(msg!=null){
            return ResponseEntity.status(200).body(msg);
        } else{
            return ResponseEntity.status(200).build();        
        }
    }

    //User Story 6 DELETE;localhost:8080/messages/{messageId}
    //PathVariable
    @DeleteMapping("/messages/{messageId}")
    @ResponseBody
    public ResponseEntity<Integer> deleteMessageById(@PathVariable int messageId){
        int delRows = msgServ.deleteMessageById(messageId);
        if (delRows==1){
            return ResponseEntity.status(200).body(1);
        } else{
            return ResponseEntity.status(200).build();
        }
    }

    //User Story 7 PATCH;localhost:8080/messages/{messageId}
    //PathVariable, RequestBody
    @PatchMapping("/messages/{messageId}")
    @ResponseBody 
    public ResponseEntity<?> updateTicketById(@PathVariable String messageId, @RequestBody Map<String, String> msg_text){
        int id = Integer.parseInt(messageId);
        Integer updatedRows = msgServ.updateMessageById(id, msg_text.get("messageText"));
        if(updatedRows==null){
            return ResponseEntity.status(400).build();
        } else{
            return ResponseEntity.status(200).body(updatedRows);           
        }
    }

    //User Story 7 PATCH;localhost:8080/messages/{messageId}
    //PathVariable, RequestBody
    @PatchMapping("/messages/approve/{messageId}")
    @ResponseBody 
    public ResponseEntity<?> updateApproveById(@PathVariable int messageId){
        Integer updatedRows = msgServ.updateMessageById(messageId, "approved");
        if(updatedRows==null){
            return ResponseEntity.status(400).build();
        } else{
            return ResponseEntity.status(200).body(updatedRows);           
        }
    }

    //User Story 8 GET;localhost:8080/accounts/{accountId}/messages
    //PathVariable
    @GetMapping("/accounts/{accountId}/messages")
    @ResponseBody
    public ResponseEntity<List<Ticket>> getMessagesByAccount(@PathVariable int accountId){
        List<Ticket> msg_list = new ArrayList<Ticket>();
        msg_list = msgServ.getMessagesByPostedId(accountId);
        return ResponseEntity.status(200).body(msg_list);
    }

    //User Story 9 GET;localhost:8080/accounts/{accountId}/messages
    //PathVariable
    @GetMapping("/messages/workState/{resState}")
    @ResponseBody
    public ResponseEntity<List<Ticket>> getMessagesByAccount(@PathVariable String resState){
        List<Ticket> msg_list = new ArrayList<Ticket>();
        msg_list = msgServ.getMessagesByResState(resState);
        return ResponseEntity.status(200).body(msg_list);
    }
}

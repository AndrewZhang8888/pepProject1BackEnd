package com.example.service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
import com.example.exception.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {
    AccountRepository accRep;

    @Autowired
    public AccountService(AccountRepository accRep){
        this.accRep = accRep;
    }

    //User Story 1
    public Account registerUser(String username, String password, String occupation) {
        Optional<Account> optionalAcc = accRep.findByUsername(username);
        if(optionalAcc.isPresent()){
            throw new DuplicateUsernameException("Duplicate Username ");
        } else if (username.isEmpty() || password.length()<4){
            throw new InvalidRegistrationDataException("Invalid Data");
        } else{
            Account newAcc = new Account(username, password, occupation);
            Account savedAcc = accRep.save(newAcc);
            return savedAcc;
        }
    }

    //User Story 2
    public Account findAccountByUsernameAndPass(String username, String password){
        Optional<Account> optionalAcc = accRep.findByUsernameAndPassword(username, password);
        if(optionalAcc.isPresent()){
            return optionalAcc.get();
        } else{
            return null;
        }
    }    
}

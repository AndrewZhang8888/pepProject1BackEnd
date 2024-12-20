package com.example.repository;

import com.example.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface MessageRepository extends JpaRepository<Ticket, Integer>{
    //User Story 8
    List<Ticket> findByPostedBy(int poster_id);

    List<Ticket> findByResState(String state);

    //Built-In User Stories: User Story 3, User Story 4, User Story 5, User Story 6, User Story 7, 
}

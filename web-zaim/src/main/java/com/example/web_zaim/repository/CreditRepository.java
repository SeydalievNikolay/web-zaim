package com.example.web_zaim.repository;

import com.example.web_zaim.model.Credit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditRepository extends JpaRepository<Credit, Long> {
    @Query(value = "SELECT id, payment_string, first_payment_date FROM credits", nativeQuery = true)
    List<Credit> findAll();

}

package com.example.quoteapp.repository;

import com.example.quoteapp.model.Quote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface QuoteRepository extends JpaRepository<Quote, UUID> {
    List<Quote> findAllByOrderByCreatedAtDesc();
    Optional<Quote> findFirstByOrderByCreatedAtDesc();
}

package com.example.quoteapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.UUID;

/**
 * JPA entity representing a positive quote.
 */
@Entity
@Table(name = "quotes")
public class Quote {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false, length = 60)
    private String author;

    @Column(nullable = false, length = 240)
    private String message;

    @Column(nullable = false)
    private Instant createdAt;

    protected Quote() {
        // JPA only
    }

    private Quote(String author, String message) {
        this.author = sanitizeAuthor(author);
        this.message = message.trim();
    }

    public static Quote of(String author, String message) {
        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("Quote cannot be empty");
        }
        return new Quote(author, message);
    }

    @PrePersist
    void onCreate() {
        if (createdAt == null) {
            createdAt = Instant.now();
        }
    }

    private static String sanitizeAuthor(String author) {
        if (author == null || author.isBlank()) {
            return "Anonymous Dreamer";
        }
        return author.trim();
    }

    public UUID getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getMessage() {
        return message;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}

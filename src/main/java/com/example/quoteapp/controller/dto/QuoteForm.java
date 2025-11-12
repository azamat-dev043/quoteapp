package com.example.quoteapp.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class QuoteForm {

    @Size(max = 60, message = "Keep author names short and sweet.")
    private String author;

    @NotBlank(message = "Share a few uplifting words!")
    @Size(max = 240, message = "Brevity sparks joy—240 characters max.")
    private String message;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

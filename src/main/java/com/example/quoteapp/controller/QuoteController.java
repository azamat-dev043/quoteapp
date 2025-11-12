package com.example.quoteapp.controller;

import com.example.quoteapp.controller.dto.QuoteForm;
import com.example.quoteapp.model.Quote;
import com.example.quoteapp.service.QuoteService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class QuoteController {

    private final QuoteService quoteService;

    public QuoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<Quote> quotes = quoteService.getAllQuotes();
        populateModel(model, quotes);
        model.addAttribute("quoteForm", new QuoteForm());
        return "index";
    }

    @PostMapping("/quotes")
    public String createQuote(@Valid @ModelAttribute("quoteForm") QuoteForm form,
                              BindingResult bindingResult,
                              Model model) {
        if (bindingResult.hasErrors()) {
            populateModel(model, quoteService.getAllQuotes());
            return "index";
        }

        quoteService.addQuote(form.getAuthor(), form.getMessage());
        return "redirect:/";
    }

    private void populateModel(Model model, List<Quote> quotes) {
        model.addAttribute("quotes", quotes);
        quoteService.getRandomQuote().ifPresent(q -> model.addAttribute("spotlight", q));
        quoteService.getNewestQuote().ifPresent(q -> model.addAttribute("latest", q));
        model.addAttribute("totalQuotes", quotes.size());
    }
}

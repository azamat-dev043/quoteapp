package com.example.quoteapp.service;

import com.example.quoteapp.model.Quote;
import com.example.quoteapp.repository.QuoteRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * Service coordinating quote persistence and simple read helpers.
 */
@Service
public class QuoteService {

    private final QuoteRepository quoteRepository;
    private final Random random = new Random();

    public QuoteService(QuoteRepository quoteRepository) {
        this.quoteRepository = quoteRepository;
    }

    @Transactional(readOnly = true)
    public List<Quote> getAllQuotes() {
        return quoteRepository.findAllByOrderByCreatedAtDesc();
    }

    @Transactional
    public void addQuote(String author, String message) {
        quoteRepository.save(Quote.of(author, message));
    }

    @Transactional(readOnly = true)
    public Optional<Quote> getRandomQuote() {
        long count = quoteRepository.count();
        if (count == 0) {
            return Optional.empty();
        }
        int index = random.nextInt(Math.toIntExact(count));
        return quoteRepository.findAll(PageRequest.of(index, 1))
                .stream()
                .findFirst();
    }

    @Transactional(readOnly = true)
    public Optional<Quote> getNewestQuote() {
        return quoteRepository.findFirstByOrderByCreatedAtDesc();
    }

    @PostConstruct
    @Transactional
    void seedQuotes() {
        if (quoteRepository.count() > 0) {
            return;
        }
        quoteRepository.saveAll(List.of(
                Quote.of("Maya Angelou", "You may encounter many defeats, but you must not be defeated."),
                Quote.of("Albert Einstein", "In the middle of every difficulty lies opportunity."),
                Quote.of("Unknown", "Your smile is a superpower—use it generously today.")
        ));
    }
}

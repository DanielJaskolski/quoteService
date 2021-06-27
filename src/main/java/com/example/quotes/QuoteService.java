package com.example.quotes;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author - djaskolski created on 27.06.2021
 */

@Service
@RequiredArgsConstructor
public class QuoteService implements IQuoteService{
    private final QuoteRepository quoteRepository;


    public Quote addQuote(Quote quote) {
        return quoteRepository.save(quote);
    }

    public Optional<Quote> getQuote(long id) {
        return quoteRepository.findById(id);
    }

    public List<Quote> getAllQuotes() {
        return quoteRepository.findAll();
    }

    public void delete(long id) {
        quoteRepository.deleteById(id);
    }

    public Quote updateQuote(Quote quoteToUpdate) {
        return quoteRepository.save(quoteToUpdate);
    }
}

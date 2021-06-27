package com.example.quotes;

import java.util.List;
import java.util.Optional;

/**
 * @author - djaskolski created on 27.06.2021
 */

public interface IQuoteService {

    Quote addQuote(Quote quote);

    Optional<Quote> getQuote(long id);

    List<Quote> getAllQuotes();

    void delete(long id);

    Quote updateQuote(Quote quoteToUpdate);
}

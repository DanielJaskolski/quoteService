package com.example.quotes;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * @author - djaskolski created on 27.06.2021
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/quote")
public class QuoteController {

    private final IQuoteService quoteService;

    @PostMapping
    public ResponseEntity<Quote> addQuote(@RequestBody @Valid Quote newQuote) {
        Quote result = quoteService.addQuote(newQuote);
        return ResponseEntity.created(URI.create("/quote/" + result.getId())).body(result);
    }

    @GetMapping
    public List<Quote> getAllQuotes() {
        return new ArrayList<>(quoteService.getAllQuotes());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Quote> deleteQuote(@PathVariable long id) {
        quoteService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Quote> update(@PathVariable long id, @RequestBody @Valid Quote quoteToUpdate) {
        if (id != quoteToUpdate.getId()) {
            throw new IllegalArgumentException("Id in URL is different than in body: " + id + " and " +
                    (quoteToUpdate.getId() == 0 ? "empty" : quoteToUpdate.getId()));
        }

        if (quoteService.getQuote(id).isPresent()) {
            Quote updatedQuote = quoteService.updateQuote(quoteToUpdate);
            return ResponseEntity.ok(updatedQuote);
        } else {
            throw new IllegalArgumentException("The quote with id \"" + id + "\" does not exist.");
        }
    }

}

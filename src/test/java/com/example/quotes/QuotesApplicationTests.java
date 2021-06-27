package com.example.quotes;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class QuotesApplicationTests {

    @Autowired
    private QuoteController quoteController;

    @Autowired
    private QuoteRepository quoteRepository;

    @AfterEach
    public void clearDatabase() {
        quoteRepository.deleteAll();
    }

    @Test
    void shouldInRepositoryIs1QuoteWhenAddNewQuote() {
        //given
        Quote newQuote = getQuote();

        //when
        quoteController.addQuote(newQuote);
        List<Quote> allQuotes = quoteRepository.findAll();

        //than
        assertThat(allQuotes.size()).isEqualTo(1);
    }

    @Test
    void shouldThrowConstraintViolationExceptionWhenNewQuoteIsShorter() {
        //given
        Quote newQuote = new Quote("x", "xyz abc");

        //when
        Exception exception = assertThrows(ConstraintViolationException.class, () -> {
            quoteController.addQuote(newQuote);
        });
        String expectedMessage = "The length of the quote must be between 3 and 250 characters.";
        String actualMessage = exception.getMessage();

        //than
        assertThat(actualMessage).contains(expectedMessage);
    }

    @Test
    void shouldThrowConstraintViolationExceptionWhenAddEmptyQuote() {
        //given
        Quote newQuote = new Quote(" ", "xyz abc");

        //when
        Exception exception = assertThrows(ConstraintViolationException.class, () -> {
            quoteController.addQuote(newQuote);
        });
        String expectedMessage = "The quote cannot be empty!";
        String actualMessage = exception.getMessage();

        //than
        assertThat(actualMessage).contains(expectedMessage);

    }

    @Test
    void shouldThrowConstraintViolationExceptionWhenNewQuoteWithShorterAuthor() {
        //given
        Quote newQuote = new Quote("xyz abc", "w");

        //when
        Exception exception = assertThrows(ConstraintViolationException.class, () -> {
            quoteController.addQuote(newQuote);
        });
        String expectedMessage = "The length of the author must be between 3 and 100 characters.";
        String actualMessage = exception.getMessage();

        //than
        assertThat(actualMessage).contains(expectedMessage);
    }

    @Test
    void shouldThrowConstraintViolationExceptionWhenNewQuoteWithEmptyAuthor() {
        //given
        Quote newQuote = new Quote("xyz abc", "");

        //when
        Exception exception = assertThrows(ConstraintViolationException.class, () -> {
            quoteController.addQuote(newQuote);
        });
        String expectedMessage = "The author cannot be empty!";
        String actualMessage = exception.getMessage();

        //than
        assertThat(actualMessage).contains(expectedMessage);
    }


    @Test
    void shouldGetAllQuotes() {
        //given
        add3Quotes();

        //when
        List<Quote> allQuotes = quoteController.getAllQuotes();

        //than
        assertThat(allQuotes.size()).isEqualTo(3);
    }

    @Test
    void shouldBe2QuotesAfterDelete1Quote() {
        //given
        add3Quotes();
        List<Quote> allQuotesBeforeDelete = quoteController.getAllQuotes();
        long idFirstQuote = allQuotesBeforeDelete.get(0).getId();

        //when
        quoteController.deleteQuote(idFirstQuote);
        List<Quote> allQuotes = quoteController.getAllQuotes();

        //than
        assertThat(allQuotes.size()).isEqualTo(2);
    }

    @Test
    void shouldCorrectUpdateQuote() {
        //given
        add3Quotes();
        List<Quote> allQuotesBeforeDelete = quoteController.getAllQuotes();
        Quote firstQuote = allQuotesBeforeDelete.get(0);
        long idFirstQuote = firstQuote.getId();

        //when
        firstQuote.setQuote("Test quote");
        firstQuote.setAuthor("Test author");
        ResponseEntity<Quote> response = quoteController.update(idFirstQuote, firstQuote);
        Quote updatedQuote = response.getBody();

        //than
        assertThat(updatedQuote.getQuote()).isEqualTo(firstQuote.getQuote());
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenUpdateQuoteWithDifferentInUrlAndBody() {
        //given
        add3Quotes();
        List<Quote> allQuotesBeforeDelete = quoteController.getAllQuotes();
        Quote firstQuote = allQuotesBeforeDelete.get(0);
        long idFirstQuote = firstQuote.getId();
        long idInParam = idFirstQuote + 10;

        //when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            quoteController.update(idInParam, firstQuote);
        });
        String expectedMessage = "Id in URL is different than in body: " + idInParam + " and " + firstQuote.getId();
        String actualMessage = exception.getMessage();

        //than
        assertThat(actualMessage).contains(expectedMessage);
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenIdUpdatesNonExistQuote() {
        //given
        add3Quotes();
        List<Quote> allQuotesBeforeDelete = quoteController.getAllQuotes();
        Quote firstQuote = allQuotesBeforeDelete.get(0);
        long idFirstQuote = firstQuote.getId();
        quoteRepository.deleteById(idFirstQuote);

        //when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            quoteController.update(idFirstQuote, firstQuote);
        });
        String expectedMessage = "The quote with id \"" + idFirstQuote + "\" does not exist.";
        String actualMessage = exception.getMessage();

        //than
        assertThat(actualMessage).contains(expectedMessage);
    }


    private Quote getQuote() {
        return new Quote("Your time is limited, so don’t waste it living someone else’s life." +
                " Don’t be trapped by dogma – which is living with the results of other people’s thinking.",
                "Steve Jobs");
    }

    private void add3Quotes() {
        Quote newQuote1 = new Quote("You only live once, but if you do it right, once is enough.", "Mae West");
        Quote newQuote2 = new Quote("In three words I can sum up everything I've learned about life: it goes on.", "Robert Frost");
        Quote newQuote3 = new Quote("Your time is limited, so don’t waste it living someone else’s life. " +
                "Don’t be trapped by dogma – which is living with the results of other people’s thinking.",
                "Steve Jobs");

        quoteRepository.save(newQuote1);
        quoteRepository.save(newQuote2);
        quoteRepository.save(newQuote3);
    }


}

package com.example.quotes;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author - djaskolski created on 27.06.2021
 */

@Entity
public class Quote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @NotBlank(message = "The quote cannot be empty!")
    @Size(min = 3, max = 250, message = "The length of the quote must be between 3 and 250 characters.")
    @Getter
    @Setter
    private String quote;

    @NotBlank(message = "The author cannot be empty!")
    @Size(min = 3, max = 100, message = "The length of the author must be between 3 and 100 characters.")
    @Getter
    @Setter
    private String author;

    protected Quote() {
    }

    public Quote(String quote, String author) {
        this.quote = quote;
        this.author = author;
    }
}

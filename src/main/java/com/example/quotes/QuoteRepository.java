package com.example.quotes;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author - djaskolski created on 27.06.2021
 */

public interface QuoteRepository extends JpaRepository<Quote, Long> {
}

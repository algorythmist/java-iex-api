package com.tecacet.iex.api.impl;

import com.tecacet.iex.api.IexClient;
import com.tecacet.iex.api.Quote;
import com.tecacet.iex.api.Range;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import lombok.val;

class OkHttpIexClientTest {

    @Test
    void invalidToken() {
        IexClient iexClient = IexClient.getInstance("ax");
        try {
            iexClient.getDelayedQuote("IBM");
            fail("Exception should be thrown");
        } catch (Exception ioe) {
            assertEquals("Call failed with code 403 and message: The API key provided is not valid.", ioe.getMessage());
        }
    }

    @Test
    void validQuote() {
        IexClient iexClient = IexClient.getInstance(new EnvironmentTokenSupplier());
        Quote quote = iexClient.getDelayedQuote("IBM");
        assertEquals("IBM", quote.getSymbol());
        assertEquals("International Business Machines Corp.", quote.getCompanyName());
    }

    @Test
    void getQuotes() {
        IexClient iexClient = IexClient.getInstance();
        val quotes1m = iexClient.getDailyQuotes("NFLX", Range.ONE_MONTH);
        assertTrue(quotes1m.size() > 19);

        val quotes1y = iexClient.getDailyQuotes("NFLX", Range.ONE_YEAR);
        assertTrue(quotes1y.size() > 250);
    }

    @Test
    void getDividends() {
        val iexClient = IexClient.getInstance();
        val dividends = iexClient.getDividends("AAPL", Range.FIVE_YEARS);
        assertTrue(dividends.size() > 0); //This API appears to be faulty

        val dividend = dividends.get(0);
        assertEquals("Ordinary Shares", dividend.getDescription());
        assertEquals("quarterly", dividend.getFrequency());
    }

    @Test
    void getSplits() {
        val iexClient = IexClient.getInstance();
        val splits = iexClient.getSplits("AAPL", Range.FIVE_YEARS);
        assertEquals(1, splits.size());
        val split = splits.get(0);
        assertEquals(4, split.getToFactor());
        assertEquals(1, split.getFromFactor());
        assertEquals(LocalDate.of(2020, 8, 31), split.getExDate());
    }
}
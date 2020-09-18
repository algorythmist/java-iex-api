package com.tecacet.iex.api.impl;

import com.tecacet.iex.api.IexClient;
import com.tecacet.iex.api.Quote;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class OkHttpIexClientTest {

    @Test
    void invalidToken() {
        IexClient iexClient = IexClient.getInstance("ax");
        try {
            iexClient.getDelayedQuote("IBM");
            fail("Exception should be thrown");
        } catch (Exception ioe) {
            assertEquals("The API key provided is not valid.", ioe.getMessage());
        }
    }

    @Test
    void validQuote() {
        IexClient iexClient = IexClient.getInstance(new EnvironmentTokenSupplier());
        Quote quote = iexClient.getDelayedQuote("IBM");
        System.out.println(quote);
    }
}
package com.tecacet.iex.api.impl;

import com.tecacet.iex.api.IexClient;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class OkHttpIexClientTest {

    @Test
    void invalidToken() {
        IexClient iexClient = new OkHttpIexClient("adx");
        try {
            iexClient.getDelayedQuote("IBM");
            fail("Exception should be thrown");
        } catch (IOException ioe) {
            assertEquals("The API key provided is not valid.", ioe.getMessage());
        }
    }
}
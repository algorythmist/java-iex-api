package com.tecacet.iex.api;

import com.tecacet.iex.api.impl.OkHttpIexClient;

import java.io.IOException;
import java.util.Map;

public interface IexClient {

    Quote getDelayedQuote(String symbol) throws IOException;

    Map<String, Quote> getDelayedQuotes(String... sybmols) throws IOException;

    static IexClient getInstance(String token) {
        return new OkHttpIexClient(token);
    }
}

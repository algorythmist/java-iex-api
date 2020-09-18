package com.tecacet.iex.api;

import com.tecacet.iex.api.impl.FixedTokenSupplier;
import com.tecacet.iex.api.impl.OkHttpIexClient;

import java.io.IOException;
import java.util.Map;

public interface IexClient {

    static IexClient getInstance(String token) {
        return getInstance(new FixedTokenSupplier(token));
    }

    static IexClient getInstance(TokenSupplier tokenSupplier) {
        return new OkHttpIexClient(tokenSupplier);
    }

    Quote getDelayedQuote(String symbol);

    Map<String, Quote> getDelayedQuotes(String... symbols);
}

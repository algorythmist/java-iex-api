package com.tecacet.iex.api;

import com.tecacet.iex.api.impl.EnvironmentTokenSupplier;
import com.tecacet.iex.api.impl.FixedTokenSupplier;
import com.tecacet.iex.api.impl.OkHttpIexClient;

import lombok.SneakyThrows;

import java.util.List;
import java.util.Map;

public interface IexClient {

    static IexClient getInstance(String token) {
        return getInstance(new FixedTokenSupplier(token));
    }

    static IexClient getInstance(TokenSupplier tokenSupplier) {
        return new OkHttpIexClient(tokenSupplier);
    }

    static IexClient getInstance() {
         return new OkHttpIexClient(new EnvironmentTokenSupplier());
    }

    Quote getDelayedQuote(String symbol);

    Map<String, Quote> getDelayedQuotes(String... symbols);

    List<DailyQuote> getDailyQuotes(String symbol, Range range);

    @SneakyThrows
    List<Dividend>  getDividends(String symbol, Range range);

    @SneakyThrows
    List<Split> getSplits(String symbol, Range range);
}

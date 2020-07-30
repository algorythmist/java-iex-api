package com.tecacet.iex.api.impl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecacet.iex.api.IexClient;
import com.tecacet.iex.api.Quote;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
class QuoteWrapper {

    private Quote quote;
}

@RequiredArgsConstructor
@Slf4j
public class OkHttpIexClient implements IexClient {

    private static final String URL_BATCH_BASE = "https://cloud.iexapis.com/stable/stock/market/batch";

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final OkHttpClient httpClient = new OkHttpClient();
    private final String token;


    @Override
    public Quote getDelayedQuote(String symbol) throws IOException {
        Map<String, Quote> quotes = getDelayedQuotes(symbol);
        return quotes.get(symbol);
    }



    @Override
    public Map<String, Quote> getDelayedQuotes(String... sybmols) throws IOException {
        String symbolsString = String.join(",", sybmols);
        HttpUrl.Builder urlBuilder = HttpUrl.parse(URL_BATCH_BASE).newBuilder();
        urlBuilder.addQueryParameter("token", token);
        urlBuilder.addQueryParameter("symbols", symbolsString);
        urlBuilder.addQueryParameter("types", "quote");
        String url = urlBuilder.build().toString();
        log.info(url);

        Request request = new Request.Builder().url(url).build();
        Response response = httpClient.newCall(request).execute();
        String content = response.body().string();
        if (response.isSuccessful()) {
            return parseQuotes(content);
        }
        throw new IOException(content);
    }

    private Map<String, Quote> parseQuotes(String json) throws JsonProcessingException {
        JsonNode root = objectMapper.readTree(json);
        Map<String, Quote> quotes = new HashMap<>();
        for (Iterator<Map.Entry<String, JsonNode>> it = root.fields(); it.hasNext(); ) {
            Map.Entry<String, JsonNode> entry = it.next();
            QuoteWrapper qw = objectMapper.convertValue(entry.getValue(), QuoteWrapper.class);
            quotes.put(entry.getKey(), qw.getQuote());
        }
        return quotes;
    }
}

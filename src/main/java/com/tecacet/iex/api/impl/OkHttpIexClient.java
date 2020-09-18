package com.tecacet.iex.api.impl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.tecacet.iex.api.DailyQuote;
import com.tecacet.iex.api.Dividend;
import com.tecacet.iex.api.IexClient;
import com.tecacet.iex.api.Quote;
import com.tecacet.iex.api.Range;
import com.tecacet.iex.api.Split;
import com.tecacet.iex.api.TokenSupplier;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
class QuoteWrapper {

    private Quote quote;
}


@RequiredArgsConstructor
@Slf4j
public class OkHttpIexClient implements IexClient {

    private static final String URL_BASE = "https://cloud.iexapis.com/v1";
    private static final String URL_BATCH_BASE = URL_BASE + "/stock/market/batch";
    private static final String CHART_URL = URL_BASE + "/stock/%s/chart/%s";
    private static final String DIVIDEND_URL = URL_BASE + "/stock/%s/dividends/%s";
    private static final String SPLIT_URL = URL_BASE + "/stock/%s/splits/%s";

    private final ObjectMapper objectMapper = new ObjectMapper()
            .findAndRegisterModules()
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    private final OkHttpClient httpClient = new OkHttpClient();
    private final TokenSupplier tokenSupplier;

    @Override
    public Quote getDelayedQuote(String symbol) {
        Map<String, Quote> quotes = getDelayedQuotes(symbol);
        return quotes.get(symbol);
    }

    @Override
    @SneakyThrows
    public Map<String, Quote> getDelayedQuotes(String... symbols) {
        String symbolsString = String.join(",", symbols);
        HttpUrl.Builder urlBuilder = HttpUrl.parse(URL_BATCH_BASE).newBuilder();
        urlBuilder.addQueryParameter("token", tokenSupplier.getToken());
        urlBuilder.addQueryParameter("symbols", symbolsString);
        urlBuilder.addQueryParameter("types", "quote");
        String url = urlBuilder.build().toString();
        log.info("Calling {} for symbols {}", URL_BATCH_BASE, symbolsString);
        val content = execute(url);
        return parseQuotes(content);

    }

    @Override
    @SneakyThrows
    public List<DailyQuote> getDailyQuotes(String symbol, Range range) {
        String url = String.format(CHART_URL, symbol, range.getCode());
        log.info("Calling {}", url);
        url = buildUrl(url);
        val content = execute(url);
        return objectMapper.readValue(content, new TypeReference<List<DailyQuote>>() {
        });
    }

    @Override
    @SneakyThrows
    public List<Dividend> getDividends(String symbol, Range range) {
        String url = String.format(DIVIDEND_URL, symbol, range.getCode());
        log.info("Calling {}", url);
        url = buildUrl(url);
        val content = execute(url);
        return objectMapper.readValue(content, new TypeReference<List<Dividend>>() {
        });
    }

    @Override
    @SneakyThrows
    public List<Split> getSplits(String symbol, Range range) {
        String url = String.format(SPLIT_URL, symbol, range.getCode());
        log.info("Calling {}", url);
        url = buildUrl(url);
        val content = execute(url);
        return objectMapper.readValue(content, new TypeReference<List<Split>>() {
        });
    }

    private String buildUrl(String url) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        urlBuilder.addQueryParameter("token", tokenSupplier.getToken());
        return urlBuilder.build().toString();
    }

    private String execute(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Response response = httpClient.newCall(request).execute();
        String content = response.body().string();
        if (!response.isSuccessful()) {
            throw new IOException(String.format("Call failed with code %d and message: %s", response.code(), content));
        }
        return content;
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

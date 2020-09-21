package com.tecacet.iex.api;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Quote {

    private String symbol;
    private String companyName;
    private BigDecimal iexRealtimePrice;
    private Long iexRealtimeSize;
    private Long iexLastUpdated;

    private BigDecimal open;
    private BigDecimal close;
    private BigDecimal high;
    private BigDecimal low;
    private BigDecimal latestPrice;

    private Long volume;
    private Long latestVolume;
    private Long avgTotalVolume;
    private Long marketCap;

    private BigDecimal week52High;
    private BigDecimal week52Low;

    private BigDecimal ytdChange;
    private BigDecimal peRatio;

    private boolean isUSMarketOpen;
    //TODO: add the rest: iex properties
}

package com.tecacet.iex.api;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Quote {

    private String symbol;
    private String companyName;
    private BigDecimal iexRealtimePrice;
    private Long iexRealtimeSize;
    private Long iexLastUpdated;
    //TODO: add the rest
}

package com.tecacet.iex.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class DailyQuote {

    private LocalDate date;
    private Double open;
    private Double close;
    private Double high;
    private Double low;
    private Long volume;

}

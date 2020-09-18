package com.tecacet.iex.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Dividend {

    LocalDate exDate;
    LocalDate paymentDate;
    LocalDate recordDate;
    LocalDate declaredDate;
    BigDecimal amount;
    String frequency;
    String description;
    String flag;
}

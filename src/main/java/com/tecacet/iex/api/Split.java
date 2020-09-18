package com.tecacet.iex.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Split {

    private LocalDate exDate;
    private LocalDate declaredDate;
    private Integer toFactor;
    private Integer fromFactor;
}

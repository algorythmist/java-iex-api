package com.tecacet.iex.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Range {
    ONE_YEAR("1y"), TWO_YEARS("2y"), FIVE_YEARS("5y"), YTD("ytd"),
    ONE_MONTH("1m"), THREE_MONTHS("3m"), SIX_MONTHS("6m");

    private final String code;

}

package com.tecacet.iex.api.impl;

import com.tecacet.iex.api.TokenSupplier;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FixedTokenSupplier implements TokenSupplier {

    private final String token;

    @Override
    public String getToken() {
        return token;
    }
}

package com.tecacet.iex.api.impl;

import com.tecacet.iex.api.TokenSupplier;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class EnvironmentTokenSupplier implements TokenSupplier  {

    private final String tokenVariableName;

    public EnvironmentTokenSupplier() {
        this("IEX_TOKEN");
    }

    @Override
    public String getToken() {
        Map<String,String> env = System.getenv();
        return env.get(tokenVariableName);
    }
}

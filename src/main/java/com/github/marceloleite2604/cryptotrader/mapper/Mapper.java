package com.github.marceloleite2604.cryptotrader.mapper;

public interface Mapper<I, O> {

    default O mapTo(I input) {
        throw new UnsupportedOperationException("Not implemented.");
    }

    default I mapFrom(O output) {
        throw new UnsupportedOperationException("Not implemented.");
    }

}

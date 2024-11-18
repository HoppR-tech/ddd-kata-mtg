package com.example.mtg.model;

public record CardName(String value) {

    public CardName {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("value cannot be null or empty");
        }
    }

    public static CardName of(String value) {
        return new CardName(value);
    }

}

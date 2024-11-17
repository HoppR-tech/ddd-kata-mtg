package com.example.mtg.model;

public record Number(int value) {

    public Number {
        if (value < 0) {
            throw new IllegalArgumentException("Number cannot be negative");
        }
    }

    public static Number of(int value) {
        return new Number(value);
    }

}

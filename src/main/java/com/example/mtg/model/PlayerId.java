package com.example.mtg.model;

public record PlayerId(String value) {

    public static PlayerId of(String value) {
        return new PlayerId(value);
    }

}

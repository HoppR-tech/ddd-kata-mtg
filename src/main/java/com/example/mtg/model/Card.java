package com.example.mtg.model;

public record Card(String name) {

    public static Card of(String name) {
        return new Card(name);
    }

}

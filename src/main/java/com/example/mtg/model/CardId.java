package com.example.mtg.model;

import com.github.f4b6a3.ulid.Ulid;
import com.github.f4b6a3.ulid.UlidCreator;

public record CardId(String value) {

    public static CardId of(String value) {
        return new CardId(value);
    }

    public static CardId generate() {
        Ulid ulid = UlidCreator.getUlid();
        return new CardId(ulid.toLowerCase());
    }

}

package com.example.mtg.model;

import lombok.Builder;

@Builder
public record Card(CardId id, CardName name) {

    public Card {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        if (name == null) {
            throw new IllegalArgumentException("name cannot be null");
        }
    }

    public boolean hasId(CardId id) {
        return this.id.equals(id);
    }
}

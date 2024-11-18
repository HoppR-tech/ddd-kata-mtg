package com.example.mtg.model;

import java.util.function.Consumer;

public sealed interface Zone permits Exile, Graveyard, Hand {

    default Name name() {
        return switch (this) {
            case Graveyard ignored -> Name.GRAVEYARD;
            case Hand ignored -> Name.HAND;
            case Exile ignored -> Name.EXILE;
        };
    }

    void find(CardId cardId, Consumer<Card> output);

    void remove(CardId cardId, Consumer<Card> output);

    enum Name {
        HAND,
        GRAVEYARD,
        EXILE;
    }
}

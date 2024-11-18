package com.example.mtg.fixtures;

import com.example.mtg.model.Card;
import com.example.mtg.model.CardId;
import com.example.mtg.model.CardName;

public class CardFixtures {

    public static final CardName DIABOLIC_TUTOR = CardName.of("Diabolic Tutor");
    public static final CardName DEMONIC_TUTOR = CardName.of("Demonic Tutor");
    public static final CardName DAUTHI_VOIDWALKER = CardName.of("Dauthi Voidwalker");
    public static final CardName PLAIN = CardName.of("Plain");
    public static final CardName SWAMP = CardName.of("Swamp");

    public static Card card(CardName cardName) {
        return Card.builder()
                .id(CardId.generate())
                .name(cardName)
                .build();
    }

    public static Card card(CardId cardId, CardName cardName) {
        return Card.builder()
                .id(cardId)
                .name(cardName)
                .build();
    }

}

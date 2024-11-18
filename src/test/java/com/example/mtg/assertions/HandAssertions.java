package com.example.mtg.assertions;

import com.example.mtg.model.Card;
import com.example.mtg.model.CardName;
import com.example.mtg.model.Hand;
import lombok.RequiredArgsConstructor;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class HandAssertions {

    private final Hand hand;

    public HandAssertions hasSize(int size) {
        assertThat(hand.size()).isEqualTo(size);
        return this;
    }

    public HandAssertions doesContain(CardName... cardNames) {
        assertThat(hand.cards())
                .map(Card::name)
                .containsAnyOf(cardNames);
        return this;
    }

    public HandAssertions doesNotContain(CardName... cardNames) {
        assertThat(hand.cards())
                .map(Card::name)
                .doesNotContain(cardNames);
        return this;
    }

    public void containsOnlyOnce(CardName cardName) {
        assertThat(hand.cards())
                .map(Card::name)
                .containsOnlyOnce(cardName);
    }

    public static HandAssertions assertThatHand(Hand hand) {
        return new HandAssertions(hand);
    }
}

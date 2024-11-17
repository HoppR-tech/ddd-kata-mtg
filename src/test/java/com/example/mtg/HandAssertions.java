package com.example.mtg;

import com.example.mtg.model.Card;
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

    public HandAssertions doesContain(Card... card) {
        assertThat(hand.cards()).containsAnyOf(card);
        return this;
    }

    public static HandAssertions assertThatHand(Hand hand) {
        return new HandAssertions(hand);
    }

}

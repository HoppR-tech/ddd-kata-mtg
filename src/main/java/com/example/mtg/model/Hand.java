package com.example.mtg.model;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Accessors(fluent = true)
@EqualsAndHashCode
@ToString
public class Hand {

    private final List<Card> cards;

    private Hand(List<Card> cards) {
        this.cards = new ArrayList<>(cards);
    }

    public void add(List<Card> cards) {
        this.cards.addAll(cards);
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    public int size() {
        return cards.size();
    }

    public List<Card> cards() {
        return List.copyOf(cards);
    }

    public void discard(Card card) {
        cards.remove(card);
    }

    public static Hand composedOf(Card... cards) {
        return new Hand(Arrays.asList(cards));
    }

    public static Hand empty() {
        return composedOf();
    }
}

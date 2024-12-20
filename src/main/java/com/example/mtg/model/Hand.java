package com.example.mtg.model;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

@Accessors(fluent = true)
@EqualsAndHashCode
@ToString
public final class Hand implements Zone {

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

    @Override
    public void find(CardId cardId, Consumer<Card> output) {
        cards.stream()
                .filter(card -> card.hasId(cardId))
                .findAny()
                .ifPresent(output);
    }

    @Override
    public void remove(CardId cardId, Consumer<Card> output) {
        find(cardId, card -> {
            cards.remove(card);
            output.accept(card);
        });
    }

    public static Hand composedOf(Card... cards) {
        return new Hand(Arrays.asList(cards));
    }

    public static Hand empty() {
        return composedOf();
    }
}

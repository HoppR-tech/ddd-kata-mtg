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
public final class Exile implements Zone {

    private final List<Card> cards;

    public Exile(List<Card> cards) {
        this.cards = new ArrayList<>(cards);
    }

    public void add(List<Card> cards) {
        this.cards.addAll(cards);
    }

    public void add(Card... cards) {
        add(Arrays.asList(cards));
    }

    public int size() {
        return cards.size();
    }

    public List<Card> cards() {
        return List.copyOf(cards);
    }

    public static Exile composedOf(List<Card> cards) {
        return new Exile(cards);
    }

    public static Exile composedOf(Card... cards) {
        return composedOf(Arrays.asList(cards));
    }

    public static Exile empty() {
        return composedOf();
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
}

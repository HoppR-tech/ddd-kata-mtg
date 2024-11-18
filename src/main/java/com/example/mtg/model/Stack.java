package com.example.mtg.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Stack {

    private final List<Card> cards;

    private Stack(List<Card> cards) {
        this.cards = new ArrayList<>(cards);
    }

    public List<Card> cards() {
        return List.copyOf(cards);
    }

    public void add(Card card) {
        cards.add(card);
    }

    public static Stack composedOf(List<Card> cards) {
        return new Stack(cards);
    }

    public static Stack composedOf(Card... cards) {
        return composedOf(Arrays.asList(cards));
    }

    public static Stack empty() {
        return composedOf();
    }

}

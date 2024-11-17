package com.example.mtg.model;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

@RequiredArgsConstructor
@Accessors(fluent = true)
@EqualsAndHashCode
@ToString
public class Library {

    private final Deque<Card> cards;

    public void draw(Number numberOfCards, BiConsumer<List<Card>, Boolean> output) {
        List<Card> drawnCards = new ArrayList<>();
        boolean overdrawn = false;

        for (int i = 0; i < numberOfCards.value(); i++) {
            if (isEmpty()) {
                overdrawn = true;
                break;
            }

            Card card = cards.pop();
            drawnCards.add(card);
        }

        output.accept(drawnCards, overdrawn);
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

    public Optional<Card> first() {
        return isEmpty()
                ? Optional.empty()
                : Optional.ofNullable(cards.getFirst());
    }

    public static Library composedOf(Card... cards) {
        Deque<Card> libraryCards = new ArrayDeque<>(Arrays.asList(cards));
        return new Library(libraryCards);
    }

    public static Library empty() {
        return composedOf();
    }

}

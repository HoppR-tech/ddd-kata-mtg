package com.example.mtg.model;

import com.example.mtg.use_case.draw.CardsDrawn;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@RequiredArgsConstructor
@Accessors(fluent = true)
@EqualsAndHashCode
@ToString
public class Library {

    private final Deque<Card> cards;

    public void draw(Number numberOfCards, Consumer<CardsDrawn> output) {
        CardsDrawn.CardsDrawnBuilder eventBuilder = CardsDrawn.builder();

        for (int i = 0; i < numberOfCards.value(); i++) {
            if (isEmpty()) {
                eventBuilder.libraryOverdrawn(true);
                break;
            }

            Card card = cards.pop();
            eventBuilder.card(card);
        }

        CardsDrawn event = eventBuilder.build();
        output.accept(event);
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

}

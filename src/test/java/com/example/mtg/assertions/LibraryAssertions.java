package com.example.mtg.assertions;

import com.example.mtg.model.Card;
import com.example.mtg.model.CardName;
import com.example.mtg.model.Library;
import lombok.RequiredArgsConstructor;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class LibraryAssertions {

    private final Library library;

    public LibraryAssertions isEmpty() {
        assertThat(library.isEmpty()).isTrue();
        return this;
    }

    public LibraryAssertions hasSize(int size) {
        assertThat(library.size()).isEqualTo(size);
        return this;
    }

    public LibraryAssertions firstIs(CardName cardName) {
        assertThat(library.first())
                .map(Card::name)
                .contains(cardName);
        return this;
    }

    public LibraryAssertions doesNotContain(CardName cardName) {
        assertThat(library.cards())
                .map(Card::name)
                .doesNotContain(cardName);
        return this;
    }

    public static LibraryAssertions assertThatLibrary(Library library) {
        return new LibraryAssertions(library);
    }
}

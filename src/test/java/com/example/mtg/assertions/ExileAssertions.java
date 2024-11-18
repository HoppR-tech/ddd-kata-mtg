package com.example.mtg.assertions;

import com.example.mtg.model.Card;
import com.example.mtg.model.CardName;
import com.example.mtg.model.Exile;
import lombok.RequiredArgsConstructor;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class ExileAssertions {

    private final Exile exile;

    public ExileAssertions hasSize(int size) {
        assertThat(exile.size()).isEqualTo(size);
        return this;
    }

    public ExileAssertions doesContain(CardName... cardNames) {
        assertThat(exile.cards())
                .map(Card::name)
                .containsAnyOf(cardNames);
        return this;
    }

    public ExileAssertions doesNotContain(CardName... cardNames) {
        assertThat(exile.cards())
                .map(Card::name)
                .doesNotContain(cardNames);
        return this;
    }

    public static ExileAssertions assertThatExile(Exile exile) {
        return new ExileAssertions(exile);
    }
}

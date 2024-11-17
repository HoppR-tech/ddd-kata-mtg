package com.example.mtg.assertions;

import com.example.mtg.model.Card;
import com.example.mtg.model.Graveyard;
import lombok.RequiredArgsConstructor;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class GraveyardAssertions {

    private final Graveyard graveyard;

    public GraveyardAssertions hasSize(int size) {
        assertThat(graveyard.size()).isEqualTo(size);
        return this;
    }

    public GraveyardAssertions doesContain(Card... card) {
        assertThat(graveyard.cards()).containsAnyOf(card);
        return this;
    }

    public static GraveyardAssertions assertThatGraveyard(Graveyard graveyard) {
        return new GraveyardAssertions(graveyard);
    }
}

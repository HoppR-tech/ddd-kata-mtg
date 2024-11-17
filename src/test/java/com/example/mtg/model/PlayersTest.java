package com.example.mtg.model;

import org.junit.jupiter.api.Test;

import static com.example.mtg.fixtures.GameFixtures.RICHARD_GARFIELD;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class PlayersTest {

    @Test
    void a_game_must_be_played_by_at_least_two_players() {
        Player playerOne = Player.builder()
                .id(RICHARD_GARFIELD)
                .build();

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> Players.from(playerOne))
                .withMessage("A game must be played by at least two players");
    }
}

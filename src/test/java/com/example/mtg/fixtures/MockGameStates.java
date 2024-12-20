package com.example.mtg.fixtures;

import com.example.mtg.assertions.ExileAssertions;
import com.example.mtg.assertions.GraveyardAssertions;
import com.example.mtg.assertions.HandAssertions;
import com.example.mtg.assertions.LibraryAssertions;
import com.example.mtg.assertions.StackAssertions;
import com.example.mtg.model.Game;
import com.example.mtg.model.GameId;
import com.example.mtg.model.Player;
import com.example.mtg.model.PlayerId;

import static com.example.mtg.assertions.ExileAssertions.assertThatExile;
import static com.example.mtg.assertions.GraveyardAssertions.assertThatGraveyard;
import static com.example.mtg.assertions.HandAssertions.assertThatHand;
import static com.example.mtg.assertions.LibraryAssertions.assertThatLibrary;
import static org.assertj.core.api.Assertions.assertThat;

public class MockGameStates implements Game.Lookup {

    private Game game;
    private int timesSaveHasBeenCalled = 0;

    private MockGameStates(Game game) {
        this.game = game;
    }

    @Override
    public Game lookup(GameId id) {
        if (!game.hasId(id)) {
            throw new IllegalArgumentException("Game '%s' has not been found".formatted(id.value()));
        }
        return game;
    }

    @Override
    public void save(Game game) {
        this.timesSaveHasBeenCalled++;
        this.game = game;
    }

    public static MockGameStates init(Game game) {
        return new MockGameStates(game);
    }

    public Assertions assertions() {
        return new Assertions();
    }

    public final class Assertions {

        public Assertions gameIsOver() {
            assertThat(game.isOver()).isTrue();
            return this;
        }

        public Assertions playerLost(PlayerId playerId) {
            Player actual = game.players().get(playerId);
            assertThat(actual.hasLost()).isTrue();
            return this;
        }

        public Assertions gameHasNotBeenSaved() {
            assertThat(timesSaveHasBeenCalled).isZero();
            return this;
        }

        public Assertions gameHasBeenSaved() {
            assertThat(timesSaveHasBeenCalled).isPositive();
            return this;
        }

        public Assertions playerWon(PlayerId playerId) {
            Player actual = game.players().get(playerId);
            assertThat(actual.hasWon()).isTrue();
            return this;
        }

        public HandAssertions handOf(PlayerId playerId) {
            return assertThatHand(game.handOf(playerId));
        }

        public GraveyardAssertions graveyardOf(PlayerId playerId) {
            return assertThatGraveyard(game.graveyardOf(playerId));
        }

        public LibraryAssertions libraryOf(PlayerId playerId) {
            return assertThatLibrary(game.libraryOf(playerId));
        }

        public ExileAssertions exileOf(PlayerId playerId) {
            return assertThatExile(game.exileOf(playerId));
        }

        public StackAssertions stack() {
            return new StackAssertions(game.stack());
        }
    }
}

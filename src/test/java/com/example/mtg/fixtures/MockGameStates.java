package com.example.mtg.fixtures;

import com.example.mtg.model.Game;
import com.example.mtg.model.GameId;
import com.example.mtg.model.Player;
import com.example.mtg.model.PlayerId;

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

        public Assertions gameHasBeenSaved() {
            assertThat(timesSaveHasBeenCalled).isPositive();
            return this;
        }

        public Assertions playerWon(PlayerId playerId) {
            Player actual = game.players().get(playerId);
            assertThat(actual.hasWon()).isTrue();
            return this;
        }
    }
}

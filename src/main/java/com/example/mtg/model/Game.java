package com.example.mtg.model;

import com.example.mtg.use_case.draw.CardsDrawn;
import com.example.mtg.use_case.draw.DrawCards;
import com.example.mtg.use_case.player_lost.PlayerLost;
import com.example.mtg.use_case.player_won.PlayerWon;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.List;

@Accessors(fluent = true)
public class Game {

    @Getter
    private final GameId id;
    @Getter
    private final Players players;
    private final StateBasedActions stateBasedActions = new StateBasedActions();

    @Builder
    private Game(GameId id, Players players) {
        this.id = id;
        this.players = players;
    }

    @Getter
    private boolean isOver;

    public boolean hasId(GameId id) {
        return this.id.equals(id);
    }

    public void gameOver() {
        isOver = true;
    }

    private Player getPlayer(PlayerId playerId) {
        return players.get(playerId);
    }

    public void accept(DrawCards command) {
        Player player = players.get(command.drawnBy());
        player.draw(command.numberOfCards(), stateBasedActions::checkLibraryOverdrawn);
    }

    private class StateBasedActions {

        public void checkLibraryOverdrawn(PlayerId playerId, CardsDrawn cardsDrawn) {
            if (!cardsDrawn.libraryOverdrawn()) {
                return;
            }

            getPlayer(playerId).lost(stateBasedActions::playerLost);
        }

        private void playerLost(PlayerLost playerLost) {
            List<Player> remainingPlayers = players.remainingPlayers();

            if (remainingPlayers.size() == 1) {
                remainingPlayers.getFirst().win(stateBasedActions::playerWon);
            }
        }

        private void playerWon(PlayerWon playerWon) {
            gameOver();
        }
    }

    public interface Lookup {

        Game lookup(GameId id);

        void save(Game game);

    }

}

package com.example.mtg.model;

import com.example.mtg.use_case.cast_spell.CastSpell;
import com.example.mtg.use_case.discard.DiscardCard;
import com.example.mtg.use_case.draw.CardsDrawn;
import com.example.mtg.use_case.draw.DrawCards;
import com.example.mtg.use_case.player_lost.PlayerLost;
import com.example.mtg.use_case.player_won.PlayerWon;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.List;

@Builder
@Accessors(fluent = true)
public class Game {

    @Getter
    private final GameId id;
    @Getter
    private final Players players;
    @Getter
    @Builder.Default
    private final Stack stack = Stack.empty();
    @Builder.Default
    private Priority priority = Priority.none();

    private final StateBasedActions stateBasedActions = new StateBasedActions();

    @Getter
    private boolean isOver;

    public boolean hasId(GameId id) {
        return this.id.equals(id);
    }

    public void gameOver() {
        isOver = true;
    }

    public Hand handOf(PlayerId playerId) {
        return getPlayer(playerId).hand();
    }

    public Graveyard graveyardOf(PlayerId playerId) {
        return getPlayer(playerId).graveyard();
    }

    public Library libraryOf(PlayerId playerId) {
        return getPlayer(playerId).library();
    }

    public Exile exileOf(PlayerId playerId) {
        return getPlayer(playerId).exile();
    }

    private Player getPlayer(PlayerId playerId) {
        return players.get(playerId);
    }

    public void givesPriorityTo(PlayerId playerId) {
        this.priority = Priority.forPlayer(playerId);
    }

    public void accept(DrawCards command) {
        Player player = players.get(command.drawnBy());
        player.draw(command.numberOfCards(), stateBasedActions::checkLibraryOverdrawn);
    }

    public void accept(DiscardCard command) {
        Player player = players.get(command.targetPlayer());
        player.discard(command.chosenCard(), (event) -> {});
    }

    public void accept(CastSpell command) {
        PlayerId castedBy = command.castedBy();

        if (!priority.isActive(castedBy)) {
            throw new IllegalStateException("Priority is not given to player '%s'".formatted(castedBy.value()));
        }

        Player player = players.get(castedBy);
        player.castSpell(command.cardId(), stack, (event) -> {});
    }

    private class StateBasedActions {

        public void checkLibraryOverdrawn(CardsDrawn cardsDrawn) {
            if (!cardsDrawn.libraryOverdrawn()) {
                return;
            }

            getPlayer(cardsDrawn.drawnBy()).lost(stateBasedActions::playerLost);
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

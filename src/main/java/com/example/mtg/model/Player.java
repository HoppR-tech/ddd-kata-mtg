package com.example.mtg.model;

import com.example.mtg.use_case.discard.CardDiscarded;
import com.example.mtg.use_case.draw.CardsDrawn;
import com.example.mtg.use_case.player_lost.PlayerLost;
import com.example.mtg.use_case.player_won.PlayerWon;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.function.Consumer;

@Builder
@Accessors(fluent = true)
public class Player {

    @Getter
    private final PlayerId id;
    @Getter
    @Builder.Default
    private final Library library = Library.empty();
    @Getter
    @Builder.Default
    private final Hand hand = Hand.empty();
    @Getter
    @Builder.Default
    private final Graveyard graveyard = Graveyard.empty();
    private boolean hasWon;
    private boolean hasLost;

    public boolean hasWon() {
        return hasWon;
    }

    public boolean hasLost() {
        return hasLost;
    }

    public void draw(Number numberOfCards, Consumer<CardsDrawn> output) {
        library.draw(numberOfCards, (cards, overdrawn) -> {
            hand.add(cards);

            output.accept(CardsDrawn.builder()
                    .drawnBy(id)
                    .cards(cards)
                    .libraryOverdrawn(overdrawn)
                    .build());
        });
    }

    public void discard(CardId cardId, Consumer<CardDiscarded> output) {
        hand.discard(cardId, graveyard::add);

        output.accept(CardDiscarded.builder()
                .owner(id)
                .cardId(cardId)
                .build());
    }

    public void lost(Consumer<PlayerLost> output) {
        this.hasLost = true;

        PlayerLost event = PlayerLost.builder()
                .playerId(id)
                .build();

        output.accept(event);
    }

    public void win(Consumer<PlayerWon> output) {
        this.hasWon = true;

        PlayerWon event = PlayerWon.builder()
                .playerId(id)
                .build();

        output.accept(event);
    }
}

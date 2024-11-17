package com.example.mtg.model;

import com.example.mtg.use_case.draw.CardsDrawn;
import com.example.mtg.use_case.player_lost.PlayerLost;
import com.example.mtg.use_case.player_won.PlayerWon;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Accessors(fluent = true)
public class Player {

    @Getter
    private final PlayerId id;
    private final Library library;
    private final Hand hand;
    private boolean hasWon;
    private boolean hasLost;

    @Builder
    private Player(PlayerId id, Library library, Hand hand) {
        this.id = id;
        this.library = library;
        this.hand = hand;
    }

    public boolean hasWon() {
        return hasWon;
    }

    public boolean hasLost() {
        return hasLost;
    }

    public void draw(Number numberOfCards, BiConsumer<PlayerId, CardsDrawn> output) {
        library.draw(numberOfCards, cardsDrawn -> {
            hand.add(cardsDrawn.cards());
            output.accept(id, cardsDrawn);
        });
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

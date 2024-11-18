package com.example.mtg.model;

import com.example.mtg.use_case.cast_spell.SpellCasted;
import com.example.mtg.use_case.discard.CardDiscarded;
import com.example.mtg.use_case.draw.CardsDrawn;
import com.example.mtg.use_case.player_lost.PlayerLost;
import com.example.mtg.use_case.player_won.PlayerWon;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Stream;

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
    @Getter
    @Builder.Default
    private final Exile exile = Exile.empty();
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
        hand.remove(cardId, graveyard::add);

        output.accept(CardDiscarded.builder()
                .owner(id)
                .cardId(cardId)
                .build());
    }

    public void castSpell(CardId cardId, Stack stack, Consumer<SpellCasted> output) {
        castSpell(cardId, (card, zone) -> {
            stack.add(card);

            output.accept(SpellCasted.builder()
                    .cardId(cardId)
                    .zone(zone)
                    .castedBy(id)
                    .build());
        });
    }

    private void castSpell(CardId cardId, BiConsumer<Card, Zone.Name> output) {
        Stream.of(hand, graveyard, exile)
                .forEach(zone -> zone.remove(
                        cardId,
                        card -> output.accept(card, zone.name())));
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

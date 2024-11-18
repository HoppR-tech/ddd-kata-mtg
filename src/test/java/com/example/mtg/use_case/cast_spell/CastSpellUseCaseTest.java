package com.example.mtg.use_case.cast_spell;

import com.example.mtg.fixtures.MockGameStates;
import com.example.mtg.model.CardId;
import com.example.mtg.model.Exile;
import com.example.mtg.model.Game;
import com.example.mtg.model.Graveyard;
import com.example.mtg.model.Hand;
import com.example.mtg.model.Library;
import com.example.mtg.model.Player;
import com.example.mtg.model.Players;
import com.example.mtg.model.Priority;
import org.junit.jupiter.api.Test;

import static com.example.mtg.fixtures.CardFixtures.DAUTHI_VOIDWALKER;
import static com.example.mtg.fixtures.CardFixtures.DEMONIC_TUTOR;
import static com.example.mtg.fixtures.CardFixtures.DIABOLIC_TUTOR;
import static com.example.mtg.fixtures.CardFixtures.PLAIN;
import static com.example.mtg.fixtures.CardFixtures.SWAMP;
import static com.example.mtg.fixtures.CardFixtures.card;
import static com.example.mtg.fixtures.GameFixtures.GAME_ID;
import static com.example.mtg.fixtures.GameFixtures.JON_FINKEL;
import static com.example.mtg.fixtures.GameFixtures.RICHARD_GARFIELD;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class CastSpellUseCaseTest {

    private static final CardId CARD_ID_IN_HAND = CardId.of("CHOSEN_CARD_FROM_HAND");
    private static final CardId CARD_ID_IN_GRAVEYARD = CardId.of("CHOSEN_CARD_FROM_GRAVEYARD");
    private static final CardId CARD_ID_IN_EXILE = CardId.of("CHOSEN_CARD_FROM_EXILE");

    Player playerOne = Player.builder()
            .id(RICHARD_GARFIELD)
            .library(Library.empty())
            .hand(Hand.composedOf(
                    card(CARD_ID_IN_HAND, DIABOLIC_TUTOR),
                    card(PLAIN),
                    card(SWAMP),
                    card(SWAMP)))
            .graveyard(Graveyard.composedOf(
                    card(CARD_ID_IN_GRAVEYARD, DEMONIC_TUTOR)
            ))
            .exile(Exile.composedOf(
                    card(CARD_ID_IN_EXILE, DAUTHI_VOIDWALKER)
            ))
            .build();

    Player playerTwo = Player.builder()
            .id(JON_FINKEL)
            .library(Library.composedOf())
            .hand(Hand.empty())
            .build();

    Game game = Game.builder()
            .id(GAME_ID)
            .priority(Priority.forPlayer(RICHARD_GARFIELD))
            .players(Players.from(playerOne, playerTwo))
            .build();

    MockGameStates games = MockGameStates.init(game);

    CastSpellUseCase useCase = new CastSpellUseCase(games);

    @Test
    void cast_spell_from_hand() {
        useCase.accept(CastSpell.builder()
                .gameId(GAME_ID)
                .castedBy(RICHARD_GARFIELD)
                .cardId(CARD_ID_IN_HAND)
                .build());

        games.assertions()
                .handOf(RICHARD_GARFIELD)
                .hasSize(3)
                .doesNotContain(DIABOLIC_TUTOR);

        games.assertions()
                .stack()
                .latestIs(DIABOLIC_TUTOR);

        games.assertions()
                .gameHasBeenSaved();
    }

    @Test
    void cast_spell_from_graveyard() {
        useCase.accept(CastSpell.builder()
                .gameId(GAME_ID)
                .castedBy(RICHARD_GARFIELD)
                .cardId(CARD_ID_IN_GRAVEYARD)
                .build());

        games.assertions()
                .graveyardOf(RICHARD_GARFIELD)
                .hasSize(0)
                .doesNotContain(DEMONIC_TUTOR);

        games.assertions()
                .stack()
                .latestIs(DEMONIC_TUTOR);

        games.assertions()
                .gameHasBeenSaved();
    }

    @Test
    void cast_spell_from_exile() {
        useCase.accept(CastSpell.builder()
                .gameId(GAME_ID)
                .castedBy(RICHARD_GARFIELD)
                .cardId(CARD_ID_IN_EXILE)
                .build());

        games.assertions()
                .exileOf(RICHARD_GARFIELD)
                .hasSize(0)
                .doesNotContain(DAUTHI_VOIDWALKER);

        games.assertions()
                .stack()
                .latestIs(DAUTHI_VOIDWALKER);

        games.assertions()
                .gameHasBeenSaved();
    }

    @Test
    void prevent_from_casting_a_spell_without_priority() {
        game.givesPriorityTo(JON_FINKEL);

        CastSpell command = CastSpell.builder()
                .gameId(GAME_ID)
                .castedBy(RICHARD_GARFIELD)
                .cardId(CARD_ID_IN_EXILE)
                .build();

        assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(() -> useCase.accept(command))
                .withMessage("Priority is not given to player 'Richard Garfield'");

        games.assertions()
                .gameHasNotBeenSaved();
    }

}
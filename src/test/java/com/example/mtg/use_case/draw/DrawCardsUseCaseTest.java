package com.example.mtg.use_case.draw;

import com.example.mtg.fixtures.MockGameStates;
import com.example.mtg.model.Game;
import com.example.mtg.model.Hand;
import com.example.mtg.model.Library;
import com.example.mtg.model.Number;
import com.example.mtg.model.Player;
import com.example.mtg.model.Players;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.example.mtg.fixtures.CardFixtures.DIABOLIC_TUTOR;
import static com.example.mtg.fixtures.CardFixtures.PLAIN;
import static com.example.mtg.fixtures.CardFixtures.SWAMP;
import static com.example.mtg.fixtures.CardFixtures.card;
import static com.example.mtg.fixtures.GameFixtures.GAME_ID;
import static com.example.mtg.fixtures.GameFixtures.JON_FINKEL;
import static com.example.mtg.fixtures.GameFixtures.ONE;
import static com.example.mtg.fixtures.GameFixtures.RICHARD_GARFIELD;

public class DrawCardsUseCaseTest {

    Player playerOne = Player.builder()
            .id(RICHARD_GARFIELD)
            .library(Library.composedOf(
                    card(DIABOLIC_TUTOR),
                    card(PLAIN),
                    card(SWAMP),
                    card(SWAMP)))
            .hand(Hand.empty())
            .build();

    Player playerTwo = Player.builder()
            .id(JON_FINKEL)
            .library(Library.composedOf())
            .hand(Hand.empty())
            .build();

    Game game = Game.builder()
            .id(GAME_ID)
            .players(Players.from(playerOne, playerTwo))
            .build();

    MockGameStates games = MockGameStates.init(game);

    DrawCardsUseCase useCase = new DrawCardsUseCase(games);

    @Test
    void draw_cards_from_the_top_of_the_library() {
        useCase.accept(DrawCards.builder()
                .gameId(GAME_ID)
                .drawnBy(RICHARD_GARFIELD)
                .numberOfCards(ONE)
                .build());

        games.assertions()
                .libraryOf(RICHARD_GARFIELD)
                .hasSize(3)
                .firstIs(PLAIN)
                .doesNotContain(DIABOLIC_TUTOR);

        games.assertions()
                .handOf(RICHARD_GARFIELD)
                .hasSize(1)
                .doesContain(DIABOLIC_TUTOR);

        games.assertions()
                .gameHasBeenSaved();
    }

    @DisplayName("""
            104.3c If a player is required to draw more cards than are left in his or her library,
            he or she draws the remaining cards, and then loses the game the next time a player would receive priority.
            """)
    @Test
    void cannot_draw_enough_cards_because_library_is_empty() {
        useCase.accept(DrawCards.builder()
                .gameId(GAME_ID)
                .drawnBy(RICHARD_GARFIELD)
                .numberOfCards(Number.of(6))
                .build());

        games.assertions()
                .libraryOf(RICHARD_GARFIELD)
                .isEmpty();

        games.assertions()
                .handOf(RICHARD_GARFIELD)
                .hasSize(4);

        games.assertions()
                .playerLost(RICHARD_GARFIELD)
                .playerWon(JON_FINKEL)
                .gameIsOver()
                .gameHasBeenSaved();
    }

}

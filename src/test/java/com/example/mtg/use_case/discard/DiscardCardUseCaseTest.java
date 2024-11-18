package com.example.mtg.use_case.discard;

import com.example.mtg.fixtures.MockGameStates;
import com.example.mtg.model.CardId;
import com.example.mtg.model.Game;
import com.example.mtg.model.Graveyard;
import com.example.mtg.model.Hand;
import com.example.mtg.model.Library;
import com.example.mtg.model.Player;
import com.example.mtg.model.Players;
import org.junit.jupiter.api.Test;

import static com.example.mtg.fixtures.CardFixtures.DIABOLIC_TUTOR;
import static com.example.mtg.fixtures.CardFixtures.PLAIN;
import static com.example.mtg.fixtures.CardFixtures.SWAMP;
import static com.example.mtg.fixtures.CardFixtures.card;
import static com.example.mtg.fixtures.GameFixtures.GAME_ID;
import static com.example.mtg.fixtures.GameFixtures.JON_FINKEL;
import static com.example.mtg.fixtures.GameFixtures.RICHARD_GARFIELD;

class DiscardCardUseCaseTest {

    static final CardId CARD_ID = CardId.of("CHOSEN_CARD");

    Player playerOne = Player.builder()
            .id(RICHARD_GARFIELD)
            .library(Library.composedOf())
            .hand(Hand.composedOf(
                    card(DIABOLIC_TUTOR),
                    card(PLAIN),
                    card(CARD_ID, SWAMP),
                    card(SWAMP)))
            .graveyard(Graveyard.empty())
            .build();

    Player playerTwo = Player.builder()
            .id(JON_FINKEL)
            .build();

    Game game = Game.builder()
            .id(GAME_ID)
            .players(Players.from(playerOne, playerTwo))
            .build();

    MockGameStates games = MockGameStates.init(game);

    DiscardCardUseCase useCase = new DiscardCardUseCase(games);

    @Test
    void discard_a_card_from_player_hand() {
        useCase.accept(DiscardCard.builder()
                .gameId(GAME_ID)
                .targetPlayer(RICHARD_GARFIELD)
                .chosenCard(CARD_ID)
                .build());

        games.assertions()
                .handOf(RICHARD_GARFIELD)
                .hasSize(3)
                .containsOnlyOnce(SWAMP);

        games.assertions()
                .graveyardOf(RICHARD_GARFIELD)
                .hasSize(1)
                .doesContain(SWAMP);

        games.assertions()
                .gameHasBeenSaved();
    }

}
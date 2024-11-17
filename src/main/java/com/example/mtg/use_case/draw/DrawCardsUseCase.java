package com.example.mtg.use_case.draw;

import com.example.mtg.model.Game;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DrawCardsUseCase {

    private final Game.Lookup games;

    public void accept(DrawCards command) {
        Game game = games.lookup(command.gameId());
        game.accept(command);
        games.save(game);
    }

}

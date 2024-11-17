package com.example.mtg.use_case.discard;

import com.example.mtg.model.Game;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DiscardCardUseCase {

    private final Game.Lookup games;

    public void accept(DiscardCard command) {
        Game game = games.lookup(command.gameId());
        game.accept(command);
        games.save(game);
    }

}

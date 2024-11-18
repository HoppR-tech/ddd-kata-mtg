package com.example.mtg.use_case.cast_spell;

import com.example.mtg.model.Game;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CastSpellUseCase {

    private final Game.Lookup games;

    public void accept(CastSpell command) {
        Game game = games.lookup(command.gameId());
        game.accept(command);
        games.save(game);
    }

}

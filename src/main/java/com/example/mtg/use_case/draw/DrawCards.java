package com.example.mtg.use_case.draw;

import com.example.mtg.model.GameId;
import com.example.mtg.model.PlayerId;
import com.example.mtg.model.Number;
import lombok.Builder;

@Builder
public record DrawCards(
        GameId gameId,
        PlayerId drawnBy,
        Number numberOfCards
) {
}

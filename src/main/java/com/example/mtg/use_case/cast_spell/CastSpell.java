package com.example.mtg.use_case.cast_spell;

import com.example.mtg.model.CardId;
import com.example.mtg.model.GameId;
import com.example.mtg.model.PlayerId;
import lombok.Builder;

@Builder
public record CastSpell(GameId gameId, CardId cardId, PlayerId castedBy) {
}

package com.example.mtg.use_case.discard;

import com.example.mtg.model.Card;
import com.example.mtg.model.GameId;
import com.example.mtg.model.PlayerId;
import lombok.Builder;

@Builder
public record DiscardCard(GameId gameId, PlayerId targetPlayer, Card chosenCard) {
}

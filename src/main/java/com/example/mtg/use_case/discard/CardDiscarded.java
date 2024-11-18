package com.example.mtg.use_case.discard;

import com.example.mtg.model.CardId;
import com.example.mtg.model.PlayerId;
import lombok.Builder;

@Builder
public record CardDiscarded(CardId cardId, PlayerId owner) {
}

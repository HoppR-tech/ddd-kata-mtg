package com.example.mtg.use_case.draw;

import com.example.mtg.model.Card;
import com.example.mtg.model.PlayerId;
import lombok.Builder;
import lombok.Singular;

import java.util.List;

@Builder
public record CardsDrawn(@Singular List<Card> cards, PlayerId drawnBy, boolean libraryOverdrawn) {
}

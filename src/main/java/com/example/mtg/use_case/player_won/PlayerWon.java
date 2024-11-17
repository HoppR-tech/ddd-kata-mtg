package com.example.mtg.use_case.player_won;

import com.example.mtg.model.PlayerId;
import lombok.Builder;

@Builder
public record PlayerWon(PlayerId playerId) {
}

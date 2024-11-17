package com.example.mtg.use_case.player_lost;

import com.example.mtg.model.PlayerId;
import lombok.Builder;

@Builder
public record PlayerLost(PlayerId playerId) {
}

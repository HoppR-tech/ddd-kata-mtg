package com.example.mtg.model;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Players {

    private final Map<PlayerId, Player> playersByIds;

    private Players(List<Player> players) {
        if (players.size() < 2) {
            throw new IllegalArgumentException("A game must be played by at least two players");
        }

        this.playersByIds = players.stream()
                .collect(Collectors.toMap(Player::id, Function.identity(), (a, b) -> a));
    }

    public List<Player> remainingPlayers() {
        return playersByIds.values().stream()
                .filter(Predicate.not(Player::hasLost))
                .toList();
    }

    public Player get(PlayerId playerId) {
        return playersByIds.get(playerId);
    }

    public static Players from(List<Player> players) {
        return new Players(players);
    }

    public static Players from(Player... players) {
        return from(Arrays.asList(players));
    }

}

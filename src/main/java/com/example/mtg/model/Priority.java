package com.example.mtg.model;

public sealed interface Priority {

    Priority NONE = new None();

    boolean isActive(PlayerId playerId);

    static Priority none() {
        return NONE;
    }

    static Priority forPlayer(PlayerId playerId) {
        return new ForPlayer(playerId);
    }

    record None() implements Priority {
        @Override
        public boolean isActive(PlayerId playerId) {
            return false;
        }
    }

    record ForPlayer(PlayerId playerId) implements Priority {
        @Override
        public boolean isActive(PlayerId playerId) {
            return this.playerId.equals(playerId);
        }
    }

}

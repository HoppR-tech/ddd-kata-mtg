package com.example.mtg.assertions;

import com.example.mtg.model.Card;
import com.example.mtg.model.CardName;
import com.example.mtg.model.Stack;
import lombok.RequiredArgsConstructor;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class StackAssertions {

    private final Stack stack;

    public StackAssertions latestIs(CardName cardName) {
        assertThat(stack.cards())
                .map(Card::name)
                .first()
                .isEqualTo(cardName);
        return this;
    }

    public static StackAssertions assertThatStack(Stack stack) {
        return new StackAssertions(stack);
    }

}

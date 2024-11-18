package com.example.mtg.use_case.cast_spell;

import com.example.mtg.model.CardId;
import com.example.mtg.model.PlayerId;
import com.example.mtg.model.Zone;
import lombok.Builder;

@Builder
public record SpellCasted(CardId cardId, Zone.Name zone, PlayerId castedBy) {

}

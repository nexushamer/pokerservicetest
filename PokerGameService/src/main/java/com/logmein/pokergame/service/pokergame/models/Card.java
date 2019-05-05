package com.logmein.pokergame.service.pokergame.models;

import com.logmein.pokergame.service.pokergame.enums.CardType;
import com.logmein.pokergame.service.pokergame.enums.Cards;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Card {
	private Cards value;
	private CardType type;
	
	@Override
	public String toString() {
		return "Card [value=" + value.name() + ", type=" + type.name() + "]";
	}
}

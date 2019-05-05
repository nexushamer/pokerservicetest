package com.logmein.pokergame.service.pokergame.enums;

import lombok.Getter;

@Getter
public enum CardType {
	HEARTS(1),
	SPADES(2),
	CLUBS(3),
	DIAMONS(4);
	
	private final int value;
	
	private CardType(int value) {
		this.value = value;
	}
	
	public static CardType fromValue(int value) {
		for (CardType type : CardType.values()) {
			if( type.value == value )
				return type;
		}
		
		return null;
	}
}

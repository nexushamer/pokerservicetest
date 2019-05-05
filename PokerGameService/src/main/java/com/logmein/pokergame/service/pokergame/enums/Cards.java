package com.logmein.pokergame.service.pokergame.enums;

public enum Cards {
	ACE(1),
	TWO(2),
	THREE(3),
	FOUR(4),
	FIVE(5),
	SIX(6),
	SEVEN(7),
	EIGHT(8),
	NINE(9),
	TEN(10),
	JACK(11),
	QUEEN(12),
	KING(13);
	
	private final int value;
	
	private Cards(int value) {
		this.value = value;
	}
	
	public int value() {
		return value;
	}
	
	public static Cards fromValue(int value) {
		for (Cards type : Cards.values()) {
			if( type.value == value )
				return type;
		}
		
		return null;
	}
	
}

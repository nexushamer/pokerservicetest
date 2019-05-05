package com.logmein.pokergame.service.pokergame.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Player {
	private String email;
	private List<Card> hand;
	
	public Player() {
		hand = new ArrayList<Card>();
	}
	
	public void addCard(Card card) {
		hand.add(card);
	}
	
	public int getSizeOfHand() {
		if(hand.isEmpty())
			return 0;
		else
			return hand.stream().filter(Objects::nonNull).mapToInt((card) -> card.getValue().value()).sum();
	}
}

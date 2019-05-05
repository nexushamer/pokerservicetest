package com.logmein.pokergame.service.pokergame.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Deck {
	private UUID id;
	private List<Card> cards;
	
	public Deck() {
		this.cards = new ArrayList<Card>();
		id = UUID.randomUUID();
	}
	
	public Deck(List<Card> cards) {
		this.cards = cards;
		this.id = UUID.randomUUID();
	}
	
	public void addCardToPosition(int position, Card card) {
		this.cards.add(position, card);
	}
	
	public void setCardToPosition(int position, Card card) {
		this.cards.set(position, card);
	}
	
	public Card getCardByPosition(int position) {
		return this.cards.get(position);
	}
	
	public Card removeCardByPosition(int position) {
		Card card = this.cards.get(position);
		
		this.cards.remove(position);
		
		return card;
	}
	
	public int size() {
		return cards.size();
	}
	
	@Override
	public String toString() {
		return "Deck [cards=" + cards.toString() + "]";
	}
}

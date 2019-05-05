package com.logmein.pokergame.service.pokergame.components;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.logmein.pokergame.service.pokergame.models.Deck;

@Component
public class DeckRepository {
	private Map<String, Deck> deckMap;

	public DeckRepository() {
		deckMap = new HashMap<String, Deck>();
	}
	
	public Deck createDeck(Deck deck) {
		deckMap.put(deck.getId().toString(), deck);
		
		return deck;
	}
	
	public Deck getDeck(String deckId) {
		return deckMap.get(deckId);
	}
	
}

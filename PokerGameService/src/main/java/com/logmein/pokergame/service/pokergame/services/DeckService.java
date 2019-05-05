package com.logmein.pokergame.service.pokergame.services;

import java.util.Objects;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.logmein.pokergame.service.pokergame.components.DeckRepository;
import com.logmein.pokergame.service.pokergame.enums.CardType;
import com.logmein.pokergame.service.pokergame.enums.Cards;
import com.logmein.pokergame.service.pokergame.exceptions.RegisterDoesNotExistsException;
import com.logmein.pokergame.service.pokergame.models.Card;
import com.logmein.pokergame.service.pokergame.models.Deck;

@Service
public class DeckService {
	private final DeckRepository deckRepository;
	
	@Autowired
	public DeckService(DeckRepository deckRepository) {
		this.deckRepository = deckRepository;
	}
	
	public Deck create() {
		Deck deck = new Deck();
		
		initialize(deck);
		
		deckRepository.createDeck(deck);
		
		return deck;
	}
	
	private void initialize(Deck deck) {	
		int positionOfDeck = 0;
		for(int i = 1 ; i <= 4 ; i++) {
			final CardType type = CardType.fromValue(i);
			for(int j = 1; j <= 13 ; j++) {
				final Cards valueOfCard = Cards.fromValue(j);
				deck.addCardToPosition(positionOfDeck, new Card(valueOfCard, type));
				
				positionOfDeck++;
			}
		}
	}
	
	public Deck retrieveDeck(String deckId) {
		final Deck deck = deckRepository.getDeck(deckId);
		
		if(Objects.isNull(deck))
			throw new RegisterDoesNotExistsException();
		
		return deck;
	}
	
	public Card dealCard(Deck deck) {
		deck = shuffle(deck);
		
		final Card card = deck.getCardByPosition(0);
		deck.removeCardByPosition(0);
		
		return card;
	}
	
	public Deck shuffle(String deckId){
		final Deck deck = deckRepository.getDeck(deckId);
		if(Objects.isNull(deck))
			throw new RegisterDoesNotExistsException();
		
		return shuffle(deck);
	}
	
	public Deck shuffle(Deck deck) {
		final Random random = new Random();
		
		for(int i = deck.size() - 1; i > 0  ; i--) {
			int randomPosition = random.nextInt(i + 1);
			
			final Card card = deck.getCardByPosition(randomPosition);
			deck.setCardToPosition(randomPosition, deck.getCardByPosition(i));
			deck.setCardToPosition(i, card);
		}
		
		return deck;
	} 
	
}

package com.logmein.pokergame.service.pokergame.services;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.logmein.pokergame.service.pokergame.components.DeckRepository;
import com.logmein.pokergame.service.pokergame.models.Card;
import com.logmein.pokergame.service.pokergame.models.Deck;
import com.logmein.pokergame.service.pokergame.services.DeckService;

public class DeckServiceTest {
	private DeckRepository deckRepository;
	
	@Before
	public void setup() {
		deckRepository = Mockito.mock(DeckRepository.class);
	}
	
	@Test
	public void testIFTheDeckCreationIsDoneWell() {
		DeckService deckService = new DeckService(deckRepository);
		Deck deck = deckService.create();

		Assert.assertNotNull(deck);
	}

	@Test
	public void compareIfTheOrderOfTheDeckAfterCallTheShuffle() {
		final DeckService deckService = new DeckService(deckRepository);
		Deck deck = deckService.create();

		@SuppressWarnings("unchecked")
		Deck copyOfDeckBeforeShuffle = new Deck((List<Card>) ((ArrayList<Card>)deck.getCards()).clone());

		deck = deckService.shuffle(deck);

		boolean theOrderOFTheCardInsideTheDecksIsTheSame = thePositionOfCardsInSideTheDecksisTheSame(deck, copyOfDeckBeforeShuffle);

		Assert.assertFalse(theOrderOFTheCardInsideTheDecksIsTheSame);
	}
	
	private boolean thePositionOfCardsInSideTheDecksisTheSame(Deck deckWithoutShuffle,Deck deckWithShuffle) {
		
		int howManyCardAreInTheSamePosition = 0;
		for(int i = 0; i < deckWithoutShuffle.size() ; i++) {
			Card cardWithOutShuffle = deckWithoutShuffle.getCardByPosition(i);
			Card cardWithShuffle = deckWithShuffle.getCardByPosition(i);
			
			if((cardWithOutShuffle.getType().equals(cardWithShuffle.getType())) && cardWithOutShuffle.getValue().equals(cardWithShuffle.getValue()))
				howManyCardAreInTheSamePosition+=1;
		}
		
		if(52 == howManyCardAreInTheSamePosition)
			return true;
		else
			return false;
	}
}

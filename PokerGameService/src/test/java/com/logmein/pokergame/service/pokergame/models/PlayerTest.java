package com.logmein.pokergame.service.pokergame.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

import com.logmein.pokergame.service.pokergame.enums.CardType;
import com.logmein.pokergame.service.pokergame.enums.Cards;
import com.logmein.pokergame.service.pokergame.models.Card;
import com.logmein.pokergame.service.pokergame.models.Player;

public class PlayerTest {
	
	@Test
	public void testTheGetSizeOfHand() {
		Player player = new Player();
		
		player.setEmail("sergio.mendieta@gmail");
		player.setHand(createHand());
		
		Assert.assertTrue((!player.getHand().isEmpty())? (player.getSizeOfHand() > 0) ? true : false : false);
	}
	
	@Test
	public void testTheValueOfTheHandWithSpecificHands() {
		Player playerA = new Player();
		
		playerA.setEmail("sergio.mendieta@gmail");
		playerA.setHand(createHand(CardType.CLUBS, Cards.TEN, Cards.KING));
		
		Player playerB = new Player();
		
		playerB.setEmail("lis.murcia@gmail");
		playerB.setHand(createHand(CardType.CLUBS, Cards.SEVEN, Cards.QUEEN));
		
		Assert.assertEquals(23, playerA.getSizeOfHand());
		Assert.assertEquals(19, playerB.getSizeOfHand());
	}
	
	private List<Card> createHand() {
		List<Card> cards = new ArrayList<Card>();
		
		Random random = new Random();
		
		for(int i = 1 ; i <= 7 ; i++) {
			int randomNumber = random.nextInt(13) + 1;
			cards.add(new Card(Cards.fromValue(randomNumber), CardType.SPADES));
		}
		
		return cards;
	}
	
	private List<Card> createHand(CardType type, Cards... cards) {
		List<Card> listOfCards = new ArrayList<Card>();
		
		for(Cards cardsValue : cards) {
			listOfCards.add(new Card(cardsValue, type));
		}
		
		return listOfCards;
	}
}

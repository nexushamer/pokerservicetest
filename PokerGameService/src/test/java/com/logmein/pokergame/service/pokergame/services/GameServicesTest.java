package com.logmein.pokergame.service.pokergame.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.logmein.pokergame.service.pokergame.components.DeckRepository;
import com.logmein.pokergame.service.pokergame.components.GameRepository;
import com.logmein.pokergame.service.pokergame.components.PlayerRepository;
import com.logmein.pokergame.service.pokergame.enums.CardType;
import com.logmein.pokergame.service.pokergame.enums.Cards;
import com.logmein.pokergame.service.pokergame.models.Card;
import com.logmein.pokergame.service.pokergame.models.Deck;
import com.logmein.pokergame.service.pokergame.models.Game;
import com.logmein.pokergame.service.pokergame.models.Player;
import com.logmein.pokergame.service.pokergame.services.DeckService;
import com.logmein.pokergame.service.pokergame.services.GameService;

public class GameServicesTest {
	
	private PlayerRepository playerRepository;
	private DeckService deckService;
	private GameRepository repository;
	private DeckRepository deckRepository;
	
	@Before
	public void setup() {
		playerRepository = Mockito.mock(PlayerRepository.class);
		repository = Mockito.mock(GameRepository.class);
		deckRepository = Mockito.mock(DeckRepository.class);
		
		Mockito.when(deckRepository.createDeck(Mockito.any(Deck.class))).thenReturn(createDeck());
		
		deckService = new DeckService(deckRepository);
	}
	
	@Test
	public void testTheListUserFilteredByCardsValue() {
		DeckRepository deckRepository = new DeckRepository();

		DeckService deckServiceTestUser = new DeckService(deckRepository);
		GameRepository gameRepository = Mockito.mock(GameRepository.class);
		final PlayerServices playerService = new PlayerServices(playerRepository);
		GameService gameService = new GameService(gameRepository, deckServiceTestUser , playerService);
		final UUID id = UUID.randomUUID();
		
		Mockito.when(gameRepository.get(Mockito.any(UUID.class))).thenReturn(createGame(id, deckServiceTestUser));
		
		List<Player> players = gameService.listPlayersFilteredByCardsValue(id);
		
		Assert.assertEquals(players.get(0).getEmail(), "samantha@gmail.com");
	}
	
	@Test
	public void testHowManyCardsThereAreBySuit() {
		DeckRepository deckRepository = new DeckRepository();

		DeckService deckServiceTestUser = new DeckService(deckRepository);
		GameRepository gameRepository = Mockito.mock(GameRepository.class);
		final PlayerServices playerService = new PlayerServices(playerRepository);
		GameService gameService = new GameService(gameRepository, deckServiceTestUser, playerService);
		final UUID id = UUID.randomUUID();
		
		Mockito.when(gameRepository.get(Mockito.any(UUID.class))).thenReturn(createGame(id, deckServiceTestUser));
		
		Map<String,Long> cardsBySuit = gameService.countCardsGroupItBySuit(id).get(0);
		
		Assert.assertNotNull(cardsBySuit);
	} 
	
	@Test
	public void testHowManyCardsThereAreBySuitWithSpecificDeck() {
		final PlayerServices playerService = new PlayerServices(playerRepository);
		GameService gameService = new GameService(repository, deckService, playerService);
		final UUID id = UUID.randomUUID();
		
		Game game = new Game(id);
		game.addDeck(createDeck());
		
		Mockito.when(deckRepository.createDeck(Mockito.any(Deck.class))).thenReturn(createDeck());
		Mockito.when(repository.get(Mockito.any(UUID.class))).thenReturn(game);
		
		Map<String,Long> cardsBySuit = gameService.countCardsGroupItBySuit(id).get(0);
		
		Assert.assertNotNull(cardsBySuit);
		Assert.assertEquals(2, cardsBySuit.get(CardType.CLUBS.name()).longValue());
		Assert.assertEquals(1, cardsBySuit.get(CardType.DIAMONS.name()).longValue());
		Assert.assertEquals(1, cardsBySuit.get(CardType.SPADES.name()).longValue());
	}
	
	@Test
	public void testTheCountHowManyCardsExistsAndGroupItBySuit() {
		DeckRepository deckRepository = new DeckRepository();

		DeckService deckServiceTestUser = new DeckService(deckRepository);
		GameRepository gameRepository = Mockito.mock(GameRepository.class);
		final PlayerServices playerService = new PlayerServices(playerRepository);
		GameService gameService = new GameService(gameRepository, deckServiceTestUser, playerService);
		final UUID id = UUID.randomUUID();
		
		Mockito.when(gameRepository.get(Mockito.any(UUID.class))).thenReturn(createGame(id, deckServiceTestUser));
		
		Map<String,List<Card>> cardsCountedAndSortedBySuit = gameService.countHowManyCardsExistsAndGroupItBySuit(id);
		
		Assert.assertNotNull(cardsCountedAndSortedBySuit);
		Assert.assertEquals(4, cardsCountedAndSortedBySuit.size());
	}
	
	@Test
	public void testTheCountHowManyCardsExistsAndGroupItBySuitWithSpecificDeck() {
		final PlayerServices playerService = new PlayerServices(playerRepository);
		GameService gameService = new GameService(repository, deckService, playerService);
		final UUID id = UUID.randomUUID();
		
		Game game = new Game(id);
		game.addDeck(createDeck());
		
		Mockito.when(repository.get(Mockito.any(UUID.class))).thenReturn(game);
		
		Map<String,List<Card>> cardsBySuit = gameService.countHowManyCardsExistsAndGroupItBySuit(id);
		
		Assert.assertEquals(2, cardsBySuit.get(CardType.CLUBS.name()).size());
		Assert.assertEquals(1, cardsBySuit.get(CardType.DIAMONS.name()).size());
		Assert.assertEquals(1, cardsBySuit.get(CardType.SPADES.name()).size());
	}
	
	@Test
	public void testDealCard() {
		final PlayerServices playerService = new PlayerServices(playerRepository);
		GameService gameService = new GameService(repository, deckService, playerService);
		final UUID id = UUID.randomUUID();
		final String email = "pepe@gmail.com";
		
		Game game = new Game(id);
		
		game.addDeck(createDeck());
		
		final int originalSizeOfDeck = game.getDecks().get(0).getCards().size();
		
		Player player = new Player();
		player.setEmail("pepe@gmail.com");
		player.setHand(createHand(CardType.CLUBS, Cards.SEVEN, Cards.QUEEN));
		
		final int originalSizeOfHand = player.getHand().size();
		
		game.addPlayer(player);
		
		Mockito.when(repository.get(Mockito.any(UUID.class))).thenReturn(game);
		
		gameService.dealCards(email, id);
		
		Assert.assertEquals(originalSizeOfDeck - 1, game.getDecks().get(0).getCards().size());
		Assert.assertEquals(originalSizeOfHand + 1, game.getPlayer(email).getHand().size());
	}
	
	private Game createGame(UUID id, DeckService deckService) {
		Game game = new Game(id);
		
		game.addDeck(deckService.shuffle(deckService.create()));
		
		Player player = new Player();
		player.setEmail("pepe@gmail.com");
		player.setHand(createHand(CardType.CLUBS, Cards.SEVEN, Cards.QUEEN));
		
		game.addPlayer(player);
		
		Player player2 = new Player();
		player2.setEmail("luisa@gmail.com");
		player2.setHand(createHand(CardType.CLUBS, Cards.FOUR, Cards.ACE));
		
		game.addPlayer(player2);
		
		Player player3 = new Player();
		player3.setEmail("sergio@gmail.com");
		player3.setHand(createHand(CardType.CLUBS, Cards.TEN, Cards.KING));
		
		game.addPlayer(player3);
		
		Player player4 = new Player();
		player4.setEmail("samantha@gmail.com");
		player4.setHand(createHand(CardType.CLUBS, Cards.ACE, Cards.TWO));
		
		game.addPlayer(player4);
		
		return game;
	}
	
	private Deck createDeck() {
		Deck deck = new Deck();
		
		deck.addCardToPosition(0, new Card(Cards.ACE, CardType.CLUBS));
		deck.addCardToPosition(1, new Card(Cards.KING, CardType.CLUBS));
		deck.addCardToPosition(2, new Card(Cards.SIX, CardType.DIAMONS));
		deck.addCardToPosition(3, new Card(Cards.EIGHT, CardType.SPADES));
		
		return deck;
	}
	
	private List<Card> createHand(int howManyCards) {
		List<Card> cards = new ArrayList<Card>();
		
		Random random = new Random();
		
		for(int i = 1 ; i <= howManyCards ; i++) {
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

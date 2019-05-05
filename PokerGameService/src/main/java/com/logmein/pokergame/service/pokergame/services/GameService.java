package com.logmein.pokergame.service.pokergame.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.logmein.pokergame.service.pokergame.components.GameRepository;
import com.logmein.pokergame.service.pokergame.enums.CardType;
import com.logmein.pokergame.service.pokergame.exceptions.RegisterAlreadyExistsInsideTheGameException;
import com.logmein.pokergame.service.pokergame.exceptions.RegisterDoesNotExistsException;
import com.logmein.pokergame.service.pokergame.models.Card;
import com.logmein.pokergame.service.pokergame.models.Deck;
import com.logmein.pokergame.service.pokergame.models.Game;
import com.logmein.pokergame.service.pokergame.models.Player;

@Service
public class GameService {
	private final DeckService deckService;
	private final PlayerServices playerService;
	private final GameRepository repository;

	public GameService(GameRepository repository, DeckService deckService, PlayerServices playerService) {
		this.repository = repository;
		this.deckService = deckService;
		this.playerService = playerService;
	}

	public Game createGame() {
		final Game game = new Game(UUID.randomUUID());

		repository.add(game);

		return game;
	}

	public Game retrieveGameById(UUID id) {
		final Game gameSearched = repository.get(id);
		if (Objects.isNull(gameSearched))
			throw new RegisterDoesNotExistsException();

		return repository.get(id);
	}

	public Game deleteGame(UUID id) {
		final Game gameRemoved = repository.remove(id);
		if (Objects.isNull(gameRemoved))
			throw new RegisterDoesNotExistsException();

		return gameRemoved;
	}

	public Game addDeckToTheGame(String deckId, UUID gameId) {
		final Deck deck = deckService.retrieveDeck(deckId);
		
		return addDeckToTheGame(deck, gameId);
	}
	
	public Game addDeckToTheGame(Deck deck, UUID id) {
		Game game = retrieveGameById(id);
		
		if(checkIfADeckExistsInsideAGame(game,deck))
			throw new RegisterAlreadyExistsInsideTheGameException();
		
		game.addDeck(deck);
		
		return game;
	}
	
	public Game addPlayerToTheGame(String userId, UUID id) {
		final Player player =  playerService.retrievePlayer(userId);
		
		return addPlayerToTheGame(player, id);
	}
	
	public Game addPlayerToTheGame(Player player, UUID id) {
		Game game = retrieveGameById(id);
		
		if(checkIfAPlayerExistsInsideAGame(game, player))
			throw new RegisterAlreadyExistsInsideTheGameException();
		
		game.addPlayer(player);
		
		return game;
	}

	public Game removePlayer(String playerId, UUID id) {
		final Player player = playerService.retrievePlayer(playerId);
		Game game = retrieveGameById(id);
		
		if(!checkIfAPlayerExistsInsideAGame(game, player))
			throw new RegisterDoesNotExistsException();
		
		game = repository.get(id);
		game.removePlayer(player);
		
		return game;
	}

	public Game dealCards(String idUser, UUID id) {
		final Game game = retrieveGameById(id);

		for (Deck deck : game.getDecks()) {
			if (!deck.getCards().isEmpty()) {
				final Card card = deckService.dealCard(deck);
				game.getPlayer(idUser).addCard(card);
			}
		}
		
		return game;
	}

	public List<Player> listPlayersFilteredByCardsValue(UUID gameId) {
		List<Player> players = repository.get(gameId).getPlayers();

		players.sort((a, b) -> {
			if (a.getSizeOfHand() == b.getSizeOfHand())
				return 0;

			return (a.getSizeOfHand() > b.getSizeOfHand()) ? 1 : -1;
		});

		return players;
	}

	public List<Map<String, Long>> countCardsGroupItBySuit(UUID gameId) {
		final Game game = repository.get(gameId);

		final List<Map<String, Long>> decksGroup = game.getDecks().stream().map((deck) -> {
			return retrieveCardsOfDeckGroupItBySuit(deck);
		}).collect(Collectors.toList());

		return decksGroup;
	}

	public Map<String, List<Card>> countHowManyCardsExistsAndGroupItBySuit(UUID gameId) {
		final Game game = repository.get(gameId);

		final Deck deck = game.getDecks().get(0);

		Map<String, List<Card>> sortedConsult = new HashMap<String, List<Card>>();

		for (int i = 1; i <= CardType.values().length; i++) {
			final CardType type = CardType.fromValue(i);
			List<Card> cards = new ArrayList<Card>();
			
			cards = deck.getCards()
					.stream()
					.filter((card) -> card.getType().equals(type))
					.sorted((a, b) -> {
				if (a.getValue().value() == b.getValue().value())
					return 0;

				return (a.getValue().value() > b.getValue().value()) ? 1 : -1;
			}).collect(Collectors.toList());

			sortedConsult.put(type.name(), cards);
		}

		return sortedConsult;
	}

	private Map<String, Long> retrieveCardsOfDeckGroupItBySuit(Deck deck) {
		return deck.getCards().stream().filter(Objects::nonNull).map((card) -> card.getType().name())
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
	}
	
	private boolean checkIfADeckExistsInsideAGame(Game currentGame, Deck deckToChech) {
		for(final Deck deck : currentGame.getDecks()) {
			if(deck.equals(deckToChech)) {
				return true;
			}
		}
		
		return false;
	}
	
	private boolean checkIfAPlayerExistsInsideAGame(Game currentGame, Player playerToCheck) {
		for(final Player player : currentGame.getPlayers()) {
			if(player.equals(playerToCheck)) {
				return true;
			}
		}
		
		return false;
	}

}

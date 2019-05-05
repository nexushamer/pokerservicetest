package com.logmein.pokergame.service.pokergame.services;

import com.logmein.pokergame.service.pokergame.components.PlayerRepository;
import com.logmein.pokergame.service.pokergame.exceptions.RegisterAlreadyExistsInsideTheGameException;
import com.logmein.pokergame.service.pokergame.exceptions.RegisterDoesNotExistsException;
import com.logmein.pokergame.service.pokergame.models.Card;
import com.logmein.pokergame.service.pokergame.models.Player;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

@Service
public class PlayerServices {
	private final PlayerRepository playerRepository;
	
	public PlayerServices(PlayerRepository playerRepository) {
		this.playerRepository = playerRepository;
	}
	
	public Player createPlayer(String email) {
		Player playerAlreadyExists = playerRepository.getPlayer(email);
		
		if(Objects.nonNull(playerAlreadyExists))
			throw new RegisterAlreadyExistsInsideTheGameException();
		
		Player player = new Player();
		player.setEmail(email);
		
		playerRepository.createPlayer(player);
		
		return player;
	}
	
	public Player retrievePlayer(String playerId) {
		Player player = playerRepository.getPlayer(playerId);
		
		if(Objects.isNull(player)) {
			throw new RegisterDoesNotExistsException();
		}
		
		return player;
	}
	
	public List<Card> listOfCardsFromPlayer(String playerId){
		final Player player = playerRepository.getPlayer(playerId);
		
		if(Objects.isNull(player)) {
			throw new RegisterDoesNotExistsException();
		}
		
		return player.getHand();
	}
}

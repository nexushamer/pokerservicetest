package com.logmein.pokergame.service.pokergame.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Game {
	private UUID id;
	private List<Deck> decks;
	private List<Player> players;
	
	public Game (UUID id){
		this.id = id;
		decks = new ArrayList<Deck>();
		players = new ArrayList<Player>();
	}
	
	public void addDeck(Deck deck) {
		decks.add(deck);
	}
	
	public void addPlayer(Player player) {
		players.add(player);
	}
	
	public Player getPlayer(String id) {
		for(Player player : players) {
			if(player.getEmail().equals(id))
				return player;
		}
		
		return null;
	}
	
	public Player removePlayer(Player player) {
		return (players.remove(player)) ? player : null;
	}
}

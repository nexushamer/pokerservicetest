package com.logmein.pokergame.service.pokergame.components;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.logmein.pokergame.service.pokergame.models.Player;

@Component
public class PlayerRepository {
	private Map<String, Player> playersMap;
	
	public PlayerRepository() {
		playersMap = new HashMap<String, Player>();
	}
	
	public Player createPlayer(Player player) {
		playersMap.put(player.getEmail(), player);
		
		return player;
	}
	
	public Player getPlayer(String playerId) {
		return playersMap.get(playerId);
	}
	
}

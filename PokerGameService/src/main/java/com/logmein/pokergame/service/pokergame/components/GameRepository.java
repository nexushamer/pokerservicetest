package com.logmein.pokergame.service.pokergame.components;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.logmein.pokergame.service.pokergame.models.Game;

@Component
public class GameRepository {
	private Map<UUID, Game> gamesMap;
	
	public GameRepository() {
		this.gamesMap = new HashMap<UUID, Game>();
	}
	
	public void add(Game game) {
		gamesMap.put(game.getId(), game);
	}
	
	public Game get(UUID uuid) {
		return gamesMap.get(uuid);
	}
	
	@SuppressWarnings("unlikely-arg-type")
	public Game remove(UUID id) {
		Game gameToDelete = gamesMap.get(id);
		
		gamesMap.remove(gameToDelete);
		
		return gameToDelete;
	}
}

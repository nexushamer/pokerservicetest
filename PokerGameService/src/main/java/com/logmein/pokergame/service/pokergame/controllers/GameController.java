package com.logmein.pokergame.service.pokergame.controllers;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.logmein.pokergame.service.pokergame.exceptions.RequestDataInvalidException;
import com.logmein.pokergame.service.pokergame.models.Card;
import com.logmein.pokergame.service.pokergame.models.Game;
import com.logmein.pokergame.service.pokergame.models.Player;
import com.logmein.pokergame.service.pokergame.services.GameService;

@Controller
@RequestMapping(path = "/game")
public class GameController {
	private final GameService gameService;
	
	@Autowired
	public GameController(GameService gameService) {
		this.gameService = gameService;
	}
	
	@PostMapping
	public @ResponseBody Game createGame() {
		return gameService.createGame();
	}
	
	@GetMapping
	public @ResponseBody Game retrieveGameById(String id) {
		return gameService.retrieveGameById(UUID.fromString(id));
	}
	
	@DeleteMapping
	public @ResponseBody Game deleteGame(@RequestParam String id) {
		if(!SimpleValidator.isUUIDValid(id))
			throw new RequestDataInvalidException();
			
		return gameService.deleteGame(UUID.fromString(id));
	}
	
	@PatchMapping
	@RequestMapping(path = "/{gameId}/addDeck")
	public @ResponseBody Game addDeckToTheGame(@PathVariable String gameId, @RequestParam String deckId) {
		if(!SimpleValidator.isUUIDValid(gameId) || !SimpleValidator.isUUIDValid(deckId))
			throw new RequestDataInvalidException();
		
		return gameService.addDeckToTheGame(deckId, UUID.fromString(gameId));
	}
	
	@PatchMapping
	@RequestMapping(path = "/{gameId}/addPlayer")
	public @ResponseBody Game addPlayerToTheGame(@PathVariable String gameId, @RequestParam String userId) {
		if(!SimpleValidator.isUUIDValid(gameId))
			throw new RequestDataInvalidException();
		
		return gameService.addPlayerToTheGame(userId, UUID.fromString(gameId));
	}
	
	@DeleteMapping
	@RequestMapping(path = "/{gameId}/removePlayer")
	public @ResponseBody Game removePlayerToTheGame(@PathVariable String gameId, @RequestParam String userId) {
		if(!SimpleValidator.isUUIDValid(gameId))
			throw new RequestDataInvalidException();
		
		return gameService.removePlayer(userId, UUID.fromString(gameId));
	}
	
	@PatchMapping
	@RequestMapping(path = "/{gameId}/dealCard")
	public @ResponseBody Game dealCardsFromDeckToPlayer(@PathVariable String gameId, @RequestParam String userId) {
		if(!SimpleValidator.isUUIDValid(gameId))
			throw new RequestDataInvalidException();
		
		return gameService.dealCards(userId, UUID.fromString(gameId));
	}
	
	@GetMapping
	@RequestMapping(path = "/{gameId}/listPlayerAndFilterByMajorHand")
	public @ResponseBody List<Player> listPlayerAndFilterByMajorHand(@PathVariable String gameId){
		if(!SimpleValidator.isUUIDValid(gameId))
			throw new RequestDataInvalidException();
		
		return gameService.listPlayersFilteredByCardsValue(UUID.fromString(gameId));
	}
	
	@GetMapping
	@RequestMapping(path = "/{gameId}/countCardsGroupItBySuit")
	public @ResponseBody List<Map<String, Long>> countCardsGroupItBySuit(@PathVariable String gameId){
		if(!SimpleValidator.isUUIDValid(gameId))
			throw new RequestDataInvalidException();
		
		return gameService.countCardsGroupItBySuit(UUID.fromString(gameId));
	}
	
	@GetMapping
	@RequestMapping(path = "/{gameId}/countHowManyCardsExistsAndGroupItBySuit")
	public @ResponseBody Map<String, List<Card>> countHowManyCardsExistsAndGroupItBySuit(@PathVariable String gameId){
		if(!SimpleValidator.isUUIDValid(gameId))
			throw new RequestDataInvalidException();
		
		return gameService.countHowManyCardsExistsAndGroupItBySuit(UUID.fromString(gameId));
	}
	
	static class SimpleValidator {
		public static boolean isUUIDValid(String uuidInStringFormat) {
			try {
				UUID.fromString(uuidInStringFormat);
				return true;
			}catch (Exception e) {
				return false;
			}
		}
	}
}
